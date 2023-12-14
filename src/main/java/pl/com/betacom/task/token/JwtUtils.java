package pl.com.betacom.task.token;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import pl.com.betacom.task.repositories.UserRepository;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${betacom.app.jwtSecret}")
    private String jwtSecret;

    @Value("${betacom.app.jwtExpirationInMs}")
    private long jwtExpirationMs;
    private final UserRepository userRepository;

    public JwtUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateRequestToken(String authToken) {
        if (authToken == null || !authToken.startsWith("Bearer ")) {
            return false;
        }
        String token = authToken.substring(7);
        if (!validateJwtToken(token)) {
            return false;
        }
        final String userLogin = extractLogin(token);
        return userLogin != null && userRepository.existsByLogin(userLogin);
    }
    public String extractLogin(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    private boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT string is empty: {}", e.getMessage());
        }
        return false;
    }
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}