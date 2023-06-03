package org.example;

import person.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainDatoSql {
    public static void main(String[] args) {
        try {
            Dao<Student, Integer> daoStudentSql= new StudentDaoSql("jdbc:mysql://127.0.0.1:3306/universidad_caba","root","root");
            MyCalendar birthday = new MyCalendar(23, 1, 1998);
            MyCalendar admissionDate = new MyCalendar(11, 6, 2023);

            Student student = new Student(admissionDate, 23, 7.1, 'M', 93873483, "Mirkito", "gerez", birthday);

            //daoStudentSql.create(student);
           /* List<Student> students = daoStudentSql.findAll(true);
            students.forEach(student1 -> {
                System.out.println(student1.toString());
            });*/
         /*   Student studentRead = daoStudentSql.read(93873483);
            System.out.println(studentRead.toString());*/

          //  daoStudentSql.update(student);
            daoStudentSql.delete(93873483);
        } catch (DaoException | MyCalendarException | PersonNameException | StudentException |
                 PersonDniException e) {
            Logger.getLogger(StudentDaoTxt.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
