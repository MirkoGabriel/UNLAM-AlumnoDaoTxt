package org.example;

import person.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        try {
            Dao<Student, Integer> daoStudentTxt = new StudentDaoTxt("Test.txt");
            MyCalendar birthday = new MyCalendar(3, 4, 196);
            MyCalendar admissionDate = new MyCalendar(11, 6, 2023);

            Student student = new Student(admissionDate, 23, 7.1, 'F', 4512123, "Martina", "gerez", birthday);
            daoStudentTxt.create(student);
           /* daoStudentTxt.findAll(true).forEach(a -> {
                System.out.println(a);
            });*/

        } catch (DaoException | MyCalendarException | PersonNameException | StudentException |
                 PersonDniException e) {
            Logger.getLogger(StudentDaoTxt.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}