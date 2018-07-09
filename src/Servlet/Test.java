package Servlet;

import java.util.List;

import Bean.MovieBean;
import Service.MovieService;

//≤‚ ‘¿‡
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str="aa a 1";
		String[] s1;
		String[] s2;
		s1=str.split(" ");
		s2=str.split(" ");
		for(int i=0;i<5;i++) {
			if(i<2)
				System.out.println("s1: "+s1[i]);
			if(!s2[i].equals(null))
				System.out.println("s2: "+s2[i]);
		}
	}

}
