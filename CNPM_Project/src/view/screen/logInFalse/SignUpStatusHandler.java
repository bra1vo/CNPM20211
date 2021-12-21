package view.screen.logInFalse;

import java.io.IOException;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.Configs;
import views.screen.BaseScreenHandler;

public class SignUpStatusHandler extends BaseScreenHandler {
	
	
	@FXML
	ImageView tf_statusSignUp;
	
	@FXML
	public Button tf_return;
	
	@FXML
	public Button tf_retry;
	
	public SignUpStatusHandler(Stage stage) throws IOException {
		super(stage, Configs.SIGN_UP_STATUS_PATH);
		
		tf_return.setOnMouseClicked(e -> {
			
		});
	}
	
	private static SignUpStatusHandler status(String imagepath) throws IOException{
		SignUpStatusHandler status = new SignUpStatusHandler(new Stage());
        status.setImage(imagepath);
        return status;
    }

    public static void success(String message) throws IOException{
    	status(Configs.IMAGE_PATH + "/" + "tickgreen.png").show(true);
    }

    public void setImage(String path) {
        super.setImage(tf_statusSignUp, path);
    }

    public void show(Boolean autoclose) {
        super.show();
        if (autoclose) close(0.8);
    }

    public void show(double time) {
        super.show();
        close(time);
    }

    public void close(double time){
        PauseTransition delay = new PauseTransition(Duration.seconds(time));
        delay.setOnFinished( event -> stage.close() );
        delay.play();
    }

}
