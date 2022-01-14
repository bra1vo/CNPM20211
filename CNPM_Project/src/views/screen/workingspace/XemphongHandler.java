package views.screen.workingspace;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import entity.db.AIMSDB;
import entity.phong.Phong;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.popup.PopupScreen;

public class XemphongHandler  extends BaseScreenHandler implements Initializable {
	private static Logger LOGGER = Utils.getLogger(XemphongHandler.class.getName());
	
	@FXML
	private Label ten1;
	
	@FXML
	private TextField xemten;
	
	@FXML 
	private TextField xemsdt;
	
	@FXML
	private Button huy;
	
	@FXML
	private Button xql;
	
	private final Phong phong;
	
	public XemphongHandler(Stage stage, String screenPath, Phong phong) throws IOException {
		super(stage, screenPath);	
		this.phong = phong;
		ten1.setText(phong.gettenphong());
		xemten.setText(phong.getnguoithue());
		xemsdt.setText(phong.getsdt());
	}
	
	public void initialize(URL url, ResourceBundle resourceBundle) {
    			huy.setOnMouseClicked(e -> {
    				try {
    					huy();
    					WorkingspaceHandler workingspace;
    	    			workingspace = new WorkingspaceHandler(this.stage, Configs.WORKINGSPACE_PATH);
    	                workingspace.setScreenTitle("Working space");
    	                workingspace.show();
    				}
    				catch (SQLException | IOException e1) {
    					e1.printStackTrace();
    	    		}
    	    	});
    			xql.setOnMouseClicked(e ->{
    	         	WorkingspaceHandler workingspace;
    	         	try {
    	                 workingspace = new WorkingspaceHandler(this.stage, Configs.WORKINGSPACE_PATH);
    	                 workingspace.setScreenTitle("Working space");
    	                 workingspace.show();
    	                 
    	         	}
    	         	catch (Exception e1) {
    	 				e1.printStackTrace();
    	 			}
    	         });
    			
    }
	
	public void huy() throws SQLException {
		Statement stm = AIMSDB.getConnection().createStatement();
		String sql = "UPDATE phong SET nguoithue = null, sdt = null, trangthai = 'Free' where tenphong = " + "'" + ten1.getText() + "'";
		int re = stm.executeUpdate(sql);
	}

}
