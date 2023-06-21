package com.example.ejemploactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void abrirVerClientes(View boton){
        Intent intencion = new Intent(getApplicationContext(), VerClientes.class);
        startActivity(intencion);
    }

    public void abrirCrearClientes(View boton){
        Intent intencion = new Intent(getApplicationContext(), CrearCliente.class);
        startActivity(intencion);
    }

    public void abrirEditarClientes(View boton){
        Intent intencion = new Intent(getApplicationContext(), EditarCliente.class);
        startActivity(intencion);
    }

    public void abrirEliminarClientes(View boton){
        Intent intencion = new Intent(getApplicationContext(), EliminarCliente.class);
        startActivity(intencion);
    }

}