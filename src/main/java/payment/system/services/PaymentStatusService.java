package payment.system.services;

import org.springframework.stereotype.Service;
import payment.system.PaymentStatus;
import payment.system.entity.Payment;
import payment.system.repository.PaymentRepository;

import java.util.UUID;

@Service
public class PaymentStatusService {
    private final PaymentRepository paymentRepository;

    public PaymentStatusService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public PaymentStatus getPaymentStatus(UUID identifier) {
        Payment payment = paymentRepository.findByIdentifier(identifier);
        return payment.getStatus();
    }
}
