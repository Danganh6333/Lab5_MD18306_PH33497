package com.dangchph33497.fpoly.lab5_md18306_ph33497;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dangchph33497.fpoly.lab5_md18306_ph33497.Model.Distributor;
import com.dangchph33497.fpoly.lab5_md18306_ph33497.Model.Response;
import com.dangchph33497.fpoly.lab5_md18306_ph33497.Service.ApiServices;
import com.dangchph33497.fpoly.lab5_md18306_ph33497.Service.HttpRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;


public class MainActivity extends AppCompatActivity {
    RecyclerView recycle;
    public HttpRequest httpRequest;
    private  Recycle_Item_Distributors adapter;
    private  ArrayList<Distributor> distributors;
    private com.google.android.material.floatingactionbutton.FloatingActionButton fab;
    EditText edtTimKiem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        fab = findViewById(R.id.fab);
        edtTimKiem = findViewById(R.id.edTimKiem);
        recycle = findViewById(R.id.recycle);
        //Khởi tạo service Request
        httpRequest = new HttpRequest();
        httpRequest.callAPI().getListDistributor().enqueue(getListDistributor);
        edtTimKiem.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
               if(actionId == EditorInfo.IME_ACTION_SEARCH){
                   //Lấy từ khóa từ ô tìm kiếm
                   String key = edtTimKiem.getText().toString();
                   httpRequest.callAPI()
                           .searchDistributor(key)//Phương thức API cần thực thi
                           .enqueue(getListDistributor);//Xử lý bất đồng bộ
                   //Vì giá trị trả về vẫn là một list Distributor
                   Log.d("Search", "Search query: " + key);

                   //nên có thể sử dụng tại Callback của getListDistributor()
                   return true;
               }
                return false;
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddDialog();
            }
        });
    }
    private void getData(ArrayList<Distributor> distributors){
        adapter = new Recycle_Item_Distributors(distributors,this);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        recycle.setAdapter(adapter);
    }
    Callback<Response<ArrayList<Distributor>>> getListDistributor = new Callback<Response<ArrayList<Distributor>>>() {
        @Override
        public void onResponse(Call<Response<ArrayList<Distributor>>> call, retrofit2.Response<Response<ArrayList<Distributor>>> response) {
            int status = response.body().getStatus();
            if(status == 200){
                //Lấy data
                distributors = response.body().getData();
                //Set dữ liệu lên recycle
                getData(distributors);
                //Toast ra thông tin từ messenger
                Toast.makeText(MainActivity.this, response.body().getMessenger(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<Response<ArrayList<Distributor>>> call, Throwable t) {
            Toast.makeText(MainActivity.this, "Lỗi"+t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
    private void openAddDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater =  LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.dialog_add, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        EditText edtThem = view.findViewById(R.id.edThem);
        Button btnThem = view.findViewById(R.id.btnThem);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtThem.getText().toString().isEmpty()){
                Toast.makeText(MainActivity.this, "Trống dữ liệu", Toast.LENGTH_SHORT).show();
                return;
                }
                String name = edtThem.getText().toString();

                Distributor distributor = new Distributor();
                distributor.setName(name);
                httpRequest.callAPI().addDistributor(distributor)
                        .enqueue(responseDistributorAPI);//Phương thức API cần thực thi
                dialog.dismiss();
            }
        });

        AlertDialog alertdialog = builder.create();
        alertdialog.show();
    }
    Callback<Response<Distributor>> responseDistributorAPI = new Callback<Response<Distributor>>() {
        @Override
        public void onResponse(Call<Response<Distributor>> call, retrofit2.Response<Response<Distributor>> response) {
            if(response.isSuccessful()){
                //check status code
                if(response.body().getStatus() == 200){
                    //Call lại api danh sách
                    httpRequest.callAPI().getListDistributor()//Phương thức API cần thực thi
                            .enqueue(getListDistributor);
                    Toast.makeText(MainActivity.this, response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<Distributor>> call, Throwable t) {
            Log.d(">>> GetListDistributor","onFailure"+t.getMessage());
        }
    };

}