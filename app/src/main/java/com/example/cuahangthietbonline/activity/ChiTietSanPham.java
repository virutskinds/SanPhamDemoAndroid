package com.example.cuahangthietbonline.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.cuahangthietbonline.R;
import com.example.cuahangthietbonline.model.Giohang;
import com.example.cuahangthietbonline.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;


public class ChiTietSanPham extends AppCompatActivity {
    Toolbar toolbarchitiet;
    ImageView imgchitiet;
    TextView txtten,txtgia,txtmota;
    Spinner spinner;
    Button btndatmua;
    int id =0;
    String Tenchitiet = "";
    int Giachitiet= 0;
    String Hinhanhchitiet="";
    String Motachitiet = "";
    int Idsanpham = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        Anhxa();
        AcctionToolBar();
        Getchitietsanpham();
        CatchEventSpinner();
        EventButton();

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

    private void EventButton() {
        btndatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.manggiohang.size()>0) {
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists =false;
                    for(int i = 0 ;i < MainActivity.manggiohang.size();i++)
                    {
                        if(MainActivity.manggiohang.get(i).getIdsp() == id)
                        {
                            MainActivity.manggiohang.get(i).setSoluongsp(MainActivity.manggiohang.get(i).getSoluongsp()+sl);
                            if(MainActivity.manggiohang.get(i).getSoluongsp() >=10){
                                MainActivity.manggiohang.get(i).setSoluongsp(10);
                            }
                            MainActivity.manggiohang.get(i).setGiasp((long) Giachitiet * MainActivity.manggiohang.get(i).getSoluongsp());
                            exists=true;
                        }
                    }
                    if (exists == false)
                    {
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long Giamoi = (long) soluong * Giachitiet;
                        MainActivity.manggiohang.add(new Giohang(id,Tenchitiet,Giamoi,Hinhanhchitiet,soluong));
                    }
                }
                else{
                    int  soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long Giamoi = (long) soluong * Giachitiet;
                    MainActivity.manggiohang.add(new Giohang(id,Tenchitiet,Giamoi,Hinhanhchitiet,soluong));
                }
                Intent intent =new Intent(getApplicationContext(),GiohangActivity_1.class);
                startActivity(intent);

            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void Getchitietsanpham() {
        Sanpham sanpham= (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanpham.getID();
        Tenchitiet = sanpham.getTensanpham();
        Giachitiet=sanpham.getGiasanpham();
        Hinhanhchitiet=sanpham.getHinhanhsanpham();
        Motachitiet=sanpham.getMotasanpham();
        Idsanpham=sanpham.getIDSanpham();
        txtten.setText(Tenchitiet);
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        txtgia.setText("Giá : " + decimalFormat.format(Giachitiet));
        txtmota.setText(Motachitiet);
        Picasso.get().load(Hinhanhchitiet).into(imgchitiet);

    }

    private void AcctionToolBar() {
        setSupportActionBar(toolbarchitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarchitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbarchitiet = findViewById(R.id.toolbarchitietsanpham);
        imgchitiet= findViewById(R.id.imgechitietsanpham);
        txtten=findViewById(R.id.textviewtenchitietsanpham);
        txtgia=findViewById(R.id.textviewgiachitiet);
        txtmota=findViewById(R.id.textviewmotachitietsanpham);
        spinner=findViewById(R.id.spinner);
        btndatmua=findViewById(R.id.buttondatmua);
    }
}