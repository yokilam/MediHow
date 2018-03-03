package nyc.c4q.medihow;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import nyc.c4q.medihow.model.MedicareOffice;
import nyc.c4q.medihow.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MedicareOfficeService medicareOfficeService;
    private List<MedicareOffice> medicareOfficeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        getRetrofitCall();
    }

    public void getRetrofitCall() {
        medicareOfficeService= RetrofitClient.getRetrofit("https://data.cityofnewyork.us/")
                .create(MedicareOfficeService.class);

        medicareOfficeService.getMedicareOffice().enqueue(new Callback <List <MedicareOffice>>() {
            @Override
            public void onResponse(Call <List <MedicareOffice>> call, Response <List <MedicareOffice>> response) {
                medicareOfficeList= response.body();
                Log.d("onResponse: ", "" + medicareOfficeList.size());
                Log.d("onResponse: ", ""+ response.body().get(0).getName_of_medical_office());
                Log.d("onResponse: ", ""+ response.body().get(0).getName_of_borough());
                Log.d("onResponse: ", ""+ response.body().get(0).getCommunity_board());
            }

            @Override
            public void onFailure(Call <List <MedicareOffice>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
