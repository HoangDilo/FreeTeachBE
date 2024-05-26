package com.example.freeteachbe.Service;

import com.example.freeteachbe.DTO.BodyPayload.ProblemPostDTO;
import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.ProblemPostData;
import com.example.freeteachbe.Entity.*;
import com.example.freeteachbe.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemPostService {
    private final ProblemPostRepository problemPostRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    public List<ProblemPostData> getAllProblemPosts(int page, int limit) {
        return problemPostRepository.findAll()
                .stream()
                .map(problemPostEntity -> {
                    Long studentId = problemPostEntity.getStudent().getId();
                    StudentEntity student = studentRepository.findById(studentId).get();
                    UserEntity user = student.getUser();
                    return ProblemPostData
                            .builder()
                            .id(problemPostEntity.getId())
                            .description(problemPostEntity.getDescription())
                            .image_url(problemPostEntity.getImage_url())
                            .student_name(user.getName())
                            .student_avatar_url(user.getAvatarURL())
                            .created_at(problemPostEntity.getCreated_at())
                            .subject_name(problemPostEntity.getSubject().getSubjectName())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public List<ProblemPostData> getRecommendPosts(UserEntity user, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "created_at"));
        Set<SubjectEntity> subjectEntitySet = null;
        if (studentRepository.findByUser(user).isPresent()) {
            subjectEntitySet = studentRepository.findByUser(user).get().getSubjects();
        } else if (teacherRepository.findByUser(user).isPresent()) {
            subjectEntitySet = teacherRepository.findByUser(user).get().getSubjects();
        }
        Set<Long> subjectIds = subjectEntitySet.stream().map(subject -> subject.getId()).collect(Collectors.toSet());
        return problemPostRepository.findBySubjects(subjectIds, pageable)
                .stream()
                .map(problemPostEntity -> {
                    Long studentId = problemPostEntity.getStudent().getId();
                    StudentEntity student = studentRepository.findById(studentId).get();
                    UserEntity userPosted = student.getUser();
                    return ProblemPostData
                            .builder()
                            .id(problemPostEntity.getId())
                            .description(problemPostEntity.getDescription())
                            .image_url(problemPostEntity.getImage_url())
                            .student_name(userPosted.getName())
                            .student_avatar_url(userPosted.getAvatarURL())
                            .created_at(problemPostEntity.getCreated_at())
                            .subject_name(problemPostEntity.getSubject().getSubjectName())
                            .build();
                })
                .toList();
    }

    public ResponseEntity<Message> createNewRecommendPost(UserEntity user, ProblemPostDTO problemPostDTO) {

        Optional<StudentEntity> studentEntityOptional = studentRepository.findByUser(user);
        if (studentEntityOptional.isPresent()) {
            StudentEntity studentEntity = studentEntityOptional.get();
            Optional<SubjectEntity> subjectEntityOptional = subjectRepository.findById(problemPostDTO.getSubject_id());
            if (subjectEntityOptional.isPresent()) {
                SubjectEntity subject = subjectEntityOptional.get();
                problemPostRepository.save(ProblemPostEntity
                        .builder()
                        .description(problemPostDTO.getDescription())
                        .image_url(problemPostDTO.getImage_url())
                        .subject(subject)
                        .student(studentEntity)
                        .created_at(LocalDateTime.now())
                        .build());
                return ResponseEntity.ok(new Message("Thêm mới một câu hỏi thành công"));
            }
            return ResponseEntity.status(400).body(new Message("Môn học không tồn tại"));
        }
        return ResponseEntity.status(403).body(new Message("Bạn phải là học sinh để có thể tạo câu hỏi"));
    }

    public ResponseEntity<Message> deletePost(UserEntity user, Long postId) {
        Optional<StudentEntity> studentEntityOptional = studentRepository.findByUser(user);
        if (studentEntityOptional.isPresent()) {
            Optional<ProblemPostEntity> problemPostEntityOptional = problemPostRepository.findById(postId);
            if (problemPostEntityOptional.isPresent()) {
                StudentEntity studentEntity = studentEntityOptional.get();
                ProblemPostEntity problemPostEntity = problemPostEntityOptional.get();
                if (studentEntity.getProblemPosts().contains(problemPostEntity)) {
                    problemPostRepository.delete(problemPostEntity);
                    return ResponseEntity.ok(new Message("Xóa câu hỏi thành công"));
                }
            }
            return ResponseEntity.status(404).body(new Message("Không tìm thấy câu hỏi này"));
        }
        return ResponseEntity.status(403).body(new Message("Bạn phải là học sinh để có thể xóa câu hỏi"));
    }
}
