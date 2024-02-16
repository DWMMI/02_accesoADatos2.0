package com.example.a02_accesoadatos20;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    FirebaseDatabase db;
    EditText etNombre;
    EditText etApellido;
    EditText etEdad;
    EditText etMayorEdad;
    EditText etHobbies;
    DatabaseReference nodoPadre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        db = FirebaseDatabase.getInstance();
        etNombre = findViewById(R.id.nombre);
        etApellido = findViewById(R.id.apellido);
        etEdad = findViewById(R.id.edad);
        etMayorEdad = findViewById(R.id.mayoredad);
        etHobbies = findViewById(R.id.hobbies);

        nodoPadre = db.getReference(); // Se inicializa el DatabaseReference

        /*

        // Se añade el ChildEventListener al nodoPadre
        nodoPadre.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.getKey().equals("bart")){
                    nodoPadre.child("amigos").child("nombre").setValue("Milhouse");
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // Handle child changed
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                // Handle child removed
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // Handle child moved
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });

        */

        // Add a ValueEventListener to detect when the "Bart" node is added
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("bart")) {
                    Toast.makeText(MainActivity.this, "Se ha insertado Bart", Toast.LENGTH_SHORT).show();
                    nodoPadre.child("amigos").child("nombre").setValue("Milhouse");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
            }
        };
        nodoPadre.addValueEventListener(listener);
    }






    public void insertarSimpson(View view){
        nodoPadre = db.getReference().child(etNombre.getText().toString());
        nodoPadre.child("nombre").setValue(etNombre.getText().toString());
        /*
            if (etNombre.getText().toString().equals("bart")){
                nodoPadre.child("amigos").child("nombre").setValue("Milhouse");
            }
            */
        nodoPadre.child("apellido").setValue(etApellido.getText().toString());
        nodoPadre.child("edad").setValue(etEdad.getText().toString());
        nodoPadre.child("mayorEdad").setValue(etMayorEdad.getText().toString());
        List<String> hobbies = Arrays.asList(etHobbies.getText().toString().split(","));
        nodoPadre.child("hobbies").setValue(hobbies);
    }

    public void insertarAmigo(View view){
        DatabaseReference nodoHijo = db.getReference().child("homer").child("amigos").child(etNombre.getText().toString());
        nodoHijo.child("nombre").setValue(etNombre.getText().toString());
        nodoHijo.child("apellido").setValue(etApellido.getText().toString());
        nodoHijo.child("edad").setValue(etEdad.getText().toString());
        nodoHijo.child("mayorEdad").setValue(etMayorEdad.getText().toString());
        List<String> hobbies = Arrays.asList(etHobbies.getText().toString().split(","));
        nodoHijo.child("hobbies").setValue(hobbies);
    }



    public void borrar(View view){
        etNombre.setText("");
        etApellido.setText("");
        etEdad.setText("");
        etMayorEdad.setText("");
        etHobbies.setText("");
    }
}