package Java_Threaded_Fedex_Tracker;

import java.util.ArrayList;
import java.util.Random;

public class Package {
	private String weight;
	private String dimensions;
	private int pieces;
	private String src, dest, currentLocation, signature_Services, packing,special_Handling_Section, service;
	public String tracking_no;
	//private ArrayList<Integer> path = new ArrayList<Integer>(); 

	Package(String weight, String dimensions, int pieces, String src, String dest, String signature_Services, String packing, String special_Handling_Section, String service) {
		this.weight = weight;
		this.dimensions = dimensions;
		this.pieces = pieces;
		this.src = src;
		this.dest = dest;
		this.signature_Services = signature_Services;
		this.packing = packing;
		this.special_Handling_Section = special_Handling_Section;
		this.service = service;
	}
	
	Package(){
		super();
	}
		
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getDimensions() {
		return dimensions;
	}
	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}
	public int getPieces() {
		return pieces;
	}
	public void setPieces(int pieces) {
		this.pieces = pieces;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getDest() {
		return dest;
	}
	public void setDest(String dest) {
		this.dest = dest;
	}

	public String getSignature_Services() {
		return signature_Services;
	}

	public void setSignature_Services(String signature_Services) {
		this.signature_Services = signature_Services;
	}

	public String getPacking() {
		return packing;
	}

	public void setPacking(String packing) {
		this.packing = packing;
	}

	public String getSpecial_Handling_Section() {
		return special_Handling_Section;
	}

	public void setSpecial_Handling_Section(String special_Handling_Section) {
		this.special_Handling_Section = special_Handling_Section;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}
	public String getCurrentLocation() {
		return currentLocation;
	}
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}

//	public ArrayList<Integer> getPath() {
//		return path;
//	}
//
//	public void setPath(ArrayList<Integer> path) {
//		this.path = path;
//	}
}
