/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.elsquatrecaps.utilities.concurrence;

/**
 *
 * @author josep
 */
public class Monitor<T> {    
    private T value = null;

    public Monitor() {
    }
    
    public Monitor(T v) {
        value = v;
    }
    
    /**
     * @return the value
     */
    public T getValue() {
        return value;
    }

    /**
     * @param v the value to set
     */
    public void setValue(T v) {
        this.value = v;
    }           
}
