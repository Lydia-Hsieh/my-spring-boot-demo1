package com.example.my_spring_boot_demo1.filter;

import com.example.my_spring_boot_demo1.controller.loginController.pojo.LoginDto;
import com.example.my_spring_boot_demo1.dao.repository.AccountRepository;
import com.example.my_spring_boot_demo1.entity.Account_Entity;
import com.example.my_spring_boot_demo1.util.JwtUtil;
import com.example.my_spring_boot_demo1.util.UserUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private AccountRepository accountRepository;

    private final List<String> EXCLUDE_PATHS = List.of(
            "/account-login",
            "/register",
            "/swagger-ui",
            "/api-docs"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (EXCLUDE_PATHS.stream().anyMatch(requestURI::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }

        //驗證token
        String userId = null;
        try {
            String authHeader = request.getHeader("Authorization");
            String authToken = authHeader.split("Bearer ")[1];
            userId = JwtUtil.parseToken(authToken);

            Optional<Account_Entity> accountOptional = accountRepository.findByUserId(userId);
            if (accountOptional.isPresent()) {
                Account_Entity account = accountOptional.get();
                //創建Spring Security的Authentication物件，並存入SecurityContext
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(account, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("status", false);
            resultMap.put("errorMessage", e.getMessage());
            response.getWriter().write(new ObjectMapper().writeValueAsString(resultMap));
        }
    }
}
