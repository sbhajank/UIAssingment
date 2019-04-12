package com.afkl.exercises.spring.matrics;

import com.afkl.exercises.spring.matrics.metric.ICustomActuatorMetricService;
import com.afkl.exercises.spring.matrics.metric.IMetricService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyAsyncHandlerInterceptor extends  HandlerInterceptorAdapter {
    private static final Logger logger = LogManager.getLogger(MyAsyncHandlerInterceptor.class);

    @Autowired
    private IMetricService metricService;

    @Autowired
    private ICustomActuatorMetricService actMetricService;
    @Override
    public boolean preHandle (HttpServletRequest request,
                              HttpServletResponse response,
                              Object handler) throws Exception {

        System.out.println("interceptor#preHandle called. Thread: " + Thread
                .currentThread().getName());
        return true;

    }

    @Override
    public void postHandle (HttpServletRequest request,
                            HttpServletResponse response,
                            Object handler,
                            ModelAndView modelAndView) throws Exception {

        System.out.println("interceptor#postHandle called. Thread: " +
                Thread.currentThread()
                        .getName());
    }

    @Override
    public void afterCompletion (HttpServletRequest request,
                                 HttpServletResponse response,
                                 Object handler, Exception ex) throws Exception {

        final String req = request.getMethod() + " " + request.getRequestURI();
        final int status = response.getStatus();
        metricService.increaseCount(req, status);
        actMetricService.increaseCount(status);
        System.out.println("interceptor#afterCompletion called Thread.: " +
                Thread.currentThread()
                        .getName());
    }

    @Override
    public void afterConcurrentHandlingStarted (HttpServletRequest request,
                                                HttpServletResponse response,
                                                Object handler) throws Exception {


        System.out.println("interceptor#afterConcurrentHandlingStarted called. " +
                "Thread: " +
                Thread.currentThread()
                        .getName());
    }
}
