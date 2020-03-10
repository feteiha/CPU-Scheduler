import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class AG {

	public static ArrayList<Process> readyQ , processArray;
	public static SortedSet< pair<Integer, pair<Double, Process> > > setArrival;
	public static SortedSet< pair<pair<Double, Process>, Integer > > setAG;
    
	private static HashMap< String , ArrayList<Double> > quantumHistory;
	
    public AG(ArrayList<Process> p) {
    	quantumHistory = new HashMap<String, ArrayList<Double>>();
    	
    	readyQ = new ArrayList<Process>();
    	processArray = p;
    	for ( int i = 0; i < p.size(); ++i ) {
    		p.get(i).calcAGFactor();
    		readyQ.add(new Process(p.get(i)));
    		ArrayList<Double> temp = new ArrayList<Double>();
    		temp.add( p.get(i).quantum );
    		quantumHistory.put( p.get(i).processName , temp );
    	}
    	sortArray();
    }
    
    public static void sortArray() {
		Collections.sort(readyQ,new Comparator<Process>() {
			@Override
			public int compare(Process o1, Process o2) {
				// TODO Auto-generated method stub
				
				if ( o1.arrivalTime == o2.arrivalTime ) {
					return (o1.AGFactor > o2.AGFactor ? 1 : -1);
				}
				return (o1.arrivalTime > o2.arrivalTime ? 1 : -1);
				
			}
		});
	}
    
    public  void simulate() {
    	setArrival = new TreeSet< pair<Integer, pair<Double, Process> > >( new pairComp<Integer, pair<Double, Process> >() );
		setAG = new TreeSet< pair<pair<Double, Process>, Integer > >( new pairComp<pair<Double, Process>, Integer >() );
		
		Process currentProcess = new Process(); currentProcess.processName = "-2";
		Double currentTime = Math.ceil( readyQ.get(0).arrivalTime ); int idx = 1 , nxtReady = 0; boolean first = true;
		
		while ( nxtReady < readyQ.size() || !setArrival.isEmpty() || !setAG.isEmpty() || !currentProcess.processName.equals("-1") ) {
			
			if ( setArrival.isEmpty() && setAG.isEmpty() && nxtReady < readyQ.size() ) {
				currentTime = Math.max( currentTime , readyQ.get(nxtReady).arrivalTime); 
				first = true;
			}
			
			while ( nxtReady < readyQ.size() && readyQ.get(nxtReady).arrivalTime <= currentTime ) {
				add( idx , readyQ.get(nxtReady)); nxtReady++; idx++;
			}
			
			if ( first ) {
				pair< pair<Double,Process> , Integer > firstProcess = setAG.first();
				currentProcess = firstProcess.key.value;
				erase( firstProcess.value , firstProcess.key.value );
				first = false;
			}
			
			while ( nxtReady < readyQ.size() && readyQ.get(nxtReady).arrivalTime < currentTime + currentProcess.quantum ) {
				add( idx , readyQ.get(nxtReady) ); nxtReady++; idx++;
			}
			
			Double _50 = currentTime + Math.ceil(currentProcess.quantum/2);
			Double sum = currentTime + currentProcess.burstTime;
			if ( sum.compareTo( _50 ) <= 0 ) {
				Double end  = currentTime + currentProcess.burstTime;

				for (int j=0 ; j<processArray.size() ; j++) {
					if (processArray.get(j).processName.equals(currentProcess.processName)){
						processArray.get(j).addTime(currentTime , end);
						processArray.get(j).calculate();
					}
				}
				currentTime = end;
				
				if ( !setArrival.isEmpty() ) {
					pair<Integer , pair<Double, Process>> nxtProcess = setArrival.first();
					currentProcess = nxtProcess.value.value; erase( nxtProcess.key , nxtProcess.value.value );
				}
				else {
					if ( nxtReady >= readyQ.size() && setAG.isEmpty() && setArrival.isEmpty() ) {
						currentProcess.processName = "-1";
					}
				}
			}
			else {
				if ( !setAG.isEmpty() && setAG.first().key.key < currentProcess.AGFactor ) {
					pair<pair<Double, Process>, Integer> smallestAg = setAG.first();
					Double end = Math.max( _50  , smallestAg.key.value.arrivalTime);
					
					Process updateCurrentProcess = currentProcess;
					updateCurrentProcess.burstTime = currentProcess.burstTime - (end - currentTime);
					updateCurrentProcess.quantum = currentProcess.quantum + (currentProcess.quantum - (end - currentTime));
					quantumHistory.get( updateCurrentProcess.processName ).add( updateCurrentProcess.quantum );

					for (int j=0 ; j<processArray.size() ; j++) {
						if (processArray.get(j).processName.equals(currentProcess.processName)){
							processArray.get(j).addTime(currentTime , end);
							processArray.get(j).calculate();
							break;
						}
					}
					
					add( idx , updateCurrentProcess ); idx++;
					currentTime = end;
					
					currentProcess = smallestAg.key.value; 
					erase( smallestAg.value , smallestAg.key.value );
				}
				else {
					Double sumQuantum = 0.0 , Average = 0.0;
					
					if ( !setAG.isEmpty() ) {
						Iterator<pair<pair<Double , Process>, Integer>> itr = setAG.iterator();
						while ( itr.hasNext() ) {
							pair<pair<Double, Process> , Integer > itrProcess = itr.next();
							sumQuantum += itrProcess.key.value.quantum;
						}
						Average = sumQuantum / (1.0 * setAG.size());
						Average = Math.ceil( 0.1 * Average );
					}
					
					Double end = currentTime + Math.min( currentProcess.quantum , currentProcess.burstTime );
					Process updateCurrentProcess = currentProcess;
					updateCurrentProcess.burstTime = currentProcess.burstTime - (end - currentTime);
					updateCurrentProcess.quantum = currentProcess.quantum + Average;
					
					quantumHistory.get( updateCurrentProcess.processName ).add( updateCurrentProcess.quantum );

					for (int j=0 ; j<processArray.size() ; j++) {
						if (processArray.get(j).processName.equals(currentProcess.processName)){
							processArray.get(j).addTime(currentTime , end);
							processArray.get(j).calculate();
							break;
						}
					}
					
					if ( updateCurrentProcess.burstTime > 0 ) {
						add( idx , updateCurrentProcess ); idx++;
					}
					currentTime = end;
					
					if ( !setArrival.isEmpty() ) {
						pair<Integer,pair<Double,Process>> nextProcess = setArrival.first();
						currentProcess = nextProcess.value.value; erase( nextProcess.key , nextProcess.value.value );
					}
					else {
						currentProcess.processName = "-1";
					}
				}
			}
		}
		
		/**
		 *  ====================================================================================================
		 *  	Print All Quantums
		 */
		
		for ( Map.Entry<String, ArrayList<Double>> value : quantumHistory.entrySet() ) {
			System.out.print( value.getKey() + " : " );
			for ( int i = 0; i < value.getValue().size(); ++i ) {
				System.out.print( value.getValue().get(i) + " " );
			}
			System.out.print("0");
			System.out.println();
		}
		
		/**
		 * 	====================================================================================================
		 */
    }
    
    public static void add( int idx , Process p ) {
		setArrival.add( new pair<Integer, pair<Double,Process>>( idx , new pair<Double, Process>( p.AGFactor , p ) ) );
		setAG.add( new pair<pair<Double,Process>, Integer>( new pair<Double, Process>( p.AGFactor , p ) , idx) );
	}
	
	public static void erase ( int idx , Process p  ) {
		pair<Integer, pair<Double, Process> > object1 = new pair<Integer, pair<Double,Process>>( );	
		object1.key = idx; object1.value = new pair<Double, Process>(p.AGFactor,p);	
		setArrival.remove(object1);
		
		pair<pair<Double, Process>, Integer > object2 = new pair<pair<Double,Process>, Integer>( );
		object2.key = new pair<Double, Process>( object1.value.key , object1.value.value );
		object2.value = idx; setAG.remove(object2); 	
	}
}
