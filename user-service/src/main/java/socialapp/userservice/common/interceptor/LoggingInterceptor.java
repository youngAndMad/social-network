package socialapp.userservice.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import static socialapp.userservice.common.AppConstants.REQUEST_START_TIME;

@Component
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        var startTime = System.currentTimeMillis();
        request.setAttribute(REQUEST_START_TIME, startTime);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView
    ) throws Exception {
        var startTime = (Long) request.getAttribute(REQUEST_START_TIME);
        var totalExecution = System.currentTimeMillis() - startTime;

        log.info(
                "Completed request - uri: {}, status: {}, duration: {} ms, remoteUser: {}",
                request.getRequestURI(),
                response.getStatus(),
                totalExecution,
                request.getRemoteUser()
        );

        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
