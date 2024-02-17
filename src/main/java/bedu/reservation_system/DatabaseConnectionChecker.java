package bedu.reservation_system;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class DatabaseConnectionChecker {

    private final DataSource dataSource;

    public DatabaseConnectionChecker(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public CommandLineRunner checkConnection() {
        return args -> {
            try (Connection connection = dataSource.getConnection()) {
                System.out.println("Conexión a la base de datos exitosa!");
            } catch (Exception e) {
                System.out.println("Fallo al conectar a la base de datos!");
                e.printStackTrace();
            }
        };
    }
}
