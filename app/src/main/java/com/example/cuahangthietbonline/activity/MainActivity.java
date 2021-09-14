package com.example.cuahangthietbonline.activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;

import android.view.MenuItem;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangthietbonline.R;
import com.example.cuahangthietbonline.adapter.Loaispadapter;
import com.example.cuahangthietbonline.adapter.SanphamAdapter;
import com.example.cuahangthietbonline.model.Giohang;
import com.example.cuahangthietbonline.model.Loaisp;
import com.example.cuahangthietbonline.model.Sanpham;
import com.example.cuahangthietbonline.util.CheckConnection;
import com.example.cuahangthietbonline.util.ClickItemSanPham;
import com.example.cuahangthietbonline.util.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Giohang> manggiohang;
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewmanhinhchinh;
    NavigationView navigationView;
    ListView listViewmanhinhchinh;
    DrawerLayout drawerLayout;
    ArrayList<Loaisp> mangloaisp;
    Loaispadapter loaispadapter;
    int id = 0;
    String tenloaisp="" ;
    String hinhanhloaisp="" ;
    ArrayList<Sanpham> mangsanpham;
    SanphamAdapter sanphamAdapter;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Anhxa();
        ActionViewFlipper();
        ActionBar();
        getDuLieuLoaisp();
        getDuLieuSanPhamMoiNhat();
        catchOnItemListView();
        searchView=findViewById(R.id.searchbar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sanphamAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                sanphamAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);


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

    private void catchOnItemListView() {
        listViewmanhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent= new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent= new Intent(MainActivity.this,DienThoaiActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(position).getId());
                            startActivity(intent);
                        }
                        else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent= new Intent(MainActivity.this,Laptop01Activity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(position).getId());
                            startActivity(intent);
                        }
                        else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent= new Intent(MainActivity.this,LienHeActivity.class);
                            startActivity(intent);
                        }
                        else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent= new Intent(MainActivity.this,ThongTinActivity.class);
                            startActivity(intent);
                        }
                        else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }
            }
        });
    }

    private void getDuLieuSanPhamMoiNhat() {
        final RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.Duongdanspmoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response!=null){
                    int ID=0;
                    String Tensanpham="";
                    Integer Giasanphamm=0;
                    String Hinhanhsanpham="";
                    String Motasanpham="";
                    int IDSanpham=0;

                    for (int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject=response.getJSONObject(i);
                            ID=jsonObject.getInt("id");
                            Tensanpham=jsonObject.getString("tensp");
                            Giasanphamm=jsonObject.getInt("giasp");
                            Hinhanhsanpham=jsonObject.getString("hinhanhsp");
                            Motasanpham=jsonObject.getString("motasp");
                            IDSanpham=jsonObject.getInt("idsanpham");
                            mangsanpham.add(new Sanpham(ID,Tensanpham,Giasanphamm,Hinhanhsanpham,Motasanpham,IDSanpham));
                            sanphamAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void getDuLieuLoaisp() {
        final RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.Duongdanloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response!=null){
                    for (int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject=response.getJSONObject(i);
                            id=jsonObject.getInt("id");
                            tenloaisp=jsonObject.getString("tenloaisp");
                            hinhanhloaisp=jsonObject.getString("hinhanhloaisp");
                            mangloaisp.add(new Loaisp(id,tenloaisp,hinhanhloaisp));
                            loaispadapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                mangloaisp.add(3,new Loaisp(0,"Hỏi Đáp","https://image.flaticon.com/icons/png/512/4726/4726140.png"));
                mangloaisp.add(4,new Loaisp(0,"Thông tin","https://image.flaticon.com/icons/png/512/3867/3867460.png"));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar); //ho tro toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //thiet lap home
        getSupportActionBar().setDisplayShowHomeEnabled(true);// hien thi
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao=new ArrayList<>();
        mangquangcao.add("https://www.techoffside.com/wp-content/uploads/2019/05/S10-x-Lisa-Blackpink.jpg");
        mangquangcao.add("https://scontent.fhan2-3.fna.fbcdn.net/v/t1.6435-9/166792182_4113027425411061_5820906319855087130_n.png?_nc_cat=108&ccb=1-3&_nc_sid=e3f864&_nc_ohc=ULOUgPPk8MUAX-vJbH8&_nc_ht=scontent.fhan2-3.fna&oh=f262ffae75e0e2b2d6aa1ce00b58aba2&oe=6131B618");
        mangquangcao.add("https://scontent.fhan2-1.fna.fbcdn.net/v/t1.6435-9/194982308_4297839086929893_7981967170694716233_n.png?_nc_cat=101&ccb=1-3&_nc_sid=e3f864&_nc_ohc=Q_0HD7DeZoYAX8iDR4k&tn=w5IE3c9kIEUsy0D5&_nc_ht=scontent.fhan2-1.fna&oh=813dc0eef33256f3d221301dbd9269ca&oe=6132E306");
        mangquangcao.add("https://scontent.fhan2-4.fna.fbcdn.net/v/t1.6435-9/181979491_4197675946946208_8317186497825258340_n.png?_nc_cat=103&ccb=1-3&_nc_sid=e3f864&_nc_ohc=XS2y9I31EVsAX_pLMMu&_nc_ht=scontent.fhan2-4.fna&oh=7c6dc3f91af19f52187039a34fdc58e0&oe=6132D44C");
        mangquangcao.add("https://scontent.fhan2-4.fna.fbcdn.net/v/t1.6435-9/165570975_4097410393639431_4168858603768058081_n.png?_nc_cat=100&ccb=1-3&_nc_sid=e3f864&_nc_ohc=d4IT0lZuG0oAX857rJT&_nc_ht=scontent.fhan2-4.fna&oh=705a86a9f2fa10964f49e1cac9d3c73b&oe=61317FB2");
        mangquangcao.add("https://scontent.fhan2-4.fna.fbcdn.net/v/t1.6435-9/136754904_3872225612824578_4221389475997120624_n.png?_nc_cat=105&ccb=1-3&_nc_sid=e3f864&_nc_ohc=yorQjEJUpagAX-VpYUQ&_nc_ht=scontent.fhan2-4.fna&oh=d4a35ebd0bb014efa9e86066292a8137&oe=61300828");
        mangquangcao.add("https://scontent.fhan2-2.fna.fbcdn.net/v/t1.6435-9/231015468_4476613702385763_7621412612726229968_n.png?_nc_cat=111&ccb=1-3&_nc_sid=730e14&_nc_ohc=JoDbXu6Fv38AX-A7SpF&_nc_ht=scontent.fhan2-2.fna&oh=8e22e158d4c4e6bbd093dc3761acdcc5&oe=612F756E");
        for (int i=0;i<mangquangcao.size();i++)
        {
            ImageView imageView=new ImageView(getApplicationContext());
            Picasso.get().load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);


    }

    private void Anhxa() {
        toolbar=findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper=findViewById(R.id.viewflipper);
        recyclerViewmanhinhchinh=findViewById(R.id.recyclerview);
        navigationView=findViewById(R.id.navigationview);
        listViewmanhinhchinh=findViewById(R.id.listviewmanhinhchinh);
        drawerLayout=findViewById(R.id.drawerlayout);
        mangloaisp = new ArrayList<>();
        loaispadapter = new Loaispadapter(mangloaisp,getApplicationContext());
        listViewmanhinhchinh.setAdapter(loaispadapter);
        mangloaisp.add(0,new Loaisp(0,"Trang chủ","https://image.flaticon.com/icons/png/512/945/945176.png"));
        mangsanpham=new ArrayList<>();
        sanphamAdapter=new SanphamAdapter(mangsanpham, new ClickItemSanPham() {
            @Override
            public void onClickItemSanPham(Sanpham sanpham) {
                onClickGoToChiTiet(sanpham);
            }
        });
        recyclerViewmanhinhchinh.setHasFixedSize(true);
        recyclerViewmanhinhchinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewmanhinhchinh.setAdapter(sanphamAdapter);
        if(manggiohang !=null){
        }
        else {
            manggiohang = new ArrayList<>();
        }

    }
    private void onClickGoToChiTiet(Sanpham sanpham) {
        Intent intent = new Intent(this, ChiTietSanPham.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("thongtinsanpham", sanpham);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}