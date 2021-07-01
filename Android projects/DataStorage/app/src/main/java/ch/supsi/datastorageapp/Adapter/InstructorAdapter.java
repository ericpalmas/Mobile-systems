package ch.supsi.datastorageapp.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ch.supsi.datastorageapp.EntityClass.Course;
import ch.supsi.datastorageapp.EntityClass.Instructor;
import ch.supsi.datastorageapp.InstructorDetail;
import ch.supsi.datastorageapp.MainActivity;
import ch.supsi.datastorageapp.R;
import ch.supsi.datastorageapp.Database.RoomDB;

public class InstructorAdapter extends RecyclerView.Adapter<InstructorAdapter.ViewHolder>  {

    //Initialize variable
    private List<Instructor> instructorList;
    private List<Course> coursesList;
    private Activity context;
    private RoomDB database;
    private ListView listCourses;
    private ArrayList<Integer> coursesPosition = new ArrayList<>();

    // Create constructor
    public InstructorAdapter(List<Instructor> instructorList, List<Course> coursesList,  Activity context) {
        this.instructorList = instructorList;
        this.context = context;
        this.coursesList = coursesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Initialize view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Initialize instructor data
        Instructor instructor = instructorList.get(position);
        // Initialize database
        database = RoomDB.getInstance(context);

        // Set text on text view
        holder.textView.setText(instructor.getFirstName() + " "+  instructor.getLastName());

        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize instructor data
                Instructor instructor = instructorList.get(holder.getAdapterPosition());
                // Get id
                int sID = instructor.getID();
                // Get first name
                String name = instructor.getFirstName() ;
                String surname = instructor.getLastName();
                String qualification = instructor.getQualification();
                // Create dialog
                Dialog dialog = new Dialog(context);
                // Set content view
                dialog.setContentView(R.layout.dialog_update);

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

                Button btUpdate = dialog.findViewById(R.id.btn_update);

                listCourses = dialog.findViewById(R.id.listCourses);
                CourseAdapter adapter = new CourseAdapter(context, R.layout.list_row_courses, coursesList,coursesPosition );
                listCourses.setAdapter(adapter);

                // Set text on edit text
                nameEditText.setText(name);
                surnameEditText.setText(surname);
                qualificationEditText.setText(qualification);

                btUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Dismiss dialog
                        dialog.dismiss();
                        // Get updated text from edit text
                        String name = nameEditText.getText().toString().trim();
                        String surname = surnameEditText.getText().toString().trim();
                        String qualification = qualificationEditText.getText().toString().trim();
                        // Update instructor in database
                        database.instructorDao().update(sID, name, surname, qualification);
                        // Update instructor courses
                        for (Integer position: coursesPosition) {
                            int courseID = coursesList.get(position).getID();
                            database.courseDao().assignCourseToInstructor(courseID,sID);
                        }

                        // Notify when data is updated
                        instructorList.clear();
                        //instructorList.addAll(database.instructorDao().getAll());
                        instructorList.addAll(database.instructorDao().getAll());
                        notifyDataSetChanged();
                    }
                });
            }
        });



        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize instructor data
                Instructor instructor = instructorList.get(holder.getAdapterPosition());
                // Delete instructor from database
                database.instructorDao().delete(instructor);
                // Notify when instructor is deleted
                int position = holder.getAdapterPosition();
                instructorList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, instructorList.size());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Instructor instructor = instructorList.get(holder.getAdapterPosition());
                Intent myIntent = new Intent(v.getContext(), InstructorDetail.class);

                myIntent.putExtra("id", String.valueOf(instructor.getID()));
                myIntent.putExtra("name", instructor.getFirstName() );
                myIntent.putExtra("surname", instructor.getLastName() );
                myIntent.putExtra("qualification", instructor.getQualification() );
                v.getContext().startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return instructorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Initialize variable
        TextView textView;
        ImageView btEdit, btDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Assign variable
            textView = itemView.findViewById(R.id.text_view);
            btEdit = itemView.findViewById(R.id.bt_edit);
            btDelete = itemView.findViewById(R.id.bt_delete);
        }
    }
}
