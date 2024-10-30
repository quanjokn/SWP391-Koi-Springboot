package SWP391.Fall24.service;

import SWP391.Fall24.dto.request.HealthUpdationRequest;
import jakarta.mail.MessagingException;

public interface IHealthUpdationService {
    public void saveUpdation(HealthUpdationRequest healthUpdationRequest) throws MessagingException;
}
