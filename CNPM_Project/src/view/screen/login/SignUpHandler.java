package view.screen.login;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import controller.BaseController;
import controller.LogInController;
import controller.SignUpController;
import entity.db.AIMSDB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import view.screen.logInFalse.LogInFalseHandler;
import views.screen.BaseScreenHandler;

public class SignUpHandler extends BaseScreenHandler implements Initializable {
	
	public static Logger LOGGER = Utils.getLogger(LogInHandler.class.getName());
	
	@FXML
	private TextField tf_name;
	
	@FXML
	private TextField tf_age;
	
	@FXML
	private TextField tf_email;
	
	@FXML
	private TextField tf_address;
	
	@FXML
	private TextField tf_usernameup;
	
	@FXML 
	private TextField tf_passwordup;
	
	@FXML
	private TextField tf_phonenumberup;
	
	@FXML
	private Button tf_submit;
	
	@FXML
	public Button tf_returnSignIn;
	
	
	public SignUpHandler(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);
	}
	private static LogInFalseHandler logInFalse(String imagepath) throws IOException{
		LogInFalseHandler logFalse = new LogInFalseHandler(new Stage());
		logFalse.setImage(imagepath);
	    return logFalse;
	   }
	@Override
    public void show() {
        super.show();
    }
	
	public SignUpController getBController() {
        return (SignUpController) super.getBController();
    }
	
	public void makeNewAccount() throws SQLException {
		Statement stm = AIMSDB.getConnection().createStatement();
		String sql = "INSERT INTO User (name, email, address, phone, username, password) VALUES ("
				     + "'" + tf_name.getText() + "'" + "," 
				     + "'" + tf_email.getText() + "'" + "," 
				     + "'" + tf_address.getText() + "'" + "," 
				     + "'" + tf_phonenumberup.getText() + "'" + ","
				     + "'" + tf_usernameup.getText() + "'" + "," 
				     + "'" + tf_passwordup.getText() + "'" + ")" ;
		
		int re = stm.executeUpdate(sql);
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resource) {
		setBController(new SignUpController());
		
		tf_submit.setOnMouseClicked(e -> {
			LOGGER.info("Create New Account");
			try {
				makeNewAccount();
				
				//Check if create new account successfully, if not show failed dialog
				Statement stm = AIMSDB.getConnection().createStatement();
				String sql="select username from Users where username='"+tf_usernameup.getText()+"'";
				
				ResultSet re =stm.executeQuery(sql);
				if(re.last()) LogInFalseHandler.error();
				
			} catch (SQLException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
		
		tf_returnSignIn.setOnMouseClicked(e -> {
			LOGGER.info("Return Log In Screen");
			try {
				LogInHandler logHandler = new LogInHandler(this.stage, Configs.LOGIN_PATH);
				logHandler.setScreenTitle("Log In");
				logHandler.show();

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
	}

}
