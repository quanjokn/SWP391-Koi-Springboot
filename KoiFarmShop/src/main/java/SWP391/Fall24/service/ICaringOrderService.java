package SWP391.Fall24.service;

import SWP391.Fall24.dto.request.CareApprovalRequest;
import SWP391.Fall24.dto.request.CaringOrderRequest;
import SWP391.Fall24.dto.response.CaringOrderResponse;
import SWP391.Fall24.pojo.CaringOrders;
import jakarta.mail.MessagingException;

import java.util.List;

public interface ICaringOrderService {
    String createCaringOrder(CaringOrderRequest request, int userId);

    CaringOrderResponse getDetail(int caringOrderId);

    String approvalCaringOrder(CareApprovalRequest approvalRequest) throws MessagingException;

    List<CaringOrders> getCaringOrdersByStatus(String status);

    String receivingCaringOrder(int staffID, int orderID);

    List<CaringOrders> getReceivingOrder(int staffID);

    String completeOrder(int staffID, int orderID) throws MessagingException;


}
