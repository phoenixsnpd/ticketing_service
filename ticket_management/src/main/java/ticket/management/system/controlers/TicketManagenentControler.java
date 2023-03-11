package ticket.management.system.controlers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ticket.management.system.dto.TicketDTO;
import ticket.management.system.entity.Ticket;
import ticket.management.system.services.TicketManagementService;
import ticket.management.system.util.TicketNotCreatedException;
import ticket.management.system.util.TicketsNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/")
public class TicketManagenentControler {
    private final TicketManagementService ticketManagementService;

    public TicketManagenentControler(TicketManagementService ticketManagementService) {
        this.ticketManagementService = ticketManagementService;
    }

    @PostMapping("/buy")
    public long buyTickey(@RequestBody @Valid TicketDTO ticketDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append("; ");
            }
            throw new TicketNotCreatedException(errorMessage.toString());
        }
        Ticket ticket = TicketDTOConverter(ticketDTO);
        if (ticket.getRoute().getNumberOfTickets() <= 0) {
            throw new TicketsNotFoundException();
        }
        ticketDTO.setPaymentSum(ticket.getRoute().getTicketPrice());
        ticket.setPaymentIdentifier(ticketManagementService.paymentRequest(ticketDTO));
        ticket.getRoute().setNumberOfTickets(ticket.getRoute().getNumberOfTickets() - 1);
        ticket.setPaymentStatus(ticketManagementService.paymentStatusRequest(ticket.getPaymentIdentifier()));

        return ticketManagementService.buyTicket(ticket);
    }

    @GetMapping("/information/{id}")
    public String getTicketInformation(@PathVariable long id) {
        Ticket ticket = ticketManagementService.ticketInformation(id);
        return "Payment status: " + ticket.getPaymentStatus() + "\n" + "Route information: " + ticket.getRoute();
    }

    @ExceptionHandler(TicketsNotFoundException.class)
    private ResponseEntity<String> handlerException() {
        return new ResponseEntity<>("Tickets on this route have run out", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TicketNotCreatedException.class)
    private ResponseEntity<String> handlerException(TicketNotCreatedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private Ticket TicketDTOConverter(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setName(ticketDTO.getName());
        ticket.setSurname(ticketDTO.getSurname());
        ticket.setRoute(ticketManagementService.findRoute(ticketDTO.getRouteId()));

        return ticket;
    }
}
