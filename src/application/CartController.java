package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CartController implements Initializable {
	private static Cart cart;
	private static Customer customer;
	
	@FXML 
	private TableView<Product> cartItems;
	@FXML
	private TableColumn<Product, Integer> colItemID;
	@FXML
	private TableColumn<Product, String> colName;
	@FXML
	private TableColumn<Product, Double> colPrice;
	@FXML
	private TableColumn<Product, String> colDepartment;
	@FXML
	private TableColumn<Product, Integer> colQuanity;
	@FXML
	private TableColumn<Product, Date> colDate;
//	@FXML
//	private TableColumn<Product, String> colDescrip;
	@FXML
	private TableColumn<Product, String> deleteFromCart;
	@FXML
	private Label cartNumber;
	@FXML
	private Label subTotal; 



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colItemID.setCellValueFactory(new PropertyValueFactory<>("id"));
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		colDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
		colQuanity.setCellValueFactory(new PropertyValueFactory<>("quanity"));
		colDate.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
		cartItems.setItems(cart.getCart());
		deleteFromCart.setCellFactory(new Callback<TableColumn<Product,String>,TableCell<Product,String>>() {

			@Override
			public TableCell<Product, String> call(TableColumn<Product, String> arg0) {
				TableCell<Product, String> cell = new TableCell<Product, String>() {
                    final Button btn = new Button("delete ");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                Product product = getTableView().getItems().get(getIndex());
                                boolean r = cart.removeFromCart(product);
                                System.out.println(r);
                                getTableView().refresh();
                        		cartNumber.setText(Integer.toString(cart.getCartSize()));
                        		subTotal.setText(cart.getSubTotal());
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
				};
				return cell;
			}
		
		});
		cartNumber.setText(Integer.toString(cart.getCartSize()));
		subTotal.setText(cart.getSubTotal());
	}
	
	@FXML
	public void checkOut() {
		CheckOutController.passInCustomerAndCart(customer, cart);;
		try {
			Parent checkOutRoot = FXMLLoader.load(getClass().getResource("CheckOutView.fxml"));
			Scene CheckOutView = new Scene(checkOutRoot);
			Stage window = (Stage) cartItems.getScene().getWindow();
			window.setScene(CheckOutView);
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
