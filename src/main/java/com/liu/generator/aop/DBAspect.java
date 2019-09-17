package com.liu.generator.aop;

import com.alibaba.fastjson.JSON;
import com.liu.generator.annotation.DBAnnotation;
import com.liu.generator.config.DBContextHolder;
import com.liu.generator.entity.DBTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 暂时不使用,后期加上
 * 数据源AOP切面定义
 */
@Component
@Aspect
//@Order(-100)//这是为了保证AOP在事务注解之前生效,Order的值越小,优先级越高
@Slf4j
public class DBAspect {
    //切换放在mapper接口的方法上或添加DBAnnotation注解的类和类的方法上，所以这里要配置AOP切面的切入点
    //以下不许同时存在
//    @Pointcut("execution(* com.liu.system.mapper..*.*(..))")//用这个扫描会导致从父类继承来的方法无法切入用下面那个
    @Pointcut("within(com.baomidou.mybatisplus.core.mapper.BaseMapper+)")
//    @Pointcut("execution(* com.liu.*.service..*.*(..))")//用这个扫描会导致从父类继承来的方法无法切入用下面那个
//    @Pointcut("within(com.baomidou.mybatisplus.extension.service.IService+)")
    private void pointcut() {
    }
//    @Around("pointcut()")
    public Object xxx(ProceedingJoinPoint joinPoint) throws Throwable {
	    // 用的最多，通知的签名
	    Signature signature = joinPoint.getSignature();
        try {
	        long start = System.currentTimeMillis();
	        Object object = joinPoint.proceed();
	        long end = System.currentTimeMillis();
	        log.info(signature.getDeclaringTypeName()+"."+signature.getName() + "()"
			        +",方法的返回值是：" + JSON.toJSONString(object)
			        +",执行耗时" + (end - start) + "ms");
	        return object;
        } catch (Throwable e) {
            throw e;
        }
    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        try {
            Object target = joinPoint.getTarget();
            // 获取接口
            Class<?> clazz = target.getClass().getInterfaces()[0];
            // 获取方法
            Method method=((MethodSignature)joinPoint.getSignature()).getMethod();
            //获取方法不是空的
            if(method != null) {
                if(method.getDeclaringClass().isInterface()){
                    //如果方法在接口里
                }else if(!method.getDeclaringClass().isPrimitive()){
                    //如果方法在类里
//                clazz = method.getDeclaringClass();
                    clazz = target.getClass();
                }
                String type=(clazz.isInterface() ? "interface" : (clazz.isPrimitive() ? "" : "class")),
                        zhType = (clazz.isInterface() ? "接口" : (clazz.isPrimitive() ? "" : "类"));
                log.info("before [ "+ type +" = {}, method = {} ] execute", clazz.getName(), method.getName());

                if (clazz.isAnnotationPresent(DBAnnotation.class) && !method.isAnnotationPresent(DBAnnotation.class)) {
                    DBAnnotation dbAnnotation = clazz.getAnnotation(DBAnnotation.class);
                    DBContextHolder.setDataSource(dbAnnotation.value());
                    log.info("[" + type + " = {}, method = {} ] 切换" + zhType + "级数据源 [ {} ] 成功", clazz.getName(), method.getName(), dbAnnotation.value());
                } else if (method.isAnnotationPresent(DBAnnotation.class)) {
                    DBAnnotation dbAnnotation = method.getAnnotation(DBAnnotation.class);
                    DBContextHolder.setDataSource(dbAnnotation.value());
                    log.info("[" + type + " = {}, method = {} ] 切换" + zhType + "方法级数据源 [ {} ] 成功", clazz.getName(), method.getName(), dbAnnotation.value());
                } else {
                    DBContextHolder.setDataSource(DBTypeEnum.DB1);
                    log.info("[" + type + " = {}, method = {} ] 切换" + zhType + "或" + zhType + "方法级数据源失败, 使用默认值,切换默认数据源 [ {} ] 成功", clazz.getName(), method.getName(), DBTypeEnum.DB1);
                }
            }
        } catch (Exception e) {
            log.error("数据源切换异常", e);
        }
    }

    //执行完切面后，将线程共享中的数据源名称清空
    @After("pointcut()")
    public void after(){
        DBContextHolder.clear();
    }
}