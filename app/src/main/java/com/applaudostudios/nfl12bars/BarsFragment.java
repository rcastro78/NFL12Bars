package com.applaudostudios.nfl12bars;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.applaudostudios.nfl12bars.adapter.BarAdapter;
import com.applaudostudios.nfl12bars.models.BarVenue;
import com.applaudostudios.nfl12bars.utils.JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by RafaelCastro on 28/5/15.
 */
public class BarsFragment extends Fragment{



    String URL="http://s3.amazonaws.com/jon-hancock-phunware/nflapi-static.json";
    ProgressDialog progressDialog;

    ArrayList<BarVenue> items = new ArrayList<>();
    ListView mLvBars;

    private OnListItemSelectedListener listener;

    public interface OnListItemSelectedListener {
        public void onItemSelected(BarVenue item);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnListItemSelectedListener) {
            listener = (OnListItemSelectedListener) activity;
        } else {
            throw new ClassCastException(
                    activity.toString()
                            + " must implement ItemsListFragment.OnListItemSelectedListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bars,
                container, false);
        mLvBars = (ListView)view.findViewById(R.id.lvBars);
        new BarsAsyncTask().execute();
        mLvBars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object = mLvBars.getItemAtPosition(position);
                BarVenue bar = (BarVenue)object;
                listener.onItemSelected(bar);
            }
        });
        return view;
    }


    class BarsAsyncTask extends AsyncTask<String,Integer,ArrayList<BarVenue>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading bars...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(0);
            progressDialog.show();
        }
        @Override
        protected ArrayList<BarVenue> doInBackground(String... arg0) {
            String result;
            JSONParser jParser = new JSONParser();
            result = jParser.getJSONFromUrlString(URL);
            try {
                JSONArray mJsonArray = new JSONArray(result);
                for (int i = 0; i < mJsonArray.length(); i++) {
                    JSONObject json = mJsonArray.getJSONObject(i);
                    int id = json.getInt("id");
                    String name = json.getString("name");
                    String address =json.getString("address")+" "+
                            json.getString("city")+", "+json.getString("state")+" "+json.getString("zip");
                    String schedules="";
                    try {
                        String schedule = json.getString("schedule");
                        JSONArray mJsonArraySchedule = new JSONArray(schedule);

                        for (int j = 0; j < mJsonArraySchedule.length(); j++) {
                            JSONObject jsonSchedule = mJsonArraySchedule.getJSONObject(j);
                            schedules += setStartTime(jsonSchedule.getString("start_date")) +" a "+
                                    setEndTime(jsonSchedule.getString("end_date"))+"\n";

                        }
                    }catch (Exception e)
                    {
                        schedules = "";
                    }

                    String imageUrl = json.getString("image_url");
                    items.add(new BarVenue(id,name,address,imageUrl,schedules));
                }


            } catch (Exception e) {
            }

            return items;
        }


        @Override
        protected void onPostExecute(ArrayList<BarVenue> result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            BarAdapter mBarAdapter = new BarAdapter(getActivity(),result);
            mLvBars.setAdapter(mBarAdapter);

        }



        public String setStartTime(String dt) throws Exception
        {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
            SimpleDateFormat df_output = new SimpleDateFormat("EEEE M/dd h:mm a");
            Date date = simpleDateFormat.parse(dt);
            TimeZone destTz = TimeZone.getDefault();
            simpleDateFormat.setTimeZone(destTz);
            String result = df_output.format(date);
            return result;
        }

        public String setEndTime(String dt) throws Exception
        {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
            SimpleDateFormat df_output = new SimpleDateFormat("h:mm a");
            Date date = simpleDateFormat.parse(dt);
            TimeZone destTz = TimeZone.getDefault();
            simpleDateFormat.setTimeZone(destTz);
            String result = df_output.format(date);
            return result;
        }
    }



}
