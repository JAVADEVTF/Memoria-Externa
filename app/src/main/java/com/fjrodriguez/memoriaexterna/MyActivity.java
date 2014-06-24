package com.fjrodriguez.memoriaexterna;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class MyActivity extends Activity {

    EditText nombreFichero;
    EditText contenidoFichero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        nombreFichero = (EditText) findViewById(R.id.nombreFichero);
        contenidoFichero = (EditText) findViewById(R.id.contenidoFichero);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void guardarDatos(View view) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File raiz = Environment.getExternalStorageDirectory();
            File dir = new File(raiz.getAbsolutePath()+"/datos/miapp/loquequieras");
            dir.mkdirs();
            try {
                File archivo = new File(dir, nombreFichero.getText().toString());
                FileOutputStream fos = new FileOutputStream(archivo);
                PrintWriter pw = new PrintWriter(fos);
                pw.println(contenidoFichero.getText().toString());
                pw.flush();
                pw.close();
            } catch (FileNotFoundException e) {
                Toast.makeText(this, "Error escribiendo en " + nombreFichero.getText().toString(), Toast.LENGTH_LONG);
            }

        } else
            Toast.makeText(this, "No se puede escribir en la memoria externa", Toast.LENGTH_LONG);
    }

    public void leerDatos(View view) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File raiz = Environment.getExternalStorageDirectory();
            File dir = new File(raiz.getAbsolutePath() + "/datos/miapp/loquequieras");
            dir.mkdirs();
            try {
                File archivo = new File(dir, nombreFichero.getText().toString());
                FileReader isr = new FileReader(archivo);
                BufferedReader buf = new BufferedReader(isr);
                contenidoFichero.setText("");
                String linea = buf.readLine();
                while ( linea != null) {
                    contenidoFichero.append(linea);
                    linea = buf.readLine();
                }
                isr.close();
            } catch (IOException e) {
                Toast.makeText(this, "No se puede leer datos", Toast.LENGTH_LONG);
            }
        }
    }
}
