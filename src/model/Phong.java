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
public class Phong {
    private int ID;
    private String tenPhong;
    private NguoiChoi chuPhong;
    private NguoiChoi khach;
    private int chuPhongThang;
    private int khachThang;
    private String IP_Server;
    private int port;
    public Phong() {
    }

    public Phong(String tenPhong, NguoiChoi chuPhong) {
        this.tenPhong = tenPhong;
        this.chuPhong = chuPhong;
    }

    public Phong(int ID, String tenPhong, NguoiChoi chuPhong, NguoiChoi khach, int chuPhongThang, int khachThang, String IP_Server, int port) {
        this.ID = ID;
        this.tenPhong = tenPhong;
        this.chuPhong = chuPhong;
        this.khach = khach;
        this.chuPhongThang = chuPhongThang;
        this.khachThang = khachThang;
        this.IP_Server = IP_Server;
        this.port = port;
    }

    public int getID() {
        return ID;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public NguoiChoi getChuPhong() {
        return chuPhong;
    }

    public NguoiChoi getKhach() {
        return khach;
    }

    public int getChuPhongThang() {
        return chuPhongThang;
    }

    public int getKhachThang() {
        return khachThang;
    }

    public String getIP_Server() {
        return IP_Server;
    }

    public int getPort() {
        return port;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public void setChuPhong(NguoiChoi chuPhong) {
        this.chuPhong = chuPhong;
    }

    public void setKhach(NguoiChoi khach) {
        this.khach = khach;
    }

    public void setChuPhongThang(int chuPhongThang) {
        this.chuPhongThang = chuPhongThang;
    }

    public void setKhachThang(int khachThang) {
        this.khachThang = khachThang;
    }

    public void setIP_Server(String IP_Server) {
        this.IP_Server = IP_Server;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
