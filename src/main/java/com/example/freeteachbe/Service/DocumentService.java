package com.example.freeteachbe.Service;

import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.DocumentData;
import com.example.freeteachbe.Entity.TeacherEntity;
import com.example.freeteachbe.Entity.UserEntity;
import com.example.freeteachbe.Repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final TeacherRepository teacherRepository;
    public Set<DocumentData> getMyDocumentPost(UserEntity user) {
        Optional<TeacherEntity> teacherEntityOptional = teacherRepository.findByUser(user);
        if (teacherEntityOptional.isPresent()) {
            TeacherEntity teacherEntity = teacherEntityOptional.get();
            return teacherEntity.getDocumentPosts().stream().map(documentPostEntity ->
                    new DocumentData(
                            documentPostEntity.getId(),
                            documentPostEntity.getDescription(),
                            documentPostEntity.getImageURL(),
                            documentPostEntity.getSubject().getSubjectName())).collect(Collectors.toSet());
        }
        return null;
    }
}
