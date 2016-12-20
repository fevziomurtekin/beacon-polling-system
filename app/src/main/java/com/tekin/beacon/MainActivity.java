package com.tekin.beacon;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Nearable;
import com.estimote.sdk.Region;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private BeaconManager beaconManager;
    private static final UUID IBEACON_PROXIMITY_UUID = UUID.fromString("538c5ab2-4dba-43ba-53be-4eb041ad41b0");
    private static final Region ALL_IBEACON_BEACONS = new Region("rid", null, null, null);
    String firsbeacon_uuid;
    int firstbeacon_major,first_beacon_minor;
    EditText adsoyad,ogrenci_numarasi;
    Button giris_yap;
    String ders_adi="";
    String ogrenci_ad,ogrenci_no;
    public String[] ders1=new String[]{"538c5ab2-4dba-43ba-53be-4eb041ad41b0","153","2","Tıbbi İstatistik","15","18","1105"};
    public String[] ders2=new String[]{"538c5ab2-4dba-43ba-53be-4eb041ad41b0","153","2","Optimizasyon","9","12","1105"};
    public String[] ders3=new String[]{"538c5ab2-4dba-43ba-53be-4eb041ad41b0","153","5","Nesnelerin İnterneti","12","15","1103"};
    public String[] ders4=new String[]{"f3d1d52b-6eb0-fdaf-b51c-1ade2468c14","1","10","Mobil Uygulama ve Geliştirme","12","16","1107"};


    TextView tv,tv1,tv2,tv3,tv4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adsoyad= (EditText) findViewById(R.id.editText);
        ogrenci_numarasi= (EditText) findViewById(R.id.editText2);
        giris_yap= (Button) findViewById(R.id.button);
        beaconManager = new BeaconManager(getApplicationContext());



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android M Permission check
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect beacons in the background.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @TargetApi(23)
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                PERMISSION_REQUEST_COARSE_LOCATION);
                    }

                });
                builder.show();
            }
        }

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                if(list.size()>0){
                    Log.e("beacon:",list.toString());
                    for (int x=0;x<=list.size();x++){
                           firsbeacon_uuid= String.valueOf(list.get(0).getProximityUUID());
                           firstbeacon_major=list.get(0).getMajor();
                        first_beacon_minor=list.get(0).getMinor();
                    }

                }

            }

        });
        beaconManager.setNearableListener(new BeaconManager.NearableListener() {
            @Override
            public void onNearablesDiscovered(List<Nearable> list) {
                Log.e("Beaconlar", "Discovered nearables: " + list);
                for(int i=0;i<list.size();i++){
                    Nearable tempBeacon =list.get(i);

                }
            }
        });
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {

            @Override

            public void onServiceReady() {

                beaconManager.startRanging(new Region("rid",null
                        ,null,null));
                beaconManager.startNearableDiscovery();


            }

        });


        giris_yap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ogrenci_ad=adsoyad.getText().toString();
                ogrenci_no=ogrenci_numarasi.getText().toString();
                Intent i=new Intent(getApplication(),imzaClassi.class);
                i.putExtra("ad_soyad",ogrenci_ad);
                i.putExtra("ogrenci_numarasi",ogrenci_no);
                Log.e("beacon_uuid",firsbeacon_uuid);
                Log.e("beacon_major", String.valueOf(firstbeacon_major));
                Log.e("beacon_uuid", String.valueOf(first_beacon_minor));

                String parameters="&beacon_uuid="+firsbeacon_uuid+"&beacon_major="+firstbeacon_major+"&beacon_minor="+first_beacon_minor;
                HttpRequest httpRequest=new HttpRequest(parameters);
                httpRequest.execute();

                Date dnow=new Date();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH");
                int saat= Integer.parseInt(simpleDateFormat.format(dnow))-1;
                if(ders1[0].equals(firsbeacon_uuid) && Integer.valueOf(ders1[1])==firstbeacon_major && Integer.valueOf(ders1[2])==first_beacon_minor && Integer.valueOf(ders1[4])<=saat && Integer.valueOf(ders1[5])>=saat ){
                    ders_adi=ders1[3];
                    showNotification(ders_adi+" dersiniz var","Imza atmak için tıklayınız",ders_adi);
                    Toast.makeText(MainActivity.this, ders1[3], Toast.LENGTH_SHORT).show();}
                if(ders2[0].equals(firsbeacon_uuid) && Integer.valueOf(ders2[1])==firstbeacon_major && Integer.valueOf(ders2[2])==first_beacon_minor && Integer.valueOf(ders2[4])<=saat && Integer.valueOf(ders2[5])>=saat ) {

                    ders_adi = ders2[3];
                    showNotification(ders_adi+" dersiniz var","Imza atmak için tıklayınız",ders_adi);
                }
                if(ders3[0].equals(firsbeacon_uuid) && Integer.valueOf(ders3[1])==firstbeacon_major && Integer.valueOf(ders3[2])==first_beacon_minor && Integer.valueOf(ders2[4])<=saat && Integer.valueOf(ders2[5])>=saat ){ Toast.makeText(getApplicationContext(),"nesne eşieşti",Toast.LENGTH_LONG).show();
                    ders_adi=ders3[3];
                    showNotification(ders_adi+" dersiniz var","Imza atmak için tıklayınız",ders_adi);
                }
                if(ders4[0].equals(firsbeacon_uuid) && Integer.valueOf(ders4[1])==firstbeacon_major && Integer.valueOf(ders4[2])==first_beacon_minor && Integer.valueOf(ders3[4])<=saat && Integer.valueOf(ders3[5])>=saat ){ Toast.makeText(getApplicationContext(),"mobil eşieşti",Toast.LENGTH_LONG).show();
                ders_adi=ders4[3];
                    showNotification(ders_adi+" dersiniz var","Imza atmak için tıklayınız",ders_adi);}

                if(ders_adi.equals("")) {
                    ders_adi="Bu saatte ders bulunmamaktadir";
                }
                try {
                    ders_adi=httpRequest.sendPost("http://192.168.1.118:8088/yoklama.php", parameters);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                i.putExtra("ders",ders_adi);



                startActivity(i);

            }
        });
    }

    public void showNotification(String title,String msg,String dersadi) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("ders",dersadi);


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentTitle(title);
        builder.setContentText(msg);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());



    }
}
