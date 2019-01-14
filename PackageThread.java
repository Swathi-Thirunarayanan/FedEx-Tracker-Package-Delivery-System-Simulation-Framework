package Java_Threaded_Fedex_Tracker;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PackageThread extends Thread{
	boolean run = true;
	public static final String[] locationArray = {"Northborough, MA","Edison, NJ","Pittsburgh, PA","Allentown, PA","Martinsburg, WV","Charlotte, NC","Atlanta, GA","Orlando, FL","Memphis, TN","Grove City, OH","Indianapolis, IN","Detroit, MI","New Berlin, WI", "Minneapolis, MN", "St. Louis, MO", "Kansas, KS", "Dallas, TX", "Houston, TX","Denver, CO","Salt Lake City, UT","Phoenix, AZ","Los Angeles, CA","Chino, CA","Sacramento, CA","Seattle, WA"};
	String location, curentLocation;
	Thread t = new Thread();
	Package p;
	ArrayList<Integer> path = new ArrayList<Integer>();
	public PackageThread(Package p) {
		this.p = p;
	}
	
	public void startThread() throws SQLException {
		Connection con = SQLConnect.getConnection();
		SQLConnect.add(con, p);	
		
		PackageThread t = new PackageThread(p);
		t.start();
	}
	public void run() {
		while(run) {	
			int src = 0, dest = 0;
			DijkstrasAlgorithm algo = new DijkstrasAlgorithm();
			for(int i=0; i<locationArray.length; i++) {
				location = locationArray[i];
				if(location.equals(p.getSrc())) {
					src = i; 
				}
				if(location.equals(p.getDest())) {
					dest = i;
				}
			}
			path = algo.getShortestPath(src, dest);
			
			for (int i=0;i<path.size();i++) { 
				curentLocation = locationArray[path.get(i)];
				p.setCurrentLocation(curentLocation);
				if(curentLocation.equals(p.getDest())) {
					run = false;
				}
				//Store it to DB
				@SuppressWarnings("unused")
				Connection con = SQLConnect.getConnection();
				try {
					SQLConnect.updateCurrentLocation(con, p);
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException | SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("Error in Timer");
					e.printStackTrace();
				}
			}
		}
	}	
}

