package com.todo.todolist.config;

import com.todo.todolist.domain.UserEntity;
import com.todo.todolist.model.exception.EntityNotFoundException;
import com.todo.todolist.repository.UserRepository;
import com.todo.todolist.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private static final String INVALID_TOKEN = "Invalid token";

  private static final List<String> ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = List.of(
      "/user/login",
      "/user/create"
  );

  private final JwtUtil jwtUtil;

  private final UserRepository userRepository;

  private final HandlerExceptionResolver handlerExceptionResolver;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {
    if (checkIfEndpointIsNotPublic(request)) {
      String token = recoveryToken(request);
      if (token != null) {
        try {
          String subject = jwtUtil.extractUserEmail(token);
          UserEntity user = userRepository.findByEmail(subject)
              .orElseThrow(() -> new EntityNotFoundException("User not found"));

          if (!jwtUtil.isTokenValid(token, user.getEmail())) {
            response.sendError(403, INVALID_TOKEN);
          }
        } catch (Exception e) {
          response.sendError(400, INVALID_TOKEN);
        }
      } else {
        response.sendError(401, INVALID_TOKEN);
      }
    }
    filterChain.doFilter(request, response);
  }

  private String recoveryToken(HttpServletRequest request) {
    String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader != null) {
      return authorizationHeader.replace("Bearer ", "");
    }
    return null;
  }

  private boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
    String requestURI = request.getRequestURI();
    return !ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED.contains(requestURI);
  }
}
