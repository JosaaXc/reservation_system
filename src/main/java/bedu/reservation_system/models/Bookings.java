package bedu.reservation_system.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
public class Bookings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String name;
    
    private LocalDate date;
    private LocalTime time;

    @Min(1)
    @Max(6)
    private int numberPeople;

    @Min(1)
    @Max(15)
    private int numTable;

    @Pattern(regexp="(^$|[0-9]{10})")
    private String phoneNumber;

    @OneToOne
    private Tokens token;

    @OneToOne
    private Tickets ticket;

    private boolean validated = false;
}