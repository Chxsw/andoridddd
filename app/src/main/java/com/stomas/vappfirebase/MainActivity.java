package com.stomas.vappfirebase;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.passworr);

    }

    public void OnClickAcceder(View view) {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.equals("vicente@gmail.com") && password.equals("1234")) {
            // Acceder a la siguiente actividad
            Intent intent = new Intent(MainActivity.this, PagActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Mensaje de error
            Toast.makeText(MainActivity.this, "Datos incorrectos. Inténtelo de nuevo.", Toast.LENGTH_SHORT).show();
        }
    }
}