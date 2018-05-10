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
	private TableColumn<Product, String> colQuanity;
	@FXML
	private TableColumn<Product, Date> colDate;
	// @FXML
	// private TableColumn<Product, String> colDescrip;
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
		colQuanity.setCellFactory(new Callback<TableColumn<Product, String>, TableCell<Product, String>>() {

			@Override
			public TableCell<Product, String> call(TableColumn<Product, String> arg0) {
				TableCell<Product, String> cell = new TableCell<Product, String>() {
					final Label qLabel = new Label();

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							Product product = getTableView().getItems().get(getIndex());
							int q = (cart.getQuantityOfSameProduct(product));
							qLabel.setText(Integer.toString(q));
							product.setQuanity(q);
							setGraphic(qLabel);
							setText(null);
						}
					}
				};
				return cell;
			}

		});
		colDate.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
		cartItems.setItems(getCart());
		deleteFromCart.setCellFactory(new Callback<TableColumn<Product, String>, TableCell<Product, String>>() {

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
								if ((cart.getQuantityOfSameProduct(product)) != 0) {
								boolean r = cart.removeFromCart(product);
								System.out.println(r);
								getTableView().refresh();
								cartNumber.setText(Integer.toString(cart.getCartSize()));
								subTotal.setText(cart.getSubTotal());
								} else {
									
								}
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

	private ObservableList<Product> getCart() {
		ObservableList<Product> oneItemCart = FXCollections.observableArrayList();
		for (int i = 0; i < cart.getCartSize(); i++) {
			if (oneItemCart.size() == 0) {
				oneItemCart.add(cart.getCart().get(i));
			}
			boolean contains = false;
			for (int j = 0; j < oneItemCart.size(); j++) {
				if (cart.getCart().get(i).getId() == oneItemCart.get(j).getId()) {
					contains = true;
				}
			}
			if (!contains) {
				oneItemCart.add(cart.getCart().get(i));
			}
		}
		return oneItemCart;
	}

	@FXML
	public void checkOut() {
		CheckOutController.passInCustomerAndCart(customer, cart);
		
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
	
	@FXML
	public void goHome() {
		HomeController.passInCustomerAndCart(customer, cart);
		try {
			Parent homeRoot = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
			Scene homeView = new Scene(homeRoot);
			Stage window = (Stage) cartItems.getScene().getWindow();
			window.setScene(homeView);
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
