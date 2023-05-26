package org.example;

import persona.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {

        //Student row information at 1h 31min
        //Make read, readAll (include activeStudents boolean),
        try {
            Dao<Alumno, Integer> daoAlumnoTxt = new AlumnoDaoTxt("Test.txt");
            MiCalendario birthday = new MiCalendario(23,1,1998);
            MiCalendario admissionDate = new MiCalendario(1,3,2020);

            Alumno student = new Alumno(admissionDate, 22, 7.33, 'M', 93873483, "Mirkocho", "Pinas", birthday);
            daoAlumnoTxt.create(student);
        } catch (DaoException | MiCalendarioException | PersonaNombreException | AlumnoException | PersonaDniException e) {
            Logger.getLogger(AlumnoDaoTxt.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}