package bedu.reservation_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import bedu.reservation_system.models.Tokens;

@Repository
public interface TokenRepository extends JpaRepository<Tokens, Integer> {
    Tokens findByToken(int token);
}