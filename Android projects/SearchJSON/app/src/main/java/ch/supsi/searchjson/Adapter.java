package ch.supsi.searchjson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Adapter extends ArrayAdapter<GeoName> {

    LayoutInflater mInflater;
    List<GeoName> list;

    public Adapter(Context context, int resource, int textViewResourceId, List<GeoName> objects) {
        super(context, resource, textViewResourceId, objects);
        mInflater=LayoutInflater.from(context);
        list=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if(convertView==null){
            convertView=mInflater.inflate(R.layout.list_row_geonames,null);// R.layout.listitem_layout is your custom layout file
            holder=new ViewHolder();

            holder.summary =(TextView)convertView.findViewById(R.id.summary);
            holder.bitmapImage=(ImageView) convertView.findViewById(R.id.icon);

            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }

        GeoName geoName=list.get(position);
        holder.summary.setText(geoName.getSummary());
        holder.bitmapImage.setImageBitmap(geoName.getBmpig());

        return convertView;
    }
    class ViewHolder{
        TextView summary;
        ImageView bitmapImage;
    }
}