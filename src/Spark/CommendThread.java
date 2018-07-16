package Spark;

import java.sql.SQLException;

	/**
	Start
	Java推荐线程的实现
	@author 张子健
    **/

public class CommendThread implements Runnable{
	private Thread t;
	private int userId;
	private String threadName;
	public static long pid = 0;
	public static boolean isFirst = true;
	
	public CommendThread(String name,int userId){
		threadName = name;
		this.userId = userId;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			System.out.println("startCommending!");
			Commend.commendProductsForUser(userId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void start() {
		if(t == null) {
			t = new Thread(this,threadName);
			t.start();
			pid = t.getId();
		}
	}

}

	/**
	End
	Java推荐线程的实现
	@author 张子健
	 **/
