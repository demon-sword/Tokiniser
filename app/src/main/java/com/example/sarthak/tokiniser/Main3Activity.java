package com.example.sarthak.tokiniser;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.mapbox.mapboxsdk.*;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

public class Main3Activity extends AppCompatActivity {
Marker marker1,marker2,marker3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this,"pk.eyJ1IjoiZGVtb25zd29yZCIsImEiOiJjamZhN3pqbzAxdDJ1MnhtbXI4cmlrMTBuIn0.Qlr4FK4Ns1ZNtXltYZNwOw");
        setContentView(R.layout.activity_main3);
        MapView mapView;
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                       // One way to add a marker view
                String s=getIntent().getStringExtra("lanlong");
                String s1[]=s.split(",");
                String sk=getIntent().getStringExtra("res");
                CameraPosition cm=new CameraPosition.Builder()
                        .target(new LatLng(Double.parseDouble(s1[0]),Double.parseDouble(s1[1])))
                        .zoom(10)
                        .bearing(0)
                        .tilt(0)
                        .build();
                mapboxMap.setCameraPosition(cm);
                marker1=mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(s1[0]),Double.parseDouble(s1[1])))
                        .title(Integer.toString(Integer.parseInt(sk)*10))
                        .snippet(Integer.toString(Integer.parseInt(sk)*10))

                );
                marker2=mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(s1[0])-0.01,Double.parseDouble(s1[1])+0.01))
                        .title(Integer.toString((Integer.parseInt(sk)*10)-10))
                        .snippet(Integer.toString((Integer.parseInt(sk)*10)-10))
                );
                marker3=mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(s1[0])+0.01,Double.parseDouble(s1[1])-0.01))
                        .title(Integer.toString((Integer.parseInt(sk)*10)+10))
                        .snippet(Integer.toString((Integer.parseInt(sk)*10)+10))
                );
                mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        if(marker.equals(marker1))
                        {
                            Intent i=new Intent(Main3Activity.this,Main4Activity.class);
                            i.putExtra("qrcode",getIntent().getStringExtra("lanlong")+getIntent().getStringExtra("res"));
                            i.putExtra("res",Integer.toString((Integer.parseInt(getIntent().getStringExtra("res"))*10)));
                            startActivity(i);
                        }
                        else if(marker.equals(marker2))
                        {
                            Intent i=new Intent(Main3Activity.this,Main4Activity.class);
                            i.putExtra("qrcode",getIntent().getStringExtra("lanlong")+getIntent().getStringExtra("res"));
                            i.putExtra("res",Integer.toString((Integer.parseInt(getIntent().getStringExtra("res"))*10)-10));
                            startActivity(i);
                        }
                        else if(marker.equals(marker3))
                        {
                            Intent i=new Intent(Main3Activity.this,Main4Activity.class);
                            i.putExtra("qrcode",getIntent().getStringExtra("lanlong")+getIntent().getStringExtra("res"));
                            i.putExtra("res",Integer.toString((Integer.parseInt(getIntent().getStringExtra("res"))*10)+10));
                            startActivity(i);
                        }
                        return false;
                    }
                });
            }

        });



    }
}
