package com.example.freeteachbe.Controller;

import com.example.freeteachbe.DTO.BodyPayload.SubjectDTO;
import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.SubjectData;
import com.example.freeteachbe.Service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    @Operation(summary = "Lấy ra danh sách các môn học có trong hệ thống")
    public List<SubjectData> getALlSubjet() {
        return subSer.getAllSubject();
    }
    @PostMapping
    @Operation(summary = "Tạo mới một môn học", description = "Nhập tên môn học vào trường subjectName " +
            "bên dưới request body")
    public ResponseEntity<Message> createSubject(@RequestBody SubjectDTO subjectTO) {
        return subSer.createSubject(subjectTO);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Sửa một môn học")
    @Parameter(name = "id", description = "Nhập id của môn học muốn sửa")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nhập tên môn học mới vào trường subjectName")
    public ResponseEntity<Message> editSubject(@PathVariable Long id, @RequestBody SubjectDTO subjectDTO) {
        return subSer.editSubject(id, subjectDTO);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa một môn học")
    @Parameter(name = "id", description = "Nhập id của môn học muốn xóa")
    public ResponseEntity<Message> deleteSubject(@PathVariable Long id) {
        return subSer.deleteSubject(id);
    }
}
