package com.example.freeteachbe.Service;

import com.example.freeteachbe.DTO.BodyPayload.TeacherDTO;
import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.SubjectData;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.TeacherData;
import com.example.freeteachbe.Entity.*;
import com.example.freeteachbe.Repository.SubjectRepository;
import com.example.freeteachbe.Repository.TeacherRepository;
import com.example.freeteachbe.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeacherService {
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SubjectRepository subjectRepository;

    public List<TeacherData> getAllTeacher(String teacherName, Long subjectId, String sortBy, String sortDir) {
        String queryName = teacherName == null ? "" : teacherName;
        Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        List<TeacherEntity> teacherEntityList;
        if (subjectId == null) {
            teacherEntityList = teacherRepository.findAll(sort);
        } else {
            teacherEntityList = teacherRepository.findBySubject(subjectId, sort);
        }
        return teacherEntityList.stream().map(teacherEntity -> {
            UserEntity userEntity = teacherEntity.getUser();
            Set<FeedbackEntity> feedbackEntities = teacherEntity.getFeedbacks();
            OptionalDouble avarageOptional = feedbackEntities.stream().mapToDouble(FeedbackEntity::getPoint).average();
            double averagePoint = avarageOptional.isPresent() ? avarageOptional.getAsDouble() : 0;
            return new TeacherData(userEntity.getId(),
                    userEntity.getName(),
                    userEntity.getEmail(),
                    userEntity.getAvatarURL(),
                    userEntity.getUsername(),
                    userEntity.getMoney(),
                    teacherEntity.getPricePerHour(),
                    teacherEntity.getDescription(),
                    teacherEntity.getActiveTimeStart().toString(),
                    teacherEntity.getActiveTimeEnd().toString(),
                    teacherEntity.getActiveDays(),
                    averagePoint);
        }).filter(teacher ->
                teacher.getName().toLowerCase()
                        .contains(queryName.toLowerCase())).toList();
    }

    public ResponseEntity<Message> registerTeacher(UserEntity user, TeacherDTO teacherDTO) {
        if (user.isFirstLogin()) {
            try {
                Set<SubjectEntity> subjectEntitySet = teacherDTO.getSubjectIds().stream().map(subject ->
                        subjectRepository.findById(subject).get()).collect(Collectors.toSet());
                LocalTime activeTimeStart = LocalTime.parse(teacherDTO.getActiveTimeStart());
                LocalTime activeTimeEnd = LocalTime.parse(teacherDTO.getActiveTimeEnd());
                TeacherEntity teacherEntity = new TeacherEntity(teacherDTO.getPricePerHour(), teacherDTO.getDescription(), activeTimeStart, activeTimeEnd, teacherDTO.getActiveDays(), subjectEntitySet, user);
                teacherRepository.save(teacherEntity);
                user.setFirstLogin(false);
                user.setRole(Role.TEACHER);
                userRepository.save(user);
                return ResponseEntity.status(200).body(new Message("Đăng ký giáo viên thành công"));
            } catch (NoSuchElementException noSuchElementException) {
                return ResponseEntity.status(400).body(new Message("Có môn học đăng ký không tồn tại"));
            }
        }
        return ResponseEntity.status(400).body(new Message("Bạn đã đăng ký làm học sinh hoặc gia sư rồi"));
    }

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

    public ResponseEntity<TeacherData> getTeacherById(Long id) {
        TeacherEntity teacherEntity = _getTeacherById(id);
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        if (userEntityOptional.isPresent() && teacherEntity != null) {
            UserEntity userEntity = userEntityOptional.get();
            Set<FeedbackEntity> feedbackEntities = teacherEntity.getFeedbacks();
            OptionalDouble avarageOptional = feedbackEntities.stream().mapToDouble(FeedbackEntity::getPoint).average();
            double averagePoint = avarageOptional.isPresent() ? avarageOptional.getAsDouble() : 0;
            return ResponseEntity.status(200).body(
                    new TeacherData(userEntity.getId(),
                            userEntity.getName(),
                            userEntity.getEmail(),
                            userEntity.getAvatarURL(),
                            userEntity.getUsername(),
                            userEntity.getMoney(),
                            teacherEntity.getPricePerHour(),
                            teacherEntity.getDescription(),
                            teacherEntity.getActiveTimeStart().toString(),
                            teacherEntity.getActiveTimeEnd().toString(),
                            teacherEntity.getActiveDays(),
                            averagePoint));
        }
        return ResponseEntity.status(404).body(null);
    }

    public ResponseEntity<List<SubjectData>> getTeacherSubjects(Long id) {
        TeacherEntity teacherEntity = _getTeacherById(id);
        if (teacherEntity == null) {
            return ResponseEntity.status(404).body(null);
        }
        Set<SubjectEntity> subjectEntitySet = teacherEntity.getSubjects();
        return ResponseEntity.status(200).body(subjectEntitySet.stream().map(subjectEntity ->
                new SubjectData(subjectEntity.getId(), subjectEntity.getSubjectName())).toList());
    }
}
