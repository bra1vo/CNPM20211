package views.screen.workingspace;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.home.HomeScreenHandler;
import views.screen.popup.PopupScreen;
import controller.WorkingspaceController;
import entity.db.AIMSDB;
import entity.phong.Phong;

public class WorkingspaceHandler extends BaseScreenHandler implements Initializable{
	private static Logger LOGGER = Utils.getLogger(WorkingspaceHandler.class.getName());
	
	@FXML
	private VBox vboxPhong;
	
	@FXML
	private Button home;
	
    private List homeItems;
	
	public WorkingspaceHandler(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);	
	}
	
    public WorkingspaceController getBController() {
        return (WorkingspaceController) super.getBController();
    }
    
    @Override
    public void show() {
        setupScreen();
        super.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setBController(new WorkingspaceController());
		
    }
    
    public void setupScreen() {
        try{
            List medium = getBController().getAllPhong();
            this.homeItems = new ArrayList<>();
            for (Object object : medium){
                Phong phong = (Phong) object;
                PhongHandler d = new PhongHandler(Configs.PHONG_PATH, phong, this, this.stage);
                this.homeItems.add(d);
            }
        } catch (SQLException|IOException e) {
            LOGGER.info("Errors occured when get phong");
            e.printStackTrace();
        }
        
        home.setOnMouseClicked(e -> {
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
        
        addPhong();
        
        
    }
    
    public void addPhong() {
    	 vboxPhong.getChildren().clear();
    	 for(Object object: this.homeItems){
            PhongHandler phong = (PhongHandler) object;
            phong.datphong.setOnMouseClicked(event -> { 
	        	try{
	        		if(phong.getPhong().gettrangthai().equals("Free")) {
	        			DatphongHandler phongHandler = new DatphongHandler(this.stage, Configs.DATPHONG_PATH, phong.getPhong());
	    				phongHandler.show();
	        		}
	        		else PopupScreen.error("Room has been booked");
	        	}
			 catch (Exception e1) {
				e1.printStackTrace();
			}
            });
            phong.view.setOnMouseClicked(event -> {
            	try {
            		if(phong.getPhong().gettrangthai().equals("Busy")) {
            		XemphongHandler xphongHandler = new XemphongHandler(this.stage, Configs.XEMPHONG_PATH, phong.getPhong());
    				xphongHandler.show();
            		}
            	else PopupScreen.error("Room hasn't been booked");
            	}
			 catch (Exception e1) {
				e1.printStackTrace();
			 }
           });
             vboxPhong.getChildren().add(phong.getContent());
         }
         return;
    }
    
    
}