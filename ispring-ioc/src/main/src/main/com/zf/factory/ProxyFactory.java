package com.zf.factory;

import com.zf.annotation.Autowired;
import com.zf.annotation.Service;
import com.zf.annotation.Transaction;
import com.zf.utils.TransactionManager;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *zf
 */
@Service
public class ProxyFactory {

    @Autowired
    private TransactionManager transactionManager;

    /**
     * Jdk动态代理
     * @param obj  委托对象
     * @return   代理对象
     */
    public Object getJdkProxy(Object obj) {

        // 获取代理对象
        return  Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Object result = null;
                        Transaction annotation = method.getAnnotation(Transaction.class);
                        if(annotation != null){
                            try{
                                transactionManager.beginTransaction();
                                result = method.invoke(obj,args);
                                transactionManager.commit();
                            }catch (Exception e) {
                                e.printStackTrace();
                                transactionManager.rollback();
                                throw e;
                            }
                        }else {
                            result = method.invoke(obj,args);
                        }
                        return result;
                    }
                });
    }


    /**
     * 使用cglib动态代理生成代理对象
     * @param obj 委托对象
     * @return
     */
    public Object getCglibProxy(Object obj) {
        return  Enhancer.create(obj.getClass(), new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                Object result = null;
                Transaction annotation = method.getAnnotation(Transaction.class);
                if(annotation!=null){
                    try{
                        transactionManager.beginTransaction();
                        result = method.invoke(obj,objects);
                        transactionManager.commit();
                    }catch (Exception e) {
                        e.printStackTrace();
                        transactionManager.rollback();
                        throw e;
                    }
                }else{
                    result = method.invoke(obj,objects);
                }
                return result;
            }
        });
    }
}
