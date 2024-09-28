package com.example.driver_load.configs.security;

import com.example.driver_load.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class JwtRequestFilter implements WebFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final ServerAuthenticationEntryPoint serverAuthenticationEntryPoint;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                String username = jwtUtil.getSubject(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    return customUserDetailsService.findByUsername(username)
                            .map(userDetails -> new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())).flatMap(auth -> {
                                        SecurityContext securityContext = new SecurityContextImpl(auth);
                                        return chain.filter(exchange)
                                                .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));
                                    }
                            );
                }
            }
        }catch (Exception e){
            AuthenticationException authenticationException = new BadCredentialsException(e.getMessage());
            return serverAuthenticationEntryPoint.commence(exchange, authenticationException);
        }
        return chain.filter(exchange);
    }
}
