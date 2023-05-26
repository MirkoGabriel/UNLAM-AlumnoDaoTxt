package org.example;

import java.util.List;

public abstract class Dao <T, K> {
    public abstract void create (T entity) throws DaoException;
    public abstract T read (K id) throws DaoException;
    public abstract void update (T entity) throws DaoException;
    public abstract void delete (K id) throws DaoException;
    public abstract List<T> findAll(boolean onlyActive) throws DaoException;
    public abstract void close() throws DaoException;
    public abstract boolean exist(K id) throws DaoException;
}
