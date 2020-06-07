package com.angioni.gianluca.testapp.data;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.angioni.gianluca.testapp.model.Brewery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class BreweryRepository {
    private static final String URL = "https://api.openbrewerydb.org/breweries/";

    public void printBreweryData(Context context){
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest request = new JsonArrayRequest(URL, response->{
            System.out.println(response.toString());
        }, error->{
            System.out.println(error.getMessage());
        });

        queue.add(request);
    }

    public ArrayList<Brewery> getBreweries(Context context){
        ArrayList<Brewery> breweries = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest request = new JsonArrayRequest(URL, response->{
            breweries.addAll(extractBreweriesData(response));
        }, error->{
            System.out.println(error.getMessage());
        });

        queue.add(request);

        return breweries; //
    }

    public void getBrewery(Context context, int id, Listener<Brewery> listener){
        RequestQueue queue = Volley.newRequestQueue(context);
        String urlWithId = URL + id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlWithId,null, response -> {
            Brewery brewery = extractResponse(response);
            listener.onResponse(brewery);
        }, error -> {
            System.out.println(error.getMessage());
        });

        queue.add(request);
    }

    private Brewery extractResponse(JSONObject response) {
        Brewery result;
        try {
            result = new Brewery(
                    response.getInt("id"),
                    response.getString("name"),
                    response.getString("brewery_type"),
                    response.getString("street"),
                    response.getString("city")
            );
            return result;

        }catch (JSONException e){
            Logger.getAnonymousLogger().severe(e.getMessage());
            return null;
        }
    }

    private List<Brewery> extractBreweriesData(JSONArray response) {
        try{
            ArrayList<Brewery> data = new ArrayList<>();
            for (int i = 0; i < response.length(); i++) {
                data.add(new Brewery(
                        response.getJSONObject(i).getInt("id"),
                        response.getJSONObject(i).getString("name"),
                        response.getJSONObject(i).getString("brewery_type"),
                        response.getJSONObject(i).getString("street"),
                        response.getJSONObject(i).getString("city")
                ));
            }
            Logger.getLogger(BreweryRepository.class.getSimpleName()).severe(data.toString());
            return data;
        }catch (JSONException e){
            Logger.getAnonymousLogger().severe(e.getMessage());
            return null;
        }
    }
}
