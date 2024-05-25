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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final DocumentRepository documentRepository;
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
}
