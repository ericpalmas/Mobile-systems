package ch.supsi.searchjson;

import android.graphics.Bitmap;

public class GeoName {
    private String title;
    private String summary;
    private String thumbnailImg;
    private Bitmap bmpig;

    public GeoName(String title, String summary, String thumbnailImg) {
        this.title = title;
        this.summary = summary;
        this.thumbnailImg = thumbnailImg;
    }

    public Bitmap getBmpig() {
        return bmpig;
    }

    public void setBmpig(Bitmap bmpig) {
        this.bmpig = bmpig;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getThumbnailImg() {
        return thumbnailImg;
    }

    @Override
    public String toString() {
        return "GeoName{" +
                "title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", thumbnailImg='" + thumbnailImg + '\'' +
                '}';
    }
}
