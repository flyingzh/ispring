package com.zf.factory;

import com.zf.annotation.Autowired;
import com.zf.annotation.Service;
import com.zf.annotation.Transaction;
import com.zf.utils.ClassUtil;
import com.zf.utils.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 *zf
 */
public class BeanFactory {

    private static HashMap<String,Object> map = new HashMap<>();  // 存储对象

    static {
        parseXML();
        parseAnno();
        dependencyInject();
        transactionMana();
    }

    /**
     * 事务控制管理
     */
    private static void transactionMana() {
        Set<String> keySet = map.keySet();
        for(String key:keySet){
            Object o = map.get(key);
            Class<?> clazz = o.getClass();
            Transaction annotation = clazz.getAnnotation(Transaction.class);
            if(annotation!=null){
                Object proxyObj = null;
                ProxyFactory proxyFactory = (ProxyFactory) getBean("proxyFactory");
                if(clazz.getInterfaces() != null){
                    proxyObj = proxyFactory.getJdkProxy(o);
                }else{
                    proxyObj = proxyFactory.getCglibProxy(o);
                }
                map.put(key,(ProxyFactory)proxyObj);
            }
            /*Method[] methods = clazz.getMethods();
            for (Method method:methods){
                //public方法
                if(Modifier.isPublic(method.getModifiers())){
                    Transaction annotation = method.getAnnotation(Transaction.class);
                    if(annotation != null){
                        Object obj = null;
                        ProxyFactory proxyFactory = (ProxyFactory) getBean("proxyFactory");
                        if(clazz.getInterfaces() != null){
                            obj = proxyFactory.getJdkProxy(o);
                        }else{
                            obj = proxyFactory.getCglibProxy(o);
                        }
                        map.put(key,obj);
                    }
                }
            }*/
        }
                    /*Transaction annotation1 = clazz.getAnnotation(Transaction.class);
            if(annotation1!=null){
                Object proxyObj = null;
                ProxyFactory proxyFactory = (ProxyFactory) getBean("proxyFactory");
                if(clazz.getInterfaces() != null){
                    proxyObj = proxyFactory.getJdkProxy(o);
                }else{
                    proxyObj = proxyFactory.getCglibProxy(o);
                }
//                o = proxyObj;
                map.put(key,proxyObj);
            }*/
    }

    /**
     *  注入
     */
    private static void dependencyInject() {
        /*List<Class<?>> classes = ClassUtil.getClasses("com.zf");
        if(classes == null || classes.isEmpty()){
            return;
        }*/
        try {
            Set<String> keySet = map.keySet();
            for(String key:keySet){
                Object o = map.get(key);
                Class<?> clazz = o.getClass();
                Field[] declaredFields = clazz.getDeclaredFields();
                for (Field field : declaredFields) {
                    Annotation[] annotations = field.getAnnotations();
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof Autowired) {
//                            String idName = StringUtils.toLowerCaseFirstOne(clazz.getSimpleName());
                            field.setAccessible(true);
                            System.out.println("field.getName()--->"+field.getName());
                            Object obj = map.get(field.getName());
                            System.out.println("obj-->"+obj);
                            field.set(o,obj);

                            System.out.println("成功注入");

                        }
                    }
                }

            }

            /*for (Class<?> clazz : classes) {
                Field[] declaredFields = clazz.getDeclaredFields();
                for (Field field : declaredFields) {
                    Annotation[] annotations = field.getAnnotations();
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof Autowired) {
                            String idName = StringUtils.toLowerCaseFirstOne(clazz.getSimpleName());
                            Object o = map.get(idName);

                            field.setAccessible(true);
//                            Class c2 = (Class) field.getGenericType();
                            System.out.println("field.getName()--->"+field.getName());
                            Object obj = map.get(field.getName());
                            System.out.println("obj-->"+obj);
                            field.set(clazz.newInstance(),obj);

                            *//*if(c2.isInterface()){

                            System.out.println(field.getName() + "--这个域使用了我的注解");
                            System.out.println(field.getGenericType().toString() + "--这个域的类型");
                                //是一个接口
                                List<Class<?>> childClazz = getChildClazz(classes, c2);
                                for(Class c: childClazz){
                                    Object o3 = c.newInstance();
                                    field.set(c2, o3);
                                }
                            }else{
                                Object o2 = c2.newInstance();
                                field.set(clazz, o2);
                                System.out.println("成功注入");
                            }*//*




                            *//*Object o2 = c2.newInstance();
                            Object o = clazz.newInstance();
                            field.set(obj.getClass(),o);*//*
//                            field.set(clazz, o2);
                            System.out.println("成功注入");
                        }
                    }
                }
            }*/
        }catch(IllegalAccessException e){
            e.printStackTrace();
        }
    }

    public static List<Class<?>> getChildClazz(List<Class<?>> classes, Class clazz){
        List<Class<?>> list = new LinkedList<>();
        for (int i = 0; i < classes.size(); i++) {
            if (clazz.isAssignableFrom(classes.get(i))) {
                if (!clazz.equals(classes.get(i))) {
                    // 自身并不加进去
                    list.add(classes.get(i));
                }
            }
        }

/*
        Reflections reflections = new Reflections(this.getClass().getPackage().getName());
        Set<Class<? extends MyService>> myClasses = reflections.getSubTypesOf(MyService.class);
        for (Class<? extends MyService> myClass : myClasses) {
            MyService myService = applicationContext.getBean(monitor);
            myServiceMap.put(myService.getType(), myService);
        }*/
        return list;
    }

    /**
     * 解析@Service中的注解  添加到缓存中
     */
    private static void parseAnno() {
        List<Class<?>> classes = ClassUtil.getClasses("com.zf");
        if(classes == null || classes.isEmpty()){
            return;
        }
        try {
            for (Class<?> clazz : classes) {
                Service serviceAnno = clazz.getAnnotation(Service.class);
                if(serviceAnno != null){
                    String idName = serviceAnno.value();
                    if(StringUtils.isBlank(idName)){
                        idName = StringUtils.toLowerCaseFirstOne(clazz.getSimpleName());
                        System.out.println("idName--->"+idName);
                    }
                    if(map.get(idName) == null){
                        map.put(idName,clazz.newInstance());
                    }
                }
            /*Transaction transactionAnno = clazz.getAnnotation(Transaction.class);
            if(serviceAnno != null){

            }*/
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 解析配置文件中的Bean
     */
    private static void parseXML() {
        InputStream resourceAsStream = BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml");
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();
            List<Element> beanList = rootElement.selectNodes("//bean");
            for (int i = 0; i < beanList.size(); i++) {
                Element element =  beanList.get(i);
                String id = element.attributeValue("id");
                String clazz = element.attributeValue("class");
                Class<?> aClass = Class.forName(clazz);
                Object o = aClass.newInstance();

                map.put(id,o);
            }

            List<Element> propertyList = rootElement.selectNodes("//property");
            for (int i = 0; i < propertyList.size(); i++) {
                Element element =  propertyList.get(i);
                String name = element.attributeValue("name");
                String ref = element.attributeValue("ref");

                Element parent = element.getParent();

                String parentId = parent.attributeValue("id");
                Object parentObject = map.get(parentId);
                Method[] methods = parentObject.getClass().getMethods();
                for (int j = 0; j < methods.length; j++) {
                    Method method = methods[j];
                    if(method.getName().equalsIgnoreCase("set" + name)) {
                        method.invoke(parentObject,map.get(ref));
                    }
                }

                map.put(parentId,parentObject);

            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static  Object getBean(String id) {
        return map.get(id);
    }

}
