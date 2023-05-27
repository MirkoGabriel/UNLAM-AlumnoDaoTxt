package org.example;

import person.*;

import java.io.RandomAccessFile;
import java.util.List;

public class StudentDaoSql extends Dao <Student, Integer>{
    private RandomAccessFile raf;
    public StudentDaoSql(String fullPath) throws DaoException {

    }
    @Override
    public void create(Student entity) throws DaoException{

    }

    @Override
    public Student read(Integer id) throws DaoException{
        return null;
    }

    @Override
    public void update(Student entity) throws DaoException{

    }

    @Override
    public void delete(Integer id) throws DaoException{

    }

    @Override
    public List<Student> findAll(boolean onlyActive) throws DaoException{
        return null;
    }

    @Override
    public void close() throws DaoException{

    }

    @Override
    public boolean exist(Integer id) throws DaoException {
        return false;
    }
}
