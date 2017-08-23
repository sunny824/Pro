package com.example.sunny.pro2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class KeywordActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String[] mDataset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            String response = new HttpTask().execute("http://m.vghtpe.gov.tw:8080/MobileWeb/expertfind.do").get();

            if (response != null && response.length() > 0) {
                JSONObject jo = new JSONObject(response);
                JSONArray ja = jo.getJSONArray("RECORDS");

                java.util.HashMap<String, ArrayList<String>> map = new java.util.HashMap<String, ArrayList<String>>();
                for (int i = 0; i < ja.length(); i++) {
                    if (ja != null && ja.length() > 0) {
                        String depStr = ja.getJSONObject(i).get("DEP").toString().replaceAll("內科部", "").replaceAll("外科部", "").replaceAll("臨床毒物與職業醫學科", "臨床毒物科");
                        String kwStr = ja.getJSONObject(i).get("KEYWORD").toString();
                        mDataset[0]=depStr;
                    }
                }



            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        }

        //setContentView(R.layout.activity_keyword);
    }

    public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.MyViewHolder> {
        List<String> list=new ArrayList<String>();
        private List<String[]> mData;
        private Context context;



        ListViewAdapter(List<String[]> listdata){
            mData=listdata;
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.query_docexpert_layout, parent, false);
            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {

            //String item[] = mData.get(position);
            holder.docDeptTextView.setText(mData.get(position)[0].trim());
            //holder.docNameTextView.setText(mData.get(position)[1].trim());
            //holder.docTitleTextView.setText(mData.get(position)[2].trim());

            String strBase64 = mData.get(position)[3].trim();
            byte[] decodedString = Base64.decode(strBase64, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.docPhotoImgView.setImageBitmap(decodedByte);



            //holder.sectTextView.setText(mData.get(position)[2].trim());
            //holder.sectnoTextView.setText(mData.get(position)[0].trim());
            // // // holder.rawTextView.setText(mData.get(position)[0]);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // // // Toast.makeText(ExpertKeywordMainActivity.this, "Item " + " is clicked.", Toast.LENGTH_SHORT).show();
                    String item[] = mData.get(position);


                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // // // Toast.makeText(QueryMainSearchActivity.this, "Item " + " is long clicked.", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView docDeptTextView;
            public TextView docNameTextView;
            public TextView docTitleTextView;
            public ImageView docPhotoImgView;
            public MyViewHolder(View v) {
                super(v);
                docDeptTextView     = (TextView) v.findViewById(R.id.docDept);
                docNameTextView     = (TextView) v.findViewById(R.id.resultDoc);
                docTitleTextView    = (TextView) v.findViewById(R.id.docTitle);
                docPhotoImgView     = (ImageView) v.findViewById(R.id.docPhoto);

                //sectnoTextView = (TextView) v.findViewById(R.id.textSect);
                //rawTextView = (TextView) v.findViewById(R.id.textRaw);
            }
        }
    }


    public class HttpTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // pDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            String rtn = null;
            OkHttpClient client = new OkHttpClient();
            client.setReadTimeout(1000000, TimeUnit.SECONDS);
            client.setConnectTimeout(1000000, TimeUnit.SECONDS);
            Request.Builder requestBuilder = new Request.Builder().url(urls[0]);
            if ( urls.length > 1) {
                FormEncodingBuilder formBuilder = new FormEncodingBuilder();
                for (int i = 1; i < urls.length; i = i + 2) {
                    formBuilder.add(urls[i], urls[i + 1]);
                }
                requestBuilder.post(formBuilder.build());
            }
            // // // Request request = new Request.Builder().url(urls[0]).build();
            try {
                Response response = client.newCall(requestBuilder.build()).execute();
                rtn = response.body().string();
            }catch(IOException ex){
            }
            return rtn;
            // // // client.newCall(request).enqueue(new Callback() {
            // // //     @Override
            // // //     public void onFailure(Request request, IOException e) {
            // // //     }
            // // //     @Override
            // // //     public void onResponse(Response response) throws IOException {
            // // //         sb.append( response.body().string() );
            // // //     }
            // // // });
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // pDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String response) {
            // if(pDialog!=null)
            //    pDialog.dismiss();
            // pDialog = null;
            //将Bitmap填充进Imageview
            // imageView.setImageBitmap(bitmap);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.keyword, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
