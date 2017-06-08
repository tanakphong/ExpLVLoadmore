package com.deverdie.explvloadmore;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ExpendableListAdapter listAdapter;
    private List<ExpHeaderBean> listDataHeader = new ArrayList<>();
    private HashMap<ExpHeaderBean, List<ExpListBean>> listHashMap = new HashMap<>();
    private ExpandableListView listView;

    private String pURL = "http://192.168.1.214/WCF_KPI/Service1.svc/PalletBatchDataJSON";
    private Integer pPageNo = 1;
    private Integer pRecordPerPage = 20;
    private Integer pRecordCount = 0;


    private class FeedData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                OkHttpClient client = new OkHttpClient();

                //GET
                HttpUrl.Builder urlBuilder = HttpUrl.parse(params[0]).newBuilder();
                urlBuilder.addQueryParameter("pageNo", params[1]);
                urlBuilder.addQueryParameter("recordPerPage", params[2]);
                String url = urlBuilder.build().toString();

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                Response response = client.newCall(request).execute();
                //Log.i("deverdie doInBackground", response.body().string());
                String result = response.body().string().toString();
                Log.i("devlog", "doInBackground result : " + result);
                pPageNo++;
                return result;

            } catch (Exception e) {
                Log.i("devlog", "doInBackground catch: " + e.toString());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("devlog", "onPostExecute: " + s);
            try {
                //listDataHeader = new ArrayList<>();
                //listHashMap = new HashMap<>();

                JSONObject reader = new JSONObject(s);
                String json = reader.getString("PalletBatchDataJSONResult");
                Log.d("devlog", "JSON Object JSONDataResult : "+json);
                JSONArray jsonArray = new JSONArray(json);
                Toast.makeText(MainActivity.this, "jsonArray.length():" + jsonArray.length(), Toast.LENGTH_SHORT).show();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject row = jsonArray.getJSONObject(i);

                    String r = row.getString("R");
                    String plant = row.getString("PLT_PLANT");
                    String batch = row.getString("PLT_BATCH");
                    String material = row.getString("PLT_MATERIAL");
                    String material_text = row.getString("PLT_MATERIAL_TEXT");
                    String qty = row.getString("PLT_WEIGHT_QTY");
                    String unit = row.getString("PLT_WEIGHT_UNIT");


                    listDataHeader.add(new ExpHeaderBean(batch,
                            material_text,
                            Float.valueOf(qty),
                            unit));
                    List<ExpListBean> tkp = new ArrayList<>();
                    tkp.add(new ExpListBean(plant,
                            material));
                    listHashMap.put(listDataHeader.get(pRecordCount), tkp);
                    listAdapter.setNewItems(listDataHeader, listHashMap);
                    //id = row.getInt("id");
                    //String material_text = row.getString("PLT_MATERIAL_TEXT");
                    //Log.d("deverdie", "onPostExecute: material_text >>"+material_text);
                    //Toast.makeText(BatchListviewActivity.this, material_text, Toast.LENGTH_SHORT).show();
                    pRecordCount++;
                }


            } catch (Exception aE) {
                Log.i("devlog", "onPostExecute catch: " + aE.toString());
                Toast.makeText(MainActivity.this, "Data not found.", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = (ExpandableListView) findViewById(R.id.lvExp);
        listAdapter = new ExpendableListAdapter(this, listDataHeader, listHashMap);
        listView.setAdapter(listAdapter);

        // Creating a button - Load More
        Button btnLoadMore = new Button(this);
        btnLoadMore.setText("Load More");

        // Adding button to listview at footer
        listView.addFooterView(btnLoadMore);

        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FeedData().execute(pURL, pPageNo.toString(), pRecordPerPage.toString());
            }
        });


        new FeedData().execute(pURL, pPageNo.toString(), pRecordPerPage.toString());

    }
}
