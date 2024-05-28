package com.example.freeteachbe.Service;

import com.example.freeteachbe.DTO.BodyPayload.DocumentPostDTO;
import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.DocumentData;
import com.example.freeteachbe.Entity.DocumentPostEntity;
import com.example.freeteachbe.Entity.SubjectEntity;
import com.example.freeteachbe.Entity.TeacherEntity;
import com.example.freeteachbe.Entity.UserEntity;
import com.example.freeteachbe.Repository.DocumentRepository;
import com.example.freeteachbe.Repository.SubjectRepository;
import com.example.freeteachbe.Repository.TeacherRepository;
import com.example.freeteachbe.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    private TeacherEntity _getTeacherById(Long userId) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            Optional<TeacherEntity> teacherEntityOptional = teacherRepository.findByUser(userEntity);
            if (teacherEntityOptional.isPresent()) {
                return teacherEntityOptional.get();
            }
        }
        return null;
    }

    public Set<DocumentData> getMyDocumentPost(UserEntity user) {
        Optional<TeacherEntity> teacherEntityOptional = teacherRepository.findByUser(user);
        if (teacherEntityOptional.isPresent()) {
            TeacherEntity teacherEntity = teacherEntityOptional.get();
            return teacherEntity.getDocumentPosts().stream().map(documentPostEntity ->
                    new DocumentData(
                            documentPostEntity.getId(),
                            documentPostEntity.getDescription(),
                            documentPostEntity.getImageURL(),
                            documentPostEntity.getSubject().getSubjectName())).collect(Collectors.toSet());
        }
        return null;
    }

    public ResponseEntity<Message> createMyDocumentPost(UserEntity user, DocumentPostDTO documentPostDTO) {
        Optional<TeacherEntity> teacherEntityOptional = teacherRepository.findByUser(user);
        if (teacherEntityOptional.isPresent()) {
            TeacherEntity teacherEntity = teacherEntityOptional.get();
            Optional<SubjectEntity> subjectEntityOptional = subjectRepository.findById(documentPostDTO.getSubject_id());
            if (subjectEntityOptional.isPresent()) {
                DocumentPostEntity documentPost = DocumentPostEntity
                        .builder()
                        .description(documentPostDTO.getDescription())
                        .imageURL(documentPostDTO.getImg_url())
                        .teacher(teacherEntity)
                        .subject(subjectEntityOptional.get())
                        .build();
                documentRepository.save(documentPost);
                return ResponseEntity.ok(new Message("Tạo mới tài liệu thành công"));
            }
            return ResponseEntity.badRequest().body(new Message("Có môn học không tồn tại"));
        }
        return null;
    }

    public ResponseEntity<Message> editDocumentPost(UserEntity user, Long documentId, DocumentPostDTO documentPostDTO) {
        Optional<DocumentPostEntity> documentPostEntityOptional = documentRepository.findById(documentId);
        TeacherEntity teacherEntity = teacherRepository.findByUser(user).get();
        if (documentPostEntityOptional.isPresent()) {
            Set<DocumentPostEntity> postEntitySet = teacherEntity.getDocumentPosts();
            DocumentPostEntity documentPost = documentPostEntityOptional.get();
            if (postEntitySet.contains(documentPost)) {
                documentPost.setDescription(documentPostDTO.getDescription());
                documentPost.setImageURL(documentPostDTO.getImg_url());
                Optional<SubjectEntity> subjectEntityOptional = subjectRepository.findById(documentPostDTO.getSubject_id());
                if (subjectEntityOptional.isPresent()) {
                    documentPost.setSubject(subjectEntityOptional.get());
                    documentRepository.save(documentPost);
                    return ResponseEntity.ok(new Message("Sửa tài liệu thành công"));
                }
                return ResponseEntity.badRequest().body(new Message("Không tồn tại môn học này"));
            }
        }
        return ResponseEntity.status(404).body(new Message("Không tìm thấy bài viết này"));
    }

    public ResponseEntity<Message> deleteDocumentPost(UserEntity user, Long documentId) {
        TeacherEntity teacherEntity = teacherRepository.findByUser(user).get();
        Set<DocumentPostEntity> documentPostEntitySet = teacherEntity.getDocumentPosts();
        Optional<DocumentPostEntity> documentPostEntityOptional = documentRepository.findById(documentId);
        if (documentPostEntityOptional.isPresent()) {
            DocumentPostEntity documentPost = documentPostEntityOptional.get();
            if (documentPostEntitySet.contains(documentPost)) {
                documentRepository.delete(documentPost);
                return ResponseEntity.ok(new Message("Xoá tài liệu thành công"));
            }
        }
        return ResponseEntity.status(404).body(new Message("Không tìm thấy bài viết này"));
    }

    public ResponseEntity<List<DocumentData>> getDocumentOfATeacher(Long id) {
        TeacherEntity teacherEntity = _getTeacherById(id);
        if (teacherEntity == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(teacherEntity.getDocumentPosts()
                .stream().map(post -> new DocumentData(
                        post.getId(),
                        post.getDescription(),
                        post.getImageURL(),
                        post.getSubject().getSubjectName()
                ))
                .toList());
    }
}
