package org.elsquatrecaps.utilities.tools;

/**
 *
 * @author josep
 */
public interface Callback<P,R> {
    R call(P param);
}
