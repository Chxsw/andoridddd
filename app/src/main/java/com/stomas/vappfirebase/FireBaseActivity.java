package com.stomas.vappfirebase;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
//Liberias app y Firebase
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import android.view.View;

import org.checkerframework.checker.nullness.qual.NonNull;

public class FireBaseActivity extends AppCompatActivity {
    // Declaro variables
    private EditText txtCodigo, txtNombre, txtDueño, txtDireccion;
    private ListView lista;
    private Spinner spMascota;
    private FirebaseFirestore db;

    String[] TiposAlbumes = {"TheWeeStart", "ArianaPequeña", "GoodBonny"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firebaseee);
        // metodo cargar lista
        CargarListaFirestore();
        //iniciamos FR
        db = FirebaseFirestore.getInstance();
        // unimos variables con las del xml
        txtCodigo = findViewById(R.id.txtCodigo);
        txtNombre = findViewById(R.id.txtNombre);
        txtDueño = findViewById(R.id.txtDueño);
        txtDireccion = findViewById(R.id.txtDireccion);
        spMascota = findViewById(R.id.spMascota);
        lista = findViewById(R.id.lista);
        // Poblas spinner con albumes
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, TiposAlbumes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMascota.setAdapter(adapter);
    }
    //Enviar datos
    public void enviarDatosFirestore(View view){
        String codigo = txtCodigo.getText().toString();
        String nombre = txtNombre.getText().toString();
        String dueño = txtDueño.getText().toString();
        String direccion = txtDireccion.getText().toString();
        String tipoMascota = spMascota.getSelectedItem().toString();


        Map<String, Object> album = new HashMap<>();
        album.put("codigo",codigo);
        album.put("nombre", nombre);
        album.put("dueño", dueño);
        album.put("direccion", direccion);
        album.put("tipoAlbum", tipoMascota);

        //enviamos datos a firebase
        db.collection("albumes")
                .document(codigo)
                .set(album)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(FireBaseActivity.this, "Enviados", Toast.LENGTH_SHORT).show();
        })
                .addOnFailureListener(e ->{
                    Toast.makeText(FireBaseActivity.this, "Error", Toast.LENGTH_SHORT).show();
                });
    }
    // Metodo cargar Lista
    public void CargarLista(View view){
        CargarListaFirestore();
    }
    public void CargarListaFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("albumes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> listaAlbumes = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String linea = "||" + document.getString("codigo") + "||" +
                                document.getString("nombre") + "||" +
                                document.getString("dueño") + "||" +
                                document.getString("direccion");
                                listaAlbumes.add(linea);
                            }
                            ArrayAdapter<String> adaptador = new ArrayAdapter<>(
                                    FireBaseActivity.this,
                                    android.R.layout.simple_list_item_1,
                                    listaAlbumes
                            );
                            lista.setAdapter(adaptador);
                        } else {
                            Log.e("TAG","aa", task.getException());
                        }
                    }
                });
    }

}
