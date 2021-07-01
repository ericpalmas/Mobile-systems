package ch.supsi.datastorageapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ch.supsi.datastorageapp.Database.RoomDB;
import ch.supsi.datastorageapp.EntityClass.Course;

public class InstructorDetail extends AppCompatActivity {

    private RoomDB database;
    private List<Course> instructorCourses = new ArrayList<>();
    private ArrayAdapter<String> titleStringAdapter;
    private List<String> titleList;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_detail);
        Intent intent = getIntent();

        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        String surname = intent.getStringExtra("surname");
        String qualification = intent.getStringExtra("qualification");

        TextView textViewName = findViewById(R.id.instructorName);
        TextView textViewSurname = findViewById(R.id.instructorSurname);
        TextView textViewQualification = findViewById(R.id.instructorQualification);

        textViewName.setText(name);
        textViewSurname.setText(surname);
        textViewQualification.setText(qualification);
        ListView listCoursesView = findViewById(R.id.instructorCoursesList);

        database = RoomDB.getInstance(this);
        instructorCourses = database.courseDao().getCourseByInstructorId(Integer.parseInt(id));

        titleList = instructorCourses.stream().map(a -> a.getTitle()).collect(Collectors.toList());
        titleStringAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, titleList);
        listCoursesView.setAdapter(titleStringAdapter);
        titleStringAdapter.notifyDataSetChanged();
    }
}