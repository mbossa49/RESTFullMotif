package fr.cnamts.prototype.aat.cache;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import fr.cnamts.prototype.aat.entity.Motif;

public class CacheMotif implements Map<String, Motif> {

	public Map<String, Motif> contenu = new HashMap<String, Motif>();
	
	public int size() {
		return contenu.size();
	}

	public boolean isEmpty() {
		return contenu.size() == 0;
	}

	public boolean containsKey(Object key) {

		return contenu.containsKey((String) key);
	}

	public boolean containsValue(Object value) {		
		return false;
	}

	public Motif get(Object key) {
		Motif motif = contenu.get((String)key);
		return motif;
	}

	public Motif put(String key, Motif value) {
		Motif motif = contenu.get(key);
		if(null == motif){
			contenu.put(key, value);
			motif = value;
		}else{
			return null;
		}
		return motif;
	}

	public Motif remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	public void putAll(Map<? extends String, ? extends Motif> m) {
		// TODO Auto-generated method stub
		
	}

	public void clear() {
		contenu = new HashMap<String, Motif>();
	}

	public Set<String> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<Motif> values() {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<java.util.Map.Entry<String, Motif>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static void main(String[] args) {
		 CacheMotif cache = new CacheMotif();
		 System.out.println(cache.containsKey("5823"));
		 System.out.println(cache.size());
		 cache.put("5823", new Motif());
		 System.out.println(cache.containsKey("5823"));
		 System.out.println(cache.size());
		 System.out.println(cache.put("5823", new Motif()));
		 System.out.println(cache.size());
		 System.out.println(cache.put("5824", new Motif()));
		 System.out.println(cache.size());
		 
	}
}
