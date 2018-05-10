package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class HomeController implements Initializable{
	private HomeModel homeModel = new HomeModel();
	private static Cart cart;
	private static Customer customer;
	
	@FXML 
	private WebView webView;
	@FXML 
	private MenuItem doorsMenuItem;
	@FXML
	private MenuItem appliancesMenuItem;
	@FXML 
	private MenuItem cabinetsMenuItem;
	@FXML
	private MenuItem windowsMenuItem;
	@FXML 
	private MenuItem furnitureMenuItem;
	@FXML
	private MenuItem plumbingMenuItem;
	@FXML 
	private Label cartNumber;

	
	// required when implements initializable
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		webView.getEngine().loadContent("<iframe width=\"435\" height=\"270\" src=\"https://www.youtube.com/embed/TIESAgMbLZY\" frameborder=\"0\" allow=\"autoplay; encrypted-media\" allowfullscreen></iframe>");
		setDepartMenuBar();
		homeModel.setUpCustomer(customer);
		System.out.println(customer);
		cartNumber.setText(Integer.toString(cart.getCartSize()));
	}
	
	private void setDepartMenuBar() {
		doorsMenuItem.setOnAction(e-> {
			searchDepart("Doors");
		});
		appliancesMenuItem.setOnAction(e-> {
			searchDepart("Appliances");
		});
		cabinetsMenuItem.setOnAction(e-> {
			searchDepart("Cabinets");
		});
		windowsMenuItem.setOnAction(e-> {
			searchDepart("Windows");
		});
		furnitureMenuItem.setOnAction(e->{
			searchDepart("Furniture");
		});
		plumbingMenuItem.setOnAction(e->{
			searchDepart("Plumbing");
		});
	}
	
	private void searchDepart(String depart) {
		homeModel.closeConnection();
		SearchController.passInDepartment(depart);
		SearchController.passInCustomerAndCart(customer, cart);
		try {
			Parent searchRoot = FXMLLoader.load(getClass().getResource("SearchView.fxml"));
			Scene searchView = new Scene(searchRoot);
			Stage window = (Stage)  (webView.getScene().getWindow());
			window.setScene(searchView);
			window.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void LogOut() {
		homeModel.logOut(customer, cart);
		homeModel.closeConnection();
		try {
			Parent loginRoot = FXMLLoader.load(getClass().getResource("loginView.fxml"));
			Scene loginView = new Scene(loginRoot);
			Stage window = (Stage)  (webView.getScene().getWindow());
			window.setScene(loginView);
			window.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void passInCustomerAndCart(Customer cus, Cart sCart) {
		customer = cus;
		cart = sCart;
	}

}
