package com.qlshopquanaonhom6.shoponline.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.qlshopquanaonhom6.shoponline.R;

public class ThongtinActivity extends  FragmentActivity implements OnMapReadyCallback {
    GoogleMap map;
Toolbar toolbarthongtin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtin);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Anhxa();
        Actiontoolbar();
    }

    private void Actiontoolbar() {
        toolbarthongtin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbarthongtin = findViewById(R.id.toolbarthongtin);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        LatLng kienquoc = new LatLng(20.733370,106.639450);
        map.addMarker(new MarkerOptions().position(kienquoc).title("Kiến Quốc"));
        map.moveCamera(CameraUpdateFactory.newLatLng(kienquoc));
    }
}