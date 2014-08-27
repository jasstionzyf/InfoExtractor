
import java.util.concurrent.Callable;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class ThreadTest <K,V>{
public V test(){
	ConcurrentMap<K, FutureTask<V>> map=new ConcurrentHashMap<K, FutureTask<V>>();
	Executor executor = Executors.newFixedThreadPool(8);
	K key = null;
    FutureTask<V> f=map.get(key);
    if(f==null){
	   Callable<V> c=new Callable<V>() {

		@Override
		public V call() throws Exception {
			return null;
			// TODO Auto-generated method stub
			// return value associated with key
			//return 
		}
	};
	f = new FutureTask<V>(c);
	FutureTask old = map.putIfAbsent(key, f);
	if (old == null)
	executor.execute(f);
	else
	f = old;
	}
	try {
		return f.get();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ExecutionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
 

}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		

}
}
