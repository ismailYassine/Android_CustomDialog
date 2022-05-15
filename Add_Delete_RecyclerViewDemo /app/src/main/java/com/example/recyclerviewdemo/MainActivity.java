package com.example.recyclerviewdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private String chanel_id = "Simplified coding";
    private String chanel_name = "Simplified coding";
    private String chanel_des = "Simplified coding description";

    Button btnAdd;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    CustomAdapter customAdapter;
    ArrayList<Student> studentList = new ArrayList<Student>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(chanel_id, chanel_name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(chanel_des);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        setSupportActionBar(findViewById(R.id.toolbar));

        btnAdd = findViewById(R.id.btnAdd);

        studentList.add(new Student("Serge", "Ibaka"));
        studentList.add(new Student("Burna", "Boy"));
        studentList.add(new Student("Kendrick", "Lamar"));
        studentList.add(new Student("Jaz", "Mourad"));
        studentList.add(new Student("J.Cole", "cole"));
        studentList.add(new Student("Adel", "Kumato"));
        studentList.add(new Student("Youssoufa", "Andreas"));
        studentList.add(new Student("Medine", "Ralph"));
        studentList.add(new Student("Wiz", "Kid"));
        studentList.add(new Student("Nadal", "Guala"));

        recyclerView = findViewById(R.id.myRView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        customAdapter = new CustomAdapter(studentList, getResources().getDrawable(R.drawable.ic_profile), new CustomAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Student student) {

                Integer pos = studentList.indexOf(student);
                studentList.remove(student);
                CustomAdapter.studentListFull.remove(student);
                customAdapter.notifyItemRemoved(pos);
                displayNotification(student);
            }
        });
        recyclerView.setAdapter(customAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Student student = new Student("Boudha", "Marley");

                studentList.add(0, student);
                customAdapter.notifyItemInserted(0);
                CustomAdapter.studentListFull.add(0, student);
                recyclerView.getLayoutManager().scrollToPosition(0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem itemSearch = menu.findItem(R.id.actionSearch);
        SearchView searchView = (SearchView) itemSearch.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    public void displayNotification(Student student){

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, chanel_id )
                .setSmallIcon(R.drawable.ic_doorbell_24)
                .setContentTitle("Exercice 8")
                .setContentText(student.getFirstName() + " Is deleted")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                ;
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1, notification.build());
    }
}