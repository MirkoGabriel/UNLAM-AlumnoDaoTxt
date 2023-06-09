package org.example;

import person.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoSql extends Dao<Student, Integer> {
    private Connection conn;
    private PreparedStatement psInsert;
    private PreparedStatement psSelectAll;
    private PreparedStatement psSelect;
    private PreparedStatement psUpdate;
    private PreparedStatement psDelete;

    public StudentDaoSql(String url, String user, String pass) throws DaoException {
        try {
            conn = DriverManager.getConnection(url, user, pass);

            //CREATE
            String sqlInsert = "INSERT INTO students(dni, name, surname,gender,birthday,admission_date,approved_subject_quantity,average, active) VALUES(?,?,?,?,?,?,?,?,?)";
            psInsert = conn.prepareStatement(sqlInsert);

            //SELECT ALL
            String sqlSelectAll = "SELECT * FROM students";
            psSelectAll = conn.prepareStatement(sqlSelectAll);

            //SELECT
            String sqlSelect = "SELECT * FROM students WHERE dni = ?";
            psSelect = conn.prepareStatement(sqlSelect);

            //UPDATE
            String sqlUpdate = "UPDATE students SET name = ?, surname = ?, gender = ?, birthday = ?, admission_date = ?, approved_subject_quantity = ?, average = ?, active = ?  WHERE dni = ?";
            psUpdate = conn.prepareStatement(sqlUpdate);

            //DELETE
            String sqlDelete = "DELETE FROM students WHERE dni = ?";
            psDelete = conn.prepareStatement(sqlDelete);
        } catch (SQLException e) {
            throw new DaoException("Error to connect to database" + e.getLocalizedMessage());
        }
    }

    @Override
    public void create(Student student) throws DaoException {
        try {
            psInsert.setInt(1, student.getDni());
            psInsert.setString(2, student.getName());
            psInsert.setString(3, student.getSurname());
            psInsert.setString(4, String.valueOf(student.getGender()));
            psInsert.setDate(5, student.getBirthday().toSqlDate());
            psInsert.setDate(6, student.getAdmissionDate().toSqlDate());
            psInsert.setInt(7, student.getApprovedSubjectQuantity());
            psInsert.setDouble(8, student.getAverage());
            psInsert.setBoolean(9, student.isActive());
            psInsert.execute();
        } catch (SQLException e) {
            throw new DaoException(e.getLocalizedMessage());
        }
    }

    @Override
    public Student read(Integer dni) throws DaoException {
        try {
            Student student = null;
            psSelect.setInt(1, dni);
            ResultSet rs = psSelect.executeQuery();

            if (rs.next()) {
                student = getStudent(rs);
            }
            return student;
        } catch (SQLException | PersonDniException | PersonNameException | StudentException e) {
            throw new DaoException(e.getLocalizedMessage());
        }
    }

    private static Student getStudent(ResultSet rs) throws PersonDniException, SQLException, PersonNameException, StudentException {
        Student student;
        student = new Student();
        student.setDni(rs.getInt("dni"));
        student.setName(rs.getString("name"));
        student.setSurname(rs.getString("surname"));
        student.setBirthday(new MyCalendar(rs.getDate("birthday")));
        student.setAdmissionDate(new MyCalendar(rs.getDate("admission_date")));
        student.setGender(rs.getString("gender").charAt(0));
        student.setApprovedSubjectQuantity(rs.getInt("approved_subject_quantity"));
        student.setAverage(rs.getDouble("average"));
        student.setActive(rs.getBoolean("active"));
        return student;
    }

    @Override
    public void update(Student student) throws DaoException {
        try {
            psUpdate.setString(1, student.getName());
            psUpdate.setString(2, student.getSurname());
            psUpdate.setString(3, String.valueOf(student.getGender()));
            psUpdate.setDate(4, student.getBirthday().toSqlDate());
            psUpdate.setDate(5, student.getAdmissionDate().toSqlDate());
            psUpdate.setInt(6, student.getApprovedSubjectQuantity());
            psUpdate.setDouble(7, student.getAverage());
            psUpdate.setBoolean(8, student.isActive());
            psUpdate.setInt(9, student.getDni());
            psUpdate.execute();
        } catch (SQLException e) {
            throw new DaoException(e.getLocalizedMessage());
        }
    }

    @Override
    public void delete(Integer dni) throws DaoException {
        try {
            psDelete.setInt(1, dni);
            psDelete.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getLocalizedMessage());
        }
    }

    @Override
    public List<Student> findAll(boolean onlyActive) throws DaoException {
        try {
            List<Student> students = new ArrayList<>();
            Student student = new Student();
            ResultSet rs = psSelectAll.executeQuery();

            while (rs.next()) {
                if(onlyActive && rs.getBoolean("active") == true){
                    students.add(getStudent(rs));
                }else if(!onlyActive){
                    students.add(getStudent(rs));
                }
            }
            return students;
        } catch (SQLException | PersonDniException | PersonNameException | StudentException e) {
            throw new DaoException(e.getLocalizedMessage());
        }
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
