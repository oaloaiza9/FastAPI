package com.example.ejemploactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

public class EditarCliente extends AppCompatActivity {

    EditText campoDocumento;
    EditText campoNombres;
    EditText campoApellidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cliente);

        campoDocumento = findViewById(R.id.campoDocumento);
        campoNombres = findViewById(R.id.campoNombres);
        campoApellidos = findViewById(R.id.campoApellidos);
    }

    public void editarCliente(View boton){
        String documento = campoDocumento.getText().toString();
        String nombres = campoNombres.getText().toString();
        String apellidos = campoApellidos.getText().toString();
        String endpoint = "http://18.219.214.164:8000/clientes";

        StringRequest solicitud = new StringRequest(Request.Method.PUT, endpoint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject respuesta = new JSONObject( response );
                            if (respuesta.getBoolean("status")==true){
                                Toast.makeText(getApplicationContext(), respuesta.getString("message"), Toast.LENGTH_LONG).show();
                                campoDocumento.setText("");
                                campoNombres.setText("");
                                campoApellidos.setText("");
                                campoDocumento.requestFocus();
                            }else{
                                Toast.makeText(getApplicationContext(), respuesta.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Log.d("Error JSON", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error En Servidor", error.toString());
                    }
                }
        ){
            protected Map<String, String> getParams(){
                Map<String, String> param = new HashMap<>();
                param.put("documento", documento);
                param.put("nombres", nombres);
                param.put("apellidos", apellidos);
                return param;
            }
        };

        RequestQueue cola = Volley.newRequestQueue( getApplicationContext() );
        cola.add( solicitud );
    }
}