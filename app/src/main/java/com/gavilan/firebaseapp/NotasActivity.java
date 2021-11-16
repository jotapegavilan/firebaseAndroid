package com.gavilan.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.gavilan.firebaseapp.model.Nota;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class NotasActivity extends AppCompatActivity {

    EditText txtTitulo, txtDescripcion;
    Button btnGuardar, btnBorrar;
    Spinner spNotas;

    public void cargarSpinner(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("notas");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Nota> listNotas = new ArrayList<>();
                for( DataSnapshot ds : snapshot.getChildren() ){
                    Nota nota = ds.getValue(Nota.class);
                    listNotas.add(nota);
                    Log.d("SUCCESS", nota.getTitulo() );
                }
                ArrayAdapter<Nota> adapter = new ArrayAdapter<>(NotasActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, listNotas);
                spNotas.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnBorrar = findViewById(R.id.btnBorrar);
        spNotas = findViewById(R.id.spNotas);

        cargarSpinner();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("notas");

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Capturar los datos, crear un objeto con los datos y subir a la bd
                String titulo = txtTitulo.getText().toString();
                String descripcion = txtDescripcion.getText().toString();
                String id = UUID.randomUUID().toString();
                Nota miNota = new Nota(titulo,descripcion,id);
                reference.child(id).setValue(miNota);
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Nota notaSeleccionada = (Nota) spNotas.getSelectedItem();
                reference.child( notaSeleccionada.getId() ).removeValue();
            }
        });

        spNotas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Nota notaS = (Nota) adapterView.getSelectedItem();
                txtTitulo.setText(notaS.getTitulo());
                txtDescripcion.setText(notaS.getDescripcion());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
}