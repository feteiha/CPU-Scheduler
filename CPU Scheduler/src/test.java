import java.awt.Color;
import java.util.ArrayList;

public class test {

	public static void main(String[] args) {
		
		
		ArrayList<Process> p = new ArrayList<Process>();
		p.add( new Process( "p1" , Color.green, 0.0, 1.0, 0 , 4.0) );
		p.add( new Process( "p2" , Color.green, 10.0, 5.0,  0 , 4.0) );
		
		/*p.add( new Process( "p1" , Color.green, 0.0, 17.0, 4 , 4.0) );
		p.add( new Process( "p2" , Color.green, 3.0, 6.0,  9 , 4.0) );
		p.add( new Process( "p3" , Color.green, 4.0, 10.0, 3 , 4.0) );
		p.add( new Process( "p4" , Color.green, 29.0, 4.0, 8 , 4.0) );*/
		AG obj = new AG( p );
		obj.simulate();
		// p1 p5 p2 p3 p4
		
		
		/*ArrayList<Process> p = new ArrayList<Process>();
		p.add(new Process("p1","red", 1,1,0));      //#2
		p.add(new Process("p2","blue", 0,2,0));		//#3
		//p.add(new Process("p3","yellow", 1,1,0));	
		p.add(new Process("p4","green", 1,3,0));	//#4
		p.add(new Process("p5","black", 0,1,0));     //#1*/
		
		/*Color color = Color.green;
		ArrayList<Process> p2 = new ArrayList<Process>();
		p2.add(new Process("p1",color, 0,8,0));      //#2
		p2.add(new Process("p2",color, 1,4,0));		//#3
		//p.add(new Process("p3","yellow", 1,1,0));	
		p2.add(new Process("p3",color, 2,9,0));	//#4
		p2.add(new Process("p4",color, 3,5,0)); */
		
		
		//ShortestJobFirst s = new ShortestJobFirst(p);
		//s.process();
		
		/*ShortestRemainingTimeFirst sr = new ShortestRemainingTimeFirst(p2);
		sr.process();*/
		
		/*
		ArrayList<Process> p2 = new ArrayList<Process>();
		p2.add(new Process("p1","red",   0 , 11 ,2)) ;     //#2
		p2.add(new Process("p2","blue",  5 , 28 ,0));	   //#3
		p2.add(new Process("p3","yellow",12, 2  ,3)); 
		p2.add(new Process("p4","green", 2 , 10 ,1));	   //#4
		p2.add(new Process("p5","black", 9 , 16 ,4));
		
		ArrayList<Process> p3 = new ArrayList<Process>();
		p3.add(new Process("p1","red",   0 , 10,3)) ;     //#2
		p3.add(new Process("p2","blue",  0 , 1 ,1));	   //#3
		p3.add(new Process("p3","yellow",0, 2 ,4)); 
		p3.add(new Process("p4","green", 0 , 1 ,5));	   //#4
		p3.add(new Process("p5","black", 0 , 5 ,2));
		
		PriorityScheduling p = new PriorityScheduling(p2);
		p.process();
		System.out.println("_________________________________");
		p = new PriorityScheduling(p3);
		p.process();
		*/
		
		
	}

}
