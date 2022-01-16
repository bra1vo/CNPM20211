package views.screen.invoice;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Logger;

import common.exception.ProcessInvoiceException;
import controller.PaymentController;
import entity.db.AIMSDB;
import entity.invoice.Invoice;
import entity.user.*;
import entity.order.Order;
import entity.order.OrderMedia;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.payment.PaymentScreenHandler;

public class InvoiceScreenHandler extends BaseScreenHandler {

	private static Logger LOGGER = Utils.getLogger(InvoiceScreenHandler.class.getName());

	@FXML
	private Label pageTitle;

	@FXML
	private Label name;

	@FXML
	private Label phone;

	@FXML
	private Label province;

	@FXML
	private Label address;

	@FXML
	private Label instructions;

	@FXML
	private Label subtotal;
	
	@FXML
	private Label discount; //new

	@FXML
	private Label shippingFees;

	@FXML
	private Label total;

	@FXML
	private VBox vboxItems;

	private Invoice invoice;

	public InvoiceScreenHandler(Stage stage, String screenPath, Invoice invoice) throws IOException {
		super(stage, screenPath);
		this.invoice = invoice;
		setInvoiceInfo();
	}

	private void setInvoiceInfo(){		
		HashMap<String, String> deliveryInfo = invoice.getOrder().getDeliveryInfo();
		name.setText(deliveryInfo.get("name"));
		province.setText(deliveryInfo.get("province"));
		instructions.setText(deliveryInfo.get("instructions"));
		address.setText(deliveryInfo.get("address"));
		subtotal.setText(Utils.getCurrencyFormat(invoice.getOrder().getAmount()));
		
		int discountPercent = getDiscount(Configs.costumer);
		discount.setText(String.valueOf(discountPercent) + "%");
		
		shippingFees.setText(Utils.getCurrencyFormat(invoice.getOrder().getShippingFees()));
		int amount = invoice.getOrder().getAmount()*(100-discountPercent)/100 + invoice.getOrder().getShippingFees();
		total.setText(Utils.getCurrencyFormat(amount));
		invoice.setAmount(amount);
		invoice.getOrder().getlstOrderMedia().forEach(orderMedia -> {
			try {
				MediaInvoiceScreenHandler mis = new MediaInvoiceScreenHandler(Configs.INVOICE_MEDIA_SCREEN_PATH);
				mis.setOrderMedia((OrderMedia) orderMedia);
				vboxItems.getChildren().add(mis.getContent());
			} catch (IOException | SQLException e) {
				System.err.println("errors: " + e.getMessage());
				throw new ProcessInvoiceException(e.getMessage());
			}
			
		});

	} 

	@FXML
	void confirmInvoice(MouseEvent event) throws IOException {
		
		
		BaseScreenHandler paymentScreen = new PaymentScreenHandler(this.stage, Configs.PAYMENT_SCREEN_PATH, invoice);
		paymentScreen.setBController(new PaymentController());
		paymentScreen.setPreviousScreen(this);
		paymentScreen.setHomeScreenHandler(homeScreenHandler);
		paymentScreen.setScreenTitle("Payment Screen");
		paymentScreen.show();
		LOGGER.info("Confirmed invoice");
		
		//update Level and Point of costumer 	
				Configs.costumer.UpdateAccountLevel(invoice.getAmount());											
				try {
				UpdateToDB(Configs.costumer);
				Configs.costumer = null;
				}catch(Exception e) {
					e.printStackTrace();
				}


		
		
	}
	
	private int getDiscount(User user) {
		int userLevel = user.GetAccountLevel().GetLevel();
    	if      (userLevel == 1) return 5;
    	else if (userLevel == 2) return 10;
    	else if (userLevel == 3) return 20;
    	else if (userLevel == 4) return 35;
    	else                     return 0;
	}
	
	private void UpdateToDB(User user)throws SQLException {
		Statement stm;
		try {
		stm = AIMSDB.getConnection().createStatement();
		
		String newPoint = String.valueOf(user.GetAccountLevel().GetPoint());		
		String newLevel = String.valueOf(user.GetAccountLevel().GetLevel());
		String userPhone = user.getPhone();
		
		String sqlUpdate = "UPDATE User " + "SET point = " + newPoint + ", " + "level = " + newLevel + " " 
							+ "WHERE phone = "+ userPhone;
		stm.executeUpdate(sqlUpdate);
		
	}catch (SQLException e) {
		e.printStackTrace();
	}
		}
	
}
