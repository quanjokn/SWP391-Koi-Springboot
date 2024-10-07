package SWP391.Fall24.service;

import SWP391.Fall24.dto.request.CaringOrderRequest;

public interface ICaringOrderService {
    String createCaringOrder(CaringOrderRequest request, int userId);
}
