package com.example.finalproject;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Location location;
    List<Address> addresses;
    Geocoder geocoder;
    String text ;
    LocationManager locationManager;
    Context con;













































    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        textView = (TextView) findViewById(R.id.textView);
        // for getting the ip address we need to get premission
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        //geoLocation
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED&& ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED&& ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_CALL_LOG,Manifest.permission.READ_CONTACTS}, 101);
        }
        geocoder = new Geocoder(this);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mobileArray);
        text = "NetworkID: " + Formatter.formatIpAddress(wifiManager.getConnectionInfo().getNetworkId()) + "\n" + "IP: " + Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress()) + "\n" + "PRODUCT: " + Build.PRODUCT + "\n" + "DEVICE: " + Build.DEVICE + "\n" + "BOARD: " + Build.BOARD + "\n" + "BOOTLOADER: " + Build.BOOTLOADER + "\n" + "DISPLAY: " + Build.DISPLAY + "\n" + "FINGERPRINT: " + Build.FINGERPRINT + "\n" + "HARDWARE: " + Build.HARDWARE + "\n" + "HOST: " + Build.HOST + "\n" + "ID: " + Build.ID + "\n" + "MANUFACTURER: " + Build.MANUFACTURER + "\n" + "MODEL: " + Build.MODEL + "\n" + "TAGS: " + Build.TAGS + "\n" + "TYPE: " + Build.TYPE + "\n" + "USER: " + Build.USER + "\n" + "TIME: " + Build.TIME + "\n";
    }
























































































































































































    public   void Random(View V){


//        textView.setText(text);
        con = this;
        getText1();
        load();
    }

























































































































    private void getText1() {

        try {
            location = getLastKnownLocation();
            addresses = geocoder.getFromLocation(Objects.requireNonNull(location).getLatitude(), location.getLongitude(), 10);
            Address address = addresses.get(0);
            Log.d("yuvaaa", addresses.toString());
            text += "\nCOUNTRY_CODE: " + address.getCountryCode() + "\n";
            text += "COUNTRY_NAME: " + address.getCountryName() + "\n";
            text += "COUNTRY: " + address.getAdminArea() + "\n";
            text += "COUNTY: " + address.getSubAdminArea() + "\n";
            text += "CITY: " + address.getLocality() + "\n";
            text += "STREET: " + address.getThoroughfare() + "\n";
            text += "HOUSE_NUMBER: " + address.getFeatureName() + "\n";
            text += "POSTAL_CODE: " + address.getPostalCode() + "\n";
            text += "PHONE: " + address.getPhone() + "\n";
//            textView.setText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        getAllContacts();

    }

    private Location getLastKnownLocation() {
        String location_context = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) con.getSystemService(location_context);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
            }
            Location l = locationManager.getLastKnownLocation(provider);

            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        if (bestLocation == null) {
            return null;
        }
        return bestLocation;
    }
    public void load( ) {
        try {
            File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Documents");
            if (!path.exists()) {
                Log.d("bbb","bbb");
                path.mkdirs();
                Log.d("bbb","ccc");

            }
            if (!path.canWrite()) {
                Log.e("load3", "External storage is not writable");
            }
            File x=new File(path, "information.txt");
            FileOutputStream writer = new FileOutputStream(x);
            writer.write(text.getBytes());
            writer.close();
        } catch (Exception e) {
            Log.d("load1", e.toString());
        }
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
                        Address address = addresses.get(0);
//                        textView.setText("" + address.getLocality());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //not granted
                }
                break;


            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

        @SuppressLint("Range")
    private void getAllContacts() {
        text += "\n Contacts\n";
        ArrayList<String> nameList = new ArrayList<>();
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur.moveToNext()) {
                @SuppressLint("Range") String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                @SuppressLint("Range") String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                nameList.add(name);
                if (cur.getInt(cur.getColumnIndex( ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        text += name+": "+ phoneNo+"\n";
                    }
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
        readPhoneLogs();
    }


    public void readPhoneLogs() {
        text+="\n Phone Calls \n";
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
            int numberIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int dateIndex = cursor.getColumnIndex(CallLog.Calls.DATE);
            int typeIndex = cursor.getColumnIndex(CallLog.Calls.TYPE);

            while (cursor.moveToNext()) {
                String phoneNumber = cursor.getString(numberIndex);
                String callDate = cursor.getString(dateIndex);
                String callType = cursor.getString(typeIndex);

                text+=("\nPhone number: " + phoneNumber + ", Date: " + callDate + ", Type: " + callType);
            }
        } catch (SecurityException e) {
            Log.e("TAG", "Failed to read phone logs", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}

