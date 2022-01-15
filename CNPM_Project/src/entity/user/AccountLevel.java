package entity.user;

public class AccountLevel {
    private float Point;
    private int level;
    
    
    public AccountLevel(float point, int level) {
    	this.Point = point;
    	this.level = level;
    }
    
    /**
     * Update account level
     * needs money paid from (invoice) or (working space rent)
     * @param paidMoney
     */
    public void UpdateAccountLevel(int paidMoney) {
    	UpdatePoint(paidMoney);
    	UpdateLevel();
    }
    
    
    public float GetPoint() {
    	return Point;
    }
    
    public int GetLevel() {
    	return level;
    }
    
    /**
    * update the point of a user account
    */
    private void UpdatePoint(int paidMoney) {
    	Point += paidMoney/50;
    }
    
    
    /**
     * level 0 = bronze/
     * level 1 = silver/
     * level 2 = gold/
     * level 3 = diamond/
     * level 4 = VIP/
     * 
    */		
    private void UpdateLevel() {
    	if(Point <50) level = 0;
    	else if(Point >= 50 && Point <100) level = 1;
    	else if(Point >=100 && Point <200) level = 2;
    	else if(Point >=200 && Point <500) level = 3;
    	else if (Point >=500)              level = 4;
    }
    
    
}
