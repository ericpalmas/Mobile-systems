package ch.supsi.searchjson;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class JSONAsyncTask extends AsyncTask<String,Void,Boolean> {

    private Context mContext;
    private List<GeoName> geoNameList = new ArrayList<>();
    private HttpURLConnection connection;
    private URLConnection imgConnection;
    private ListView listView;
    int status;

    public JSONAsyncTask(Context mContext, ListView listView) {

        this.mContext = mContext;
        this.listView = listView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(String... urls) {

        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urls[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            status = connection.getResponseCode();

            switch (status){
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();

                    Gson gson = new Gson();
                    JsonObject object = JsonParser.parseString(sb.toString()).getAsJsonObject();
                    JsonArray array = object.getAsJsonArray("geonames");

                    InputStream imgStream = null;
                    for (int i = 0; i < array.size(); i++) {
                        GeoName geoName = gson.fromJson(array.get(i), GeoName.class);
                        geoNameList.add(geoName);

                        URL imgURL = new URL(geoName.getThumbnailImg());
                        imgConnection = imgURL.openConnection();
                        HttpURLConnection httpConn = (HttpURLConnection) imgConnection;
                        httpConn.connect();

                        imgStream = httpConn.getInputStream();
                        Bitmap bmpimg = BitmapFactory.decodeStream(imgStream);
                        geoName.setBmpig(bmpimg);
                    }
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        Adapter adapter = new Adapter(mContext, R.layout.activity_main, R.id.listViewResult, geoNameList);
        listView.setAdapter(adapter);
    }
}
