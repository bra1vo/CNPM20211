package views.screen.confirmpurchaserinfo;

import java.io.IOException;
import java.lang.System.Logger;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.LogManager;

import controller.PlaceOrderController;
import common.exception.InvalidPurchaserInfoException;
import entity.db.AIMSDB;
import entity.invoice.Invoice;
import entity.order.Order;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.invoice.InvoiceScreenHandler;
import views.screen.popup.PopupScreen;
import entity.user.User;

public class ConfirmPurchaserInfoScreenHandler extends BaseScreenHandler implements Initializable {


	@FXML
	private Button findButton;
	
	@FXML
	private Label screenTitle;

	// Customer
	@FXML
	private TextField name;

	@FXML
	private TextField phone;

	@FXML
	private TextField address;
	
	@FXML
	private TextField level;
	
	
	// Staff
	@FXML
	private TextField name2;

	@FXML
	private TextField phone2;

	@FXML
	private TextField level2;


	@FXML
	private ComboBox<String> province;
	
	@FXML
	private ComboBox<String> province1;
	

	private Order order;

	public ConfirmPurchaserInfoScreenHandler(Stage stage, String screenPath, Order order) throws IOException {
		super(stage, screenPath);
		this.order = order;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load
		name.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                content.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });
		this.province.getItems().addAll(Configs.PROVINCES);
		this.province1.getItems().addAll(Configs.PROVINCES);
		
		findButton.setOnMouseClicked(e->{
			try {
				findCustomer(Integer.parseInt(phone.getText()));
			} catch (Exception e2) {
				// TODO: handle exception
			}
		});
		
		name2.setText(Configs.user.getName());
		
		phone2.setText(Configs.user.getPhone());
		
//		level2.setText(String.valueOf(Configs.user.GetAccountLevel()));
		level2.setText("Staff");
	}

	@FXML
	void submitDeliveryInfo(MouseEvent event) throws IOException, InterruptedException, SQLException {
	
		// add info to messages
		HashMap messages = new HashMap<>();
		messages.put("name", name.getText());
		messages.put("phone", phone.getText());
		messages.put("address", address.getText());
//		messages.put("instructions", instructions.getText());
		messages.put("province", province.getValue());
		try {
			// process and validate delivery info
			getBController().processPurchaserInfo(messages);
		} catch (InvalidPurchaserInfoException e) {
			throw new InvalidPurchaserInfoException(e.getMessage());
		}
	
		order.setPurchaserInfo(messages);
		
		// create invoice screen
		Invoice invoice = getBController().createInvoice(order);
		BaseScreenHandler InvoiceScreenHandler = new InvoiceScreenHandler(this.stage, Configs.INVOICE_SCREEN_PATH, invoice);
		InvoiceScreenHandler.setPreviousScreen(this);
		InvoiceScreenHandler.setHomeScreenHandler(homeScreenHandler);
		InvoiceScreenHandler.setScreenTitle("Invoice Screen");
		InvoiceScreenHandler.setBController(getBController());
		InvoiceScreenHandler.show();
	}

	public PlaceOrderController getBController(){
		return (PlaceOrderController) super.getBController();
	}
	
	public boolean findCustomer(int phoneNumber) throws SQLException {
		
		Statement stm;
		try {
			int EMAIL_ADDRESS_COLUMN=3;
			int NAME_COLUMN=2;
			int PHONE_COLUMN = 5;
			int POINT_COLUMN = 9;
			int LEVEL_COLUMN = 10;
			
			stm = AIMSDB.getConnection().createStatement();
			String sql = "SELECT * FROM User WHERE phone = '" + phone.getText()+"'";
			ResultSet re = stm.executeQuery(sql);
			
			//create a costumer in configs with information of customer searched with phone number
			Configs.costumer = new User( re.getString(PHONE_COLUMN),re.getFloat(POINT_COLUMN),re.getInt(LEVEL_COLUMN));

			
			address.setText(re.getString(EMAIL_ADDRESS_COLUMN));
			name.setText(re.getString(NAME_COLUMN));
			level.setText(String.valueOf(re.getInt(LEVEL_COLUMN)));
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		
		return true;
	}

	public void notifyError(){
		// TODO: implement later on if we need
	}

}
