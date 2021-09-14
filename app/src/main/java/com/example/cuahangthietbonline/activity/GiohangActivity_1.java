package com.example.cuahangthietbonline.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.cuahangthietbonline.R;
import com.example.cuahangthietbonline.adapter.GioHangAdapter;
import com.example.cuahangthietbonline.util.CheckConnection;

import java.text.DecimalFormat;

public class GiohangActivity_1 extends AppCompatActivity {
    ListView lvgiohang;
    TextView txtthongbao;
    static TextView txttongtien;
    Button btnThanhtoan,btntieptucmua;
    Toolbar toolbargiohang;
    GioHangAdapter gioHangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang1);
        Anhxa();
        Actionbar();
        CheckData();
        EnventUltil();
        CactchOnItemListview();
        EventButton();
    }

    private void EventButton() {
        btntieptucmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnThanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.manggiohang.size() > 0) {
                    Intent intent = new Intent(getApplicationContext(), Thongtinkhachhang.class);
                    startActivity(intent);
                }
                else
                {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Giỏ hàng của bạn không có sản phẩm");
                }
            }
        });
    }


    private void CactchOnItemListview() {
        lvgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(GiohangActivity_1.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn chắc chắn xóa sản phẩm này");
                builder.setPositiveButton("Có",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MainActivity.manggiohang.size() <= 0 ){
                            txtthongbao.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            MainActivity.manggiohang.remove(i);
                            gioHangAdapter.notifyDataSetChanged();
                            EnventUltil();
                            if(MainActivity.manggiohang.size() <= 0 )
                            {
                                txtthongbao.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                txtthongbao.setVisibility(View.INVISIBLE);
                                gioHangAdapter.notifyDataSetChanged();
                                EnventUltil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EnventUltil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EnventUltil() {
        long tongtien = 0;
        for (int i =0 ; i < MainActivity.manggiohang.size();i++)
        {
            tongtien += MainActivity.manggiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txttongtien.setText(decimalFormat.format(tongtien) + "Đ");

    }

    private void CheckData() {
        if(MainActivity.manggiohang.size() <= 0){
            txtthongbao.setVisibility(View.VISIBLE);
            txttongtien.setVisibility(View.INVISIBLE);
            gioHangAdapter.notifyDataSetChanged();
        }
        else {
            txtthongbao.setVisibility(View.INVISIBLE);
            txttongtien.setVisibility(View.VISIBLE);
            gioHangAdapter.notifyDataSetChanged();
        }
    }

    private void Anhxa() {
        lvgiohang=findViewById(R.id.listviewgiohang);
        txtthongbao=findViewById(R.id.textviewthongbao);
        txttongtien=findViewById(R.id.textviewtongtien);
        btnThanhtoan=findViewById(R.id.buttonthanhtoangiohang);
        btntieptucmua=findViewById(R.id.buttontieptucmuahang);
        toolbargiohang=findViewById(R.id.toolbargiohang);
        gioHangAdapter =new GioHangAdapter(MainActivity.manggiohang,GiohangActivity_1.this);
        lvgiohang.setAdapter(gioHangAdapter);
    }

    private void Actionbar() {
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}