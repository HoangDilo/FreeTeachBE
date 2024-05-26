package com.example.freeteachbe.Service;

import com.example.freeteachbe.DTO.BodyPayload.ProblemPostDTO;
import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.ProblemPostData;
import com.example.freeteachbe.Entity.*;
import com.example.freeteachbe.Repository.ProblemPostRepository;
import com.example.freeteachbe.Repository.StudentRepository;
import com.example.freeteachbe.Repository.SubjectRepository;
import com.example.freeteachbe.Repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
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

    public List<ProblemPostData> getAllProblemPosts(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "created_at"));

        return problemPostRepository.findAll(pageable)
                .stream()
                .map(problemPostEntity ->
                    ProblemPostData
                        .builder()
                        .description(problemPostEntity.getDescription())
                        .image_url(problemPostEntity.getImage_url())
                        .subject_name(problemPostEntity.getSubject().getSubjectName())
                        .build())
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
                .map(problemPostEntity ->
                        ProblemPostData
                                .builder()
                                .description(problemPostEntity.getDescription())
                                .image_url(problemPostEntity.getImage_url())
                                .created_at(problemPostEntity.getCreated_at())
                                .subject_name(problemPostEntity.getSubject().getSubjectName())
                                .build())
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
}
