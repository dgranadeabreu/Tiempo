/*Clase que es llamada por el Fragment principal y que es basicamente un hilo,
* que llama a otra clase(WeatherHttpClient)para abrir la conexion,y luego
* extrae del JSON que recibe cada una de las variables que voy a mostrar por
* pantalla,como la temperatura ,velocidad de viento etc....
*
* En el constructor de esta clase se le pasa el rootView de la aplicacion para
* finalmente poder mostrar las variables del JSON por pantalla.
* */

package com.example.dgranadeabreu.tiempo;

import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dgranadeabreu on 2/12/15.
 */
public class JSONWeatherTask extends AsyncTask<String, Void, String>
{
    View rootView;
    public JSONWeatherTask(View root)
    {
        this.rootView=root;

    }
    //devuelve el string con el json en formato JSON
    protected String doInBackground(String... params)
    {
        String data = ( (new WeatherHttpClient()).getWeatherData(params[0]));
        return  data;
    }
    @Override
    /*esto salta nada mas acabar la ejecucion del hilo.Este metodo pasa el JSON a variables y despues
      muestra por pantalla las que me interesan en el programa.
    */
    protected void onPostExecute(String result)
    {
        ImageButton imagen;
        try
        {
            //A partir de aqui extraigo los datos del JSON que tengo en la variable result
            JSONObject jObj = new JSONObject(result);

            JSONObject coordenadas = jObj.getJSONObject("coord");
            Double latitud=coordenadas.getDouble("lat");
            Double longitud=coordenadas.getDouble("lon");

            JSONObject sys=jObj.getJSONObject("sys");
            String ciudad=sys.getString("country");
            Integer sunrise=sys.getInt("sunrise");//hora a la que sale el sol
            Integer sunset=sys.getInt("sunset");//hora a la que se pone el sol

            String ciudadd=jObj.getString("name");//nombre de la ciudad

            //el siguiente bloque de json contiene un array por lo que se debe proceder un poco distinto
            JSONArray jArr = jObj.getJSONArray("weather");
            JSONObject JSONWeather = jArr.getJSONObject(0);
            Integer id= JSONWeather.getInt("id");//id de la ciudad que buscaste
            String descripcion=JSONWeather.getString("description");
            String main=JSONWeather.getString("main");
            String icono=JSONWeather.getString("icon");

            JSONObject mainObj = jObj.getJSONObject("main");
            Integer humedad=mainObj.getInt("humidity");
            Integer presion=mainObj.getInt("pressure");
            Double temperMax=mainObj.getDouble("temp_max");
            Double temperMin=mainObj.getDouble("temp_min");
            Double temperatura=mainObj.getDouble("temp");

            // Wind
            JSONObject wObj = jObj.getJSONObject("wind");
            Double velocidadViento=wObj.getDouble("speed");
            Double degViento=wObj.getDouble("deg");

            // Clouds
            JSONObject cObj = jObj.getJSONObject("clouds");
            Integer nubes=cObj.getInt("all");


            TextView txt2 = (TextView)rootView.findViewById(R.id.lugarBuscado);
            txt2.setText(String.valueOf(ciudadd).toString()+"\n"+String.valueOf(ciudad).toString());
            txt2.setTextColor(Color.rgb(255,255,255));//cambiar color a un textView

            TextView txt3 = (TextView)rootView.findViewById(R.id.temperatura);
            txt3.setText(String.valueOf(Math.round(temperatura)).toString()+"Cº");
            txt3.setTextColor(Color.rgb(255,255,255));//cambiar color a un textView

            TextView txt6 = (TextView)rootView.findViewById(R.id.tipo2);
            txt6.setText("Estado----->"+"\n"+"Actual------>"+"\n"+"Humedad->");
            txt6.setTextColor(Color.rgb(255, 255, 255));//cambiar color a un textView

            TextView txt7 = (TextView)rootView.findViewById(R.id.tipo);
            txt7.setText(String.valueOf(main).toString()+"\n"+String.valueOf(descripcion).toString()+"\n"+String.valueOf(humedad).toString()+"%");
            txt7.setTextColor(Color.rgb(255, 255, 255));//cambiar color a un textView

            TextView txt4 = (TextView)rootView.findViewById(R.id.tempMin);
            txt4.setText("Min:"+String.valueOf(Math.round(temperMin)).toString()+"Cº");
            txt4.setTextColor(Color.rgb(255, 255, 255));//cambiar color a un textView

            TextView txt5 = (TextView)rootView.findViewById(R.id.tempMax);
            txt5.setText("Max:"+String.valueOf(Math.round(temperMax)).toString()+"Cº");
            txt5.setTextColor(Color.rgb(255, 255, 255));//cambiar color a un textView

            //para ver una imagen correspondiente al clima,utilizo este codigo
            imagen=(ImageButton)rootView.findViewById(R.id.imagenesTiempo);
            String s = main.toString();

            if (s.equals("Clouds"))
            {
                imagen.setImageResource(R.drawable.cloudicon);
            }
            if (s.equals("Rain"))
            {
                imagen.setImageResource(R.drawable.rainicon);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }

}
