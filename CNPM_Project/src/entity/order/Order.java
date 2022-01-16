package entity.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utils.Configs;

public class Order {
    
    private List lstOrderMedia;
    private HashMap<String, String> purchaserInfo;

    public Order(){
        this.lstOrderMedia = new ArrayList<>();
    }

    public Order(List lstOrderMedia) {
        this.lstOrderMedia = lstOrderMedia;
    }

    public void addOrderMedia(OrderMedia om){
        this.lstOrderMedia.add(om);
    }

    public void removeOrderMedia(OrderMedia om){
        this.lstOrderMedia.remove(om);
    }

    public List getlstOrderMedia() {
        return this.lstOrderMedia;
    }

    public void setlstOrderMedia(List lstOrderMedia) {
        this.lstOrderMedia = lstOrderMedia;
    }

    public HashMap getPurchaserInfo() {
        return purchaserInfo;
    }

    public void setPurchaserInfo(HashMap purchaserInfoMap) {
        this.purchaserInfo = purchaserInfoMap;
    }

    public int getAmount(){
        double amount = 0;
        for (Object object : lstOrderMedia) {
            OrderMedia om = (OrderMedia) object;
            amount += om.getPrice();
        }
        return (int) (amount + (Configs.PERCENT_VAT/100)*amount);
    }

}
