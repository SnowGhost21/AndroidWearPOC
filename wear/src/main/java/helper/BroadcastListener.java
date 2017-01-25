package helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by diegocunha on 25/01/17.
 */

public class BroadcastListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("message");
        try {

            Gson gson = new Gson();

            Type listType = new TypeToken<List<Pub>>() {}.getType();

            List<Pub> pubs = gson.fromJson(message, listType);

            Log.e("Teste", "Aqui");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
