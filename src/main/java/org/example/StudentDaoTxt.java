package org.example;

import org.apache.commons.lang3.math.NumberUtils;
import person.*;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class StudentDaoTxt extends Dao<Student, Integer> {
    private final RandomAccessFile raf;

    public StudentDaoTxt(String fullPath) throws DaoException {
        try {
            raf = new RandomAccessFile(new File(fullPath), "rws");
        } catch (IOException e) {
            Logger.getLogger(StudentDaoTxt.class.getName()).log(Level.SEVERE, null, e);
            throw new DaoException("Error to instance dao: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void create(Student alumno) throws DaoException {
        if (exist(alumno.getDni())) {
            throw new DaoException("Student with dni " + alumno.getDni() + " alredy exists");
        }
        try {
            raf.seek(raf.length());
            raf.writeBytes(alumno + System.lineSeparator());
        } catch (IOException e) {
            throw new DaoException("Error inserting: " + e.getLocalizedMessage());
        }
    }

    @Override
    public Student read(Integer id) throws DaoException {
        try {
            raf.seek(0);
            String line, idReaded;
            while ((line = raf.readLine()) != null) {
                idReaded = line.substring(0, 8);
                if (id.equals(NumberUtils.toInt(idReaded, 0))) {
                    String separador = Pattern.quote("\t");
                    List<String> studentRow = Arrays.asList(line.split(separador));
                    return parseStudent(studentRow);
                }
            }
        } catch (IOException | StudentException | PersonNameException | PersonDniException |
                 MyCalendarException e) {
            Logger.getLogger(StudentDaoTxt.class.getName()).log(Level.SEVERE, null, e);
            throw new DaoException("Error into read operation: " + e.getLocalizedMessage());
        }
        return new Student();
    }

    @Override
    public void update(Student student) throws DaoException {
        try {
            raf.seek(0);
            String line, idReaded;
            long pos = 0;
            while ((line = raf.readLine()) != null) {
                idReaded = line.substring(0, 8);
                if (student.getDni() == NumberUtils.toInt(idReaded, 0)) {
                    raf.seek(pos);
                    raf.writeBytes(student + System.lineSeparator());
                    return;
                }
                pos = raf.getFilePointer();
            }
        } catch (IOException e) {
            Logger.getLogger(StudentDaoTxt.class.getName()).log(Level.SEVERE, null, e);
            throw new DaoException("Error into update operation: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void delete(Integer dni) throws DaoException {
        Student student = read(dni);
        student.setActive(false);
        update(student);
    }

    @Override
    public List<Student> findAll(boolean onlyActive) throws DaoException {
        try {
            raf.seek(0);
            String line;
            List<Student> studentList = new ArrayList<>();
            while ((line = raf.readLine()) != null) {
                String separador = Pattern.quote("\t");
                List<String> studentRow = Arrays.asList(line.split(separador));
                if (onlyActive && "A".equals(studentRow.get(8))) {
                    studentList.add(parseStudent(studentRow));
                }else if(!onlyActive) {
                    studentList.add(parseStudent(studentRow));
                }
            }
            return studentList;
        } catch (IOException | StudentException | PersonNameException | PersonDniException |
                 MyCalendarException e) {
            Logger.getLogger(StudentDaoTxt.class.getName()).log(Level.SEVERE, null, e);
            throw new DaoException("Error into finAll operation: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void close() throws DaoException {
        try {
            raf.close();
        } catch (IOException e) {
            Logger.getLogger(StudentDaoTxt.class.getName()).log(Level.SEVERE, null, e);
            throw new DaoException("Error when it close the connection: " + e.getLocalizedMessage());
        }
    }

    @Override
    public boolean exist(Integer dni) throws DaoException {
        try {
            raf.seek(0);
            String line, idReaded;
            while ((line = raf.readLine()) != null) {
                idReaded = line.substring(0, 8);
                if (dni.equals(NumberUtils.toInt(idReaded, 0))) {
                    return true;
                }
            }
        } catch (IOException e) {
            Logger.getLogger(StudentDaoTxt.class.getName()).log(Level.SEVERE, null, e);
            throw new DaoException("Error to validate dni: " + e.getLocalizedMessage());
        }
        return false;
    }

    public Student parseStudent(List<String> studentRow) throws StudentException, PersonNameException, PersonDniException, MyCalendarException {
        Integer dni = Integer.valueOf(studentRow.get(0));
        String name = studentRow.get(1);
        String surname = studentRow.get(2);
        char gender = studentRow.get(3).charAt(0);
        MyCalendar birthday = parseStudentDates(studentRow.get(4));
        MyCalendar admissionDate = parseStudentDates(studentRow.get(5));
        Integer approvedSubjectQueantity = Integer.valueOf(studentRow.get(6));
        Double average = Double.valueOf(studentRow.get(7));
        Student student = new Student(admissionDate, approvedSubjectQueantity, average, gender, dni, name, surname, birthday);
        if ("B".equals(studentRow.get(8))) {
            student.setActive(false);
        }
        return student;
    }

    public MyCalendar parseStudentDates(String date) throws MyCalendarException {
        String separador = Pattern.quote("/");
        String[] birthdaySeparate = date.split(separador);
        int day = Integer.parseInt(birthdaySeparate[0]);
        int month = Integer.parseInt(birthdaySeparate[1]);
        int year = Integer.parseInt(birthdaySeparate[2]);
        return new MyCalendar(day, month, year);
    }
}
