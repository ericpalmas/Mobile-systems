package ch.supsi.searchjson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {
    private EditText inputPlace, inputLanguage, inputRows;
    private Button send;
    private JSONAsyncTask jsonAsyncTask;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputPlace = findViewById(R.id.placeEditText);
        inputLanguage = findViewById(R.id.languageEditText);
        inputRows = findViewById(R.id.maxrowsEditText);
        send = findViewById(R.id.button);
        listView = findViewById(R.id.listViewResult);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!inputPlace.getText().toString().equals("")){
                    if(inputLanguage.getText().toString().equals(""))
                        inputLanguage.setText("en");
                    if(inputRows.getText().toString().equals(""))
                        inputRows.setText("10");

                    String url = "http://api.geonames.org/wikipediaSearchJSON?q=" + inputPlace.getText() + "&maxRows=" + inputRows.getText() + "&lang="+ inputLanguage.getText() +"&username=supsi";

                    jsonAsyncTask= new JSONAsyncTask(MainActivity.this, listView);
                    jsonAsyncTask.execute(url);
                }
            }
        });
    }
}