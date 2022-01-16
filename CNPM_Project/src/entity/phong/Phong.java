package entity.phong;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import entity.db.AIMSDB;
import entity.phong.Phong;
import utils.Utils;

public class Phong {
	 private static final Logger LOGGER = Utils.getLogger(Phong.class.getName());
	 
	 protected Statement statement;
	 protected int phongid;
	 protected String tenphong;
	 protected String trangthai;
	 protected String nguoithue;
	 protected String sdt;
	 protected int table;
	 protected String area;
	 protected int price;
	 
	 public Phong() throws SQLException{
	        statement = AIMSDB.getConnection().createStatement();
	    }
	 
	 public Phong(int id, String name, String trangthai, String nguoithue, String sdt, int table, String area, int price) throws SQLException {
		 this.phongid = id;
		 this.tenphong = name;
		 this.trangthai = trangthai;
		 this.nguoithue = nguoithue;
		 this.sdt = sdt;
		 this.table = table;
		 this.area = area;
		 this.price = price;
	 }
	 
	 public static List getAllPhong() throws SQLException {
		 Statement statement = AIMSDB.getConnection().createStatement();
		 ResultSet res = statement.executeQuery("select * from phong");
		 ArrayList medium = new ArrayList<>();
	        while(res.next()){
	            Phong phong = new Phong(res.getInt("phongid"), res.getString("tenphong"), res.getString("trangthai"), res.getString("nguoithue"), res.getString("sdt"),
	            		res.getInt("table"), res.getString("area"), res.getInt("price"));
	            medium.add(phong);
	        }
	        return medium;
	 }
	 
	 public void setphongid(int phongid) {
		 this.phongid = phongid;
	 }
	 
	 public int getphongid() {
		 return phongid;
	 }
	 
	 public void settenphong(String tenphong) {
		 this.tenphong = tenphong;
	 }
	 
	 public String gettenphong() {
		 return tenphong;
	 }
	 
	 public void settrangthai(String trangthai) {
		 this.trangthai = trangthai;
	 }
	 
	 public String gettrangthai() {
		 return trangthai;
	 }
	 
	 public void setnguoithue(String nguoithue) {
		 this.nguoithue= nguoithue;
	 }
	 
	 public String getnguoithue() {
		 return nguoithue;
	 }
	 
	 public void setsdt(String sdt) {
		 this.sdt = sdt;
	 }
	 
	 public String getsdt() {
		 return sdt;
	 }
	 
	 public void setarea(String area) {
		 this.area = area;
	 }
	 
	 public String getarea() {
		 return area;
	 }
	 
	 public void settable(int table) {
		 this.table = table;
	 }
	 
	 public int gettable() {
		 return table;
	 }
	 
	 public void setprice(int price) {
		 this.price = price;
	 }
	 
	 public int getprice() {
		 return price;
	 }
}
