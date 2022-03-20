package com.qlshopquanaonhom6.shoponline.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.qlshopquanaonhom6.shoponline.database.DBhelper;
import com.qlshopquanaonhom6.shoponline.model.NguoiDung;

import java.util.ArrayList;
import java.util.List;

public class NguoiDungDAO {
    private SQLiteDatabase db;

    public NguoiDungDAO(Context context) {
        DBhelper dBhelper =new DBhelper(context);
        db=dBhelper.getWritableDatabase();
    }
    public long insert(NguoiDung obj){
        ContentValues values =new ContentValues();
        values.put("MaND",obj.getMaND());
        values.put("tenND",obj.getTenND());
        values.put("MK",obj.getMK());
        values.put("SDT",obj.getSDT());
        return db.insert("NguoiDung",null,values);
    }
    public long updatePass(NguoiDung obj){
        ContentValues values =new ContentValues();
        values.put("TenND",obj.getTenND());
        values.put("MK",obj.getMK());
        return db.update("NguoiDung",values,"MaND=?",new String[]{obj.getMaND()});
    }
    public int delete(String id){
        return db.delete("NguoiDung","MaND=?",new String[]{id});
    }
    @SuppressLint("Range")
    public List<NguoiDung> getdata(String sql, String...selectionArgs){
        List<NguoiDung> list =new ArrayList<>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            NguoiDung obj =new NguoiDung();
            obj.setMaND(c.getString(c.getColumnIndex("MaND")));
            obj.setTenND(c.getString(c.getColumnIndex("tenND")));
            obj.setMK(c.getString(c.getColumnIndex("MK")));
            obj.setSDT(c.getString(c.getColumnIndex("SDT")));
            list.add(obj);
        }
        return list;
    }
    public List<NguoiDung> getall(){
        String sql="select * from NguoiDung";
        return getdata(sql);
    }
    public NguoiDung getID(String id){
        String sql="select * from NguoiDung WHERE MaND=?";
        List<NguoiDung> list =getdata(sql,id);
        return list.get(0);
    }
    public int checkLogin(String id,String pass){
        String sql="select * from NguoiDung where MaND=? and MK=?";
        List<NguoiDung> list =getdata(sql,id,pass);
        if (list.size()==0){
            return -1;
        }
        return 1;
    }
}
