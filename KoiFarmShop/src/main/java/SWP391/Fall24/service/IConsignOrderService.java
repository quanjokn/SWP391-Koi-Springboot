package SWP391.Fall24.service;

import SWP391.Fall24.dto.request.ConsignOrderRequest;
import SWP391.Fall24.pojo.Users;

public interface IConsignOrderService{
    String createOrder(ConsignOrderRequest consignOrderRequest, int userId);
}
