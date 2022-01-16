package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;

import entity.user.User;

public class LogInController extends BaseController {
	
	
	private static Logger LOGGER = utils.Utils.getLogger(LogInController.class.getName());
	
	/**
     * This method takes responsibility for processing the info from user
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    public void processAccountInfo(HashMap info) throws InterruptedException, IOException{
        LOGGER.info(info.toString());
        validateAccountInfo(info);
    }
    
    /**
     * The method validates the info
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
      public void validateAccountInfo(HashMap<String, String> info) throws InterruptedException, IOException{
      	if(!validateUsername(info.get("username"))){
              throw new InterruptedException("Username is invalid");
          }
          if(!validatePassword(info.get("password"))){
              throw new InterruptedException("Password is invalid");
          }
         
      }
	
	public boolean validateUsername(String username) {
	    	// TODO: your work
	    	return true;
	}
	    
	public boolean validatePassword(String password) {
	    	// TODO: your work
	    	return true;
	}

}
