/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Admin
 */
public class NguoiChoi {
    private int ID;
    private String taiKhoan;
    private String matKhau;
    private String nickName;
    private int soVanChoi;
    private int soVanThang;

    public NguoiChoi() {
    }

    public NguoiChoi(int ID, String taiKhoan, String matKhau, String nickName, int soVanChoi, int soVanThang) {
        this.ID = ID;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.nickName = nickName;
        this.soVanChoi = soVanChoi;
        this.soVanThang = soVanThang;
    }

    public NguoiChoi(String taiKhoan, String matKhau, String nickName, int soVanChoi, int soVanThang) {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.nickName = nickName;
        this.soVanChoi = soVanChoi;
        this.soVanThang = soVanThang;
    }


    public int getID() {
        return ID;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public String getNickName() {
        return nickName;
    }

    public int getSoVanChoi() {
        return soVanChoi;
    }

    public int getSoVanThang() {
        return soVanThang;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setSoVanChoi(int soVanChoi) {
        this.soVanChoi = soVanChoi;
    }

    public void setSoVanThang(int soVanThang) {
        this.soVanThang = soVanThang;
    }
    
}
