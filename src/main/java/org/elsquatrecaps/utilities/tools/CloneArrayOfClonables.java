package org.elsquatrecaps.utilities.tools;

import java.util.ArrayList;

/**
 *
 * @author josep
 */
public class CloneArrayOfClonables{
//    private static Method clone = null;
//    static {
//        try { 
//            clone = Object.class.getDeclaredMethod("clone");
//        } catch (NoSuchMethodException | SecurityException ex) {
//            Logger.getLogger(CloneArrayOfClonables.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    /**
     *
     * @param <T>
     * @param array
     * @return
     */
    public static <T> T[] cloneArray(T[] array, Callback<T, T> cbToClone) {
        ArrayList<T> newArray = new ArrayList<>();
        for(T v: array){
            newArray.add(cbToClone.call(v));
        }
        return (T[]) newArray.toArray();
    }
    
}
