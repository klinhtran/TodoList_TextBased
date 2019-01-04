package model;
//use code from
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Scanner;

import org.json.*;

public class WeatherReport {

    public String weatherDes;
    public double temperature;
    public static DecimalFormat df = new DecimalFormat("#.#");

    private static String fetchContentFromURL(URL url) throws IOException {


        StringBuilder sb = new StringBuilder();

        // read from the URL
        Scanner scan = new Scanner(url.openStream());

        while (scan.hasNext())
            sb.append(scan.nextLine());
        scan.close();

        //System.out.println(sb);

        return sb.toString();
    }
    public void fetch() throws MalformedURLException, IOException {
        String apikey = "40e62dfb320f3f801abf9c752e12d9af"; //fill this in with the API key they email you
        String vancouverweatherquery = "https://api.openweathermap.org/data/2.5/weather?q=Vancouver,ca&APPID=";
        String theURL = vancouverweatherquery + apikey;
        //this can point to any URL
        URL url = new URL(theURL);

        JSONObject obj = new JSONObject(fetchContentFromURL(url));
        weatherDes = getWeatherState(obj);
        temperature = (double)getTemp(obj) - 273.15;


    }

    @Override
    public String toString() {
        return "The weather today is " + weatherDes +" with a temperature of "+ df.format(temperature)+" degrees";
    }

    public static String getWeatherState(JSONObject obj){
        JSONArray weather = obj.getJSONArray("weather");
        if(weather.length() != 0){
            JSONObject o = weather.getJSONObject(0);
            String description = o.getString("description");
            return description;
        }

        return null;
    }

    public static Number getTemp(JSONObject obj){
        Number temp = obj.getJSONObject("main").getNumber("temp");
        return temp;
    }
}
