package com.example.diegocunha.androidwearpoc.ui.splash;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.diegocunha.androidwearpoc.module.Pub;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diegocunha on 11/01/17.
 */

public class SplashPresenter {

    private SplashView view;
    private GoogleApiClient client;


    public SplashPresenter(SplashView view, Context context) {
        this.view = view;

        connectGoogleApiClient(context);
    }


    private void connectGoogleApiClient(Context context){
        client = new GoogleApiClient.Builder(context)
                .addApi(Wearable.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        view.onConnectionSuccesfull("Connected!");


                        List<Pub> pubList = new ArrayList<>();


                        Pub pub = new Pub();
                        pub.setId("30-4923-04");
                        pub.setNamePub("Teste");
                        pub.setStreetPub("Dollyinho street");

                        pubList.add(pub);

                        PutDataMapRequest request = PutDataMapRequest.create("/pubs");
                        for (Pub item : pubList){
                            request.getDataMap().putString("id", item.getId());
                            request.getDataMap().putString("name", item.getNamePub());
                            request.getDataMap().putString("street", item.getStreetPub());


                            PutDataRequest dataRequest = request.asPutDataRequest();

                            PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi.putDataItem(client, dataRequest);
                        }
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        view.onConnectionSuspend("Suspended");
                    }
                })

                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        view.onConnectionFailed(connectionResult.getErrorMessage() != null ? connectionResult.getErrorMessage() : "Failed");
                    }
                }).build();


        client.connect();
    }
}
