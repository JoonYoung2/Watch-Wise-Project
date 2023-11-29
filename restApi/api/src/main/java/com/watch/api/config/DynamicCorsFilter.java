package com.watch.api.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.watch.api.repository.CorsConfigRepository;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsProcessor;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DynamicCorsFilter extends OncePerRequestFilter {

    private final CorsConfigRepository repo;
    private final CorsProcessor corsProcessor;

    public DynamicCorsFilter(CorsConfigRepository repo, CorsProcessor corsProcessor) {
        this.repo = repo;
        this.corsProcessor = corsProcessor;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String[] allowedOrigins = repo.getAllowedOrigins();

        // 동적으로 CORS 설정을 변경
        addCorsMappings(request, response, allowedOrigins);

        // 필터 체인 실행
        filterChain.doFilter(request, response);
    }

    private void addCorsMappings(HttpServletRequest request, HttpServletResponse response, String[] allowedOrigins) {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of(allowedOrigins));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        corsConfiguration.setAllowedHeaders(List.of("Content-Type"));
        corsConfiguration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        // CorsProcessor를 사용하여 설정 적용
        if (CorsUtils.isCorsRequest(request)) {
            try {
				corsProcessor.processRequest(corsConfiguration, request, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
}