package lab04;

import java.util.HashMap;

class CounterMap<K> extends HashMap<K, Integer> {
    CounterMap() {
        super();
    }
    public void increment(K key) {
        this.put(key, this.getOrDefault(key, 0) + 1);
    }

    @Override
    public Integer get(Object key) {
        if ( ! super.containsKey(key) && key != null) {
            this.put((K) key, 0);
        }
        return super.get(key);
    }
}
