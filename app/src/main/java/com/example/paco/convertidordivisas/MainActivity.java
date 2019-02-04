package com.example.paco.convertidordivisas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.InputSource;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    //Iniciar las variables

    private Spinner listaDivisas;
    private EditText cantidad;
    private TextView titulo;
    private Button convertir, tasas;
    double Icantidad;
    double resultado;
    String result;


   String[] items = new String[32];
   double[] arrayTasas = new double[32];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //enlazar controles con variables

        listaDivisas=(Spinner)findViewById(R.id.spinnerDivisas);
        cantidad=(EditText)findViewById(R.id.cantidadEuro);
        titulo=(TextView)findViewById(R.id.tvTitulo);
        convertir=(Button)findViewById(R.id.btnConvertir);
        tasas=(Button)findViewById(R.id.btnTasas);

        //añadir los items al spinner

        items[0]="USD";

       new OperacionAsincrona().execute("");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        listaDivisas.setAdapter(adapter);




        //metodo onClick convertir

        convertir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                titulo.setText(listaDivisas.getSelectedItem().toString());

                if(cantidad.getText().toString().equals("")){

                    Toast toast1 = Toast.makeText(getApplicationContext(), "Tienes que introducir un número", Toast.LENGTH_SHORT);
                    toast1.show();

                }
                else{

                   result= convertir();
                    titulo.setText(Icantidad+" euros son "+result+" "+listaDivisas.getSelectedItem());
                }

            }
        });



        //metodo onClick tasas

        tasas.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                llamarActivity2();

            }
        });



    }


    //metodo llamar activity 2

    public void llamarActivity2(){

        Intent i = new Intent(this, Main2Activity.class );
        i.putExtra("arrayTasas", arrayTasas);
        i.putExtra("items", items);
        startActivity(i);

    }


    //Metodo convertir

    public String convertir(){

        Icantidad = Double.parseDouble(cantidad.getText().toString());

        SharedPreferences prefs = getSharedPreferences("pref", MODE_PRIVATE);
        String tasaConvertir = prefs.getString(items[listaDivisas.getSelectedItemPosition()], "No name defined");//"No name defined" is the default value.


        resultado = Icantidad * Double.parseDouble(tasaConvertir);


        resultado= Math.round(resultado * 100d) / 100d;
        result= Double.toString(resultado);



        return result;



    }



    //recupera los datos xml de una web y los mete en un array


    private class OperacionAsincrona extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {

            String Stasa="";


            try {

                URL url = new URL("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/xml");

                InputStream xml = connection.getInputStream();

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = null;

                dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(xml);
                doc.getDocumentElement().normalize();

                NodeList nList = doc.getElementsByTagName("Cube");

                for(int i = 2 ; i < nList.getLength(); i++){

                    Stasa= nList.item(i).getAttributes().getNamedItem("rate").getNodeValue().toString();
                    arrayTasas[i-2]=Double.parseDouble(Stasa);
                    items[i-2]=nList.item(i).getAttributes().getNamedItem("currency").getNodeValue().toString();

                }


            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            //usa sharedpreferences para hacer persistentes los datos del array

            SharedPreferences.Editor editor = getSharedPreferences("pref", MODE_PRIVATE).edit();
            SharedPreferences prefs = getSharedPreferences("pref", MODE_PRIVATE);

            if(!prefs.getString("control", "nose").equals("1")) {
                for (int i = 0; i < arrayTasas.length; i++) {
                    editor.putString(items[i], Double.toString(arrayTasas[i]));
                    editor.apply();
                }
            }

        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }





}


