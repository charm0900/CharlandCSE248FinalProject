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
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AccountingController implements Initializable {
	private AccountingModel accModel = new AccountingModel();
	
	@FXML
	private Button signOutBtn;
	@FXML
	private Button addProdBtn;
	@FXML
	private CategoryAxis x;
	@FXML
	private NumberAxis y;
	@FXML
	private LineChart<?, ?> cart;
	@FXML
	private Label ordersPlaced;
	@FXML
	private Label productsSold;
	@FXML
	private Label totalSales;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		double pastWeek[] = accModel.getSalesForPastWeek();
		for (int i = 0; i < pastWeek.length; i++) {
			System.out.println(pastWeek[i]);
		}
		
		XYChart.Series series = new XYChart.Series<>();
		Date now = Calendar.getInstance().getTime();
		
	    Calendar cal = Calendar.getInstance();
	    int j = 7;
		for (int i = 0; i < pastWeek.length; i++) {
			cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -j);
			now.setTime(cal.getTimeInMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			String sDate = sdf.format(now);
			j--;
			series.getData().add(new XYChart.Data(sDate, pastWeek[j]));
		}
		cart.getData().add(series);
		
		ordersPlaced.setText(Integer.toString(accModel.getOrdersPlaced()));
		productsSold.setText(Integer.toString(accModel.getProdcutsSold()));
		totalSales.setText((accModel.getTotalSales()));
	}
	
	@FXML
	public void signOut() {
		accModel.closeConnection();
		try {
			Parent empRoot = FXMLLoader.load(getClass().getResource("EmployeeLoginView.fxml"));
				Scene empView = new Scene(empRoot);
				Stage window =  (Stage) signOutBtn.getScene().getWindow();
				window.setScene(empView);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void goToAddProd() {
		accModel.closeConnection();
		try {
			Parent addRoot = FXMLLoader.load(getClass().getResource("AddProductsView.fxml"));
				Scene addView = new Scene(addRoot);
				Stage window =  (Stage) signOutBtn.getScene().getWindow();
				window.setScene(addView);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
