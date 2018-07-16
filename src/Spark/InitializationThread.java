package Spark;

import java.sql.SQLException;

	/**
	Start
	Java初始化线程的实现
	@author 张子健
	 **/

public class InitializationThread implements Runnable{
	
	private Thread t;
	private String threadName;
	
	public InitializationThread(String name) {
		threadName = name;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("SparkContext initializing...");
		long startTime = System.currentTimeMillis();
		Commend.initialization();
		Commend.loadRdd();
		try {
			Commend.commendProductsForUser(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Consuming "+ (endTime-startTime) + "ms");
		
	}
	
	public void start() {
		if(t == null) {
			t = new Thread(this,threadName);
			t.start();
		}
	}

}

	/**	
	End
	Java初始化线程的实现
	@author 张子健
	 **/
