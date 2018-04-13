package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;

public class HomeController implements Initializable{
	private HomeModel homeModel = new HomeModel();
	
	@FXML 
	private WebView webView;

	
	// required when implements initializable
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		webView.getEngine().loadContent("<iframe width=\"435\" height=\"270\" src=\"https://www.youtube.com/embed/TIESAgMbLZY\" frameborder=\"0\" allow=\"autoplay; encrypted-media\" allowfullscreen></iframe>");
		
	}


}
