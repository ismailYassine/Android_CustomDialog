package com.example.recyclerviewdemo;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> implements Filterable {

    ArrayList<Student> studentList;
    static ArrayList<Student> studentListFull;
    Drawable icon;
    ItemClickListener listener;

    public CustomAdapter(ArrayList<Student> countries, Drawable icon, ItemClickListener itemClickListener){
        this.studentList = countries;
        studentListFull = new ArrayList<>(studentList);
        this.icon = icon;
        this.listener = itemClickListener;
    }

    @Override
    public Filter getFilter() {
        return exempleFilter;
    }

    private Filter exempleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            ArrayList<Student> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0){
                filteredList.addAll(studentListFull);
            }else {
                String filterPatern = charSequence.toString().toLowerCase(Locale.ROOT).trim();

                for (Student student : studentListFull){
                    if (student.getFirstName().toLowerCase(Locale.ROOT).startsWith(filterPatern)) {
                        filteredList.add(student);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            studentList.clear();
            studentList.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };



    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView firstName;
        TextView lastName;

        ImageView myImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.tvFirstName);
            lastName = itemView.findViewById(R.id.tvLastName);
            myImage = itemView.findViewById(R.id.myicon);

        }
        public TextView getFirstName(){
            return firstName;
        }
        public TextView getLastName(){
            return lastName;
        }

        public ImageView getIcon(){
            return myImage;
        }

    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {

    holder.getFirstName().setText(studentList.get(position).firstName);
    holder.getLastName().setText(studentList.get(position).lastName);
    holder.getIcon().setBackground(icon);

    holder.itemView.setOnClickListener(view -> {
        listener.onItemClick(studentList.get(position));
    });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }


    public void addStudent(Student student) {
        studentList.add(0, student);
        studentListFull.add(0, student);
        notifyItemInserted(0);
    }

    public interface ItemClickListener{
        void onItemClick(Student student);
    }


}
