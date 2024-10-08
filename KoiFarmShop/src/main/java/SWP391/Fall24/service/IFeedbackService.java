package SWP391.Fall24.service;

import SWP391.Fall24.dto.FeedbackDTO;
import SWP391.Fall24.dto.FeedbackDetailDTO;
import SWP391.Fall24.dto.FishDetailDTO;
import SWP391.Fall24.pojo.Evaluations;

import java.util.List;

public interface IFeedbackService {
    public String saveFeedback(FeedbackDetailDTO feedbackDetailDTO , int orderId , int fishId);
    public List<Evaluations> getAllFeedback(int fishId);


}
