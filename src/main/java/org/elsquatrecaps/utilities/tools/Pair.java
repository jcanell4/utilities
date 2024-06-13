package org.elsquatrecaps.utilities.tools;

/**
 *
 * @author josep
 */
public class Pair<A, B> {
    A firstValue;
    B secondValue;

    public Pair(A firstValue, B secondValue) {
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }
    
    public A getFirst() {
        return firstValue;
    }

    public B getSecond() {
        return secondValue;
    }

    public B getLast() {
        return getSecond();
    }    
}
