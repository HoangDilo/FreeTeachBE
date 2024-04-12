package com.example.freeteachbe.Service;

import com.example.freeteachbe.DTO.BodyPayload.SubjectDTO;
import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.SubjectData;
import com.example.freeteachbe.Entity.SubjectEntity;
import com.example.freeteachbe.Repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    @Autowired
    SubjectRepository subRe;
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
}
