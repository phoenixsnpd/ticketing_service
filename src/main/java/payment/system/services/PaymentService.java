package payment.system.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import payment.system.PaymentStatus;
import payment.system.entity.Payment;
import payment.system.repository.PaymentRepository;
import payment.system.util.PaymentNotFoundException;

import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public void createPayment(Payment payment) {
        paymentRepository.save(payment);
    }

    public PaymentStatus getPaymentStatus(UUID identifier) {
        Payment payment = paymentRepository.findByIdentifier(identifier);
        if (payment == null) {
            throw new PaymentNotFoundException();
        }
        return payment.getStatus();
    }
}