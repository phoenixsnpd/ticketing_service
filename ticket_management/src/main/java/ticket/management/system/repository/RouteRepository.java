package ticket.management.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ticket.management.system.entity.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
}
