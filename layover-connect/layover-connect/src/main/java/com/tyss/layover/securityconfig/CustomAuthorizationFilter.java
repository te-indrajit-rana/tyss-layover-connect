package com.tyss.layover.securityconfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tyss.layover.entity.UserDetails;
import com.tyss.layover.exception.CustomAccessDeniedException;
import com.tyss.layover.exception.TokenExpiredException;
import com.tyss.layover.repository.UserRepository;
import com.tyss.layover.util.JwtUtils;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

	private final CustomAccessDeniedException accessDenied;

	private final JwtUtils jwtUtils;

	private final UserRepository userRepository;

	@SuppressWarnings("unchecked")
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer ")) {
			try {
				String token = header.substring(7);
				jwtUtils.validateJwtToken(token);
				Claims claims = jwtUtils.getClaimsFromToken(token);
				String username = jwtUtils.getUsernameFromToken(token);
				verification(request, response, username);
				List<String> roles = (List<String>) claims.get("roles");
				Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
				roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(token,
						null, authorities);
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				filterChain.doFilter(request, response);
			} catch (Exception exception) {
				try {
					accessDenied.handle(request, response, new AccessDeniedException(exception.getMessage()));
				} catch (Exception exception2) {
					log.error(exception2.getMessage());
				}
			}
		} else
			filterChain.doFilter(request, response);
	}

	private void verification(HttpServletRequest request, HttpServletResponse response, String username) {
		UserDetails userDetails = userRepository.findByEmail(username).orElse(null);
		if (userDetails == null) {
			try {
				log.error("Unauthorized Access Token");
				accessDenied.handle(request, response, new AccessDeniedException("Unauthorized Access Token"));
			} catch (Exception exception2) {
				throw new TokenExpiredException("Something Went Wrong");
			}

		}
	}

}
