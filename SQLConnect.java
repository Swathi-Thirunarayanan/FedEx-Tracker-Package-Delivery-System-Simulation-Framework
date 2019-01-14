package Java_Threaded_Fedex_Tracker;

import java.sql.*;

//import com.mysql.cj.xdevapi.Statement;

public class SQLConnect {
	public static Connection getConnection(){
		try{	
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/fedex_db?autoReconnect=true&useSSL=false", "root", "Swathi");
			return con;
		}
		catch(Exception e)
		{
			System.out.println("DB Error");
			e.printStackTrace();
		}
		return null;
	}
	public static void add(Connection con, Package p) throws SQLException {
		String sql = "insert into package_travel" + "(Weight, Source, Destination, Signature_Services, Packing, Total_Pieces, Special_Handling_Section, Service, Tracking_Number)" + "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement myStmt = con.prepareStatement(sql);
		myStmt.setString(1, p.getWeight());
		myStmt.setString(2, p.getSrc());
		myStmt.setString(3, p.getDest());
		myStmt.setString(4, p.getSignature_Services());
		myStmt.setString(5, p.getPacking());
		myStmt.setInt(6, p.getPieces());
		myStmt.setString(7, p.getSpecial_Handling_Section());
		myStmt.setString(8, p.getService());
		myStmt.setString(9, p.tracking_no);
		myStmt.executeUpdate();
		con.close();
	}
	public static void updateCurrentLocation(Connection con, Package p) throws SQLException {
		String currentLocation = p.getCurrentLocation();
		String sql = "UPDATE package_travel SET Present_Location='"+currentLocation+"' WHERE Tracking_Number="+p.tracking_no;
		PreparedStatement myStmt = con.prepareStatement(sql);
		myStmt.executeUpdate(sql);

		String insert_query = "INSERT INTO travel_history (Arrival_Time, Tracking_Number, Location) values (?, ?, ?)";
		PreparedStatement historyStmt = con.prepareStatement(insert_query);
		historyStmt.setLong(1, System.currentTimeMillis());
		historyStmt.setString(2, p.tracking_no);
		historyStmt.setString(3, currentLocation);
		historyStmt.executeUpdate();

		con.close();
	}
	public static void clearDB()throws SQLException {
		Connection con = getConnection();
		String sql = "DELETE FROM package_travel";
		Statement stmt = con.createStatement();
		stmt.executeUpdate(sql);
		
		String sql1 = "DELETE FROM travel_history";
		Statement stmt1 = con.createStatement();
		stmt1.executeUpdate(sql1);
	}
}
