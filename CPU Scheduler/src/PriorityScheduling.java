import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;




public class PriorityScheduling {
	double currentTime = 20,contextSwitching = 0;
	
	class QueueComparator implements Comparator<Process> { 
	    public int compare(Process x,Process y) 
	    { 
	    	// TODO Auto-generated method stub
			if (x.arrivalTime > currentTime) {
				return 1;
			}
			else if (y.arrivalTime > currentTime) {
				return -1;
			}
			
			if (x.priority < y.priority) {
		           return -1;
		    } else if (x.priority > y.priority){
	           return 1;
	        } else {
	    	   return 0;
	    	   
	       }
	    } 
	}
	ArrayList<Process> process;
	ArrayList<Process> temp;
	PriorityQueue<Process> pr;
	public PriorityScheduling (ArrayList<Process> p) {
		process = p;
		temp = new ArrayList<Process>();
		for (int i=0 ; i<p.size() ; i++) {
			temp.add(new Process(p.get(i)));
		}
		pr  = new PriorityQueue<Process>(process.size(), new QueueComparator());
		
	}
	
	public void process() {
		
		currentTime = process.get(0).arrivalTime ;
		for(int i=1 ; i<process.size(); i++) {
			if (currentTime > process.get(i).arrivalTime)
				currentTime = process.get(i).arrivalTime;
		}
		
		for (int i=0 ; i<process.size(); i++) {

			Process temp = new Process(process.get(i));
			pr.add(temp);
		}
		
		while (!pr.isEmpty()) {
			Process polled = pr.poll();

			currentTime = Math.max(currentTime, polled.arrivalTime);
			for (int j=0 ; j<process.size() ; j++) {
				if (process.get(j).processName.equals(polled.processName)){
					process.get(j).addTime(currentTime+contextSwitching);
					process.get(j).calculate();
					System.out.println(process.get(j).processName + " Start: " + process.get(j).timeFrames.get(0).start + " End: " +process.get(j).timeFrames.get(0).end + " P: " + process.get(j).priority);
				}
			}
			
			
			Iterator<Process> value = pr.iterator();
			while (value.hasNext()) {
				Process temp = ((Process)(value.next()));
				if (temp.arrivalTime <= currentTime && temp.priority != 0) {
					temp.priority--;
				}
	        }
	        
			currentTime += (polled.burstTime + contextSwitching);
			if(!pr.isEmpty()) {
			    pr.add(pr.remove());
			}
		}
		
		for (int i=0 ; i<pr.size() ; i++)
			System.out.println(pr.poll().processName + " ");
	}
}
