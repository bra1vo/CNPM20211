package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import entity.cart.Cart;
import entity.cart.CartMedia;
import common.exception.InvalidPurchaserInfoException;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.order.OrderMedia;
import views.screen.popup.PopupScreen;

/**
 * This class controls the flow of place order usecase in our AIMS project
 * @author nguyenlm
 */
public class PlaceOrderController extends BaseController{

    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());

    /**
     * This method checks the avalibility of product when user click PlaceOrder button
     * @throws SQLException
     */
    public void placeOrder() throws SQLException{
        Cart.getCart().checkAvailabilityOfProduct();
    }

    /**
     * This method creates the new Order based on the Cart
     * @return Order
     * @throws SQLException
     */
    public Order createOrder() throws SQLException{
        Order order = new Order();
        for (Object object : Cart.getCart().getListMedia()) {
            CartMedia cartMedia = (CartMedia) object;
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(), 
                                                   cartMedia.getQuantity(), 
                                                   cartMedia.getPrice());    
            order.getlstOrderMedia().add(orderMedia);
        }
        return order;
    }

    /**
     * This method creates the new Invoice based on order
     * @param order
     * @return Invoice
     */
    public Invoice createInvoice(Order order) {
        return new Invoice(order);
    }

    /**
     * This method takes responsibility for processing the purchaser info from user
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    public void processPurchaserInfo(HashMap info) throws InterruptedException, IOException{
        LOGGER.info("Process Purchaser Info");
        LOGGER.info(info.toString());
        validatePurchaserInfo(info);
    }
    
    /**
   * The method validates the info
   * @param info
   * @throws InterruptedException
   * @throws IOException
   */
    public void validatePurchaserInfo(HashMap<String, String> info) throws InterruptedException, IOException{
/*    	if(!validateName(info.get("name"))){
            throw new InterruptedException("Name is invalid");
        }
        if(!validateAddress(info.get("address"))){
            throw new InterruptedException("Address is invalid");
        }
        if(!validatePhoneNumber(info.get("phone"))){
            throw new InterruptedException("Phone number is invalid");
        }
*/
    }
    
    public boolean validatePhoneNumber(String phoneNumber) {
    	// TODO: your work
    	return false;
    }
    
    public boolean validateName(String name) {
    	// TODO: your work
    	return false;
    }
    
    public boolean validateAddress(String address) {
    	// TODO: your work
    	return false;
    }
    
}
