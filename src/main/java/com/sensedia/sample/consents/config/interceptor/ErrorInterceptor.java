package com.sensedia.sample.consents.config.interceptor;

import com.sensedia.sample.consents.config.exception.enums.TitleEnum;
import com.sensedia.sample.consents.config.exception.response.ErrorMessage;
import com.sensedia.sample.consents.config.exception.ErrorContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;

@Component
public class ErrorInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String locale = request.getHeader("Accept-Language");
        String method = null;
        HashMap<String, TitleEnum> supportedOperations = new HashMap<>();
        supportedOperations.put("POST", TitleEnum.CREATING);
        supportedOperations.put("GET", TitleEnum.SEARCHING_BY_ID);
        supportedOperations.put("PUT", TitleEnum.CHANGING);
        supportedOperations.put("DELETE", TitleEnum.DELETING);

        if(request.getRequestURI().contains("/consents"))
            method = request.getMethod();

        ErrorContext.setErrorMessage(new ErrorMessage(null, null), locale, supportedOperations.get(method));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ErrorContext.clear();
    }
}
