package application;

import java.text.DecimalFormat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Cart {
	private ObservableList<Product> cart = FXCollections.observableArrayList();

	private int nElems;
	
	public Cart() {
		nElems = 0;
	}
	
	public ObservableList<Product> getCart() {
		return cart;
	}
	
	public boolean addToCart(Product product) {
		boolean result = cart.add(product);
		nElems++;
		return result;
	}
	
	public boolean removeFromCart(Product product) {
		boolean result = cart.remove(product);
		nElems--;
		return result;
	}
	
	public int getCartSize() {
		return nElems;
	}
	
	public String getSubTotal() {
		double subTotal = 0;
		for (int i = 0; i < nElems; i++) {
			subTotal += cart.get(i).getPrice();
		}
		String df = new DecimalFormat("##.##").format(subTotal);
		return df;
		
	}
	

}
