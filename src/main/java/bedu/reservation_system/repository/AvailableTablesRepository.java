package bedu.reservation_system.repository;

import bedu.reservation_system.models.AvailableTables;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailableTablesRepository extends JpaRepository<AvailableTables, Integer> {
    AvailableTables findByNumTable(int numTable);
}