package SWP391.Fall24.service;

import SWP391.Fall24.dto.request.HealthUpdationRequest;
import SWP391.Fall24.exception.AppException;
import SWP391.Fall24.exception.ErrorCode;
import SWP391.Fall24.pojo.CaredKois;
import SWP391.Fall24.pojo.HealthUpdation;
import SWP391.Fall24.repository.ICaredKoiRepository;
import SWP391.Fall24.repository.IHealthUpdationRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthUpdationService implements IHealthUpdationService {

    @Autowired
    private ICaredKoiRepository icaredKoiRepository;

    @Autowired
    private IHealthUpdationRepository healthUpdationRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public void saveUpdation(HealthUpdationRequest healthUpdationRequest) throws MessagingException {
        HealthUpdation healthUpdation = new HealthUpdation();
        CaredKois caredKois = icaredKoiRepository.findById(healthUpdationRequest.getCaredKoiID()).orElseThrow(()-> new AppException(ErrorCode.FISH_NOT_EXISTED));
        healthUpdation.setCaredKoi(caredKois);
        healthUpdation.setEvaluation(healthUpdationRequest.getEvaluation());
        healthUpdation.setDate(healthUpdationRequest.getDate());
        healthUpdation.setPhoto("/images/"+healthUpdationRequest.getPhoto());
        healthUpdation.setStatus(true);
        emailService.sendMail(caredKois.getCaringOrder().getCustomer().getEmail(), emailService.subjectOrder(caredKois.getCaringOrder().getCustomer().getName()), emailService.emailHealthUpdate(caredKois.getCaringOrder()));
        healthUpdationRepository.save(healthUpdation);
    }
}
