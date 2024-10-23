package org.elsquatrecaps.utilities.proxies;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.reflections.Reflections;

/**
 *
 * @author josepcanellas
 * @param <T>
 * @param <A>
 */
public class ProxyByAnnotationsBuilder<T, A extends Annotation> {
    private HashMap<String, Class<T>> classMap;
    private final String[] searchPackage;
    private final Class<T> instanceClass;
    private final Class<A> markAnnotation;

    public ProxyByAnnotationsBuilder(Class<T> instanceClass, Class<A> markAnnotation, String... searchPackage) {
        this.searchPackage = searchPackage;
        this.instanceClass = instanceClass;
        this.markAnnotation = markAnnotation;
    }
    
    public T getInstance(String type){
        T ret = null;
        if(classMap.containsKey(type)){
            if(instanceClass.isAssignableFrom(classMap.get(type))){
                try {
                    ret =  classMap.get(type).getConstructor().newInstance();
                } catch (SecurityException | NoSuchMethodException | InvocationTargetException 
                        | IllegalArgumentException | InstantiationException | IllegalAccessException ex) {
                    throw new RuntimeException(ex.getMessage(), ex);
                }
            }else{
               throw new RuntimeException(String.format("Class for type %s is not Runnable", type)); 
            }
        }else{
            throw new RuntimeException(String.format("%s is an unknown type class for this proxy", type));
        }
        return ret;        
    }
    
    public void updateClassMap(){
        if(classMap==null){
            classMap = new HashMap<>();
            Reflections ref = new Reflections((Object[]) searchPackage);
            Set<Class<?>> annotated = ref.getTypesAnnotatedWith(markAnnotation);
            annotated.forEach((Class<?> clazz) -> {
                A annotation = clazz.getAnnotation(markAnnotation);
                String id = getMarkAnnotationIdValue(annotation);
                classMap.put(id, (Class<T>) clazz);
            });  
        }
    }
    
    private static String getMarkAnnotationIdValue(Annotation annot){
        List<String> id = new ArrayList<>();
        List<Integer>order = new ArrayList<>();
        Method[] am = annot.annotationType().getMethods();
        for(Method m: am){
            PartOfMarkAnnotationId p = m.getAnnotation(PartOfMarkAnnotationId.class);
            if(p!=null){
                try {
                    int pos=id.size();
                    id.add((String) m.invoke(annot));
                    order.add(p.orderNum());
                    while(pos>0 && order.get(pos)<order.get(pos-1)){
                        order.set(pos-1, order.get(pos));
                        id.set(pos-1, id.get(pos));
                        pos--;
                    }
                } catch (IllegalAccessException | InvocationTargetException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        return String.join(".", id);
    }

//        static {
//        classMap = new HashMap<>();   
//        Reflections ref = new Reflections("org.elsquatrecaps.autonewsextractor");
//        Set<Class<Object>> annotated = ref.getTypesAnnotatedWith(RunnableMarkerAnnotation.class);
//        annotated.forEach(new Consumer<Class<?>>() {
//            @Override
//            public void accept(Class<?> clazz) {
//                RunnableMarkerAnnotation annotation = clazz.getAnnotation(RunnableMarkerAnnotation.class);
//                classMap.put(annotation.id(), (Class<Runnable>) clazz);
//            }
//        });
//    }   

    
}
