package com.example.freeteachbe.Service;

import com.example.freeteachbe.DTO.BodyPayload.StudentDTO;
import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.StudentData;
import com.example.freeteachbe.Entity.StudentEntity;
import com.example.freeteachbe.Entity.SubjectEntity;
import com.example.freeteachbe.Entity.UserEntity;
import com.example.freeteachbe.Repository.StudentRepository;
import com.example.freeteachbe.Repository.SubjectRepository;
import com.example.freeteachbe.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
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
            return new StudentData(user.getId(), student.getGrade(),user.getName(), user.getEmail(), user.getAvatarURL(), user.getUsername(), user.getMoney());
        }).toList();
    };
    public ResponseEntity<Message> createStudent(@RequestBody StudentDTO studentDTO) {
        Optional<UserEntity> userFound = ur.findById(studentDTO.getUser_id());
        if (userFound.isPresent()) {
            UserEntity user = userFound.get();
            Set<SubjectEntity> subjectEntitySet = studentDTO.getSubject_ids().stream().map(subject -> {
                return subRe.findById(subject).get();
            }).collect(Collectors.toSet());
            try {
                StudentEntity student = new StudentEntity(studentDTO.getGrade(), subjectEntitySet, user);
                sr.save(student);
                return ResponseEntity.status(200).body(new Message("Thành công"));
            } catch (DataIntegrityViolationException dataIntegrityViolationException) {
                return ResponseEntity.status(400).body(new Message("Người dùng này đã tồn tại"));
            }
        } else {
            return ResponseEntity.status(404).body(new Message("Không tìm thấy user"));
        }
    }

}
