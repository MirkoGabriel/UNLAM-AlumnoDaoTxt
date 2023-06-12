package org.example;

import person.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainDaoTxt {
    public static void main(String[] args) {
        try {
            DaoFactory daoFactory = DaoFactory.getInstance();
            Map<String, String> config = new HashMap<>();
            config.put(DaoFactory.TYPE_DAO, DaoFactory.DAO_TXT);
            config.put(DaoFactory.DAO_PATH_TXT, "Test.txt");

            Dao<Student, Integer> daoStudentTxt = daoFactory.crearDao(config);
            MyCalendar birthday = new MyCalendar(3, 4, 196);
            MyCalendar admissionDate = new MyCalendar(11, 6, 2023);

            Student student = new Student(admissionDate, 23, 7.1, 'F', 4512123, "Martina", "gerez", birthday);
            //daoStudentTxt.create(student);
            daoStudentTxt.findAll(false).forEach(System.out::println);

        } catch (MyCalendarException | PersonNameException | StudentException | PersonDniException |
                 DaoFactoryException | DaoException e) {
            Logger.getLogger(StudentDaoTxt.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}