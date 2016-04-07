package com.testing.letshelpngo;


import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListNgo extends ListActivity {
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_NGO = "NGO";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "Name";
    private static final String TAG_DESCRIPTION = "Description";
    private static final String TAG_ACTIVITY = "Activity";
    private static final String TAG_CONTACT = "Contact";
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> ngoMapList;
    JSONArray ngo = null;
    ListView listView;
    private ProgressDialog pDialogue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_ngo);
        ngoMapList = new ArrayList<HashMap<String, String>>();

        new ListAllNgos().execute();

        //listView = (ListView)findViewById(android.R.id.list);
        listView = getListView();
        getListView().setDivider(null);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pid = ((TextView) view.findViewById(R.id.pid)).getText().toString();
                try {
                    JSONObject c = ngo.getJSONObject(position);
                    Intent i = new Intent(getApplicationContext(), print_ngo_details.class);
                    i.putExtra("name", c.getString(TAG_NAME));
                    i.putExtra("desc", c.getString(TAG_DESCRIPTION));
                    i.putExtra("activity", c.getString(TAG_ACTIVITY));
                    i.putExtra("contact", c.getString(TAG_CONTACT));
                    startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    class ListAllNgos extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogue = new ProgressDialog(ListNgo.this);
            pDialogue.setMessage("Loading Data...");
            pDialogue.setIndeterminate(false);
            pDialogue.setCancelable(false);
            pDialogue.show();
        }


        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<>();
            JSONObject json = jParser.makeHttpRequest("http://192.168.43.196/GET_INFO.php", "GET", params);
            dbHandler dbH = new dbHandler(ListNgo.this, null, null, 1);
            Log.d("All Products: ", json.toString());
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {

                    ngo = json.getJSONArray(TAG_NGO);
                    for (int i = 0; i < ngo.length(); i++) {
                        JSONObject c = ngo.getJSONObject(i);
                        String id = c.getString(TAG_ID);
                        String Name = c.getString(TAG_NAME);
                        String Desc = c.getString(TAG_DESCRIPTION);
                        String Activity = c.getString(TAG_ACTIVITY);
                        String Contact = c.getString(TAG_CONTACT);
                        HashMap<String, String> map = new HashMap<String, String>();

                        map.put(TAG_ID, id);
                        map.put(TAG_NAME, Name);
                        ngoMapList.add(map);

                  /* ngoDatabse n = new ngoDatabse(Integer.parseInt(id),Name,Desc,Activity,Contact);
                    if(dbH.checkNotAvailable(n))
                        Log.d("listngo","mein hai");
                        dbH.add_ngo(n);
                    if(dbH.checkUpdated(n))
                        dbH.update(n);
                                            */

                    }
                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;

        }

        protected void onPostExecute(String file_url) {
            pDialogue.dismiss();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //ListAdapter listAdapter = new customListAdapter(ListNgo.this, listItems);
                    ListAdapter listAdapter = new SimpleAdapter(ListNgo.this, ngoMapList, R.layout.custom_list_layout, new String[]{TAG_ID, TAG_NAME}, new int[]{R.id.pid, R.id.listText});
                    setListAdapter(listAdapter);

                }
            });
        }
    }


}
