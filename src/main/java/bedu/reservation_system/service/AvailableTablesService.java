package bedu.reservation_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bedu.reservation_system.models.AvailableTables;
import bedu.reservation_system.repository.AvailableTablesRepository;

@Service
public class AvailableTablesService {

    @Autowired
    private AvailableTablesRepository availableTablesRepository;

    public AvailableTables findByNumTable(int numTable) {
        return availableTablesRepository.findByNumTable(numTable);
    }

    @SuppressWarnings("null")
    public AvailableTables save(AvailableTables table) {
        return availableTablesRepository.save(table);
    }

}