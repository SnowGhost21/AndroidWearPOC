package com.example.diegocunha.androidwearpoc;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResolvingResultCallbacks;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

import helper.BroadcastListener;
import helper.GradientHelper;
import helper.LayerListener;
import service.BackgroundThemeService;

public class MainActivity extends Activity {

    private TextView mTextView;
    private GoogleApiClient client;
    private View layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, BackgroundThemeService.class));


        IntentFilter filter = new IntentFilter(Intent.ACTION_SEND);
        BroadcastListener broadcast = new BroadcastListener();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcast, filter);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
                layout = stub.findViewById(R.id.layout);

                GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, GradientHelper.colorsForTime());
                drawable.setShape(GradientDrawable.RECTANGLE);
                drawable.setCornerRadius(0f);

                layout.setBackgroundDrawable(drawable);
                connect();
            }
        });
    }


    private void connect() {
        client = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        Wearable.DataApi.addListener(client, new DataApi.DataListener() {
                            @Override
                            public void onDataChanged(DataEventBuffer dataEventBuffer) {
                                PendingResult<DataItemBuffer> bufferPendingResult = Wearable.DataApi.getDataItems(client);
                                bufferPendingResult.setResultCallback(new ResultCallbacks<DataItemBuffer>() {
                                    @Override
                                    public void onSuccess(@NonNull DataItemBuffer dataItems) {
                                        for (int i = 0; i < dataItems.getCount(); i++) {
                                            final DataItem item = dataItems.get(i);

                                            Uri uri = item.getUri();

                                            if ("/pubs".equals(uri.getPath())) {
                                                DataMap map = DataMapItem.fromDataItem(item).getDataMap();
                                                Log.e("Response", map.getString("name"));
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Status status) {

                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .addApi(Wearable.API)
                .build();

        client.connect();
    }
}
