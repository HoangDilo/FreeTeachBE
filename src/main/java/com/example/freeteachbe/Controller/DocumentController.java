package com.example.freeteachbe.Controller;

import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.DocumentData;
import com.example.freeteachbe.Entity.UserEntity;
import com.example.freeteachbe.Service.DocumentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
@Tag(name = "Document")
public class DocumentController {
    private final DocumentService documentService;
    @GetMapping("/my")
    public ResponseEntity<Set<DocumentData>> getMyDocumentPost(
            @AuthenticationPrincipal UserEntity user
            ) {
        return ResponseEntity.ok(documentService.getMyDocumentPost(user));
    }
}
