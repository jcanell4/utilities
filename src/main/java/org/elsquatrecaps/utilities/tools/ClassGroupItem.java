/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.elsquatrecaps.utilities.tools;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.reflections.Reflections;

/**
 *
 * @author josep
 */
public class ClassGroupItem<T> implements Identifiable<String> {
    
    protected String id;
    protected Class<T> type;
    protected T instance;

    public ClassGroupItem() {
    }

    public ClassGroupItem(String id, Class<T> type) {
        this.id=id;
        try {
            this.type = type;
            this.instance = type.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            this.type = type;
            this.instance = null;
        }
    }

    public T getInstance() {
        return instance;
    }

    public void setInstance(T instance) {
        this.instance = instance;
    }

    public Class<T> getType() {
        return type;
    }

    @Override
    public String toString() {
        return this.getId();
    }

    @Override
    public String getId() {
        return this.id;
    }
    
    public static<C, CI extends ClassGroupItem<C>, A extends Annotation>  List<CI> getItemsList(String packageToSearch, Class<A> annotationInfo, Callback<Pair<A, Class<C>>, CI> cb){
        Reflections reflections = new Reflections(packageToSearch);
        Set<Class<?>> types = reflections.getTypesAnnotatedWith(annotationInfo);
        return types.stream().map(clazz -> {
            Class<C> classC = (Class<C>) clazz;
            A a = classC.getAnnotation(annotationInfo);
            return cb.call(new Pair<>(a, classC));
        }).collect(Collectors.toList());
    }
}
