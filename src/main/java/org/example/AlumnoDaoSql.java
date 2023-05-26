package org.example;

import persona.Alumno;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlumnoDaoSql extends Dao <Alumno, Integer>{
    private RandomAccessFile raf;
    public AlumnoDaoSql(String fullPath) throws DaoException {

    }
    @Override
    public void create(Alumno entity) throws DaoException{

    }

    @Override
    public Alumno read(Integer id) throws DaoException{
        return null;
    }

    @Override
    public void update(Alumno entity) throws DaoException{

    }

    @Override
    public void delete(Integer id) throws DaoException{

    }

    @Override
    public List<Alumno> findAll(boolean onlyActive) throws DaoException{
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
