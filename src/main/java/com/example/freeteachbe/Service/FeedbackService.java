package com.example.freeteachbe.Service;

import com.example.freeteachbe.DTO.BodyPayload.FeedbackDTO;
import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.FeedbackData;
import com.example.freeteachbe.Entity.FeedbackEntity;
import com.example.freeteachbe.Entity.StudentEntity;
import com.example.freeteachbe.Entity.TeacherEntity;
import com.example.freeteachbe.Entity.UserEntity;
import com.example.freeteachbe.Repository.FeedbackRepository;
import com.example.freeteachbe.Repository.StudentRepository;
import com.example.freeteachbe.Repository.TeacherRepository;
import com.example.freeteachbe.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
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
    public ResponseEntity<Message> createFeedback(UserEntity user, FeedbackDTO feedbackDTO, Long teacherId) {
        Optional<StudentEntity> studentEntityOptional = studentRepository.findByUser(user);
        if (studentEntityOptional.isPresent()) {
            TeacherEntity teacherEntity = _getTeacherById(teacherId);
            if (teacherEntity != null) {
                double point = feedbackDTO.getPoint();
                if (point < 0 || point > 5) {
                    return ResponseEntity.status(400).body(new Message("Điểm đánh giá không được vượt quá 5"));
                }
                StudentEntity student = studentEntityOptional.get();
                feedbackRepository.save(FeedbackEntity.builder()
                        .description(feedbackDTO.getDescription())
                        .point(feedbackDTO.getPoint())
                        .student(student)
                        .teacher(teacherEntity)
                        .build());
                return ResponseEntity.status(200).body(new Message("Tạo đánh giá thành công"));
            }
            return ResponseEntity.status(404).body(new Message("Không tìm thấy gia sư này"));
        }
        return ResponseEntity.status(403).body(new Message("Bạn phải là học sinh để có thể tạo đánh gía"));
    }

    public List<FeedbackData> getTeacherFeedback(Long teacherId) {
        TeacherEntity teacherEntity = _getTeacherById(teacherId);
        if (teacherEntity != null) {
            return teacherEntity.getFeedbacks().stream().map(
                    feedback -> FeedbackData.builder()
                            .id(feedback.getId())
                            .student_name(feedback.getStudent().getUser().getName())
                            .student_avatar_url(feedback.getStudent().getUser().getAvatarURL())
                            .description(feedback.getDescription())
                            .point(feedback.getPoint())
                            .build())
                    .toList();
        }
        return null;
    }

    public ResponseEntity<Double> getAveragePoint(Long teacherId) {
        TeacherEntity teacherEntity = _getTeacherById(teacherId);
        if (teacherEntity != null) {
            Set<FeedbackEntity> feedbackEntities = teacherEntity.getFeedbacks();
            OptionalDouble avarageOptional = feedbackEntities.stream().mapToDouble(FeedbackEntity::getPoint).average();
            if (avarageOptional.isPresent()) {
                return ResponseEntity.ok(avarageOptional.getAsDouble());
            }
            return ResponseEntity.status(400).body(null);
        }
        return ResponseEntity.status(404).body(null);
    }
}
