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
public class TinNhan {
    private NguoiChoi nguoiGui;
    private NguoiChoi nguoiNhan;
    private String noiDung;

    public TinNhan(NguoiChoi nguoiGui, NguoiChoi nguoiNhan, String noiDung) {
        this.nguoiGui = nguoiGui;
        this.nguoiNhan = nguoiNhan;
        this.noiDung = noiDung;
    }

    public TinNhan() {
    }

    public NguoiChoi getNguoiGui() {
        return nguoiGui;
    }

    public NguoiChoi getNguoiNhan() {
        return nguoiNhan;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNguoiGui(NguoiChoi nguoiGui) {
        this.nguoiGui = nguoiGui;
    }

    public void setNguoiNhan(NguoiChoi nguoiNhan) {
        this.nguoiNhan = nguoiNhan;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
    
}
