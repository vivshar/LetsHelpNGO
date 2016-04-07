package com.testing.letshelpngo;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class update_service extends IntentService {

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_NGO = "NGO";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "Name";
    private static final String TAG_DESCRIPTION = "Description";
    private static final String TAG_ACTIVITY = "Activity";
    private static final String TAG_CONTACT = "Contact";
    private static final Integer NOTIFICATION_ID = 1;
    public Integer UNIQUE_INT_PER_CALL = 0;
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> ngoMapList;
    JSONArray ngo = null;

    public update_service() {
        super("update_service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {


        List<NameValuePair> params = new ArrayList<>();
        JSONObject json = jParser.makeHttpRequest("http://192.168.43.196/GET_INFO.php", "GET", params);
        dbHandler dbH = new dbHandler(this, null, null, 1);
        Log.d("All Products: ", json.toString());
        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {

                ngo = json.getJSONArray(TAG_NGO);
                for (int i = 0; i < ngo.length(); i++) {
                    JSONObject c = ngo.getJSONObject(i);
                    String id = c.getString(TAG_ID);
                    ngoDatabse n = new ngoDatabse(Integer.parseInt(id), c.getString(TAG_NAME), c.getString(TAG_DESCRIPTION), c.getString(TAG_ACTIVITY), c.getString(TAG_CONTACT));
                    Log.d("service", "running");
                    if (dbH.checkNotAvailable(n))
                        dbH.add_ngo(n);
                    if (dbH.checkUpdated(n)) {


                        NotificationManager mNotificationManager = (NotificationManager)
                                this.getSystemService(Context.NOTIFICATION_SERVICE);
                        NotificationCompat.Builder mBuilder =
                                new NotificationCompat.Builder(this);
                        mBuilder.setSmallIcon(R.drawable.ngopic);
                        mBuilder.setContentTitle("New Activity!!");
                        mBuilder.setWhen(System.currentTimeMillis());
                        mBuilder.setContentText(c.getString(TAG_ACTIVITY));
                        Intent notificationIntent = new Intent(this, ListNgo.class);
                       /* notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        notificationIntent.putExtra("name", c.getString(TAG_NAME));
                        notificationIntent.putExtra("desc", c.getString(TAG_DESCRIPTION));
                        notificationIntent.putExtra("activity", c.getString(TAG_ACTIVITY));
                        notificationIntent.putExtra("contact", c.getString(TAG_CONTACT));
                        Log.d(c.getString(TAG_NAME), "passing");
                        Log.d(c.getString(TAG_DESCRIPTION), "passing");
                        Log.d(c.getString(TAG_ACTIVITY), "passing");*/
                        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
                        mBuilder.setContentIntent(contentIntent);
                        mBuilder.setAutoCancel(true);
                        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

                        //mNotificationManager.notify();
                        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
                        dbH.update(n);


                    }


                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
