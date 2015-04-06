/*Fragment principal alojado en el xml activity_main.xml y que muestra sus datos en el fragment_main.xml
* Contiene:
* Inicializacion de los controladores del programa
* LLamada a la clase JsonWeatherTask,la cual lanzara un hilo para obtener los datos necesarios.
*
* */
package com.example.dgranadeabreu.tiempo;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.logging.Handler;

/**
 * Created by dgranadeabreu on 2/6/15.
 */
public class WeatherFragment extends Fragment
{
    WeatherHttpClient conexionAPI;

    Typeface weatherFont;

    TextView cityField;
    TextView updatedField;
    TextView detailsField;
    TextView currentTemperatureField;
    TextView weatherIcon;
    ImageButton botonBuscar;
    EditText ciudadAbuscar;
    ImageButton botonClima;
    Handler handler;

    String resultado="si";

    static View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);

        cityField = (TextView)rootView.findViewById(R.id.city_field);
        updatedField = (TextView)rootView.findViewById(R.id.updated_field);
        detailsField = (TextView)rootView.findViewById(R.id.details_field);
        currentTemperatureField = (TextView)rootView.findViewById(R.id.temperatura);
        botonBuscar=(ImageButton)rootView.findViewById(R.id.imagenes);
        ciudadAbuscar=(EditText)rootView.findViewById(R.id.buscarCiudad);
        botonClima=(ImageButton)rootView.findViewById(R.id.imagenesTiempo);
        weatherIcon = (TextView)rootView.findViewById(R.id.textView12);
        weatherIcon.setTypeface(weatherFont);

        //botonClima.setImageResource(R.drawable.ic_launcher);

        botonBuscar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                JSONWeatherTask task=new JSONWeatherTask(rootView);
                task.execute(ciudadAbuscar.getText().toString());
            }
        });

        //Lanzamos un hilo que nos devuelve el JSON con el clima
        JSONWeatherTask task=new JSONWeatherTask(rootView);
        task.execute("Madrid");

        return rootView;

    }

    public WeatherFragment()
    {

    }
}
