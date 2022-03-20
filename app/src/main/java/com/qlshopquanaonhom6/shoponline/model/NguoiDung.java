package com.qlshopquanaonhom6.shoponline.model;

public class NguoiDung {
    private String MaND;
    private String TenND;
    private String MK;
    private String SDT;

    public String getMaND() {
        return MaND;
    }

    public void setMaND(String maND) {
        MaND = maND;
    }

    public String getTenND() {
        return TenND;
    }

    public void setTenND(String tenND) {
        TenND = tenND;
    }

    public String getMK() {
        return MK;
    }

    public void setMK(String MK) {
        this.MK = MK;
    }



    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public NguoiDung() {
    }

    public NguoiDung(String maND, String tenND, String MK, String SDT) {
        MaND = maND;
        TenND = tenND;
        this.MK = MK;
        this.SDT = SDT;
    }
}
