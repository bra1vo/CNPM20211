package views.screen.workingspace;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import controller.WorkingspaceController;
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

public class DatphongHandler extends BaseScreenHandler implements Initializable  {
	private static Logger LOGGER = Utils.getLogger(DatphongHandler.class.getName());
	
	@FXML
	private Label ten;
	
	@FXML
	private TextField datten;
	
	@FXML
	private TextField datsdt;
	
	@FXML
	private Button dat;
	
	@FXML
	private Button ql;
	
	private final Phong phong;
	
	
	public DatphongHandler(Stage stage, String screenPath, Phong phong) throws IOException {
		super(stage, screenPath);	
		this.phong = phong;
		ten.setText(phong.gettenphong());
		
	}

    public void dat() throws SQLException {
    	Statement stm = AIMSDB.getConnection().createStatement();
		String sql = "UPDATE phong SET nguoithue = "
				     + "'" + datten.getText() + "'" + "," + "trangthai = 'Busy'," + "sdt = "
				     + "'" + datsdt.getText() +  "'" + "where tenphong = " + "'" + ten.getText() + "'" ;
		
		int re = stm.executeUpdate(sql);
		
	}
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	dat.setOnMouseClicked(e -> {
    		try {
    			if (datten.getText()!="" && datsdt.getText()!="") {
    			dat();
    			WorkingspaceHandler workingspace;
    			workingspace = new WorkingspaceHandler(this.stage, Configs.WORKINGSPACE_PATH);
                workingspace.setScreenTitle("Working space");
                workingspace.show();
    			}
    			else {
    				PopupScreen.error("Fail");
    			}
    		}
    		catch (SQLException | IOException e1) {
				e1.printStackTrace();
    		}
    	});
    	 ql.setOnMouseClicked(e ->{
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

}

