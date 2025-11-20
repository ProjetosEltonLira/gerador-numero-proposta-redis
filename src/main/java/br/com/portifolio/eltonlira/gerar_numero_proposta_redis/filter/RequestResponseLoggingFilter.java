package br.com.portifolio.eltonlira.gerar_numero_proposta_redis.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Wrap para conseguir ler o body depois
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        long start = System.currentTimeMillis();
        filterChain.doFilter(requestWrapper, responseWrapper);
        long duration = System.currentTimeMillis() - start;

        logRequest(requestWrapper);
        logResponse(responseWrapper, duration);

        // Copiar o body de volta para o response real
        responseWrapper.copyBodyToResponse();
    }

    private void logRequest(ContentCachingRequestWrapper request) {

        String body = new String(request.getContentAsByteArray(), StandardCharsets.UTF_8);

        // Container/pod que está processando
        String podName = System.getenv("HOSTNAME");

        logger.info("\n--- REQUEST RECEIVED ---" +
                "\nPod: " + podName +
                "\nMethod: " + request.getMethod() +
                "\nURI: " + request.getRequestURI() +
                "\nQuery: " + (request.getQueryString() != null ? request.getQueryString() : "") +
                "\nHeaders: " + request.getHeaderNames().asIterator().toString() +
                "\nBody: " + body +
                "\n------------------------");
    }

    private void logResponse(ContentCachingResponseWrapper response, long duration) {

        String body = new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);

        String podName = System.getenv("HOSTNAME");

        logger.info("\n--- RESPONSE SENT ---" +
                "\nPod: " + podName +
                "\nStatus: " + response.getStatus() +
                "\nTime: " + duration + " ms" +
                "\nBody: " + body +
                "\n----------------------");
    }
}
