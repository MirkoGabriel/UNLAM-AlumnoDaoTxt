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
            MiCalendario birthday = new MiCalendario(25,1,1976);
            MiCalendario admissionDate = new MiCalendario(1,3,2020);

            Alumno student = new Alumno(admissionDate, 22, 4.1, 'F', 93873481, "Erika", "Cisneros", birthday);
            daoAlumnoTxt.findAll(true);
        } catch (DaoException | MiCalendarioException | PersonaNombreException | AlumnoException | PersonaDniException e) {
            Logger.getLogger(AlumnoDaoTxt.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}