package com.example.paco.convertidordivisas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private TextView titulo;
    private ListView listaTasas;
    private RecyclerView reyclerViewUser;
    private RecyclerView.Adapter mAdapter;
    double[] arrayTasas;
    String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //inicializa intent y coge las variables enviadas

        Intent intent = getIntent();
        arrayTasas = intent.getExtras().getDoubleArray("arrayTasas");
        items = intent.getExtras().getStringArray("items");

        //establece las relaciones

        reyclerViewUser = (RecyclerView)findViewById(R.id.reyclerViewUser);


        reyclerViewUser.setHasFixedSize(true);

        // use a linear layout manager
        reyclerViewUser.setLayoutManager(new LinearLayoutManager(this));


        initializeAdapter();

    }



    //guarda los arrays y los lleva al activity 3

    public void llamarActivity3(int posicion){


        Intent i2 = new Intent(this, Main3Activity.class );
        i2.putExtra("arrayTasas", arrayTasas);
        i2.putExtra("items", items);
        i2.putExtra("posicion", posicion);

        startActivity(i2);
    }



 //mete los objetos en el recyclerview y crea el metodo customitemclicklistener
    private void initializeAdapter(){
        mAdapter = new Adaptador(getData(arrayTasas, items), new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                llamarActivity3(position);
            }
        });
        reyclerViewUser.setAdapter(mAdapter);
    }




    // crea objetos de la clase del modelo y los mete en un arraylist
    public List<Elemento> getData(double[] arrayTasas, String[] items) {

        List<Elemento> userModels = new ArrayList<>();
        SharedPreferences prefs = getSharedPreferences("pref", MODE_PRIVATE);

        for(int i=0;i<arrayTasas.length;i++){

            String tasa = prefs.getString(items[i], "No name defined");//"No name defined" is the default value.

            userModels.add(new Elemento(items[i],"tasa de cambio: "+tasa));
        }


        return userModels;
    }



}
