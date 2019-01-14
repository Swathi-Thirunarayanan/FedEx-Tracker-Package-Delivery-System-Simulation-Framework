package Java_Threaded_Fedex_Tracker;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class MainClass {
	
	public static void main(String args[]) throws SQLException{
		Scanner s = null;
		try {
			System.out.println("File OPen\n");
			s = new Scanner(new File("package.txt"));
			s.useDelimiter(" - ");
		}
		catch (Exception e) {
			System.out.println("Couldnt find file");
			return;
		}
		ArrayList<Package> packageList = new ArrayList<Package>();
		while (s.hasNext()) {
			String weight = s.next();
			String dimensions = s.next();
			int pieces = s.nextInt();
			String src = s.next();
			String dest= s.next();
			String signature_Services = s.next();
			String packing = s.next();
			String special_Handling_Section = s.next();
			String service = s.next();
			Package p = new Package(weight, dimensions, pieces, src, dest, signature_Services, packing, special_Handling_Section, service);
			packageList.add(p);		
		}
		s.close();
		
		Connection con = getConnection();
		SQLConnect.clearDB();
		SQLConnect.add(con, packageList);	
		
		PackageThread t[] = new PackageThread([packageList.size()]);
		for(int i=0;i<packageList.size();i++) {
		  t[i] = new PackageThread(packageList.get(i));
		  t[i].start();
		
	}

}
