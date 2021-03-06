package top.ryzeyang.demo.common.aspect;

import cn.hutool.core.lang.UUID;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import top.ryzeyang.demo.common.api.CommonResult;

/**
 * @author Java4ye
 * @date 2020/12/25 上午 10:32
 * @微信公众号： Java4ye
 * @GitHub https://github.com/RyzeYang
 * @博客 https://blog.csdn.net/weixin_40251892
 */
@Component
@Aspect
@Slf4j
public class ExceptionHandlerLogAspect {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.ExceptionHandler)")
    private void pointcut(){}

    /**
     * 唯一 ID， 方便在日志中直接定位到问题点
     * @param point
     * @return
     */
    @Around("pointcut()")
    public CommonResult<String> around(ProceedingJoinPoint point){
        String uuid = UUID.randomUUID().toString();
        log.error("Error uuid: {}" , uuid);
        try {
            CommonResult proceed = (CommonResult<String>)point.proceed();
            String msg = proceed.getMsg();
            proceed.setMsg(msg+" : "+uuid);
            return  proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }


}
