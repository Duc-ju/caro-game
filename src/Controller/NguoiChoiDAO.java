/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.NguoiChoi;

/**
 *
 * @author Admin
 */
public class NguoiChoiDAO extends DAO{

    public NguoiChoiDAO() {
        super();
    }
    public NguoiChoi kiemTraTaiKhoan(String taiKhoan,String matKhau){
        NguoiChoi nguoiChoi = null;
        try {
            Statement statement = con.createStatement();
            ResultSet rs= statement.executeQuery("SELECT *\n" +
"From NguoiChoi\n" +
"Where NguoiChoi.taiKhoan='"+ taiKhoan+"'\n" +
"and NguoiChoi.matKhau='"+matKhau+"'");
            while (rs.next()) {
                int ID = rs.getInt(1);
                String nickName = rs.getString(4);
                int soVanChoi = rs.getInt(5);
                int soVanThang = rs.getInt(6);
                nguoiChoi = new NguoiChoi(ID,taiKhoan,matKhau,nickName,soVanChoi,soVanThang);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nguoiChoi;
    }
    public NguoiChoi layNguoiChoi(int ID){
        NguoiChoi nguoiChoi = null;
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT *\n"
                    + "FROM NguoiChoi\n"
                    + "WHERE NguoiChoi.ID=" + ID);
            while (rs.next()) {
                String taiKhoan = rs.getString(2);
                String matKhau = rs.getString(3);
                String nickName = rs.getString(4);
                int soVanChoi = rs.getInt(5);
                int soVanThang = rs.getInt(6);
                nguoiChoi = new NguoiChoi(ID,taiKhoan,matKhau,nickName,soVanChoi,soVanThang);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nguoiChoi;
    }
    public void themNguoiChoi(String taiKhoan, String matKhau, String nickName) throws SQLException {
        Statement statement1 = con.createStatement();
        ResultSet rs = statement1.executeQuery("SELECT NguoiChoi.ID\n"
                + "From NguoiChoi\n"
                + "Where NguoiChoi.taiKhoan='" + taiKhoan + "'");
        if (rs.next()) {
            throw new SQLException("Tài khoản đã tồn tại, vui lòng chọn tên khác");
        }
        Statement statement = con.createStatement();
        statement.executeUpdate("Insert into NguoiChoi(taiKhoan,matKhau,nickName)\n"
                + "Values('" + taiKhoan + "','" + matKhau + "',N'" + nickName + "');");
    }

    public void themVanThang(NguoiChoi nc) {
        try {
            Statement statement = con.createStatement();
            statement.executeUpdate("UPDATE NguoiChoi\n"
                    + "SET soVanThang=" + nc.getSoVanThang()+ "\n"
                    + "WHERE NguoiChoi.id='" + nc.getID() + "'");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void themVanChoi(NguoiChoi nc) {
        try {
            Statement statement = con.createStatement();
            int temp = nc.getSoVanChoi();
            statement.executeUpdate("UPDATE NguoiChoi\n"
                    + "SET soVanChoi=" + temp + "\n"
                    + "WHERE NguoiChoi.id='" + nc.getID() + "'");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String layBangXepHang() {
        String res = "";
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("Select nickName, soVanChoi, soVanThang, (soVanThang*1.0/soVanChoi*100) As tiLeThang\n" +
"from NguoiChoi\n" +
"where soVanChoi != 0\n" +
"order by soVanThang DESC");
            int i=1;
            while (rs.next()) {
                String row  = "Top "+i+": Nickname: "+rs.getString(1)+", Số ván chơi: "+rs.getInt(2)+", Số ván thắng: "+rs.getInt(3)+", Tỉ lệ thắng: "+rs.getInt(4)+"%\n";
                res+=row;
                i++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return res;
    }
}
