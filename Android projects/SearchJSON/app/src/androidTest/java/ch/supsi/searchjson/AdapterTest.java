package ch.supsi.searchjson;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.ListView;

import androidx.annotation.ContentView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.Rule;

import java.util.ArrayList;
import java.util.List;

public class AdapterTest extends TestCase {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    List<GeoName> geoNameList = new ArrayList<>();
    private Context context;


    public void testGetView() {

        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ListView listView = ((Activity)context).findViewById(R.id.listViewResult);

        geoNameList.add(new GeoName("titleTest","summaryTest","urlTest"));
        Adapter adapter = new Adapter( context, R.layout.activity_main, R.id.listViewResult, geoNameList);

        assertEquals(1, geoNameList.size());
        listView.setAdapter(adapter);
    }
}