package com.example.kiexpress.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kiexpress.ModelClasses.User;
import com.example.kiexpress.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AdminRecyclerAdapter extends FirebaseRecyclerAdapter<User,AdminRecyclerAdapter.myViewHolder> {
    String item;

    public AdminRecyclerAdapter(@NonNull FirebaseRecyclerOptions<User> options) {
        super(options);
    }

    //myViewHolder(inner-class) is responsible for accessing/holding our view objects
    class myViewHolder extends RecyclerView.ViewHolder{

        TextView userName,userType,userEmail;
        Button updateBtn,deleteBtn;
        AutoCompleteTextView autoCompleteText;

        ArrayAdapter<String> adapterItems;
        String[] items = {"Admin","Employee","Customer"};
        DatabaseReference reference;

        //Constructor of this class
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.userNameText); //itemview is required since we are not in an activity
            userType = itemView.findViewById(R.id.userTypeText);
            userEmail = itemView.findViewById(R.id.userEmailText);
            updateBtn = itemView.findViewById(R.id.updateBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);

            autoCompleteText = itemView.findViewById(R.id.autoCompleteText);
            adapterItems = new ArrayAdapter<String>(autoCompleteText.getContext(),R.layout.courier_type_dropdown_item,items);
            autoCompleteText.setAdapter(adapterItems);

            reference = FirebaseDatabase.getInstance().getReference("Users");

        }
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") final int  position, @NonNull User model) {
        holder.userName.setText(model.getName());
        holder.userType.setText(model.getPosition());
        holder.userEmail.setText(model.getEmail());
        holder.autoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = parent.getItemAtPosition(position).toString();
            }
        });


        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailFromDB = model.getEmail();
                String nameFromDB = model.getName();
                String passwordFromDB = model.getPassword();
                String phoneFromDB = model.getPhone();
                String newPosition = item;

                Map<String,Object> map = new HashMap<>();
                map.put("email",emailFromDB);
                map.put("name",nameFromDB);
                map.put("password",passwordFromDB);
                map.put("phone",phoneFromDB);
                map.put("position",newPosition);

                Query query =  holder.reference.orderByChild("name").equalTo(nameFromDB);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                String key = snapshot1.getKey();
                                holder.reference.child(key).updateChildren(map);
                                Toast.makeText(holder.autoCompleteText.getContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                holder.autoCompleteText.setText(null);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(holder.autoCompleteText.getContext(), "Update Failed", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.autoCompleteText.getContext());
                builder.setTitle("Delete User");
                builder.setMessage("Are you sure you want to remove this user?");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Users").child(getRef(position).getKey()).removeValue();
                        Toast.makeText(holder.autoCompleteText.getContext(), "User Removed", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();
            }
        });


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_recycler_item,parent,false); //Generating View Object
        return new myViewHolder(view);
    }




}
