package com.example.kiexpress.Users.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.kiexpress.ModelClasses.User;
import com.example.kiexpress.R;
import com.example.kiexpress.recyclerview.AdminRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class AdminViewUsers extends AppCompatActivity {

    RecyclerView recyclerView;
    AdminRecyclerAdapter adapter;
    String userPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_users);

        recyclerView = findViewById(R.id.recyclerView);

        FirebaseRecyclerOptions<User> options =  new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users"), User.class)
                        .build();

        adapter = new AdminRecyclerAdapter(options);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //The items in recyclerview will be displayed in linear fashion

        //getIntent
        userPhone = getIntent().getStringExtra("phone");

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem item = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchText(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                SearchText(query);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    private void SearchText(String text) {
        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("position").startAt(text).endAt(text + "~"), User.class)
                        .build();

        adapter = new AdminRecyclerAdapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(AdminViewUsers.this, AdminDashboard.class);
        intent.putExtra("phone", userPhone);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }


}