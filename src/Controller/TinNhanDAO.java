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

/**
 *
 * @author Admin
 */
public class TinNhanDAO extends DAO{

    public TinNhanDAO() {
        super();
    }
    public List<String> layTinNhan(NguoiChoi toi, NguoiChoi nguoiConLai){
        List<String> listTinNhan = new ArrayList<>();
        try {
            Statement statement = con.createStatement();
            ResultSet rs= statement.executeQuery("select TinNhan.ID_NguoiGui, noiDung\n" +
"from TinNhan\n" +
"where (TinNhan.ID_NguoiGui='"+toi.getID()+"' and TinNhan.ID_NguoiNhan='"+nguoiConLai.getID()+"')\n" +
"or (TinNhan.ID_NguoiGui='"+nguoiConLai.getID()+"' and TinNhan.ID_NguoiNhan='"+toi.getID()+"')");
            while (rs.next()) {
                int ID_gui = rs.getInt(1);
                String noiDung = rs.getString(2);
                if(ID_gui == toi.getID()){
                    listTinNhan.add("tôi: "+noiDung);
                }
                else listTinNhan.add(nguoiConLai.getNickName()+" : "+noiDung);
            } 
        } catch (SQLException ex) {
        }
        return listTinNhan;
    }
    public void themTinNhan(NguoiChoi nguoigui, NguoiChoi nguoinhan, String noidung){
        try {
            Statement statement = con.createStatement();
            statement.executeUpdate("INSERT INTO TinNhan(ID_NguoiGui,ID_NguoiNhan,noiDung)\n" +
"VALUES('"+nguoigui.getID()+"','"+nguoinhan.getID()+"',N'"+noidung+"');");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
