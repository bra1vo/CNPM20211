package views.screen.manage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.home.HomeScreenHandler;
import views.screen.popup.PopupScreen;
import controller.ManageController;
import controller.WorkingspaceController;
import entity.db.AIMSDB;
import entity.phong.Phong;
import entity.user.User;

public class ManageHandler extends BaseScreenHandler implements Initializable{
	private static Logger LOGGER = Utils.getLogger(ManageHandler.class.getName());
	
	@FXML
	private Button backButton;

	@FXML
	private TableView<User> userTableView;
	
	@FXML
	private TableColumn<User, Integer> id;
	
	@FXML
	private TableColumn<User, String> name;
	
	@FXML
	private TableColumn<User, String> email;
	
	@FXML
	private TableColumn<User, String> address;
	
	@FXML
	private TableColumn<User, String> phone;
	
	@FXML
	private TableColumn<User, String> username;
	
	@FXML
	private TableColumn<User, String> password;
	
	@FXML
	private TableColumn<User, String> role;
	
	
	
	public ManageHandler(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);	
	}
	
    public ManageController getBController() {
        return (ManageController) super.getBController();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setBController(new ManageController());
		
        
        backButton.setOnMouseClicked(e->{
        	LOGGER.info("Return Home Screen");
			try {
				HomeScreenHandler homeHandler = new HomeScreenHandler(this.stage, Configs.HOME_PATH);
				homeHandler.setScreenTitle("Home Screen");
				homeHandler.setImage();
				homeHandler.show();

			} catch (IOException e1) {
				e1.printStackTrace();
			}
        });
        doSomething();
        System.out.println("#########################");
        fillUpTable();
       
        
    }
    
    public void doSomething() {
    	id.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		email.setCellValueFactory(new PropertyValueFactory<>("email"));
		address.setCellValueFactory(new PropertyValueFactory<>("address"));
		phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		username.setCellValueFactory(new PropertyValueFactory<>("username"));
		password.setCellValueFactory(new PropertyValueFactory<>("password"));
		role.setCellValueFactory(new PropertyValueFactory<>("role"));
		
	}
	
    
    private void fillUpTable() {
    	
    	
    	try {
			ResultSet resultSet;
			Connection connection = AIMSDB.getConnection();
			
			String sql = "SELECT * FROM User;";
			resultSet = connection.createStatement().executeQuery(sql);
			List<User> data =  new ArrayList<User>();
			
			
			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getInt("id"));
				user.setusername(resultSet.getString("name"));
				user.setEmail(resultSet.getString("email"));
				user.setAddress(resultSet.getString("address"));
				user.setPhone(resultSet.getString("phone"));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
				user.setRole(resultSet.getString("role"));
				data.add(user);
				
				System.out.println(user.getEmail()+" " + user.getName());
			}
	
			ObservableList<User> dbData = FXCollections.observableArrayList(data);
			
			System.out.println(dbData);
			userTableView.setItems(dbData);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
        

     
    
    
}