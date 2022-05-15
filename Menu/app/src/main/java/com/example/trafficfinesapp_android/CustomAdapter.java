package com.example.trafficfinesapp_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    ArrayList<Offender> offenderList;
    ItemClickListener listener;

    public CustomAdapter(ArrayList<Offender> offenderList, ItemClickListener listener){
        this.offenderList = offenderList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getOffenderName().setText(offenderList.get(position).firstName + " " + offenderList.get(position).lastName);
        holder.getfinedDate().setText(offenderList.get(position).fineDate);
        holder.getFinedAmount().setText(offenderList.get(position).fineAmount.toString()+"$");

        holder.itemView.setOnClickListener(view -> {
            listener.onItemClick(offenderList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return offenderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView offenderName;
        TextView fineddate;
        TextView finedAmount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            offenderName = itemView.findViewById(R.id.tvName);
            fineddate = itemView.findViewById(R.id.tvDate);
            finedAmount = itemView.findViewById(R.id.tvFinedAmount);
        }

        public TextView getOffenderName(){
            return offenderName;
        }
        public TextView getfinedDate(){
            return fineddate;
        }
        public TextView getFinedAmount(){ return finedAmount; }
    }
    public interface ItemClickListener{
        void onItemClick(Offender offender);
    }
}
