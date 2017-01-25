package com.example.diegocunha.androidwearpoc.ui.splash;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.example.diegocunha.androidwearpoc.R;
import com.example.diegocunha.androidwearpoc.module.Pub;
import com.example.diegocunha.androidwearpoc.service.BackgroundThemeService;
import com.example.diegocunha.androidwearpoc.ui.commom.BaseActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends BaseActivity implements SplashView, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    View layout;
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startService(new Intent(this, BackgroundThemeService.class));

        layout = findViewById(R.id.activity_splash);

        client = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        client.connect();
    }


    @Override
    protected void onBackgroundThemeChanged(int[] theme) {
        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, theme);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(0f);

        layout.setBackgroundDrawable(drawable);

    }


    @Override
    public void onConnectionSuccesfull(String message) {

    }

    @Override
    public void onConnectionSuspend(String message) {

    }

    @Override
    public void onConnectionFailed(String message) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        sendData();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void sendData() {

        Pub item = new Pub();
        item.setId("4598345");
        item.setNamePub("Mulligan");
        item.setStreetPub("Rua nenhuma, 124");


        final JSONObject object = new JSONObject();

        Pub[] value = {item};

        final JSONArray array = new JSONArray();

        for (int i = 0; i < value.length; i++) {
            JSONObject valueObject = new JSONObject();

            try {
                valueObject.put("id", value[i].getId());
                valueObject.put("name", value[i].getNamePub());
                valueObject.put("street", value[i].getStreetPub());

                array.put(valueObject);

                object.put(String.valueOf(i), array);

            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(client).await();

                for (Node node : nodes.getNodes()) {
                    MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(client, node.getId(), "/data", array.toString().getBytes()).await();

                    if (result.getStatus().isSuccess()) {
                        Log.e("WearableService", "Flau");
                    } else {
                        Log.e("WearableService", result.getStatus().getStatusMessage());
                    }
                }
            }
        }).start();
    }

}
