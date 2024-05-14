package com.example.freeteachbe.Service;

import com.example.freeteachbe.DTO.BodyPayload.StudentDTO;
import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.StudentData;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.SubjectData;
import com.example.freeteachbe.Entity.Role;
import com.example.freeteachbe.Entity.StudentEntity;
import com.example.freeteachbe.Entity.SubjectEntity;
import com.example.freeteachbe.Entity.UserEntity;
import com.example.freeteachbe.Repository.StudentRepository;
import com.example.freeteachbe.Repository.SubjectRepository;
import com.example.freeteachbe.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    private StudentRepository sr;
    @Autowired
    private UserRepository ur;
    @Autowired
    private SubjectRepository subRe;

    public List<StudentData> getAllStudent() {
        List<StudentEntity> studentEntities = sr.findAll();
        return studentEntities.stream().map(student -> {
            UserEntity user = student.getUser();
            return new StudentData(user.getId(), student.getGrade(), user.getName(), user.getEmail(), user.getAvatarURL(), user.getUsername(), user.getMoney());
        }).toList();
    }

    public ResponseEntity<Message> createStudent(UserEntity user, StudentDTO studentDTO) {
        if (user.isFirstLogin()) {
            try {

                Set<SubjectEntity> subjectEntitySet = studentDTO.getSubject_ids().stream().map(subject ->
                        subRe.findById(subject).get()).collect(Collectors.toSet());
                StudentEntity student = new StudentEntity(studentDTO.getGrade(), subjectEntitySet, user);
                sr.save(student);
                user.setFirstLogin(false);
                user.setRole(Role.STUDENT);
                ur.save(user);
                return ResponseEntity.status(200).body(new Message("Thành công"));
            } catch (NoSuchElementException noSuchElementException) {
                return ResponseEntity.status(400).body(new Message("Có môn học đăng ký không tồn tại"));
            }
        }
        return ResponseEntity.status(400).body(new Message("Bạn đã đăng ký làm học sinh hoặc gia sư rồi"));
    }

    public StudentEntity _getStudentById(Long userId) {
        Optional<UserEntity> userEntityOptional = ur.findById(userId);
        if (userEntityOptional.isPresent()) {
            UserEntity user = userEntityOptional.get();
            Optional<StudentEntity> studentEntityOptional = sr.findByUser(user);
            if (studentEntityOptional.isPresent()) {
                return studentEntityOptional.get();
            }
        }
        return null;
    }

    public ResponseEntity<StudentData> getStudentById(Long userId) {
        Optional<UserEntity> userEntityOptional = ur.findById(userId);
        StudentEntity studentEntity = _getStudentById(userId);
        if (userEntityOptional.isPresent() && studentEntity != null) {
            UserEntity user = userEntityOptional.get();
            return ResponseEntity.status(200).body(new StudentData(userId, studentEntity.getGrade(), user.getName(), user.getEmail(), user.getAvatarURL(), user.getUsername(), user.getMoney()));
        }
        return ResponseEntity.status(404).body(null);
    }

    public ResponseEntity<Set<SubjectData>> getStudentSubjects(Long userId) {
        StudentEntity studentEntity = _getStudentById(userId);
        if (studentEntity != null) {
            Set<SubjectEntity> subjectEntities = studentEntity.getSubjects();
            Set<SubjectData> subjectDataSet = subjectEntities.stream().map(subject -> new SubjectData(subject.getId(), subject.getSubjectName())).collect(Collectors.toSet());
            return ResponseEntity.status(200).body(subjectDataSet);
        }
        return ResponseEntity.status(404).body(null);
    }
}
