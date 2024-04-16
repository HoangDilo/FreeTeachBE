package com.example.freeteachbe.Service;

import com.example.freeteachbe.DTO.BodyPayload.SubjectDTO;
import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.SubjectData;
import com.example.freeteachbe.Entity.StudentEntity;
import com.example.freeteachbe.Entity.SubjectEntity;
import com.example.freeteachbe.Repository.StudentRepository;
import com.example.freeteachbe.Repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubjectService {
    @Autowired
    SubjectRepository subRe;
    @Autowired
    StudentRepository studentRepository;
    public List<SubjectData> getAllSubject() {
        List<SubjectEntity> listSubjects = subRe.findAll();
        return listSubjects.stream().map(subject -> new SubjectData(subject.getId(), subject.getSubjectName())).toList();
    }
    public ResponseEntity<Message> createSubject(SubjectDTO subjectDTO) {
        System.out.println(subjectDTO.getSubjectName());
        SubjectEntity subject = new SubjectEntity(subjectDTO.getSubjectName());
        subRe.save(subject);
        return ResponseEntity.status(200).body(new Message("Thêm mới môn học thành công"));
    }
    public ResponseEntity<Message> editSubject(Long subjectId, SubjectDTO subjectDTO) {
        Optional<SubjectEntity> subjectEntityOptional = subRe.findById(subjectId);
        if (subjectEntityOptional.isPresent()) {
            SubjectEntity subjectEntity = subjectEntityOptional.get();
            subjectEntity.setSubjectName(subjectDTO.getSubjectName());
            subRe.save(subjectEntity);
            return ResponseEntity.status(200).body(new Message("Sửa đổi thành công"));
        }
        return ResponseEntity.status(404).body(new Message("Không tìm thấy môn học"));
    }
    public ResponseEntity<Message> deleteSubject(Long subjectId) {
        Optional<SubjectEntity> subjectEntityOptional = subRe.findById(subjectId);
        if (subjectEntityOptional.isPresent()) {
            SubjectEntity subjectEntity = subjectEntityOptional.get();
            subjectEntity.getStudents().clear();
            subRe.save(subjectEntity);
            subjectEntity.getStudents().forEach(studentEntity -> {
                studentEntity.getSubjects().remove(subjectEntity);
                studentRepository.save(studentEntity);
            });
            subRe.save(subjectEntity);
            subRe.delete(subjectEntity);
            return ResponseEntity.status(200).body(new Message("Xóa môn học thành công"));
        }
        return ResponseEntity.status(404).body(new Message("Không tìm thấy môn học"));
    }
}
