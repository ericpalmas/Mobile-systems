package ch.supsi.datastorageapp.EntityClass;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.io.Serializable;


@Entity (tableName = "Instructor", indices = {@Index(value = {"instructor_id"}, unique = true)})
public class Instructor implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "instructor_id")
    private int ID;

    @ColumnInfo(name = "firstName")
    private String firstName;

    @ColumnInfo(name = "lastName")
    private String lastName;

    @ColumnInfo(name = "qualification")
    private String qualification;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
