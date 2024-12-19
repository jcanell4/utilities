/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.elsquatrecaps.utilities.iterators;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.elsquatrecaps.utilities.concurrence.ConcurrentLinkedQueue;
import org.elsquatrecaps.utilities.concurrence.ConcurrentQueue;

/**
 *
 * @author josep
 * @param <T>
 */
public class ConsumerIterator<T> implements Consumer<T>, Iterator<T>{
    private boolean finish=false;
    private final ConcurrentQueue<T> queue = new ConcurrentLinkedQueue<>("a");
    private T currentValue;

    
    @Override
    public void accept(T t) {
        try {
            queue.put(t);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean hasNext() {
        try {
            if(currentValue==null){
                currentValue = queue.take();
            }
            return !finish || currentValue!=null || !queue.isEmpty();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public T next() {
        T ret = currentValue;
        currentValue = null;
        return ret;
    }
    
    public T examineNextValue() {
        T ret;
        if(currentValue==null){
            try {
                ret = queue.peekWaitingForValue();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }else{
            ret = currentValue;
        }
        return ret;
    }

    
//    @Override
//    public void forEachRemaining(Consumer<? super T> action){
//        LinkedList<T> l = (LinkedList<T>) queue;
//        for(T item: l){
//            accept(item);
//        }
//    }

    public void close(){
        finish=true;
        queue.close();
    }        
}
