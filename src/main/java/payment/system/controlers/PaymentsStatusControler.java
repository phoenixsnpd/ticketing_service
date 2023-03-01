package payment.system.controlers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import payment.system.PaymentStatus;
import payment.system.services.PaymentStatusService;

import java.util.UUID;

@RestController
@RequestMapping("/statuses")
public class PaymentsStatusControler {
    private final PaymentStatusService paymentStatusService;

    public PaymentsStatusControler(PaymentStatusService paymentStatusService) {
        this.paymentStatusService = paymentStatusService;
    }

    @GetMapping("/get/{identifier}")
    public PaymentStatus getPaymentStatus(@PathVariable UUID identifier) {
        return paymentStatusService.getPaymentStatus(identifier);
    }
}
