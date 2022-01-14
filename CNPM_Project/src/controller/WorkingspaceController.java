package controller;

import java.sql.SQLException;
import java.util.List;
import entity.phong.Phong;


public class WorkingspaceController extends BaseController {
	public List getAllPhong() throws SQLException{
        return Phong.getAllPhong();
	}
}
