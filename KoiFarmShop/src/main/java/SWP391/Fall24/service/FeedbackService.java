package SWP391.Fall24.service;

import SWP391.Fall24.dto.FeedbackDetailDTO;
import SWP391.Fall24.pojo.*;
import SWP391.Fall24.pojo.Enum.FeedbackStatus;
import SWP391.Fall24.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService implements IFeedbackService {

    @Autowired
    IFeedbackRepository iFeedbackRepository;
    @Autowired
    IOrderDetailRepository iOrderDetailRepository;
    @Autowired
    IEvaluationRepository iEvaluationRepository;
    @Autowired
    IOrderRepository iOrderRepository;
    @Autowired
    IUserRepository iUserRepository;
    @Autowired
    FishService  fishService;


    @Override
    public String saveFeedback(FeedbackDetailDTO feedbackDetailDTO , int orderId , int fishId ) {
        OrderDetails orderDetails = iFeedbackRepository.findByOrdersIdAndFishesId(orderId , fishId);
        orderDetails.setFeedback(feedbackDetailDTO.getFeedback());
        orderDetails.setRating(feedbackDetailDTO.getRating());
        orderDetails.setEvaluationStatus(true);
        orderDetails.setApprovalStatus(FeedbackStatus.Approving.toString());
        iOrderDetailRepository.save(orderDetails);
        return "Saved rating and feedback successfully";
    }

    @Override
    public List<Evaluations> getAllFeedback(int fishId) {
       List<Evaluations> evaluationsList = iEvaluationRepository.getEvaluationsByFishId(fishId);
       return evaluationsList;
        }

    }

