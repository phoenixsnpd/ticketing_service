package payment.system.controlers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import payment.system.PaymentStatus;
import payment.system.dto.PaymentDTO;
import payment.system.entity.Payment;
import payment.system.services.PaymentService;
import payment.system.util.PaymentErrorResponse;
import payment.system.util.PaymentNotCreatedException;
import payment.system.util.PaymentNotFoundException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentsControler {

    private final PaymentService paymentService;

    public PaymentsControler(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping()
    public UUID pay(@RequestBody @Valid PaymentDTO paymentDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new PaymentNotCreatedException(errorMessage.toString());
        }
        Payment payment = convertPaymentDTO(paymentDTO);
        paymentService.createPayment(payment);
        return payment.getIdentifier();
    }

    @GetMapping("/status/{identifier}")
    public PaymentStatus getPaymentStatus(@PathVariable UUID identifier) {
        PaymentStatus paymentStatus = paymentService.getPaymentStatus(identifier);
        if (paymentStatus == null) {
            throw new PaymentNotFoundException();
        }
        return paymentService.getPaymentStatus(identifier);
    }

    @ExceptionHandler
    private ResponseEntity<PaymentErrorResponse> handlerException(PaymentNotFoundException ex) {
        PaymentErrorResponse paymentErrorResponse = new PaymentErrorResponse("Identifier wasn't find");
        return new ResponseEntity<>(paymentErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<PaymentErrorResponse> handlerException(PaymentNotCreatedException ex) {
        PaymentErrorResponse paymentErrorResponse = new PaymentErrorResponse(ex.getMessage());
        return new ResponseEntity<>(paymentErrorResponse, HttpStatus.BAD_REQUEST);
    }

    private Payment convertPaymentDTO(PaymentDTO paymentDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Payment payment = modelMapper.map(paymentDTO, Payment.class);
        payment.setIdentifier(UUID.randomUUID());
        payment.setStatus(PaymentStatus.getRandomStatus());
        return payment;
    }
}
