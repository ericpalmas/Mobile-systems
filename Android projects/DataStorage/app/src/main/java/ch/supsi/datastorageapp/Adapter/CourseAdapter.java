package ch.supsi.datastorageapp.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import java.util.ArrayList;
import java.util.List;

import ch.supsi.datastorageapp.EntityClass.Course;
import ch.supsi.datastorageapp.R;

public class CourseAdapter extends ArrayAdapter<Course> {
    private List<Course> listCourses;
    private ArrayList<Integer> coursesPosition;


    public CourseAdapter(Context context, int textViewResourceId, List<Course> listCourses, ArrayList<Integer> coursesPosition) {
        super(context, textViewResourceId, listCourses);
        this.listCourses = listCourses;
        this.coursesPosition = coursesPosition;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_row_courses, null);

        TextView title = (TextView)convertView.findViewById(R.id.titleView);
        CheckBox checkBox = convertView.findViewById(R.id.sub_chk);
        Course c = getItem(position);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            coursesPosition.add(position);
                        }else{
                            coursesPosition.removeIf(s -> s.equals(position));
                        }
                    }
                });

        title.setText(c.getTitle());
        return convertView;
    }



}
