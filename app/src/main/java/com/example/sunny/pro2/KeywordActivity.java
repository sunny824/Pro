package com.example.sunny.pro2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeywordActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String[] mDataset;
    private AsyncTask progressDialogTask;
    private RecyclerView keywordListRecycleView;

    private String kwRespResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {


            progressDialogTask = new AsyncTask<Void, Void, ArrayList<String[]>>() {
                android.app.ProgressDialog myDialog;

                @Override
                protected void onCancelled() {
                    myDialog.dismiss();
                    super.onCancelled();
                }

                @Override
                protected void onPreExecute() {
                    // final android.app.ProgressDialog
                    myDialog = android.app.ProgressDialog.show(KeywordActivity.this,"", "搜尋中，請稍候....", true);
                }


                protected ArrayList<String[]> doInBackground(Void... params){
                    ArrayList<String[]> myDataset = null;
                    try{
                        myDataset = getDocfromKeyWordByURL("http://m.vghtpe.gov.tw:8080/MobileWeb/expertfind.do?kw=" + URLEncoder.encode("骨折", "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    return myDataset;
                }

                @Override
                protected void onProgressUpdate(Void... values) {
                    super.onProgressUpdate(values);
                }

                @Override
                protected void onPostExecute(ArrayList<String[]> myDataset) {
//                    if (myDataset != null && myDataset.size() > 0) {
//                       // keywordListRecycleView.setAdapter( new ListViewAdapter(myDataset) );
//                    }else {
//                        new AlertDialog.Builder(KeywordActivity.this).setTitle("查無關鍵字關聯醫師").setMessage("很抱歉！查無此關鍵字關聯醫師！").setIcon(android.R.drawable.ic_dialog_alert).
//                                setPositiveButton("確定", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        // // // dialog.dismiss();
//                                     //   finish();
//                                    }
//                                }).create().show();
//                        // // // return false;
//                    }
                    // // // if (isOnline()){
                    // // // }else{
                    // // //     new android.app.AlertDialog.Builder(ExpertKeywordMainActivity.this).setTitle("網路連線異常").setMessage("網路連線異常，請確認網路連線後重新啟動").setIcon(android.R.drawable.ic_dialog_alert).
                    // // //             setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    // // //                 @Override
                    // // //                 public void onClick(DialogInterface dialog, int which) {
                    // // //                     // // // dialog.dismiss();
                    // // //                     finish();
                    // // //                 }
                    // // //             }).create().show();
                    // // // }
                    myDialog.dismiss();
                }
            };
            //循序：SERIAL_EXECUTOR，並行：THREAD_POOL_EXECUTOR
            progressDialogTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
            // // // if (android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.HONEYCOMB)
            // // //     progressDialogTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
            // // // else
            // // //     progressDialogTask.execute();


        }catch (Exception e) {
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

    private ArrayList<String[]> getDocfromKeyWordByURL(String url){
        ArrayList<String[]> myDataset = null;
        try {
            if (kwRespResult == null || kwRespResult.length() <= 0) {
                kwRespResult = new HttpTask().execute(url).get();
            }
            String response = kwRespResult;
            if (response != null && response.length() > 0) {
                myDataset = new ArrayList<String[]>();

                String docDept = "";
                String docName = "";
                String base64 = "";
                String guidUrl = "";

                //for (String line : response.split("\n")) {
                try {
                    JSONObject jo = new JSONObject(response);
                    JSONArray ja = jo.getJSONArray("RECORDS");
                    Pattern pattern = null;
                    for (int i = 0; i < ja.length(); i++) {
                        if (ja != null && ja.length() > 0) {
                            pattern = Pattern.compile("\\s*(\\S+)$", Pattern.MULTILINE);
                            String deptStr = ja.getJSONObject(i).get("DEPARTMENT").toString();
                            Matcher matcher = pattern.matcher(deptStr);
                            if (matcher.find()) {
                                docDept = matcher.group(0);
                            }


                            pattern = Pattern.compile("\\s*(\\S+)", Pattern.MULTILINE);
                            String docNameStr = ja.getJSONObject(i).get("DOCNAME").toString();
                            matcher = pattern.matcher(docNameStr);
                            if (matcher.find()) {
                                docName = matcher.group(0);
                            }

                            String docNameTitle = ja.getJSONObject(i).get("TITLE").toString();

                            base64 = ja.getJSONObject(i).get("IMAGEBASE64").toString();
                            guidUrl = ja.getJSONObject(i).get("GUIDEURL").toString();
                            myDataset.add(new String[]{deptStr, docNameStr, docNameTitle, base64, guidUrl, docName});
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return myDataset;
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
            //keywordListRecycleView.setAdapter( new SearchRecycleAdapter(myDataset) );
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
