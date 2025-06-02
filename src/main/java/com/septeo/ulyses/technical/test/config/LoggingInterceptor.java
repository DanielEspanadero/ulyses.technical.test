package com.septeo.ulyses.technical.test.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class LoggingInterceptor implements HandlerInterceptor {


    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        String log = String.format(
                "%s - %s %s - Status: %d - Duration: %d ms\n",
                LocalDateTime.now().format(formatter),
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus(),
                duration
        );

        writeLog(log);
    }

    private synchronized void writeLog(String log) {
        try (FileWriter fw = new FileWriter("api-requests.log", true)) {
            fw.write(log);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
