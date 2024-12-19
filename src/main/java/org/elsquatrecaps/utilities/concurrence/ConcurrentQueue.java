/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.elsquatrecaps.utilities.concurrence;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author josep
 */
public interface ConcurrentQueue<E> extends BlockingQueue<E>{
    int THREAD_PROVIDER=0;
    int THREAD_CONSUMER=1;
    void preventNextlocking();
    public E peekWaitingForValue(long timeout, TimeUnit unit) throws InterruptedException;
    public E peekWaitingForValue()  throws InterruptedException;
    boolean close(String fromThreadName);
    boolean close();
}
