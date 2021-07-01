package ch.supsi.datastorageapp.DaoClass;
import ch.supsi.datastorageapp.EntityClass.Instructor;
import ch.supsi.datastorageapp.EntityClass.InstructorWithCourse;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;


import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface InstructorDao {

    @Insert(onConflict = REPLACE)
    void insert(Instructor instructor);

    @Delete
    void delete(Instructor instructor);

    @Delete
    void reset(List<Instructor> instructors);

    @Query("UPDATE instructor SET firstName = :name, lastName = :surname, qualification = :qualification WHERE instructor_id = :sID")
    void update(int sID, String name,String surname,String qualification);

    @Query("SELECT * FROM Instructor")
    List<Instructor> getAll();

    @Query("select * from Instructor where instructor_id = :id")
    Instructor getInstructorById(int id);

    @Transaction
    @Query("SELECT * FROM Instructor")
    List<InstructorWithCourse> getUsersWithCourse();

}
