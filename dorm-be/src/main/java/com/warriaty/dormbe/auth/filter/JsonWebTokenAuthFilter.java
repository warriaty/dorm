package com.warriaty.dormbe.auth.filter;

import com.warriaty.dormbe.auth.jwt.JwtProvider;
import com.warriaty.dormbe.auth.service.UserDetailsServiceImpl;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.authentication.UsernamePasswordAuthenticationToken.authenticated;

@Component
@RequiredArgsConstructor
public class JsonWebTokenAuthFilter extends OncePerRequestFilter {

    private static final Pattern TOKEN_REGEX = Pattern.compile("Bearer (?<token>.+)");
    private static final WebAuthenticationDetailsSource AUTH_DETAILS_SOURCE = new WebAuthenticationDetailsSource();

    private final JwtProvider tokenProvider;

    private final UserDetailsServiceImpl userPrincipalDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            getJwtFromRequest(request).ifPresent(jwt -> authenticateToken(request, jwt));
        } catch (JwtException exc) {
            logger.error("Could not authenticate the token", exc);
        }
        filterChain.doFilter(request, response);
    }

    private void authenticateToken(HttpServletRequest request, String jwt) throws JwtException {
        String username = tokenProvider.getUsernameFromJwt(jwt);
        UserDetails userDetails = userPrincipalDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authentication = createSuccessAuthentication(userDetails, request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private static Optional<String> getJwtFromRequest(HttpServletRequest request) {
        return ofNullable(request.getHeader(AUTHORIZATION))
                .map(TOKEN_REGEX::matcher)
                .filter(Matcher::matches)
                .map(tokenMatcher -> tokenMatcher.group("token"));
    }

    /**
     * Inspired by {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider#createSuccessAuthentication(Object, Authentication, UserDetails)}
     */
    private UsernamePasswordAuthenticationToken createSuccessAuthentication(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authentication = authenticated(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        authentication.setDetails(AUTH_DETAILS_SOURCE.buildDetails(request));
        return authentication;
    }
}
