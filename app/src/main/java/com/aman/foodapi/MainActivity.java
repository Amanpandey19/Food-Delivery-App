package com.aman.foodapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    TextView current_location;
    String currentLocation ="";
    String current_locality="";

    RecyclerView categoriesRecyclerView;
    CategoriesAdapter categoriesAdapter;
    ArrayList<FoodCategories> foodCategoriesArrayList;


    RecyclerView restaurantsRecyclerview;
    RestaurantAdapter restaurantAdapter;
    ArrayList<Restaurant> restaurantArrayList;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    FusedLocationProviderClient mFusedLocationClient;

    float lat , lng;
    int PERMISSION_ID = 44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        current_location = findViewById(R.id.current_location_txt);
        categoriesRecyclerView = findViewById(R.id.food_categories_recyclerview);
        restaurantsRecyclerview = findViewById(R.id.restaurant_recyclerview);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // method to get the location
        getLastLocation();
        setCategories();
        setRestaurants();
        callAPI();
    }

    private void setRestaurants() {
        restaurantArrayList = new ArrayList<>();
        restaurantArrayList.add(new Restaurant("American Restaurant", "15- 20 mins", this.getDrawable(R.drawable.restaurant), "4.0", "15"));
        restaurantArrayList.add(new Restaurant("Indian Restaurant", "40- 45 mins", this.getDrawable(R.drawable.r4), "3.6", "20"));
        restaurantArrayList.add(new Restaurant("Italian Food", "20- 25 mins", this.getDrawable(R.drawable.r3), "3.9", "10"));
        restaurantArrayList.add(new Restaurant("Wok Food", "50 -55 mins", this.getDrawable(R.drawable.r5), "4.1", "22"));
        restaurantArrayList.add(new Restaurant("Delicious Burger", "25- 30 mins", this.getDrawable(R.drawable.r2), "3.4", "20"));
        restaurantArrayList.add(new Restaurant("Suraj Fast Food", "15- 20 mins", this.getDrawable(R.drawable.restaurant), "4.0", "15"));
        restaurantArrayList.add(new Restaurant("Suraj Fast Food", "15- 20 mins", this.getDrawable(R.drawable.restaurant), "4.0", "15"));
        restaurantArrayList.add(new Restaurant("Suraj Fast Food", "15- 20 mins", this.getDrawable(R.drawable.restaurant), "4.0", "15"));


        restaurantAdapter = new RestaurantAdapter(MainActivity.this, restaurantArrayList);
        restaurantsRecyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL, false));
        restaurantsRecyclerview.setAdapter(restaurantAdapter);
    }

    private void setCategories() {
        foodCategoriesArrayList = new ArrayList<>();
        foodCategoriesArrayList.add(new FoodCategories(null, "All", false));
        foodCategoriesArrayList.add(new FoodCategories(getDrawable(R.drawable.pizza), "Pizza", true));
        foodCategoriesArrayList.add(new FoodCategories(getDrawable(R.drawable.chicken), "Chicken", false));
        foodCategoriesArrayList.add(new FoodCategories(getDrawable(R.drawable.salad), "Salad", false));
        foodCategoriesArrayList.add(new FoodCategories(getDrawable(R.drawable.burger), "Burger", false));

        categoriesAdapter = new CategoriesAdapter(MainActivity.this, foodCategoriesArrayList);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,RecyclerView.HORIZONTAL, false));
        categoriesRecyclerView.setAdapter(categoriesAdapter);
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            lat = (float) location.getLatitude();
                            lng = (float) location.getLongitude();

                            Geocoder gcd = new Geocoder(getBaseContext(),
                                    Locale.getDefault());
                            List<Address> addresses;
                            try {
                                addresses = gcd.getFromLocation(location.getLatitude(),
                                        location.getLongitude(), 1);
                                if (addresses.size() > 0) {
                                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                    String locality = addresses.get(0).getLocality();
                                    String subLocality = addresses.get(0).getSubLocality();
                                    String state = addresses.get(0).getAdminArea();
                                    String country = addresses.get(0).getCountryName();
                                    String postalCode = addresses.get(0).getPostalCode();
                                    String knownName = addresses.get(0).getFeatureName();
                                    if (subLocality != null) {

                                        currentLocation = locality + "," + subLocality;
                                    } else {

                                        currentLocation = locality;
                                    }
                                    current_locality = locality;

                                    current_location.setText(currentLocation+" "+current_locality);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            lat = (float) mLastLocation.getLatitude();
            lng = (float) mLastLocation.getLongitude();

        }
    };

        // method to check for permissions
        private boolean checkPermissions() {
            return ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        }

        // method to request for permissions
        private void requestPermissions() {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
        }

        // method to check
        // if location is enabled
        private boolean isLocationEnabled() {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }

        // If everything is alright then
        @Override
        public void
        onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == PERMISSION_ID) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLastLocation();
                }
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            if (checkPermissions()) {
                getLastLocation();
            }
        }


        void callAPI() {
            //okhttp
            OkHttpClient client = new OkHttpClient();

            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("lat", lat);
                jsonBody.put("lng", lng);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = RequestBody.create(jsonBody.toString(), JSON);
            Request request = new Request.Builder()
                    .url("https://theoptimiz.com/restro/public/api/get_resturants")
                    .post(body)
                    .build();


            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MainActivity.this, "" + "Failed to load response due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        JSONObject jsonObject;
                        MainActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                try {
                                    Toast.makeText(MainActivity.this, response.body().string(), Toast.LENGTH_LONG).show();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });
                    } else {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(MainActivity.this, "" + "Failed to load response due to " + response.body().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });

        }

}