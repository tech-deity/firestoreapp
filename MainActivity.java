package com.techdeity.myfirebaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.m_lo_btn)
    Button mLoBtn;
    @BindView(R.id.m_n_tv)
    TextView mNTv;
    @BindView(R.id.m_e_tv)
    TextView mETv;
    @BindView(R.id.m_ph_tv)
    TextView mPhTv;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        userID=fAuth.getCurrentUser().getUid();
        DocumentReference documentReference =fStore.collection("users").document(userID);
         documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
             @Override
             public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
             mNTv.setText(documentSnapshot.getString("naam"));
             mETv.setText(documentSnapshot.getString("e_mail"));
             mPhTv.setText(documentSnapshot.getString("ph"));


             }
         });
        mLoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login2FireStore.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
