package bedu.reservation_system.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tokens")
@Data
@NoArgsConstructor
public class Tokens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int token;

    @OneToOne(mappedBy = "token")
    @JsonBackReference
    private Bookings booking;
}