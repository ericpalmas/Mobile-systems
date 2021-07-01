package ch.supsi.searchjson;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.ListView;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import androidx.test.platform.app.InstrumentationRegistry;

import javax.security.auth.callback.Callback;


public class JSONAsyncTaskTest extends TestCase {

    private static JSONAsyncTask jsonAsyncTask;
    private Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    private ListView listView;




    @Before
    public void testDoInBackground() throws Throwable{


        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String url = "http://api.geonames.org/wikipediaSearchJSON?q=london&maxRows=3&lang=it&username=supsi";

        // se non casto il contesto con Activity non posso chiamare find by id
        listView = ((Activity) context).findViewById(R.id.listViewResult);

        jsonAsyncTask= new JSONAsyncTask(context, listView);
        jsonAsyncTask.execute(url);
        try {
            jsonAsyncTask.get(2000, TimeUnit.MILLISECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        assertEquals(jsonAsyncTask.getStatus(),AsyncTask.Status.RUNNING );
 }

    @After
    public void testOnPostExecute() {
        assertEquals(201, jsonAsyncTask.status);
    }
}