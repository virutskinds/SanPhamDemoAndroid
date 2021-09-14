package com.example.cuahangthietbonline.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangthietbonline.R;
import com.example.cuahangthietbonline.adapter.DienThoaiAdapter;
import com.example.cuahangthietbonline.model.Sanpham;
import com.example.cuahangthietbonline.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DienThoaiActivity extends AppCompatActivity {

    Toolbar toolbardt;
    ListView lvdt;
    DienThoaiAdapter dienThoaiAdapter;
    ArrayList<Sanpham> mangdt;
    int iddt = 0;
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
        Anhxa();
        GetIdLoaiSanPham();
        AcctionToolbar();
        GetDuLieuDienThoai(page);
        Getchitietsanpham();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menugiohang:
                Intent intent =new Intent(getApplicationContext(),GiohangActivity_1.class);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
    private void Getchitietsanpham() {
        lvdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham",mangdt.get(position));
                startActivity(intent);
            }
        });
    }
    private void GetDuLieuDienThoai(int Page) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String duongdan= Server.Duongdandienthoai+ String.valueOf(Page);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int ID = 0;
                String Tendienthoai = "";
                int Giadienthoai = 0;
                String Hinhanhdienthoai = "";
                String Motadienthoai = "";
                int IDdienthoai = 0;
                if (response != null && response.length() != 2) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0;i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            ID=jsonObject.getInt("id");
                            Tendienthoai=jsonObject.getString("tensp");
                            Giadienthoai=jsonObject.getInt("giasp");
                            Hinhanhdienthoai=jsonObject.getString("hinhanhsp");
                            Motadienthoai=jsonObject.getString("motasp");
                            IDdienthoai=jsonObject.getInt("idsanpham");
                            mangdt.add(new Sanpham(ID,Tendienthoai,Giadienthoai,Hinhanhdienthoai,Motadienthoai,IDdienthoai));
                            dienThoaiAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String, String>();
                param.put("idsanpham",String.valueOf(iddt));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void AcctionToolbar() {
        setSupportActionBar(toolbardt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbardt.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }
        );

    }
    private void GetIdLoaiSanPham() {
        iddt = getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("giatrisanpham",iddt+"");
    }

    private void Anhxa() {
        toolbardt = findViewById(R.id.toolbardienthoai);
        lvdt = findViewById(R.id.listviewdienthoai);
        mangdt = new ArrayList<>();
        dienThoaiAdapter =new DienThoaiAdapter(mangdt,getApplicationContext());
        lvdt.setAdapter(dienThoaiAdapter);
    }
}