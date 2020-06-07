package com.angioni.gianluca.testapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.angioni.gianluca.testapp.data.BreweryRepository;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    private static final BreweryRepository REPO = new BreweryRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Simple data print from volley response
        //REPO.printBreweryData(this);

        // Synchronous volley request
        //ArrayList<Brewery> breweries = REPO.getBreweries(this);
        //Logger.getLogger(MainActivity.class.getSimpleName()).severe(breweries.toString());

        REPO.getBrewery(this, 2, brewery ->{
            Logger.getLogger(MainActivity.class.getSimpleName()).severe(brewery.toString());
        });
    }
}
