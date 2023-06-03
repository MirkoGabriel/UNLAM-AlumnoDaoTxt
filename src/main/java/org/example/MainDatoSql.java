package org.example;

import person.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainDatoSql {
    public static void main(String[] args) {
        try {
            Dao<Student, Integer> daoStudentSql= new StudentDaoSql("jdbc:mysql://127.0.0.1:3306/universidad_caba","root","root");
            MyCalendar birthday = new MyCalendar(3, 4, 1976);
            MyCalendar admissionDate = new MyCalendar(11, 6, 2023);

            Student student = new Student(admissionDate, 23, 7.1, 'F', 4512121, "Martina", "gerez", birthday);

            daoStudentSql.create(student);
        } catch (DaoException | MyCalendarException | PersonNameException | StudentException |
                 PersonDniException e) {
            Logger.getLogger(StudentDaoTxt.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
