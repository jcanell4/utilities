package org.elsquatrecaps.utilities.tools.configuration;

/**
 *
 * @author josep
 */
public interface Configuration {
    <T extends Object> T getAttr(String key);
    
}
