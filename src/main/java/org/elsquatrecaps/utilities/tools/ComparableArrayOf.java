
package org.elsquatrecaps.utilities.tools;

import java.util.Arrays;

/**
 *
 * @author josep
 * @param <T>
 */
public class ComparableArrayOf<T extends Comparable<? super T>> implements Comparable<ComparableArrayOf<T>>{
        T[] values;

        public ComparableArrayOf(T[] values) {
            this.values = values;
        }
        
        @Override
        public int compareTo(ComparableArrayOf t) {
            return Arrays.compare(values, (T[]) t.values);
        }     
        
        public T[] getValues(){
            return values;
        }
}
