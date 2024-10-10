package SWP391.Fall24.service;

import SWP391.Fall24.dto.request.ConsignApprovalRequest;
import SWP391.Fall24.dto.request.ConsignOrderRequest;
import SWP391.Fall24.dto.response.ConsignOrderResponse;
import SWP391.Fall24.pojo.Users;

public interface IConsignOrderService{
    ConsignOrderResponse createOrder(ConsignOrderRequest consignOrderRequest, int userId);

    ConsignOrderResponse getDetail(int id);

//    ConsignOrderService approvalResponse(ConsignApprovalRequest consignApprovalRequest);

}
