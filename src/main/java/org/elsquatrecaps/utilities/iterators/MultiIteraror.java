package org.elsquatrecaps.utilities.iterators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author josep
 */
public class MultiIteraror<T> implements Iterator<T>{
    List<Iterator<T>> singleIterators = new ArrayList<>();
    int currentId = 0;

    @Override
    public boolean hasNext() {
        boolean ret=false;
        while(currentId<singleIterators.size() && !ret){
            ret = (singleIterators.get(currentId)).hasNext();
            if(!ret){
                currentId++;
            }
        }
        return ret;
    }

    @Override
    public T next() {
        return this.singleIterators.get(currentId).next();
    }
    
}
