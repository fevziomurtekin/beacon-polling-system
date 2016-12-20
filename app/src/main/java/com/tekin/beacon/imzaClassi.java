package com.tekin.beacon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ömür on 18.12.2016.
 */
public class imzaClassi extends Activity {
    String beacon_uuid,ders_adi,ogrenci_numarasi,ogrenci_adsoyad;
    int beacon_major,beacon_minor;
    TextView tv1,tv2,tv3,tv4,tv5;
    Button imza_at;
    MainActivity mainActivity=new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imza);
        Intent intent = getIntent();
        ogrenci_adsoyad=intent.getStringExtra("ad_soyad");
        ogrenci_numarasi=intent.getStringExtra("ogrenci_numarasi");
        beacon_uuid=intent.getStringExtra("beacon_uuid");
        beacon_major=intent.getIntExtra("beacon_major",0);
        beacon_minor=intent.getIntExtra("beacon_minor",0);
        ders_adi=intent.getStringExtra("ders");

        tv1= (TextView) findViewById(R.id.textView3);
        tv2= (TextView) findViewById(R.id.textView5);
        tv3= (TextView) findViewById(R.id.textView6);
        tv4= (TextView) findViewById(R.id.textView7);

        imza_at= (Button) findViewById(R.id.button2);
        tv2.setText(ogrenci_adsoyad);
        tv3.setText(ogrenci_numarasi);
        tv4.setText(ders_adi);

        if(ders_adi.equals("Bu saatte ders bulunmamaktadir")){
            Toast.makeText(getApplicationContext(),"Bu saatte ders bulunmamaktadir",Toast.LENGTH_SHORT).show();
            Intent i =new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
        }else {
            imza_at.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Date dnow = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
                    int saat = Integer.parseInt(simpleDateFormat.format(dnow));
                    String parameters = "&imza_saati=" + saat + "&ogrenci_adi=" + ogrenci_adsoyad + "&ogrenci_numarasi=" + ogrenci_numarasi + "&ders_adi=" + ders_adi;
                    ImzaRequest imza=new ImzaRequest(parameters);
                    imza.execute();

                    AlertDialog.Builder builder = new AlertDialog.Builder(imzaClassi.this);
                    builder.setTitle("Sakarya Universitesi Yoklama Sistemi");
                    builder.setIcon(R.mipmap.ic_launcher);
                    builder.setMessage(ders_adi+" dersine imza attınız.");
                    builder.setNeutralButton("Tamam", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            finish();

                        }
                    });
                    builder.show();
                    Thread thread=new Thread(){
                        @Override
                        public void run(){
                            try {
                                synchronized(this){
                                    wait(3000);
                                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(i);
                                }
                            }
                            catch(InterruptedException ex){
                            }

                            // TODO
                        }
                    };

                    thread.start();

                }
            });


        }
    }
}
