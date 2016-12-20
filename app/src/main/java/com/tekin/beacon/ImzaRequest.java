package com.tekin.beacon;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ömür on 18.12.2016.
 */
public class ImzaRequest extends AsyncTask {
    String paremeters,services;
    private final String USER_AGENT = "Mozilla/5.0";
    String veri;

    // HTTP GET request
    public ImzaRequest(String paremeters){
        this.paremeters=paremeters;

    }



    private String sendGet() throws Exception {

        String url = "http://localhost:8080/cek.php";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

        return url;
    }

    // HTTP POST request
    public String sendPost(String url, String urlParameters) throws Exception {


        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        veri=response.toString();
        return response.toString();
    }

    @Override

    protected Object doInBackground(Object[] params) {
        String post=" ";


        try {
            post = sendPost("web_adress", paremeters);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return post;
    }
    protected String onPostExecute(Object[] params){
        String get=" ";
        try{
            get=veri;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return veri;
    }





}
