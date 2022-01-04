package com.example.firstaidkit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountProfile extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference dbReference;

    String userID;
    TextView txtDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_profile);

        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        dbReference= FirebaseDatabase.getInstance().getReference("Users");
        userID=user.getUid();

       txtDisplay=findViewById(R.id.txt4);

        dbReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user= snapshot.getValue(User.class);

                if (user != null){
                    String fullName,email,phone;
                    fullName = user.fullName;
                    email = user.email;
                    phone = user.phone;

                    txtDisplay.setText("Full name: "+fullName+"\n Email: "+email+"\n Phone: "+phone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AccountProfile.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}