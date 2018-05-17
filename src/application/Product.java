package application;

import java.util.Date;

public class Product {
	private int id;
	private String name;
	private double price;
	private int quanity;
	private String department;
	private String description;
	private String dateAdded;
	private double height;
	private double length;
	private double width;
	
	public Product(int id, String name, double price, int quanity, String department, String description,
			String dateAdded, double height, double length, double width) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.quanity = quanity;
		this.department = department;
		this.description = description;
		this.dateAdded = dateAdded;
		this.height = height;
		this.length = length;
		this.width = width;
	}

	public Product() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuanity() {
		return quanity;
	}

	public void setQuanity(int quanity) {
		this.quanity = quanity;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}
	
	@Override
	public String toString() {
		return "id=" + id + " " + "name=" + name ;
		
	}

}
