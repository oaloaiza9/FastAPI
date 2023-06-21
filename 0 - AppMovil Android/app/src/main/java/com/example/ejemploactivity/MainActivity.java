package com.example.ejemploactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText campoEmail;
    EditText campoPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        campoEmail = findViewById(R.id.campoEmail);
        campoPassword = findViewById(R.id.campoPassword);
    }

    public void eventoDeClick(View vista){
        String email = campoEmail.getText().toString();
        String password = campoPassword.getText().toString();
        String endPoint = "http://18.219.214.164:8000/login";

        StringRequest request = new StringRequest(Request.Method.POST, endPoint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Codigo cuando reciba respuesta de la API
                        try {
                            JSONObject respuesta = new JSONObject( response );
                            if ( respuesta.getBoolean("status")==true ){
                                Intent intencion = new Intent( getApplicationContext(), Menu.class );
                                startActivity( intencion );
                            }else{
                                Toast.makeText(getApplicationContext(), "DATOS INVALIDOS", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Log.d("Error en Servidor", "Error en conversion del JSON");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Codigo cuando ocurra un error en la comunicacion con la API
                        Log.d("Error Servidor", error.toString() );
                    }
                }
        ){
            protected Map<String, String> getParams(){
                Map<String, String> param = new HashMap<>();
                param.put("email", email);
                param.put("password", password);
                return param;
            }
        };

        RequestQueue queue = Volley.newRequestQueue( getApplicationContext() );
        queue.add( request );
    }


}