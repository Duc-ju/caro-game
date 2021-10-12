/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.NguoiChoi;
import model.Phong;

/**
 *
 * @author Admin
 */
public class PhongDAO extends DAO{

    public PhongDAO() {
        super();
    }
    public void themPhong(Phong phong) throws SQLException{
        Statement statement = con.createStatement();
        statement.executeUpdate("INSERT INTO Phong(tenPhong,ID_ChuPhong,IP_Server,portNumber)\n" +
"VALUES(N'"+phong.getTenPhong()+"',"+phong.getChuPhong().getID()+",'"+phong.getIP_Server()+"',"+phong.getPort()+");");
        
    }
    public int layIDPhong(){
        int res = 0;
        try {

            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT TOP 1 ID\n"
                    + "FROM Phong\n"
                    + "ORDER BY ID DESC");
            if(rs.next())
                res = rs.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return res;
    }
    public List<Phong> layDanhSachPhong(NguoiChoi nc){
        List<Phong> listPhong = new ArrayList<>();
        try {
            Statement statement = con.createStatement();
            ResultSet rs= statement.executeQuery("Select NguoiChoi.*,Phong.ID,Phong.tenPhong,Phong.IP_Server,Phong.portNumber\n" +
"From Phong, NguoiChoi\n" +
"where phong.ID_ChuPhong = NguoiChoi.ID\n" +
"AND Phong.ID_Khach is null\n" +
"AND Phong.ID_ChuPhong != "+nc.getID()+"");
            while (rs.next()) {
                NguoiChoi chuPhong = new NguoiChoi();
                Phong  phong= new Phong();
                chuPhong.setID(rs.getInt(1));
                chuPhong.setTaiKhoan(rs.getString(2));
                chuPhong.setMatKhau(rs.getString(3));
                chuPhong.setNickName(rs.getString(4));
                chuPhong.setSoVanChoi(rs.getInt(5));
                chuPhong.setSoVanThang(rs.getInt(6));
                phong.setChuPhong(chuPhong);
                phong.setID(rs.getInt(7));
                phong.setTenPhong(rs.getString(8));
                phong.setIP_Server(rs.getString(9));
                phong.setPort(rs.getInt(10));
                listPhong.add(phong);
            } 
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listPhong;
    }
    public void thietLapKhach(Phong phong, NguoiChoi khach){
        try {
            Statement statement = con.createStatement();
            statement.executeUpdate("UPDATE Phong\n" +
                    "SET Phong.ID_Khach='"+khach.getID()+"'\n" +
                    "WHERE Phong.ID='"+phong.getID()+"';");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public NguoiChoi layKhach(Phong p){
        NguoiChoi n = new NguoiChoi();
        try {
            Statement statement = con.createStatement();
            ResultSet rs= statement.executeQuery("Select NguoiChoi.*\n" +
"from NguoiChoi,Phong\n" +
"where NguoiChoi.ID = Phong.ID_Khach\n" +
"and Phong.id = '"+p.getID()+"';");
            if (rs.next()) {
                n.setID(rs.getInt(1));
                n.setTaiKhoan(rs.getString(2));
                n.setMatKhau(rs.getString(3));
                n.setNickName(rs.getString(4));
                n.setSoVanChoi(rs.getInt(5));
                n.setSoVanThang(rs.getInt(6));
            } 
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }
}
