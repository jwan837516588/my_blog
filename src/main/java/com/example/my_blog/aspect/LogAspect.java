package com.example.my_blog.aspect;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.spi.ObjectThreadContextMap;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Component
@Aspect
public class LogAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.example.my_blog.web.*.*(..))")
    public void log() {}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        String uri = request.getRequestURI();
        String ip = request.getRemoteAddr();
        String contextPath = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(uri, ip, contextPath, args);

        logger.info("RequestLog: {}", requestLog);
    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Object result) {
        logger.info("result {}", result);
    }

    private class RequestLog {
        private String url;
        private String ip;
        private String classpath;
        private Object[] args;

        public RequestLog(String url, String ip, String classpath, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classpath = classpath;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classpath='" + classpath + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
