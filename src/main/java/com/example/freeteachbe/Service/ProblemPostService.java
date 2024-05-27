package com.example.freeteachbe.Service;

import com.example.freeteachbe.DTO.BodyPayload.AnswerDTO;
import com.example.freeteachbe.DTO.BodyPayload.ProblemPostDTO;
import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.AnswerData;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.ProblemPostData;
import com.example.freeteachbe.Entity.*;
import com.example.freeteachbe.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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
    private final AnswerRepository answerRepository;

    public List<ProblemPostData> getListProblemPosts(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "createdAt"));
        return problemPostRepository.findAll(pageable)
                .stream()
                .map(problem -> {
                    Long studentId = problem.getStudent().getId();
                    StudentEntity student = studentRepository.findById(studentId).get();
                    UserEntity user = student.getUser();
                    return ProblemPostData
                            .builder()
                            .id(problem.getId())
                            .description(problem.getDescription())
                            .image_url(problem.getImage_url())
                            .student_name(user.getName())
                            .student_avatar_url(user.getAvatarURL())
                            .created_at(problem.getCreatedAt())
                            .subject_name(problem.getSubject().getSubjectName())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public List<ProblemPostData> getRecommendPosts(UserEntity user, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "createdAt"));
        Set<SubjectEntity> subjectEntitySet = null;
        if (studentRepository.findByUser(user).isPresent()) {
            subjectEntitySet = studentRepository.findByUser(user).get().getSubjects();
        } else if (teacherRepository.findByUser(user).isPresent()) {
            subjectEntitySet = teacherRepository.findByUser(user).get().getSubjects();
        }
        if (subjectEntitySet == null) {
            return getListProblemPosts(page, limit);
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
                            .created_at(problemPostEntity.getCreatedAt())
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
                        .createdAt(LocalDateTime.now())
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

    public ResponseEntity<Message> editPost(UserEntity user, Long postId, ProblemPostDTO postDTO) {
        Optional<StudentEntity> studentEntityOptional = studentRepository.findByUser(user);
        if (studentEntityOptional.isPresent()) {
            Optional<ProblemPostEntity> problemPostEntityOptional = problemPostRepository.findById(postId);
            if (problemPostEntityOptional.isPresent()) {
                ProblemPostEntity problemPostEntity = problemPostEntityOptional.get();
                if (studentEntityOptional.get().getProblemPosts().contains(problemPostEntity)) {
                    Optional<SubjectEntity> subjectEntityOptional = subjectRepository.findById(postDTO.getSubject_id());
                    if (subjectEntityOptional.isPresent()) {
                        problemPostRepository.save(ProblemPostEntity.builder()
                                .description(postDTO.getDescription())
                                .image_url(postDTO.getImage_url())
                                .subject(subjectEntityOptional.get())
                                .build());
                        return ResponseEntity.ok(new Message("Sửa câu hỏi thành công"));
                    }
                    return ResponseEntity.status(400).body(new Message("Môn học không tồn tại"));
                }
            }
            return ResponseEntity.status(404).body(new Message("Không tìm thấy câu hỏi này"));
        }
        return ResponseEntity.status(403).body(new Message("Bạn phải là học sinh để có thể sửa câu hỏi"));
    }

    public List<AnswerData> getPostAnswers(Long postId) {
        Optional<ProblemPostEntity> problemPostEntityOptional = problemPostRepository.findById(postId);
        if (problemPostEntityOptional.isPresent()) {
            ProblemPostEntity problemPostEntity = problemPostEntityOptional.get();
            return problemPostEntity.getAnswers().stream().map(answerEntity ->
                    AnswerData.builder()
                            .id(answerEntity.getId())
                            .answer(answerEntity.getAnswer())
                            .answer_image_url(answerEntity.getAnswerAvatarURL())
                            .teacher_name(answerEntity.getTeacher().getUser().getName())
                            .teacher_avatar_url(answerEntity.getTeacher().getUser().getAvatarURL())
                            .build()).toList();
        }
        return null;
    }

    public ResponseEntity<Message> createAnswer(UserEntity user, Long postId, AnswerDTO answerDTO) {
        Optional<TeacherEntity> teacherEntityOptional = teacherRepository.findByUser(user);
        if (teacherEntityOptional.isPresent()) {
            Optional<ProblemPostEntity> problemPostEntityOptional = problemPostRepository.findById(postId);
            if (problemPostEntityOptional.isPresent()) {
                ProblemPostEntity problemPostEntity = problemPostEntityOptional.get();
                answerRepository.save(AnswerEntity.builder()
                        .answer(answerDTO.getAnswer())
                        .answerAvatarURL(answerDTO.getAnswer_image_url())
                        .post(problemPostEntity)
                        .teacher(teacherEntityOptional.get())
                        .build());
            }
            return ResponseEntity.status(404).body(new Message("Không tìm thấy bài viết này"));
        }
        return ResponseEntity.status(403).body(new Message("Bạn phải là gia sư để trả lời câu hỏi"));
    }
}
