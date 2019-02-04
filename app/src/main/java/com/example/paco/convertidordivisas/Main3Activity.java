package com.example.paco.convertidordivisas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {

    private TextView titulo;
    private EditText tasa;
    private Button modificarTasa;
    private EditText editTasa;
    double[] arrayTasas;
    String[] items;
    int posicion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


    //inicializa intent y coge las variables enviadas
        Intent intent = getIntent();
        arrayTasas = intent.getExtras().getDoubleArray("arrayTasas");
        items = intent.getExtras().getStringArray("items");
        posicion = intent.getExtras().getInt("posicion");

    //hace las relaciones
        titulo=(TextView)findViewById(R.id.textMoneda);
        tasa=(EditText)findViewById(R.id.editTasa);
        modificarTasa=(Button)findViewById(R.id.modificarTasa);
        editTasa=(EditText) findViewById(R.id.editTasa);

    //carga el valor almacenado en el edittext
        SharedPreferences prefs = getSharedPreferences("pref", MODE_PRIVATE);
        String Stasa = prefs.getString(items[posicion], "No name defined");

        titulo.setText(items[posicion]);
        tasa.setText(Stasa);

        titulo.setTextSize(TypedValue.COMPLEX_UNIT_SP,26);


    //metodo modificar tasas

        modificarTasa.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

               if(!editTasa.getText().toString().equals("")) {
                   SharedPreferences.Editor editor = getSharedPreferences("pref", MODE_PRIVATE).edit();
                   editor.putString(items[posicion], editTasa.getText().toString());
                   editor.putString("control", "1");
                   editor.apply();

                   Toast toast1 = Toast.makeText(getApplicationContext(), "La tasa se ha modificado correctamente", Toast.LENGTH_SHORT);
                   toast1.show();
               }
               else{
                   Toast toast1 = Toast.makeText(getApplicationContext(), "Tienes que introducir un número", Toast.LENGTH_SHORT);
                   toast1.show();
               }
            }
        });



    }
}
