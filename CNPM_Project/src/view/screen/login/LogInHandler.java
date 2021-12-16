package view.screen.login;

import java.sql.Statement;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import controller.HomeController;
import controller.LogInController;
import entity.db.AIMSDB;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import view.screen.logInFalse.LogInFalseHandler;
import views.screen.BaseScreenHandler;
import views.screen.home.HomeScreenHandler;
import views.screen.popup.PopupScreen;

public class LogInHandler extends BaseScreenHandler implements Initializable {
	
	private boolean isLogin = true;
	
	public static Logger LOGGER = Utils.getLogger(LogInHandler.class.getName());
	
	@FXML
	private Button tf_signIn;
	
	@FXML 
	private Button tf_signUp;
	
	@FXML
	private TextField tf_username;

	@FXML
	private TextField tf_password;

	

	public LogInHandler(Stage stage, String screenPath) throws IOException {
		// TODO Auto-generated constructor stub
		super(stage, screenPath);
	}
	
	@Override
    public void show() {
        super.show();
    }
	
	public LogInController getBController() {
        return (LogInController) super.getBController();
    }

	@Override
	public void initialize(URL location, ResourceBundle resource) {
		setBController(new LogInController());
		tf_signIn.setOnMouseClicked(e -> {
			try {
				LOGGER.info("User clicked to Sign In");
				validateLogin();
				if (isLogin) {
					HomeScreenHandler homeHandler = new HomeScreenHandler(this.stage, Configs.HOME_PATH);
					homeHandler.setScreenTitle("Home Screen");
					homeHandler.setImage();
					homeHandler.show();
				} else {
					LOGGER.info("False a Login");
					LogInFalseHandler.error();
				}
				
			} catch (Exception e1) {
			}
		});
	}
	
	public void setUserInformation(String userName, String email) {
		
	}
	
	public void validateLogin() throws SQLException {
		Statement stm = AIMSDB.getConnection().createStatement();
		String sql = "SELECT count(1) FROM User WHERE username = '" + tf_username.getText() +
				     "' AND password = '" + tf_password.getText() + "';";
		
		ResultSet re = stm.executeQuery(sql);
		try {
			if (re.getInt(1) == 1) {
				isLogin = true;
			} else {
				isLogin = true;
			}; 
		} catch(Exception e) {
		}
		
	}
	
	

}
