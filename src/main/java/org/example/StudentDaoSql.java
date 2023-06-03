package org.example;

import person.*;

import java.io.RandomAccessFile;
import java.sql.*;
import java.util.List;

public class StudentDaoSql extends Dao<Student, Integer> {
    private Connection conn;
    private PreparedStatement ps;

    public StudentDaoSql(String url, String user, String pass) throws DaoException {
        //jdbc:mysql://127.0.0.1:3306/universidad_caba
        try {
            conn = DriverManager.getConnection(url, user, pass);
            String sqlInsert = "INSERT INTO students(dni, name, surname,gender,birthday,admission_date,approved_subject_quantity,average) VALUES(?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sqlInsert);
        } catch (SQLException e) {
            throw new DaoException("Error to connect to database" + e.getLocalizedMessage());
        }
    }

    @Override
    public void create(Student student) throws DaoException {
        try {
            ps.setInt(1, student.getDni());
            ps.setString(2, student.getName());
            ps.setString(3, student.getSurname());
            ps.setString(4, String.valueOf(student.getGender()));
            ps.setDate(5, student.getBirthday().toSqlDate());
            ps.setDate(6, student.getAdmissionDate().toSqlDate());
            ps.setInt(7,student.getApprovedSubjectQuantity());
            ps.setDouble(8,student.getAverage());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Student read(Integer id) throws DaoException {
        return null;
    }

    @Override
    public void update(Student entity) throws DaoException {

    }

    @Override
    public void delete(Integer id) throws DaoException {

    }

    @Override
    public List<Student> findAll(boolean onlyActive) throws DaoException {
        return null;
    }

    @Override
    public void close() throws DaoException {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new DaoException("Error to close connection" + e.getLocalizedMessage());
        }
    }

    @Override
    public boolean exist(Integer id) throws DaoException {
        return false;
    }
}
