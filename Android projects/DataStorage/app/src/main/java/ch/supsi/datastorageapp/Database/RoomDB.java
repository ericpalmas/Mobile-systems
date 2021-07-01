package ch.supsi.datastorageapp.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ch.supsi.datastorageapp.DaoClass.CourseDao;
import ch.supsi.datastorageapp.DaoClass.InstructorDao;
import ch.supsi.datastorageapp.EntityClass.Course;
import ch.supsi.datastorageapp.EntityClass.Instructor;

@Database(entities = {Instructor.class, Course.class}, version = 2, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

    // Create database instance
    private static RoomDB database;

    //Define database name
    private static String DATABASE_NAME = "database";

    public synchronized static RoomDB getInstance(Context context) {
        // Check condition
        if (database == null){
            // Database initialization
            database = Room.databaseBuilder(context.getApplicationContext()
                    ,RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    //Create Dao
    public abstract InstructorDao instructorDao();
    public abstract CourseDao courseDao();
}
