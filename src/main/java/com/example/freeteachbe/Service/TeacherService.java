package com.example.freeteachbe.Service;

import com.example.freeteachbe.DTO.BodyPayload.TeacherDTO;
import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.SubjectData;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.TeacherData;
import com.example.freeteachbe.Entity.StudentEntity;
import com.example.freeteachbe.Entity.SubjectEntity;
import com.example.freeteachbe.Entity.TeacherEntity;
import com.example.freeteachbe.Entity.UserEntity;
import com.example.freeteachbe.Repository.StudentRepository;
import com.example.freeteachbe.Repository.SubjectRepository;
import com.example.freeteachbe.Repository.TeacherRepository;
import com.example.freeteachbe.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeacherService {
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    StudentRepository studentRepository;

    public List<TeacherData> getAllTeacher() {
        List<TeacherEntity> teacherEntityList = teacherRepository.findAll();
        return teacherEntityList.stream().map(teacherEntity -> {
            UserEntity userEntity = teacherEntity.getUser();
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
                    teacherEntity.getActiveDays());
        }).toList();
    }

    public TeacherEntity _getTeacherById(Long userId) {
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

    private StudentEntity checkViolationStudent(Long userId) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        if (userEntityOptional.isPresent()) {
            UserEntity user = userEntityOptional.get();
            Optional<StudentEntity> studentEntityOptional = studentRepository.findByUser(user);
            if (studentEntityOptional.isPresent()) {
                return studentEntityOptional.get();
            }
        }
        return null;
    }

    public ResponseEntity<Message> registerTeacher(TeacherDTO teacherDTO) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(teacherDTO.getUserId());
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            try {
                if (checkViolationStudent(teacherDTO.getUserId()) != null) {
                    throw new DataIntegrityViolationException("");
                }
                Set<SubjectEntity> subjectEntitySet = teacherDTO.getSubjectIds().stream().map(subject ->
                        subjectRepository.findById(subject).get()).collect(Collectors.toSet());
                LocalTime activeTimeStart = LocalTime.parse(teacherDTO.getActiveTimeStart());
                LocalTime activeTimeEnd = LocalTime.parse(teacherDTO.getActiveTimeEnd());
                TeacherEntity teacherEntity = new TeacherEntity(teacherDTO.getPricePerHour(), teacherDTO.getDescription(), activeTimeStart, activeTimeEnd, teacherDTO.getActiveDays(), subjectEntitySet, userEntity);
                teacherRepository.save(teacherEntity);
                userEntity.setFirstLogin(false);
                userRepository.save(userEntity);
                return ResponseEntity.status(200).body(new Message("Thêm mới giáo viên thành công"));
            } catch (NoSuchElementException noSuchElementException) {
                return ResponseEntity.status(400).body(new Message("Có môn học đăng ký không tồn tại"));
            } catch (DataIntegrityViolationException dataIntegrityViolationException) {
                return ResponseEntity.status(400).body(new Message("Người dùng này đã được đăng ký"));
            }
        } else {
            return ResponseEntity.status(404).body(new Message("Không tìm thấy người dùng này"));
        }
    }

    public ResponseEntity<TeacherData> getTeacherById(Long id) {
        TeacherEntity teacherEntity = _getTeacherById(id);
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        if (userEntityOptional.isPresent() && teacherEntity != null) {
            UserEntity userEntity = userEntityOptional.get();
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
                    teacherEntity.getActiveDays()));
        }
        return ResponseEntity.status(404).body(null);
    }

    public ResponseEntity<List<SubjectData>> getTeacherSubjects(Long userId) {
        TeacherEntity teacherEntity = _getTeacherById(userId);
        if (teacherEntity != null) {
            Set<SubjectEntity> subjectEntitySet = teacherEntity.getSubjects();
            return ResponseEntity.status(200).body(subjectEntitySet.stream().map(subjectEntity ->
                    new SubjectData(subjectEntity.getId(), subjectEntity.getSubjectName())).toList());
        }
        return ResponseEntity.status(404).body(null);
    }
}
