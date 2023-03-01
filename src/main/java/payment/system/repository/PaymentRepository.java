package payment.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import payment.system.entity.Payment;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByIdentifier(UUID identifier);
}
