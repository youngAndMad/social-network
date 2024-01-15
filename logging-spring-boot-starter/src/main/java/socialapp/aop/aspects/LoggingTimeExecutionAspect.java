package socialapp.aop.aspects;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.concurrent.TimeUnit;

/**
 * The {@code LoggableAspect} class is an AspectJ aspect responsible for logging method executions
 * of classes or methods annotated with the {@link socialapp.aop.annotations.LoggableTime} annotation.
 *
 * <p>This aspect intercepts method calls and logs information about the method execution, including
 * the method name, execution time, and a message indicating the start and end of execution.
 * It is designed to work in conjunction with Spring AOP.
 *
 * <p>Example usage:
 * <pre>
 * // Annotate a class or method with @Loggable
 * @Loggable
 * public class MyService {
 *     public void doSomething() {
 *         // Method implementation
 *     }
 * }
 * </pre>
 * <p>
 * When methods within the annotated class are invoked, this aspect will log information about their execution.
 * The aspect can be enabled or disabled globally by enabling or disabling component scanning of the package
 * where this aspect is defined.
 */
@Aspect
@Slf4j
public class LoggingTimeExecutionAspect {

    @Pointcut("within(@socialapp.aop.annotations.LoggableTime *) && execution(* *(..))")
    public void annotatedByLoggable() {
    }

    @Around("annotatedByLoggable()")
    public Object logMethodExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        final var stopWatch = new StopWatch();

        stopWatch.start();
        var result = pjp.proceed();
        stopWatch.stop();

        log.info(
                "%s.%s :: %s ms".formatted(
                        methodSignature.getDeclaringType().getSimpleName(), // class name
                        methodSignature.getName(), // method name
                        stopWatch.getTime(TimeUnit.MILLISECONDS) // execution time in milliseconds
                )
        );

        return result;
    }

}

