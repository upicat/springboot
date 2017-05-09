package xm.spring.boot.base.thread;

public class SynchDemo {
	private static Object obj = new Object();
	
	public void method1(int i) {
		synchronized(obj){
			System.out.println(i);
			method2(i+1);
		}
	}
	
	public void method2(int i) {
		synchronized(obj){
			System.out.println(i);
		}
	}
	
}
