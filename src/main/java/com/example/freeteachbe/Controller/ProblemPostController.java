package com.example.freeteachbe.Controller;

import com.example.freeteachbe.DTO.BodyPayload.AnswerDTO;
import com.example.freeteachbe.DTO.BodyPayload.ProblemPostDTO;
import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.AnswerData;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.ProblemPostData;
import com.example.freeteachbe.Entity.UserEntity;
import com.example.freeteachbe.Service.ProblemPostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
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
        return problemPostService.getListProblemPosts(page, limit);
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

    @PutMapping("{id}")
    public ResponseEntity<Message> editPost(
            @AuthenticationPrincipal UserEntity user,
            @PathVariable Long id,
            @RequestBody ProblemPostDTO problemPostDTO
    ) {
        return problemPostService.editPost(user, id, problemPostDTO);
    }

    @GetMapping("/{id}/answer/list")
    public ResponseEntity<List<AnswerData>> getPostAnswers(
            @PathVariable Long id
    ) {
        List<AnswerData> answerDataList = problemPostService.getPostAnswers(id);
        if (answerDataList == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(answerDataList);
    }

    @PostMapping("/{id}/answer")
    public ResponseEntity<Message> createAnswer(
            @AuthenticationPrincipal UserEntity user,
            @PathVariable Long id,
            @RequestBody AnswerDTO answerDTO
    ) {
        return problemPostService.createAnswer(user, id, answerDTO);
    }

}
