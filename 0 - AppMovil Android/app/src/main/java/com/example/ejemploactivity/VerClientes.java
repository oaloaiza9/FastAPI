package com.example.ejemploactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VerClientes extends AppCompatActivity {

    TextView etqResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_clientes);

        etqResultado = findViewById(R.id.etqResultado);
        cargarListaClientes();
    }

    public void cargarListaClientes(){

        String endpoint = "http://18.219.214.164:8000/clientes";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, endpoint, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            etqResultado.setText("");
                            for (int i=0; i<response.length(); i++){
                                JSONObject temporal = response.getJSONObject(i);
                                String texto = temporal.getString("documento")+" - "+temporal.getString("nombres")+" "+temporal.getString("apellidos");
                                etqResultado.append( texto+"\n" );
                            }
                        } catch (JSONException e) {
                            Log.d("Error JSON", "Error al extraer los datos: "+e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d( "Error en servidor", error.toString() );
                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue( getApplicationContext() );
        queue.add( request );
    }

}