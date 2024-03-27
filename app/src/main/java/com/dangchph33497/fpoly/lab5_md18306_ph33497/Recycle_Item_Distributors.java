package com.dangchph33497.fpoly.lab5_md18306_ph33497;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.dangchph33497.fpoly.lab5_md18306_ph33497.Model.Distributor;
import com.dangchph33497.fpoly.lab5_md18306_ph33497.Model.Response;
import com.dangchph33497.fpoly.lab5_md18306_ph33497.Service.HttpRequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Recycle_Item_Distributors extends RecyclerView.Adapter<Recycle_Item_Distributors.ViewHolder> {
    private ArrayList<Distributor> distributors;
    private Context context;
    private HttpRequest httpRequest;

    public Recycle_Item_Distributors(ArrayList<Distributor> distributors, Context context) {
        this.distributors = distributors;
        this.context = context;
        this.httpRequest = new HttpRequest();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Distributor distributor = distributors.get(position);
        holder.tvIndex.setText(String.valueOf(position));
        holder.tvContent.setText(distributor.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUpdateDialog(distributor.getId(),holder.getAdapterPosition());
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return distributors.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvIndex, tvContent;
        ImageButton btnDel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIndex = itemView.findViewById(R.id.tvIndex);
            tvContent = itemView.findViewById(R.id.tvContent);
            btnDel = itemView.findViewById(R.id.btnDel);
        }
    }

    private void showDeleteConfirmationDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xóa nhà phân phối");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteItem(position);
                distributors.remove(position);
                notifyItemRemoved(position);
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openUpdateDialog(String distributorId,int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dialog_update, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        EditText edtSua = view.findViewById(R.id.edSua);
        edtSua.setText(distributors.get(position).getName());
        Button btnSua = view.findViewById(R.id.btnSua);
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtSua.getText().toString().isEmpty()){
                Toast.makeText(context, "Trống dữ liệu", Toast.LENGTH_SHORT).show();
                return;
                }
                String newName = edtSua.getText().toString();

                updateDistributor(distributorId, newName);
                dialog.dismiss();
                notifyItemChanged(position);
            }
        });

        dialog.show();
    }
    private void updateDistributor(String distributorId, String newName) {
        Distributor newDistributor = new Distributor(distributorId, newName); // Assuming Distributor constructor takes id and name
        Call<Response<Distributor>> call = httpRequest.callAPI().updateDistributor(distributorId, newDistributor);
        call.enqueue(new Callback<Response<Distributor>>() {
            @Override
            public void onResponse(Call<Response<Distributor>> call, retrofit2.Response<Response<Distributor>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Cập nhật nhà phân phối thành công", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Cập nhật nhà phân phối thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<Distributor>> call, Throwable t) {
                Toast.makeText(context, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteItem(int position) {
        Distributor distributor = distributors.get(position);
        Call<Response<Distributor>> call = httpRequest.callAPI().deleteDistributorById(distributor.getId());
        call.enqueue(new Callback<Response<Distributor>>() {
            @Override
            public void onResponse(Call<Response<Distributor>> call, retrofit2.Response<Response<Distributor>> response) {
                if (response.isSuccessful()) {
                    distributors.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Xóa nhà phân phối thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Xóa nhà phân phối thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<Distributor>> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
