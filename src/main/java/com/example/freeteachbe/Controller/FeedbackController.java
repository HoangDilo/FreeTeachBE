package com.example.freeteachbe.Controller;

import com.example.freeteachbe.DTO.BodyPayload.FeedbackDTO;
import com.example.freeteachbe.DTO.ReturnPayload.Message;
import com.example.freeteachbe.DTO.ReturnPayload.ReturnData.FeedbackData;
import com.example.freeteachbe.Entity.UserEntity;
import com.example.freeteachbe.Service.FeedbackService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
@Tag(name = "Feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;
    @PostMapping("/{teacherId}")
    public ResponseEntity<Message> createFeedback(
            @AuthenticationPrincipal UserEntity user,
            @RequestBody FeedbackDTO feedbackDTO,
            @PathVariable Long teacherId
    ) {
        return feedbackService.createFeedback(user, feedbackDTO, teacherId);
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<List<FeedbackData>> getTeacherFeedback(
            @PathVariable Long teacherId
    ) {
        List<FeedbackData> feedbackDataList = feedbackService.getTeacherFeedback(teacherId);
        if (feedbackDataList == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(feedbackDataList);
    }

    @GetMapping("/average/{teacherId}")
    public ResponseEntity<Double> getTeacherAveragePoint(
            @PathVariable Long teacherId
    ) {
        return feedbackService.getAveragePoint(teacherId);
    }
}
