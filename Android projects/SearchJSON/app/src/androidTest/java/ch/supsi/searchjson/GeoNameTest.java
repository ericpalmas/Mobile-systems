package ch.supsi.searchjson;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import junit.framework.TestCase;

public class GeoNameTest extends TestCase {

    private GeoName geoName = new GeoName("Test","summaryTest","http://www.geonames.org/img/wikipedia/58000/thumb-57388-100.jpg");

    public void testBmpig() {
        Bitmap bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        geoName.setBmpig(bitmap);
        assertEquals(400, bitmap.getHeight());
    }

    public void testGetTitle() {
        assertEquals("Test", geoName.getTitle());
    }

    public void testGetSummary() {
        assertEquals("summaryTest", geoName.getSummary());
    }

    public void testGetThumbnailImg() {
        assertEquals("http://www.geonames.org/img/wikipedia/58000/thumb-57388-100.jpg", geoName.getThumbnailImg());
    }
}