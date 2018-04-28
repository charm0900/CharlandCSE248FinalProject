package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SearchController implements Initializable {
	private static SearchModel searchModel = new SearchModel();
	private static Cart cart;
	private static Customer customer;
	
	@FXML 
	private TableView<Product> searchResults;
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
	private TableColumn<Product, String> addToCart;
	@FXML
	private Label cartNumber;

	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colItemID.setCellValueFactory(new PropertyValueFactory<>("id"));
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		colDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
		colQuanity.setCellValueFactory(new PropertyValueFactory<>("quanity"));
		colDate.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
		searchResults.setItems(searchModel.searchByDepartment());
//		colDescrip.setCellFactory(new Callback<TableColumn<Product,String>,TableCell<Product,String>>() {
//
//			@Override
//			public TableCell<Product, String> call(TableColumn<Product, String> arg0) {
//				TableCell<Product, String> cell = new TableCell<Product, String>() {
//                    final Label btn = new Label("View description");
//
//                    @Override
//                    public void updateItem(String item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (empty) {
//                            setGraphic(null);
//                            setText(null);
//                        } else {
//                            btn.setOnMouseClicked(event -> {
//                                Product product = getTableView().getItems().get(getIndex());
//                                product.setQuanity(99);
//                                getTableView().refresh();
//                                // add product to cart
//                            });
//                            setGraphic(btn);
//                            setText(null);
//                        }
//                    }
//				};
//				return cell;
//			}
//		
//		});
		addToCart.setCellFactory(new Callback<TableColumn<Product,String>,TableCell<Product,String>>() {

			@Override
			public TableCell<Product, String> call(TableColumn<Product, String> arg0) {
				TableCell<Product, String> cell = new TableCell<Product, String>() {
                    final Button btn = new Button("Add to cart");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                Product product = getTableView().getItems().get(getIndex());
                                boolean r = cart.addToCart(product);
                                System.out.println(r);
                                System.out.println(cart.getCart().toString());
                                getTableView().refresh();
                        		cartNumber.setText(Integer.toString(cart.getCartSize()));
                                // add product to cart
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
				};
				return cell;
			}
		
		});
		searchModel.closeConnection();
		cartNumber.setText(Integer.toString(cart.getCartSize()));
	}
	
	@FXML
	public void goToCart() {
		CartController.passInCustomerAndCart(customer, cart);;
		try {
			Parent searchRoot = FXMLLoader.load(getClass().getResource("CartView.fxml"));
			Scene searchView = new Scene(searchRoot);
			Stage window = (Stage) searchResults.getScene().getWindow();
			window.setScene(searchView);
			window.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void passInDepartment(String text) {
		searchModel.setDepartment(text);
	}
	
	public static void passInCustomerAndCart(Customer cus, Cart sCart) {
		customer = cus;
		cart = sCart;
	}
	
	
	
	
}
