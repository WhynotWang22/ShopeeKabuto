package com.qlshopquanaonhom6.shoponline.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {
    Context context;
    public static final String INSERT_NGUOIDUNG="INSERT INTO NguoiDung(MaND, tenND, MK, SDT) VALUES ('admin', 'nguyen admin', 'admin','09357698')";
    public DBhelper(@Nullable Context context) {
        super(context, "dbname", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table NguoiDung(MaND text primary key, tenND text, MK text,SDT text)");
        db.execSQL(INSERT_NGUOIDUNG);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
db.execSQL("drop table if exists NguoiDung");
onCreate(db);
    }
}
