import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.mchange.v2.log.MLog;


public class ThreadLocalDemo {
public static void main(String[] args){
	ThreadLocalDemo demo=new ThreadLocalDemo();
	demo.test();
	demo.run();
	
}
public void test(){
	byte[] bytes=new byte[1024*1024*230]; 
}
public void run(){
	ExecutorService executor=Executors.newFixedThreadPool(1);
	executor.execute(new Task());
	executor.shutdown();
	System.out.print(executor.isShutdown());
	System.gc();
	try {
		Thread.currentThread().sleep(1000*10);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		MLog.info("hjdhfjdhf");
	}
}
class Task implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//byte[] bytes=new byte[1024*1024*230]; 

		//ThreadLocal<byte[]> localString=new ThreadLocal<byte[]>();
	     //localString.set(bytes);
		//localString.set(null);
		
	
	}
	
}
}
