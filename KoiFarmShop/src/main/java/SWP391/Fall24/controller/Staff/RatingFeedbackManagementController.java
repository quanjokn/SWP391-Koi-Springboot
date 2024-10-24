package SWP391.Fall24.controller.Staff;

import SWP391.Fall24.pojo.*;
import SWP391.Fall24.pojo.Enum.FeedbackStatus;
import SWP391.Fall24.repository.IEvaluationRepository;
import SWP391.Fall24.repository.IOrderDetailRepository;
import SWP391.Fall24.repository.IOrderRepository;
import SWP391.Fall24.repository.IUserRepository;
import SWP391.Fall24.service.FishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ratingFeedbackController")
@CrossOrigin(origins = "http://localhost:3000")
public class RatingFeedbackManagementController {
    @Autowired
    private IOrderDetailRepository orderDetailRepository;

    @Autowired
    private IOrderRepository iOrderRepository;

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private FishService fishService;

    @Autowired
    private IEvaluationRepository evaluationRepository;

    @PostMapping("/getList")
    public List<OrderDetails> getList(){
        return orderDetailRepository.findByApprovalStatus("Approving");
    }

    @PostMapping("approval/{orderID}/{fishID}/{approval}")
    public String approval(@PathVariable("orderID") int orderID, @PathVariable("fishID") int fishID, @PathVariable("approval") int approval){
        OrderDetails orderDetails = orderDetailRepository.findByOrdersIdAndFishesId(orderID, fishID);
        if(orderDetails != null){
            if(approval == 1){
                orderDetails.setApprovalStatus(FeedbackStatus.Accepted.toString());
            } else orderDetails.setApprovalStatus(FeedbackStatus.Rejected.toString());
            orderDetailRepository.save(orderDetails);
            Optional<Orders> orders = iOrderRepository.findById(orderID);
            Optional<Users> users = iUserRepository.findUsersById(orders.get().getCustomer().getId());
            Fishes fishes = fishService.findFishById(fishID);

            LocalDate date = LocalDate.now();

            List<OrderDetails> orderDetailsList = orderDetailRepository.findByFishesIdAndApprovalStatus(fishID, FeedbackStatus.Accepted.toString());
            float totalRating = 0 ;
            for(OrderDetails od : orderDetailsList) {
                totalRating += od.getRating();
            }
            float avgRating = totalRating / orderDetailsList.size();

            fishes.setRating(avgRating);
            Evaluations evaluations = new Evaluations(fishes,date,users.get().getUserName(),orderDetails.getRating(),orderDetails.getFeedback());
            evaluationRepository.save(evaluations);
            return "Approve feedback successfully";
        }else return "OrderDetail is not available";
    }
}