package com.example.cuahangthietbonline.activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangthietbonline.R;
import com.example.cuahangthietbonline.util.CheckConnection;
import com.example.cuahangthietbonline.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Thongtinkhachhang extends AppCompatActivity {
    Button btnxacnhan,btntrove;
    EditText edtten,edtemail,edtsdt,edtdiachi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinkhachhang);
        AnhXa();
        EventButton();
    }
    private void EventButton() {
        btnxacnhan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               final String ten = edtten.getText().toString().trim();
               final String sdt = edtsdt.getText().toString().trim();
               final String email=edtemail.getText().toString().trim();
               final String diachi=edtdiachi.getText().toString().trim();
               if(ten.length()>0 && sdt.length() > 0 && email.length() >0 && diachi.length()>0)
               {
                   RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                   final StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.Donhang, new Response.Listener<String>() {
                       @Override
                       public void onResponse(String madonhang) {
                            Log.d("madonhang",madonhang);
                            if(Integer.parseInt(madonhang)>0)
                            {
                                RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                                StringRequest request =new StringRequest(Request.Method.POST, Server.chitietdonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("1"))
                                        {
                                            MainActivity.manggiohang.clear();
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn đã mua hàng thành công");
                                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Mời bạn tiếp tục mua hàng");
                                        }
                                        else {
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Dữ liệu giỏ hàng của bạn đã bị lỗi");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Nullable
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for(int i =0;i < MainActivity.manggiohang.size();i++)
                                        {
                                            JSONObject jsonObject=new JSONObject();
                                            try {
                                                jsonObject.put("madonhang",madonhang);
                                                jsonObject.put("masanpham",MainActivity.manggiohang.get(i).getIdsp());
                                                jsonObject.put("tensanpham",MainActivity.manggiohang.get(i).getTensp());
                                                jsonObject.put("giasanpham",MainActivity.manggiohang.get(i).getGiasp());
                                                jsonObject.put("soluongsanpham",MainActivity.manggiohang.get(i).getSoluongsp());

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);

                                        }
                                        HashMap<String,String> hashMap = new HashMap<>();
                                        hashMap.put("json",jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            }
                       }
                   }, new Response.ErrorListener() {
                       @Override
                       public void onErrorResponse(VolleyError error) {
                       }
                   }){
                       @NonNull
                       @Override
                       protected Map<String, String> getParams() {
                           HashMap<String,String> hashMap= new HashMap<>();
                           hashMap.put("tenkhachhang",ten);
                           hashMap.put("sodienthoai",sdt);
                           hashMap.put("email",email);
                           hashMap.put("diachinhanhang",diachi);
                           return hashMap;
                       }
                   };
                   requestQueue.add(stringRequest);
               }
               else {
                   CheckConnection.ShowToast_Short(getApplicationContext(),"Hãy kiểm tra lại dữ liệu");
               }
            }
        });
        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void AnhXa() {
        btnxacnhan=findViewById(R.id.btnxacnhan);
        btntrove=findViewById(R.id.btntrove);
        edtten=findViewById(R.id.edittextten);
        edtsdt=findViewById(R.id.edittextphone);
        edtemail=findViewById(R.id.edittextemail);
        edtdiachi=findViewById(R.id.edittextdiachi);
    }
}