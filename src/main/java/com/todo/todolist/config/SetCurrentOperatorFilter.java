package com.todo.todolist.config;

import com.todo.todolist.util.JwtUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SetCurrentOperatorFilter implements Filter {

  private final JwtUtil jwtUtil;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    try {
      HttpServletRequest httpRequest = (HttpServletRequest) request;
      getOperatorId(httpRequest)
          .or(() -> Optional.ofNullable((httpRequest.getRequestURI())))
          .ifPresent(CurrentOperator::setOperator);
    } catch (Exception ex) {
      log.warn("SetCurrentOperatorFilter.doFilter(): Cannot get operatorId parameter or requestUri");
    }

    try {
      chain.doFilter(request, response);
    } finally {
      CurrentOperator.removeOperator();
    }
  }

  private Optional<String> getOperatorId(HttpServletRequest httpRequest) {
    String token = httpRequest.getHeader("Authorization");
    return Optional.ofNullable(token)
        .filter(t -> t.startsWith("Bearer "))
        .map(t -> t.substring(7))
        .map(jwtUtil::extractUserEmail)
        .or(() -> Optional.ofNullable(httpRequest.getParameter("operatorId")));
  }
}
