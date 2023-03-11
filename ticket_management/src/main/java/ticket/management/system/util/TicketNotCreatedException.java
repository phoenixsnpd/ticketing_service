package ticket.management.system.util;

public class TicketNotCreatedException extends RuntimeException {
    public TicketNotCreatedException(String msg) {
        super(msg);
    }
}
