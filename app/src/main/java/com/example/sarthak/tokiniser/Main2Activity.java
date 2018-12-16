package com.example.sarthak.tokiniser;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,buy.OnFragmentInteractionListener,sell.OnFragmentInteractionListener,tokens.OnFragmentInteractionListener,register.OnFragmentInteractionListener{
    String resp;
    Intent i;
    RequestQueue MyRequestQueue;
    TextView t1,t2,t3,t4;
    ImageView ik;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        t1=(TextView)findViewById(R.id.latlon);
        t2=(TextView)findViewById(R.id.access);
        t3=(TextView)findViewById(R.id.res);
        t4=(TextView)findViewById(R.id.ins);
        ik=(ImageView)findViewById(R.id.logo);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment f=null;
        t4.setVisibility(View.INVISIBLE);
        ik.setVisibility(View.INVISIBLE);
        if (id == R.id.buy_property) {
            f=new buy();
        } else if (id == R.id.sell_property) {
            f=new sell();
        } else if (id == R.id.tokens) {
            f=new tokens();
        } else if (id == R.id.register) {
            f=new register();
        }
        if (f != null) {
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.game, f);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void next2(View view) {
        EditText e1=(EditText)findViewById(R.id.editText3);
        String s1=e1.getText().toString();
        Log.e("place",s1);
        String s="https://apis.mapmyindia.com/advancedmaps/v1/o3ntolj2whoxy4c3uc2ty2qbkuvjfwlg/geo_code?addr="+s1;
        MyRequestQueue = Volley.newRequestQueue(this);
        resp=" ";
        i=new Intent(Main2Activity.this,Main3Activity.class);

        JsonObjectRequest MyStringRequest =new JsonObjectRequest(Request.Method.GET, s,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    resp=response.getJSONArray("results").getJSONObject(0).getString("lat")+","+response.getJSONArray("results").getJSONObject(0).getString("lng");
                    Log.e("response1",resp);
                    t1=(TextView)findViewById(R.id.latlon);
                    t1.setText(resp);
                    secondone(resp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("THere is an error","ERROR");
                //This code is executed if there is an error.
            }
        });
        MyRequestQueue.add(MyStringRequest);
        Log.e("response",resp);

    }
    public void secondone(String res)
    {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
            String sr="https://outpost.mapmyindia.com/api/security/oauth/token";
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, sr, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        String ksa=jsonObject.getString("token_type")+" "+jsonObject.getString("access_token");
                        Log.e("response2",ksa);
                        t2=(TextView)findViewById(R.id.access);
                        t2.setText(ksa);
                        thirdone(ksa);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
                @Override
                public void onErrorResponse(VolleyError error) {
                    //This code is executed if there is an error.
                }
            }) {
                protected Map<String, String> getParams() {
                    Map<String, String> MyData = new HashMap<String, String>();
                    MyData.put("grant_type", "client_credentials");
                    MyData.put("client_id","1J7dpeLBd_XR2Q6Ufqw4imjQyFTRJFOc");
                    MyData.put("client_secret","pAjpcL1aNrnwfu2qaUPQrD3d7YW05Ruh");//Add the data you'd like to send to the server.
                    return MyData;
                }
            };
            MyRequestQueue.add(MyStringRequest);
    }
    public void thirdone(String som)
    {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());

        t1=(TextView)findViewById(R.id.latlon);
        Log.e("lanlong",t1.getText().toString());
        StringRequest getRequest = new StringRequest(Request.Method.GET,"https://atlas.mapmyindia.com/api/places/nearby/json?keywords=Hospital;Cofee;Schools&refLocation="+t1.getText().toString(),
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            Log.e("response",response);
                            JSONObject jso=null;
                            try {
                                jso=new JSONObject(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            t3=(TextView)findViewById(R.id.res);
                            try {
                                t3.setText(Integer.toString(jso.getJSONArray("suggestedLocations").length()));
                                Log.e("suggested",t3.getText().toString());
                                Intent i=new Intent(Main2Activity.this,Main3Activity.class);
                                i.putExtra("res",t3.getText().toString());
                                t1=(TextView)findViewById(R.id.latlon);
                                i.putExtra("lanlong",t1.getText().toString());
                                startActivity(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Log.d("ERROR","error => "+error.toString());
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  params = new HashMap<String, String>();
                        t2=(TextView)findViewById(R.id.access);
                        Log.e("access",t2.getText().toString());
                        params.put("Authorization", t2.getText().toString());
                    return params;
                }
            };
            MyRequestQueue.add(getRequest);

    }

    }


