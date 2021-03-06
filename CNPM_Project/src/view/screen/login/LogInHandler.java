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
import entity.user.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
	private Button viewCustomer;
	
	@FXML
	private TextField tf_username;

	@FXML
	private PasswordField tf_password;

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
			loginToHomeSceen();
		});
		
		tf_password.setOnKeyPressed(e->{
			if(e.getCode()==KeyCode.ENTER){
				loginToHomeSceen();
			}
		});
		
		tf_username.setOnKeyPressed(e->{
			if(e.getCode()==KeyCode.ENTER){
				loginToHomeSceen();
			}
		});
		
		tf_signUp.setOnMouseClicked(e -> {
			try {
				LOGGER.info("User clicked to Sign Up");
				SignUpHandler signUp = new SignUpHandler(this.stage, Configs.SIGN_UP_PATH);
				signUp.setScreenTitle("Sign Up");
				signUp.show();
			} catch (Exception e2) {
				
			}
		});
		
		viewCustomer.setOnMouseClicked(e -> {
			Configs.isCustomer = true;
			loginToHomeSceenByUser();
		});
	}
	
	private void loginToHomeSceenByUser() {
		// TODO Auto-generated method stub
		try {
			LOGGER.info("User clicked to Sign In by Customer");		
			HomeScreenHandler homeHandler = new HomeScreenHandler(this.stage, Configs.HOME_PATH);
			homeHandler.setScreenTitle("Home Screen");
			homeHandler.setImage();
			
			homeHandler.setCurrentUser("C");
			homeHandler.setManageButtonOnOrOff(); 
//			System.out.println("FALSSSSSS");
			homeHandler.show();
			
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void loginToHomeSceen() {
		try {
			LOGGER.info("User clicked to Sign In");
			validateLogin();
			if (isLogin) {		
				HomeScreenHandler homeHandler = new HomeScreenHandler(this.stage, Configs.HOME_PATH);
				homeHandler.setScreenTitle("Home Screen");
				homeHandler.setImage();
				
				try {
					Statement stm = AIMSDB.getConnection().createStatement();
					String sql = "SELECT username,role FROM User WHERE username='"+ tf_username.getText() +"';";
					ResultSet re = stm.executeQuery(sql);
					String str =re.getString(2);
					homeHandler.setCurrentUser(str);
					
					System.out.println("ROLE ROLE ROLE "+str);
					
					homeHandler.setCurrentUser(str);
					homeHandler.setManageButtonOnOrOff();
					homeHandler.userNameLabel.setText(re.getString(1));
				} 
				catch(Exception e) {
					e.printStackTrace();
				}
				homeHandler.show();
			} else {
				LOGGER.info("False a Login");
				LogInFalseHandler.error();
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	

	public void validateLogin() throws SQLException {
		Statement stm = AIMSDB.getConnection().createStatement();
		String sql = "SELECT count(1) FROM User WHERE username = '" + tf_username.getText() +
				     "' AND password = '" + tf_password.getText() + "';";
		
		String sql2 = "SELECT * FROM User Where username = '" + tf_username.getText() +
					"' AND password = '" + tf_password.getText() + "';";
		
		ResultSet re = stm.executeQuery(sql);
		try {
			if (re.getInt(1) == 1) {
				isLogin = true;
				
				ResultSet re2 = stm.executeQuery(sql2);
				while (re2.next()) {
					Configs.user = new User(re2.getInt("id"), re2.getString("name"), re2.getString("email"),
							re2.getString("address"), re2.getString("phone"), re2.getString("username"),
							re2.getString("password"), re2.getString("role"));
				}
				
			} else {
				isLogin = false;
			}; 
		} catch(Exception e) {
		}
		
	}
	
	

}
