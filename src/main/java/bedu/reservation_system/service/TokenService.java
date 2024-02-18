package bedu.reservation_system.service;

import org.springframework.stereotype.Service;
import bedu.reservation_system.models.Tokens;
import bedu.reservation_system.repository.TokenRepository;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Tokens findByToken(int token) {
        return tokenRepository.findByToken(token);
    }
}