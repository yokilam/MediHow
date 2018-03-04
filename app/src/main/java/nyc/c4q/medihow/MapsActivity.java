package nyc.c4q.medihow;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import nyc.c4q.medihow.model.MedicareOffice;
import nyc.c4q.medihow.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private MedicareOfficeService medicareOfficeService;
    public static List<MedicareOffice> medicareOfficeList;

    private HashMap<String, LatLng> offices;

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;


    Button toggle;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getRetrofitCall();
        offices = new HashMap<>();

        toggle = findViewById(R.id.toggle_button);



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpBottomSheetFragment();
            }
        });

    }

    public void getRetrofitCall() {
        medicareOfficeService = RetrofitClient.getRetrofit("https://data.cityofnewyork.us/")
                .create(MedicareOfficeService.class);
        medicareOfficeService.getMedicareOffice().enqueue(new Callback<List<MedicareOffice>>() {
            @Override
            public void onResponse(Call<List<MedicareOffice>> call, Response<List<MedicareOffice>> response) {
                medicareOfficeList = response.body();
                Log.d("onResponse: ", "" + medicareOfficeList.size());
                Log.d("onResponse: ", "" + medicareOfficeList.get(0).getLatitude());
                Log.d("onResponse: ", "" + medicareOfficeList.get(0).getLongtitude());

                for (MedicareOffice b : medicareOfficeList) {
                    double lat = Double.valueOf(b.getLatitude());
                    double lng = Double.valueOf(b.getLongtitude());
                    LatLng temp = new LatLng(lat, lng);
                    offices.put(b.getName_of_medical_office(), temp);
                }



                for (String a : offices.keySet()) {
                    LatLng temp = offices.get(a);
                    mMap.addMarker(new MarkerOptions().position(temp).title(a));
                }
            }

            @Override
            public void onFailure(Call<List<MedicareOffice>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1020);
        } else {
            mMap.setMyLocationEnabled(true);
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                double lat = location.getLatitude();
                                double lng = location.getLongitude();
                                mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("My Location"));
                                CameraPosition myLocation = CameraPosition.builder()
                                        .target(new LatLng(lat, lng))
                                        .zoom(16)
                                        .bearing(0)
                                        .tilt(45)
                                        .build();
                                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(myLocation));
                            }
                        }
                    });
        }
        Geocoder coder = new Geocoder(getApplicationContext());
        List<Address> address;
        LatLng p1 = null;
        UiSettings settings = mMap.getUiSettings();
        settings.setCompassEnabled(true);
        settings.setMyLocationButtonEnabled(true);
        settings.setAllGesturesEnabled(true);
        settings.setZoomControlsEnabled(true);
    }


    public void setUpBottomSheetFragment() {
        TestingBottomSheeetFragment fragment = new TestingBottomSheeetFragment();
        fragment.show(getSupportFragmentManager(),fragment.getTag());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(MapsActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
