package com.twd.technicaltest.infrastructure.exception.logger;

import com.twd.technicaltest.infrastructure.exception.dto.ResponseErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.JoinPoint;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;


@Aspect
@Component
@RequiredArgsConstructor
public class LogExceptionImpl {

    private final HttpServletRequest httpServletRequest;

    @AfterReturning(pointcut = "@annotation(LogException)", returning = "responseEntity")
    public void logAfterReturning(JoinPoint joinPoint, ResponseEntity<ResponseErrorDTO> responseEntity){
        Long currentThreadId = Thread.currentThread().threadId();
        Logger logger = getLogger(joinPoint);
        HttpStatusCode httpStatusCode = responseEntity.getStatusCode();
        ResponseErrorDTO responseErrorDTO = responseEntity.getBody();
        if(responseErrorDTO == null || ObjectUtils.isEmpty(responseErrorDTO)) return;
        logger.error("[Thread-{}] {}", currentThreadId, responseErrorDTO.getMessage());
        logger.info("[Thread-{}] [Fetch: {}] [Status: {}]", currentThreadId, httpServletRequest.getRequestURL(), httpStatusCode);
    }

    private Logger getLogger(JoinPoint joinPoint) {
        Class<?> clazz = joinPoint.getTarget().getClass();
        return LogManager.getLogger(clazz);
    }
}
