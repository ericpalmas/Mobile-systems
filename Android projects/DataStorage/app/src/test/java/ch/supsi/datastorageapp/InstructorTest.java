package ch.supsi.datastorageapp;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.util.List;

import ch.supsi.datastorageapp.DaoClass.InstructorDao;
import ch.supsi.datastorageapp.Database.RoomDB;
import ch.supsi.datastorageapp.EntityClass.Instructor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Config(maxSdk = 29)
@RunWith(RobolectricTestRunner.class)
public class InstructorTest {
    private InstructorDao instructorDao;
    private RoomDB db;

    @Before
    public void createDb(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, RoomDB.class).build();
        instructorDao = db.instructorDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeInstructorAndReadInList() throws Exception {
        Instructor instructor = new Instructor();
        instructor.setFirstName("Marco");
        instructorDao.insert(instructor);
        List<Instructor> byFirstName = (List<Instructor>) instructorDao.getInstructorById(instructor.getID());
        assertEquals(byFirstName.get(0), instructor.getFirstName());

    }
}