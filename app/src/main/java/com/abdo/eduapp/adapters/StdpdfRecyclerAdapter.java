package com.abdo.eduapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdo.eduapp.R;
import com.abdo.eduapp.models.ModelPdf;
import com.abdo.eduapp.models.SharedModel;

import java.util.ArrayList;

public class StdpdfRecyclerAdapter extends RecyclerView.Adapter<StdpdfRecyclerAdapter.Holder> {


    ArrayList<ModelPdf> list = new ArrayList<>();

    public void setList(ArrayList<ModelPdf> list) {
        this.list = list;
    }

    private StdpdfRecyclerAdapter.OnItemClick onItemClick ;

    public void setOnItemClick(StdpdfRecyclerAdapter.OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }




    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pdf,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.PdfName.setText(list.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return list == null ? 0: list.size() ;
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView PdfName;


        public Holder(@NonNull View itemView) {
            super(itemView);

            PdfName = itemView.findViewById(R.id.pdf_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClick != null){

                        SharedModel.setSelected_pdf(list.get(getLayoutPosition()).getName());
                        SharedModel.setSelected_pdf_Url(list.get(getLayoutPosition()).getUrl());
                        onItemClick.OnClick(SharedModel.getSelected_pdf());
                    }
                }
            });

        }
    }

    public interface OnItemClick{

        void OnClick(String PdfName );

    }





}
