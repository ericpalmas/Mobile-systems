package ch.supsi.datastorageapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import ch.supsi.datastorageapp.Adapter.InstructorAdapter;
import ch.supsi.datastorageapp.Database.RoomDB;
import ch.supsi.datastorageapp.EntityClass.Course;
import ch.supsi.datastorageapp.EntityClass.Instructor;

public class MainActivity extends AppCompatActivity  {


    private RecyclerView recyclerView;
    private Button btAdd, btReset, btAddCourse;
    private List<Instructor> instructorsList = new ArrayList<>();
    private List<Course> courseList = new ArrayList<>();


    private LinearLayoutManager linearLayoutManager;
    private RoomDB database;
    private InstructorAdapter adapter;

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity", "onStart");
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        btAdd = findViewById(R.id.bt_add);
        btReset = findViewById(R.id.bt_reset);
        btAddCourse = findViewById(R.id.insertCourseButton);


        //Initialize database
        database = RoomDB.getInstance(this);
        //Store database value in data list
        instructorsList = database.instructorDao().getAll();
        courseList = database.courseDao().getAllCourses();
        // Initialize linear layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        // Set layout manager
        recyclerView.setLayoutManager(linearLayoutManager);
        //Initialize adapter
        adapter = new InstructorAdapter(instructorsList, courseList,MainActivity.this );
        //set adapter
        recyclerView.setAdapter(adapter);


        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(MainActivity.this);
                // Set content view
                dialog.setContentView(R.layout.instructor_insertion);

                // Initialize width
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                // Initialize height
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                // Set layout
                dialog.getWindow().setLayout(width, height);
                // Show dialog
                dialog.show();

                // Initialize and assign variable
                EditText nameEditText = dialog.findViewById(R.id.instructorNameInsertion);
                EditText surnameEditText = dialog.findViewById(R.id.instructorSurnameInsertion);
                EditText qualificationEditText = dialog.findViewById(R.id.instructorQualificationInsertion);
                Button insertInstructor = dialog.findViewById(R.id.insertInstructor);

                insertInstructor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //get string from field
                        String name = nameEditText.getText().toString().trim();
                        String surname = surnameEditText.getText().toString().trim();
                        String qualification = qualificationEditText.getText().toString().trim();

                        // Check condition
                        if (!name.equals("") && !surname.equals("") && !qualification.equals("")) {
                            Instructor instructor = new Instructor();
                            instructor.setFirstName(name);
                            instructor.setLastName(surname);
                            instructor.setQualification(qualification);
                            //Insert text in database
                            database.instructorDao().insert(instructor);
                            // Clear edit text
                            nameEditText.setText("");
                            surnameEditText.setText("");
                            qualificationEditText.setText("");

                            // Notify when data is inserted
                            instructorsList.clear();
                            instructorsList.addAll(database.instructorDao().getAll());
                            adapter.notifyDataSetChanged();

                            // Dismiss dialog
                            dialog.dismiss();
                        }
                    }
                });

            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete all data from database
                database.instructorDao().reset(instructorsList);
                // Notify when all data deleted
                instructorsList.clear();
                instructorsList.addAll(database.instructorDao().getAll());
                adapter.notifyDataSetChanged();
            }
        });

        btAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(MainActivity.this);
                // Set content view
                dialog.setContentView(R.layout.dialog_course_insertion);
                // Initialize width
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                // Initialize height
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                // Set layout
                dialog.getWindow().setLayout(width, height);
                // Show dialog
                dialog.show();

                EditText titleEditText = dialog.findViewById(R.id.courseNameInsertion);
                EditText creditsEditText = dialog.findViewById(R.id.courseCreditsInsertion);
                EditText descriptionEditText = dialog.findViewById(R.id.courseDescriptionInsertion);

                Button addNewCourse = dialog.findViewById(R.id.buttonAddNewCourse);

                addNewCourse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = titleEditText.getText().toString().trim();
                        String credits = creditsEditText.getText().toString().trim();
                        String description = descriptionEditText.getText().toString().trim();

                        // Check condition
                        if (!title.equals("") && !credits.equals("") && !description.equals("")) {

                            Course course = new Course();
                            course.setTitle(title);
                            course.setCredits(Integer.parseInt(credits));
                            course.setDescription(description);
                            //Insert text in database
                            database.courseDao().insert(course);
                            // Clear edit text
                            titleEditText.setText("");
                            creditsEditText.setText("");
                            descriptionEditText.setText("");

                            courseList.clear();
                            courseList.addAll(database.courseDao().getAllCourses());

                            // Dismiss dialog
                            dialog.dismiss();
                        }

                    }
                });
            }
        });

    }
}