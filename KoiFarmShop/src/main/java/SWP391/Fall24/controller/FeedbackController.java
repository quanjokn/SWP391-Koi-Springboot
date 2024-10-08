package SWP391.Fall24.controller;

import SWP391.Fall24.dto.FeedbackDTO;
import SWP391.Fall24.dto.FeedbackDetailDTO;
import SWP391.Fall24.dto.FishDetailDTO;
import SWP391.Fall24.exception.AppException;
import SWP391.Fall24.pojo.Evaluations;
import SWP391.Fall24.service.FeedbackService;
import SWP391.Fall24.service.FishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    FeedbackService feedbackService;
    @Autowired
    FishService fishService;

    @PostMapping("/{orderId}/{fishId}")
    public String saveFeedback(@PathVariable("orderId") int orderId, @PathVariable("fishId") int fishId, @RequestBody FeedbackDetailDTO feedbackDetailDTO) {
        return feedbackService.saveFeedback(feedbackDetailDTO, orderId, fishId);
    }

    @GetMapping("/{fishId}")
    public Optional<FishDetailDTO> getFeedback(@PathVariable("fishId") int fishId) {
        List<FishDetailDTO> allFish = fishService.allFish();
        for (FishDetailDTO fd : allFish) {
            if (fd.getId() == fishId) {
                Optional<FishDetailDTO> fishDetailDTO = Optional.of(fd);
                if (fishDetailDTO.isPresent()) {
                    FishDetailDTO fish    = fishDetailDTO.get();
                    fish.setEvaluation(feedbackService.getAllFeedback(fish.getId()));
                }
                return fishDetailDTO;
            }

        }
        return Optional.empty();
    }
}