package application;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddProductsController implements Initializable{
	private AddProductsModel addProductsModel = new AddProductsModel();
	private static Employee employee;
	private Product product = new Product();
	
	@FXML
	private TextField productNameTF;
	@FXML
	private TextField priceTF;
	@FXML
	private TextField HeightTF;
	@FXML 
	private TextField widthTF;
	@FXML
	private TextField lengthTF;
	@FXML
	private TextArea descriptionTA;
	@FXML
	private ComboBox<String> departmentCB;
	@FXML
	private ComboBox<String> quantityCB;
	@FXML
	private Button addBtn;
	@FXML
	private Label status;
	@FXML
	private Label empName;
	@FXML
	private Button signOutBtn;
	@FXML
	private Button accoutingBtn;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		empName.setText(employee.getName());
		setUpDepartments();
		setUpQuantity();
	}
	
	private void setUpQuantity() {
		quantityCB.getItems().addAll("0","1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
				"11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
				"24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36",
				"37", "38", "39", "40");
		quantityCB.setValue("0");
	}

	private void setUpDepartments() {
		departmentCB.getItems().addAll("Doors", "Plumbing", "Appliances", "Cabinets", "Windows", "Furniture");
		departmentCB.setValue("Doors");
	}

	@FXML
	public void signOut() {
		addProductsModel.closeConnection();
		try {
			Parent empRoot = FXMLLoader.load(getClass().getResource("EmployeeLoginView.fxml"));
				Scene empView = new Scene(empRoot);
				Stage window =  (Stage) productNameTF.getScene().getWindow();
				window.setScene(empView);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void goToAcc() {
		addProductsModel.closeConnection();
		try {
			Parent accRoot = FXMLLoader.load(getClass().getResource("AccountingView.fxml"));
				Scene accView = new Scene(accRoot);
				Stage window =  (Stage) productNameTF.getScene().getWindow();
				window.setScene(accView);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void AddProduct() {
		if (checkInput()) {
			if (addProductsModel.addProduct(product)) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Success");
				alert.setContentText("Product successfully added to inventory");
				alert.show();
				resetPage();
			} else {
				System.out.println("product not created");
			}
		} else {
			
		}
	}
	
	private void resetPage() {
		productNameTF.clear();
		priceTF.clear();
		widthTF.clear();
		HeightTF.clear();
		lengthTF.clear();
		descriptionTA.clear();
		departmentCB.setValue("Doors");
		quantityCB.setValue("0");
		status.setText("");
	}

	private boolean checkInput() {
		if (productNameTF.getText().equals("")){
			status.setText("Please Enter product name");
			return false;
		}
		product.setName(productNameTF.getText());
	    try {
	        double price = Double.parseDouble(priceTF.getText());
	        double width = Double.parseDouble(widthTF.getText());
	        double length = Double.parseDouble(lengthTF.getText());
	        double height = Double.parseDouble(HeightTF.getText());
	        product.setPrice(price);
	        product.setWidth(width);
	        product.setHeight(height);
	        product.setLength(length);
	    } catch (NumberFormatException ignore) {
	        status.setText("Please enter valid price and/or deminsions");
	        return false;
	    }
	    int q = Integer.parseInt(quantityCB.getValue());
	    if (q == 0) {
	    	status.setText("please enter quantity");
	    	return false;
	    }
	    product.setQuanity(q);
	    setDate();
	    product.setDescription(descriptionTA.getText());
	    System.out.println("");
	    product.setDepartment(departmentCB.getValue());
	    
		return true;
	}

	private void setDate() {
		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		product.setDateAdded(sdf.format(date));
		System.out.println("product added date :" + product.getDateAdded());
	}

	public static void passInEmployee(Employee emp) {
		employee = emp;
	}

}
