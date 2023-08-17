package XPress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DefaultListHashMap<K, V> extends HashMap<K,List<V>> {
    @Override
    public List<V> get(Object k) {
        if(!containsKey(k))
            this.put((K) k, new ArrayList<>());
        return super.get(k);
    }
}