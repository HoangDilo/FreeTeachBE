package com.example.freeteachbe.Controller;

import com.example.freeteachbe.DTO.BodyPayload.SubjectDTO;
import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.SubjectData;
import com.example.freeteachbe.Service.SubjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subject")
@Tag(name = "Subject")
public class SubjectController {
    @Autowired
    SubjectService subSer;
    @GetMapping
    public List<SubjectData> getALlSubjet() {
        return subSer.getAllSubject();
    }
    @PostMapping
    public ResponseEntity<Message> createSubject(@RequestBody SubjectDTO subjectTO) {
        System.out.println("controller: " + subjectTO.getSubjectName());
        return subSer.createSubject(subjectTO);
    }
}
