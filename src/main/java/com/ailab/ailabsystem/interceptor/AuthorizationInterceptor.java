package com.ailab.ailabsystem.interceptor;

import com.ailab.ailabsystem.annotation.AuthCheck;
import com.ailab.ailabsystem.enums.ResponseStatusEnum;
import com.ailab.ailabsystem.enums.UserRole;
import com.ailab.ailabsystem.exception.CustomException;
import com.ailab.ailabsystem.model.entity.User;
import com.ailab.ailabsystem.util.RedisOperator;
import com.ailab.ailabsystem.util.UserHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xiaozhi
 * @description 权限拦截器
 * @create 2022-11-2022/11/12 14:01
 */
@Aspect
@Component
public class AuthorizationInterceptor {

    @Resource
    private RedisOperator redis;

    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint,
                                AuthCheck authCheck) throws Throwable {
        // 获取注解上的角色
        UserRole userRole = authCheck.mustRole();
        User user = UserHolder.getUser();
        // 有对应权限才通过
        if (user.getUserRight() != userRole.getCode()) {
            throw new CustomException(ResponseStatusEnum.NO_AUTH_ERROR);
        }
        // 权限通过，放行
        return joinPoint.proceed();
    }

}
