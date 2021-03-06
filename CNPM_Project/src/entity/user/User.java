package entity.user;

import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.media.Media;

public class User {
    
    private int id;
    private String name;
    private String email;
    private String address;
    private String phone;

    private AccountLevel accountLevel;

    private String username;
    private String password;
    private String role;

    public User(int id, String name, String email, String address, String phone, String username, String password, String role){
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    public User() {
    	
    }
    
    public User( String phone,float point, int level){
       
    	this.phone = phone;
        this.accountLevel = new AccountLevel(point,level); 
    }
    
    
    // override toString method
    @Override
    public String toString() {
        return "{" +
        	"  id='" + id + "'" +
            "  username='" + name + "'" +
            ", email='" + email + "'" +
            ", address='" + address + "'" +
            ", phone='" + phone + "'" +
            ", username='" + username + "'" +
            ", password='" + password + "'" +
            ", role='" + role + "'" +
            "}";
    } 

    // getter and setter
    public String getName() {
        return this.name;
    }

    public void setusername(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return this.phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public AccountLevel GetAccountLevel() {
    	return this.accountLevel;
    }
    
    public void UpdateAccountLevel(int paidMoney) {
    	accountLevel.UpdateAccountLevel(paidMoney);
    }
    
    // geter - seter username + pass

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPassword() {
        return this.password;
    }

	public void setRole(String string) {
		// TODO Auto-generated method stub
		role =string;
	}
	
	public String getRole() {
		return role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

   
}
