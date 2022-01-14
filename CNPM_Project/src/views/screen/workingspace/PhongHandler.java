package views.screen.workingspace;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import entity.phong.Phong;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.FXMLScreenHandler;

public class PhongHandler extends FXMLScreenHandler {
	@FXML
	protected Label name;
	
	@FXML
	protected Label state;
	
	@FXML
	protected Button view;
	
	@FXML
	protected Button datphong;
	
	 private static final Logger LOGGER = Utils.getLogger(PhongHandler.class.getName());
	 private Phong phong;
	 private final WorkingspaceHandler ws;
	 
	 public PhongHandler(String screenPath,Phong phong, WorkingspaceHandler ws, Stage stage) throws IOException {
	        super(screenPath);
	        this.phong = phong;
	        this.ws = ws;
	        setPhongInfor();
	    }
	 
	 private void setPhongInfor() {
		 name.setText(phong.gettenphong());
		 state.setText(phong.gettrangthai());
	 }
	 
	 public void setPhong(Phong phong) {
		 this.phong = phong;
	 }
	 
	 public Phong getPhong() {
		 return phong;
	 }
}
