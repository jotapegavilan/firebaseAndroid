package com.gavilan.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gavilan.firebaseapp.model.Nota;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    EditText txtTemp, txtHum;
    Button btnSubir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTemp = findViewById(R.id.txtTemp);
        txtHum = findViewById(R.id.txtHum);
        btnSubir = findViewById(R.id.btnSubir);

        startActivity(new Intent(this, NotasActivity.class));



        // Conexión a la base de datos real time
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Referencia a un elemento de la base de datos
        //DatabaseReference myRef = database.getReference("nombre");
        //myRef.setValue("Catalina");



        DatabaseReference myRef = database.getReference("temperatura");
        DatabaseReference myRef2 = database.getReference("humedad");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    float value = dataSnapshot.getValue(float.class);
                    Log.d("SUCCESS", "Value is: " + value);
                    txtTemp.setText(value+"");
                }catch (Exception ex){
                    Toast.makeText(MainActivity.this,
                            "Ocurrio un error al obtener los datos",
                            Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERROR", "Failed to read value.", error.toException());
            }
        });

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int value = snapshot.getValue(int.class);
                txtHum.setText(value+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}