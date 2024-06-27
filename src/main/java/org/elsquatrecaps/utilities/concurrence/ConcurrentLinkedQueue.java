package org.elsquatrecaps.utilities.concurrence;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author josepcanellas
 */
public class ConcurrentLinkedQueue<E> extends LinkedList<E> implements ConcurrentQueue<E>{
    private static int count=0;
    private int maxSize = Integer.MAX_VALUE;
    private final Monitor<Boolean> prMonitor = new Monitor(true);
    private final Monitor<Boolean> coMonitor = new Monitor(true);
    private String keyForClose=null;

    public ConcurrentLinkedQueue(String keyForClose, int maxSize) {
        this.maxSize = maxSize;
        this.keyForClose = keyForClose;
    }

    public ConcurrentLinkedQueue(String keyForClose) {
        this.keyForClose = keyForClose;
    }

    @Override
    public void put(E e) throws InterruptedException {
        synchronized (prMonitor) {
            while(prMonitor.getValue() && this.size()>=maxSize){
                prMonitor.wait(1000);
            }
            if(prMonitor.getValue()){
                super.addLast(e);
            }            
        }
        synchronized (coMonitor) {
            coMonitor.notify();
        }
    }

    @Override
    public E take() throws InterruptedException {
        E ret=null;
//        int m = count++;
//        System.out.println(String.format("\"Entering ConcurrentLinkedQueue::take with id\";\"Q-%015d-E\"", m));
        synchronized (coMonitor) {
            while(coMonitor.getValue() && this.isEmpty()){
                coMonitor.wait(1000);
            }
            if(this.tryToTake()){
                ret = super.removeFirst();            
            }
        }
        if(ret!=null){
            synchronized (prMonitor) {
                prMonitor.notify();
            }
        }
//        System.out.println(String.format("\"Leaving ConcurrentLinkedQueue::take with id\";\"Q-%015d-L\"", m));
        return ret;
    }

    @Override
    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        boolean ret=false;
        long timeoutMillis = unit.toMillis(timeout);
        long counterMillis = 0;
        synchronized (prMonitor) {
            while(prMonitor.getValue() && counterMillis<timeoutMillis && this.size()>=maxSize){                
                prMonitor.wait(1000);
                counterMillis += 1000;
            }
            if(prMonitor.getValue()){
                ret = super.offerLast(e);
            }            
        }
        synchronized (coMonitor) {
            coMonitor.notify();
        }
        return ret;    
    }

    @Override
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        E ret=null;
        long timeoutMillis = unit.toMillis(timeout);
        long counterMillis = 0;
        synchronized (coMonitor) {
            while(coMonitor.getValue() && counterMillis<timeoutMillis && this.isEmpty()){                
                coMonitor.wait(1000);
                counterMillis += 1000;
            }
            if(this.tryToTake()){            
                ret = super.pollFirst();            
            }
        }
        if(ret!=null){
            synchronized (prMonitor) {
                prMonitor.notify();
            }
        }
        return ret;   
    }

    @Override
    public int remainingCapacity() {
        return maxSize-this.size();
    }

    @Override
    public int drainTo(Collection<? super E> c) {
        int ret =0;
        synchronized (this) {
            while(!this.isEmpty()){
                c.add(this.removeFirst());
                ret++;
            }
        }
        return ret;
    }

    @Override
    public int drainTo(Collection<? super E> c, int maxElements) {
        int ret =0;
        while(ret<maxElements && !this.isEmpty()){
            c.add(this.removeFirst());
            ret++;
        }
        return ret;
    }    
    
    private boolean tryToTake(){
        return !this.isEmpty();
    }
    
    @Override
    public void preventNextlocking(){
        coMonitor.setValue(false);
    }    
    
    @Override
    public boolean close(String keyForClose){
        if(!this.keyForClose.equals(keyForClose)){
            throw new RuntimeException("Error. close method is calling with wrong key");
        }
        if(this.size()==maxSize){
            //???
        }
        synchronized (coMonitor) {
            coMonitor.setValue(false);
            coMonitor.notifyAll();
        }      
        synchronized (prMonitor) {
            prMonitor.setValue(false);
            prMonitor.notifyAll();
        }        
        return this.isEmpty();
    }       
}
