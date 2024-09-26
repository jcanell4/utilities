/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.elsquatrecaps.utilities.tools.configuration;

/**
 *
 * @author josep
 */
public class IoRuntimeException extends RuntimeException {

    public IoRuntimeException() {
        super();
    }
    
    public IoRuntimeException(String message){
        super(message);
    }
    
    public IoRuntimeException(Throwable cause){
        super(cause);
    }
    
    public IoRuntimeException(String message, Throwable cause){
        super(message, cause);
        
    }
}
