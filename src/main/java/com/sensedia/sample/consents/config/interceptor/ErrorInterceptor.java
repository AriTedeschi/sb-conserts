package com.sensedia.sample.consents.config.interceptor;

import com.sensedia.sample.consents.config.exception.response.ErrorMessage;
import com.sensedia.sample.consents.config.exception.ErrorContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ErrorInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String locale = request.getHeader("Accept-Language");
        ErrorContext.setErrorMessage(new ErrorMessage(null, null), locale);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ErrorContext.clear();
    }
}
