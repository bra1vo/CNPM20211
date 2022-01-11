package views.screen.home;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import common.exception.MediaNotAvailableException;
import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.db.AIMSDB;
import entity.media.Book;
import entity.media.Media;
import entity.user.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.FXMLScreenHandler;
import views.screen.home.HomeScreenHandler;
import views.screen.mediainfo.MediaInfoScreenHandler;
import views.screen.popup.PopupScreen;

public class MediaHandler extends FXMLScreenHandler{

    @FXML
    protected ImageView mediaImage;

    @FXML
    protected Label mediaTitle;

    @FXML
    protected Label mediaPrice;

    @FXML
    protected Label mediaAvail;

    @FXML
    protected Spinner<Integer> spinnerChangeNumber;

    @FXML
    protected Button addToCartBtn;

    private static Logger LOGGER = Utils.getLogger(MediaHandler.class.getName());
    private Media media;
    private Book book;
    private HomeScreenHandler home;

    public MediaHandler(String screenPath, Media media, HomeScreenHandler home) throws SQLException, IOException{
        super(screenPath);
        this.media = media;
        this.home = home;
        addToCartBtn.setOnMouseClicked(event -> {
            try {
                if (spinnerChangeNumber.getValue() > media.getQuantity()) throw new MediaNotAvailableException();
                Cart cart = Cart.getCart();
                // if media already in cart then we will increase the quantity by 1 instead of create the new cartMedia
                CartMedia mediaInCart = home.getBController().checkMediaInCart(media);
                if (mediaInCart != null) {
                    mediaInCart.setQuantity(mediaInCart.getQuantity() + 1);
                }else{
                    CartMedia cartMedia = new CartMedia(media, cart, spinnerChangeNumber.getValue(), media.getPrice());
                    cart.getListMedia().add(cartMedia);
                    LOGGER.info("Added " + cartMedia.getQuantity() + " " + media.getTitle() + " to cart");
                }

                // subtract the quantity and redisplay
                media.setQuantity(media.getQuantity() - spinnerChangeNumber.getValue());
                mediaAvail.setText(String.valueOf(media.getQuantity()));
                home.getNumMediaCartLabel().setText(String.valueOf(cart.getTotalMedia() + " media"));
                PopupScreen.success("The media " + media.getTitle() + " added to Cart");
            } catch (MediaNotAvailableException exp) {
                try {
                    String message = "Not enough media:\nRequired: " + spinnerChangeNumber.getValue() + "\nAvail: " + media.getQuantity();
                    LOGGER.severe(message);
                    PopupScreen.error(message);
                } catch (Exception e) {
                    LOGGER.severe("Cannot add media to cart: ");
                }
                
            } catch (Exception exp) {
                LOGGER.severe("Cannot add media to cart: ");
                exp.printStackTrace();
            }
        });
        setMediaInfo();
        
        mediaImage.setOnMouseClicked(e -> {
        	try {
        		LOGGER.info("Image Click!");
        		if (media.getType().equals("book")) {
        			LOGGER.info("Image Click!");
        			setMediaBook();
        			
        			MediaInfoScreenHandler.success(book);
        			LOGGER.info("Type Book!");
        		} else {
        			LOGGER.info("Type Coffee");
        		}
        	} catch (Exception e2) {
        		
        	}
        });
    }

    public Media getMedia(){
        return media;
    }

    private void setMediaInfo() throws SQLException {
        // set the cover image of media
        File file = new File(media.getImageURL());
        Image image = new Image(file.toURI().toString());
        mediaImage.setFitHeight(160);
        mediaImage.setFitWidth(152);
        mediaImage.setImage(image);

        mediaTitle.setText(media.getTitle());
        mediaPrice.setText(Utils.getCurrencyFormat(media.getPrice()));
        mediaAvail.setText(Integer.toString(media.getQuantity()));
        spinnerChangeNumber.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1)
        );

        setImage(mediaImage, media.getImageURL());     
    }
    
    private void setMediaBook() throws SQLException {
    	Statement stm = AIMSDB.getConnection().createStatement();
    	String sql = "SELECT * FROM Book WHERE id = " + media.getId() + ";";
    	
    	ResultSet re = stm.executeQuery(sql);
    	
    	while (re.next()) {
			this.book = new Book(re.getInt("id"), media.getTitle(), media.getCategory(),
					media.getPrice(), media.getQuantity(), media.getType(),re.getString("author"), re.getString("coverType"),
					re.getString("publisher"), re.getDate("publishDate"), re.getInt("numOfpages"), re.getString("language"),
					re.getString("bookCategory"));
		}
    	
    	book.setMediaURL(media.getImageURL());
    	
    }
       
}
