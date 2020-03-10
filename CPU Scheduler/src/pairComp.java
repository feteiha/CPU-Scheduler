import java.util.Comparator;


public class pairComp<K,V> implements Comparator<pair<K, V>> {

	@Override
	public int compare(pair<K, V> o1, pair<K, V> o2) {
		if ( o1.key.getClass().getName().equals( "java.lang.Integer" ) ) {
			if ( o1.key == o2.key ) {
				return 0;
			}
			return ((int)o1.key > (int)o2.key ? 1 : -1);
		}
		else {
			//System.out.println("OK");
			@SuppressWarnings("unchecked")
			pair<Double,Process> keyPair = (pair<Double, Process>) o1.key;
			@SuppressWarnings("unchecked")
			pair<Double,Process> valPair = (pair<Double, Process>) o2.key;
			if ( keyPair.key.compareTo( valPair.key ) == 0 ) {
				return 0;
			}
			
			return ((Double)keyPair.key > (Double)valPair.key ? 1 : -1);
		}
	}
}
