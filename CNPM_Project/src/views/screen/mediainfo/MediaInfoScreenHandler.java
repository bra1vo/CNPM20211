package views.screen.mediainfo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import entity.media.Book;
import entity.media.Media;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.Configs;
import views.screen.BaseScreenHandler;

public class MediaInfoScreenHandler extends BaseScreenHandler {
	
	@FXML
	ImageView image;
	
	@FXML
	Text name;
	
	@FXML 
	Text author;
	
	@FXML
	Text publisher;
	
	@FXML
	Text publishDate;
	
	@FXML
	Text language;
	
	@FXML
	Text title;
	
	@FXML
	Text numpages;
	

	public MediaInfoScreenHandler(Stage stage) throws IOException {
		super(stage, "/views/fxml/media_info.fxml");
	}
	
	private static MediaInfoScreenHandler mediaInfo(Book book) throws IOException {
		MediaInfoScreenHandler mediaInfo = new MediaInfoScreenHandler(new Stage());
		mediaInfo.setMediaInfo(book);
		return mediaInfo;
	}
	
	public void setMediaInfo(Book book) {
		File file = new File(book.getImageURL());
        Image imageS = new Image(file.toURI().toString());
        image.setImage(imageS);
        name.setText(book.getCoverType());
        author.setText(book.getAuthor());
        publisher.setText(book.getPublisher());
        language.setText(book.getLanguage());        
        numpages.setText(String.valueOf(book.getNumOfPages()));
        title.setText(book.getBookCategory());
	}
	
	public void setImage(String imagePath) {
		setImage(image, imagePath);
	}
	
	public static void success(Book book) throws IOException{
		System.out.println("HIIIIIIII");
        mediaInfo(book).show(true);
    }
	
	public void show(Boolean autoclose) {
	    super.show();
	    if (autoclose) close(3);
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
