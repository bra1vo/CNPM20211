package view.screen.logInFalse;

import java.io.IOException;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import utils.Configs;
import views.screen.BaseScreenHandler;

public class LogInFalseHandler extends BaseScreenHandler {
	
	@FXML
	ImageView falseicon;
	
	public LogInFalseHandler(Stage stage) throws IOException {
		super(stage, Configs.FALSE_LOGIN_PATH);
	}
	
	private static LogInFalseHandler logInFalse(String imagepath) throws IOException{
		LogInFalseHandler logFalse = new LogInFalseHandler(new Stage());
		logFalse.setImage(imagepath);
	    return logFalse;
	   }
	 
	public static void error() throws IOException{
		logInFalse(Configs.IMAGE_PATH + "/" + "falseImage.png").show(true);
	   }
	
	public void setImage(String path) {
        super.setImage(falseicon, path);
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
