package com.example.freeteachbe.Service;

import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.Entity.StudentEntity;
import com.example.freeteachbe.Entity.TeacherEntity;
import com.example.freeteachbe.Entity.UserEntity;
import com.example.freeteachbe.Repository.StudentRepository;
import com.example.freeteachbe.Repository.TeacherRepository;
import com.example.freeteachbe.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HireService {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    public ResponseEntity<Message> hireATeacher(UserEntity user, Long teacherId, int hours) {
        Optional<StudentEntity> studentEntityOptional = studentRepository.findByUser(user);
        if (studentEntityOptional.isPresent()) {
            Optional<TeacherEntity> teacherEntityOptional = teacherRepository.findById(teacherId);
            if (teacherEntityOptional.isPresent()) {
                TeacherEntity teacher = teacherEntityOptional.get();
                int price = teacher.getPricePerHour();
                int totalPrice = price * hours;
                if (user.getMoney() < totalPrice) {
                    return  ResponseEntity.status(400).body(new Message("Bạn không đủ tiền để thuê gia sư này"));
                }
                UserEntity student = user;
                student.setMoney(student.getMoney() - totalPrice);
                userRepository.save(student);
                UserEntity teacherUser = teacher.getUser();
                teacherUser.setMoney(teacherUser.getMoney() + totalPrice);
                userRepository.save(teacherUser);
                return ResponseEntity.ok(new Message("Thuê gia sư thành công với giá " + totalPrice));
            }
        }
        return ResponseEntity.status(403).body(new Message("Bạn phải là học sinh để thuê một gia sư"));
    }
}
