package ch.supsi.datastorageapp.EntityClass;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import ch.supsi.datastorageapp.EntityClass.Course;
import ch.supsi.datastorageapp.EntityClass.Instructor;

public class InstructorWithCourse {
    @Embedded public Instructor instructor;
    @Relation(
            parentColumn = "instructor_id",
            entityColumn = "instructorID"
    )
    public List<Course> courses;
}
