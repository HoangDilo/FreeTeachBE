package com.example.freeteachbe.Controller;

import com.example.freeteachbe.DTO.BodyPayload.ProblemPostDTO;
import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.ProblemPostData;
import com.example.freeteachbe.Entity.UserEntity;
import com.example.freeteachbe.Service.ProblemPostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Tag(name = "Post")
public class ProblemPostController {
    private final ProblemPostService problemPostService;
    @GetMapping("/list/recommended")
    public List<ProblemPostData> getRecommendedPosts(
            @AuthenticationPrincipal UserEntity user,
            @RequestParam int page,
            @RequestParam int limit
            ) {
        return problemPostService.getRecommendPosts(user, page, limit);
    }

    @GetMapping("/list")
    public List<ProblemPostData> getAllPosts(
            @RequestParam int page,
            @RequestParam int limit
    ) {
        return problemPostService.getAllProblemPosts(page, limit);
    }

    @PostMapping
    public ResponseEntity<Message> createNewPost(
            @AuthenticationPrincipal UserEntity user,
            @RequestBody ProblemPostDTO problemPostDTO) {
        return  problemPostService.createNewRecommendPost(user, problemPostDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deletePost(
            @AuthenticationPrincipal UserEntity user,
            @PathVariable Long id
    ) {
        return problemPostService.deletePost(user, id);
    }
}
