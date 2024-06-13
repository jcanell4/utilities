/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.elsquatrecaps.utilities.tools;

/**
 *
 * @author josep
 */
public class FileAndContent<T> {
    private String fileName;
    private T content;

    public FileAndContent(String fileName, T content) {
        this.fileName = fileName;
        this.content = content;
    }


    public String getFileName() {
        return fileName;
    }

    public T getContent() {
        return content;
    }
    
    
}
