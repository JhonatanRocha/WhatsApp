package com.jhonatanrocha.whatsapp.activity;

import android.app.Activity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jhonatanrocha.whatsapp.R;

public class MainActivity extends Activity {

    //private DatabaseReference fireBaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fireBaseReference.child("pontos").setValue(100);
    }
}
