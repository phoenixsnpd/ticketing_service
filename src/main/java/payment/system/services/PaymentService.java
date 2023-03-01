package payment.system.services;

import org.springframework.stereotype.Service;
import payment.system.entity.Payment;
import payment.system.repository.PaymentRepository;

import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public UUID createPayment(String name, String surname, int paymentSum) {
        Payment payment = new Payment(name, surname, paymentSum);
        paymentRepository.save(payment);
        return payment.getIdentifier();
    }
}