
package org.elsquatrecaps.utilities.tools;

import java.util.Arrays;

/**
 *
 * @author josepcanellas
 * @param <T>
 */
public class CalculableDistanceArrayOf<T extends Number & Comparable<? super T>> implements Comparable<CalculableDistanceArrayOf<T>> {
    T[] values;

    public CalculableDistanceArrayOf(T[] values) {
        this.values = values;
    }
    
    public float getDistance(CalculableDistanceArrayOf<T> v){ 
        return getDistance(v.values);
    }

    public float getDistance(T[] v){
        float Sum = 0.0f;
        for(int i=0;i<values.length;i++) {
           Sum += Math.pow(values[i].floatValue()-v[i].floatValue(),2.0);
        }
        return (float) Math.sqrt(Sum);
    }
 
    @Override
    public int compareTo(CalculableDistanceArrayOf t) {
        return Arrays.compare(values, (T[]) t.values);
    }     

    public T[] getValues(){
        return values;
    }
}
