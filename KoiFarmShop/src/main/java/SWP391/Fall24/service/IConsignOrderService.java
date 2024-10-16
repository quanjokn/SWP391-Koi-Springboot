package SWP391.Fall24.service;

import SWP391.Fall24.dto.request.ConsignApprovalRequest;
import SWP391.Fall24.dto.request.ConsignOrderRequest;
import SWP391.Fall24.dto.response.ConsignOrderResponse;
import SWP391.Fall24.pojo.ConsignOrders;
import SWP391.Fall24.pojo.ConsignedKois;
import SWP391.Fall24.pojo.Users;

import java.util.List;
import java.util.Optional;

public interface IConsignOrderService{
    ConsignOrderResponse createOrder(ConsignOrderRequest consignOrderRequest, int userId);

    ConsignOrderResponse getDetail(int id);

    String receiveConsignOrder(int staffID, int orderID);

    String approvalResponse(ConsignApprovalRequest approvalRequest, int staffID);

    List<ConsignOrders> findByStatus(String status);

    List<ConsignOrders> getReceivingOrder(int staffID);

    String completeOrder(int staffID, int orderID);
}
