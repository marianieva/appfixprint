package adv.security.jwt;

import java.security.Key;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;

import adv.security.dto.JwtDto;
import adv.security.entity.UsuarioPrincipal;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtProvider {
	
	private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);
	
	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expiration}")
	private int expiration;
	
	public String generateToken(Authentication authentication) {
		UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();
		return Jwts.builder().setSubject(usuarioPrincipal.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + expiration * 1000))
				.signWith(getSecret(secret))
				.compact();
	}

	public String getUsernameFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(getSecret(secret)).build().parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			
			Jwts.parserBuilder().setSigningKey(getSecret(secret)).build().parseClaimsJws(token);
			return true;
			
		} catch (MalformedJwtException e) {
			logger.error("Token mal formado");
		} catch (UnsupportedJwtException e) {
			logger.error("Token no soportado");
		} catch (ExpiredJwtException e) {
			logger.error("Token expirado");
		} catch (IllegalArgumentException e) {
			logger.error("Token vacio");
		} catch (SignatureException e) {
			logger.error("Token en la firma");
		}
		
		return false;
	}
	
	public String refrehToken(JwtDto jwtDto) throws ParseException{
		
		try {
			Jwts.parserBuilder().setSigningKey(getSecret(secret)).build().parseClaimsJws(jwtDto.getToken());
		} catch (ExpiredJwtException e) {
			
			JWT jwt = JWTParser.parse(jwtDto.getToken());
			JWTClaimsSet claims = jwt.getJWTClaimsSet();
			String username = claims.getSubject();
			List<String> roles = (List<String>) claims.getClaim("roles");
			
			return Jwts.builder()
					.setSubject(username)
					.claim("roles", roles)
					.setIssuedAt(new Date())
					.setExpiration(new Date(new Date().getTime() + expiration))
					.signWith(getSecret(secret))
					.compact();
		}
		return null;
	}
	
	private Key getSecret(String secret) {
		byte[] secretBytes = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(secretBytes);
	}
}
