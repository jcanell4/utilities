/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.elsquatrecaps.exceptions;

import java.io.IOException;

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
    
    public IoRuntimeException(IOException cause){
        super(cause);
    }
    
    public IoRuntimeException(String message, IOException cause){
        super(message, cause);
    }
    
    /**
     *
     * @return
     */
    @Override
    public IOException getCause(){
        return (IOException) super.getCause();
    }
}
