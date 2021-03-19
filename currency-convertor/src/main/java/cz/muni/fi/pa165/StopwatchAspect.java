package cz.muni.fi.pa165;

import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.StopWatch;

import javax.inject.Named;
import java.util.Arrays;

@Aspect
@Named
public class StopwatchAspect {

    @Around("execution(public * *(..))")
    public Object measure(ProceedingJoinPoint pjp) throws Throwable {

        StopWatch stopWatch = new StopWatch();

        System.out.println("Calling method " + pjp.getSignature().getName() +
                " with parameters " + Arrays.asList(pjp.getArgs()));

        stopWatch.start();
        Object result = pjp.proceed();
        stopWatch.stop();

        System.out.println("Method " + pjp.getSignature().getName() + " finished in time " + stopWatch.shortSummary());

        return result;
    }
}
