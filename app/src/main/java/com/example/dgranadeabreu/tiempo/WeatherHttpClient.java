/*Clase intermedia que recibe un String de Wether Fragment y abrira la conexion con el API que me proporciona
* los datos del clima(en formato JSON)
* Contiene: el metodo de conexion al API.
*
* */
package com.example.dgranadeabreu.tiempo;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dgranadeabreu on 2/11/15.
 */
public class WeatherHttpClient
{

    public WeatherHttpClient()
    {

    }

    public String getWeatherData(String location)
    {
        HttpURLConnection con = null ;
        InputStream objetoLectura = null;
        try {
            //fijas la direccion a la que quieres conectarte
            con=(HttpURLConnection)(new URL("http://api.openweathermap.org/data/2.5/weather?q="+location+"&units=metric")).openConnection();
            //fijas el tipo de metodo de conexion que vas a usar(puede ser get o post)
            con.setRequestMethod("GET");
            //estos dos comando son para abrir/cerrar flujo
            con.setDoInput(true);
            con.setDoOutput(true);
            //hacer la conexion a la pagina
            con.connect();

            StringBuffer buffer = new StringBuffer();
            //recoje la respuesta de la pagina
            objetoLectura = con.getInputStream();

            BufferedReader objetoEscritura = new BufferedReader(new InputStreamReader(objetoLectura));
            String line = null;
            //añade a un buffer los datos que recibimos del servidor
            while (  (line = objetoEscritura.readLine()) != null )
                buffer.append(line + "\r\n");

            objetoLectura.close();
            con.disconnect();
            return buffer.toString();
        }
        catch(Exception e) {
            System.out.println("error papichulo"+e.getMessage());
        }
        finally
        {
            try
            {
                objetoLectura.close();

            } catch(Throwable t)
            {

            }
            try
            {
                con.disconnect();

            } catch(Throwable t)
            {

            }
        }

        return "no devuelvo nada";

    }

}
