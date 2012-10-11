package webapp.model.helper;

public class Pair<K, V> {

	private K k_;
	private V v_;

	public Pair(K k, V v) {
		k_ = k;
		v_ = v;
	}
	
	public K getKey() {
		return k_;
	}
	
	public V getValue() {
		return v_;
	}
}