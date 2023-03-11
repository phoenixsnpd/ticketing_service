package ticket.management.system.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ticket.management.system.dto.TicketDTO;
import ticket.management.system.entity.Route;
import ticket.management.system.entity.Ticket;
import ticket.management.system.repository.RouteRepository;
import ticket.management.system.repository.TicketRepository;

import java.util.Date;
import java.util.UUID;

@Service
public class TicketManagementService {
    private final RouteRepository routeRepository;
    private final TicketRepository ticketRepository;
    private final WebClient webClient;

    public TicketManagementService(RouteRepository routeRep, TicketRepository ticketRep, WebClient webClient) {
        this.routeRepository = routeRep;
        this.ticketRepository = ticketRep;
        this.webClient = webClient;

        routeRepository.save(new Route("A -> B", new Date(), 100, 1));
        routeRepository.save(new Route("B -> C", new Date(), 200, 10));
        routeRepository.save(new Route("C -> A", new Date(), 300, 10));


    }

    public long buyTicket(Ticket ticket) {
        ticketRepository.save(ticket);
        return ticket.getId();
    }

    public Ticket ticketInformation(Long id) {
        return ticketRepository.findById(id).get();
    }

    public UUID paymentRequest(TicketDTO ticketDTO) {
        return webClient.post()
                .uri("/payments")
                .body(Mono.just(ticketDTO), TicketDTO.class)
                .retrieve()
                .bodyToMono(UUID.class).block();
    }

    public String paymentStatusRequest(UUID identifier) {
        return webClient.get()
                .uri("/status/" + identifier.toString())
                .retrieve()
                .bodyToMono(String.class).block();
    }

    public Route findRoute(long id) {
        return routeRepository.findById(id).get();
    }
}
