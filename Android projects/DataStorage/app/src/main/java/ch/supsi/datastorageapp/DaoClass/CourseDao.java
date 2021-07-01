package ch.supsi.datastorageapp.DaoClass;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ch.supsi.datastorageapp.EntityClass.Course;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface CourseDao {
    @Insert(onConflict = REPLACE)
    void insert(Course course);

    @Query("DELETE FROM Course")
    void deleteAll();

    @Query("SELECT * FROM Course")
    List<Course> getAllCourses();

    @Query("select * from Course where course_id = :id")
    Course getCourseById(int id);

    @Query("select * from Course where instructorID = :id")
    List<Course> getCourseByInstructorId(int id);

    @Query("UPDATE Course SET instructorID = :instructor_id WHERE course_id = :courseID")
    void assignCourseToInstructor(int courseID, Integer instructor_id);
}
