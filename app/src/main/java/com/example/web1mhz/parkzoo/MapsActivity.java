package com.example.web1mhz.parkzoo;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    GoogleMap mGoogleMap;
    private final static int MY_PERMISSION_FINE_LOCATION = 101;
    private ZoomControls zoom;
    private Button btnMark;
    private Button btnBack;
    private Button bntclear;
    private Button btnAdd;
    private double myLatitude, curLatitude;
    private double myLongitude, curLongitude;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    protected static final String TAG = "MapsActivity";
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Toolbar toolbar;
    private LatLng ll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (googleServiceAvailable()) {
            Toast.makeText(this, "구글 서비스에 연결되었습니다", Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_maps);
            //구글맵 초기 셋팅
            iniMap();

        } else {
            Toast.makeText(this, "구글 서비스에 연결할 수 없습니다.", Toast.LENGTH_LONG).show();
            finish();
        }

        toolbar = (Toolbar) findViewById(R.id.maptoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        locationRequest = new LocationRequest();
        locationRequest.setInterval(15 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //set to balanced power accuracy on real device

    }//onCreate함수 끝끝

    private void iniMap() {
        if(mGoogleMap==null){

            MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapfragment);
            mapFragment.getMapAsync(this);
        }

    }

    public boolean googleServiceAvailable() {

        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "구글 서비스에 연결할 수 없습니다.", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;

        goToLocationZoom(38, 127, 5);

//        LatLng curLocation = new LatLng(curLatitude, curLongitude);
//        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(curLocation, 7));
//        mGoogleMap.addMarker(new MarkerOptions().position(curLocation).title("My location"));

        //!==를 ==를 바꿔야 현재위치 기능이 수행됨
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            mGoogleMap.setMyLocationEnabled(true);
        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);

            }
        }

        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                ll=latLng;
                 mGoogleMap.addMarker(new MarkerOptions().position(latLng).title("지도클릭 경도: " + ll.longitude + " 위도: " + ll.latitude));
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
            }
        });

        zoom = (ZoomControls) findViewById(R.id.zoomC);
        zoom.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mGoogleMap.animateCamera(CameraUpdateFactory.zoomOut());

            }
        });
        zoom.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mGoogleMap.animateCamera(CameraUpdateFactory.zoomIn());

            }
        });

        btnMark = (Button) findViewById(R.id.btnMark);
        btnMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng myLocation = new LatLng(myLatitude, myLongitude);
                mGoogleMap.addMarker(new MarkerOptions().position(myLocation).title("My location"));
            }
        });

        bntclear = (Button) findViewById(R.id.btnClear);
        bntclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleMap.clear();
            }
        });

        btnAdd = (Button) findViewById(R.id.btnAdd);

//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent mapin=new Intent(MapsActivity.this, null);
//               // LatLng po = new LatLng(curLatitude, curLongitude);
//                Toast.makeText(MapsActivity.this, ll.latitude + "" + ll.longitude, Toast.LENGTH_LONG ).show();
//                mapin.putExtra("ycoord", ll.latitude );
//                mapin.putExtra("xcoord", ll.longitude);
//                startActivity(mapin);
//            }
//        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case MY_PERMISSION_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {
                    Toast.makeText(this, "위치 수신 동의가 필요합니다.", Toast.LENGTH_LONG).show();
                    finish();
                }

                break;

            default:
                break;

        }

    }

    private void goToLocationZoom(double lat, double lng, int zoom) {

        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mGoogleMap.moveCamera(update);
    }

    private void goToLocation(double lat, double lng) {

        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLng(ll);
        mGoogleMap.moveCamera(update);
    }

    //go 버튼 기능처리
    public void geoLocate(View view){
        EditText et = (EditText) findViewById(R.id.txtGo);
        String location = et.getText().toString().trim();

        if (location ==null || location.equals("")) {
            Toast.makeText(this, "장소를 입력하세요", Toast.LENGTH_SHORT).show();
            //담에 Alert Dialog 사용
            return;
        }

        List<Address> addressList = null;
        Geocoder gc = new Geocoder(MapsActivity.this);

        try {
            addressList = gc.getFromLocationName(location, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Address address = addressList.get(0);
        String locality = address.getLocality();

        //Toast.makeText(this, locality, Toast.LENGTH_LONG).show();
        float zoomfactor = 12;
        double lat = address.getLatitude();
        double lon = address.getLongitude();
        //goToLocationZoom(lat, lon, 12);

        LatLng latLng = new LatLng(lat, lon);
        mGoogleMap.addMarker(new MarkerOptions().position(latLng).title("from geocoder"));
        //mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomfactor));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mapTypeNone:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.mapTypeNormal:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.mapTypeTerrain:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.mapTypeSatellite:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.mapTypeHybrid:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        requestLocationUpdates();
    }

    private void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }

    };

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "연결");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "연결실패 = " + connectionResult.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {

        myLatitude = location.getLatitude();
        myLongitude = location.getLongitude();

    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(googleApiClient.isConnected()){
            requestLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
       googleApiClient.disconnect();
    }
}
