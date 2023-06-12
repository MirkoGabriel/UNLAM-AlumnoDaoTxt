package org.example;

import java.util.Map;

public class DaoFactory {
    private static DaoFactory instance;
    public static final String TYPE_DAO = "TYPE_DAO";
    public static final String DAO_TXT = "DAO_TXT";
    public static final String DAO_PATH_TXT = "DAO_PATH_TXT";
    public static final String DAO_SQL = "DAO_SQL";
    public static final String SQL_URL = "SQL_URL";
    public static final String SQL_USER = "SQL_USER";
    public static final String SQL_PASS = "SQL_PASS";

    private DaoFactory() {

    }

    public static DaoFactory getInstance() {
        if (instance == null) {
            instance = new DaoFactory();
        }
        return instance;
    }

    public Dao crearDao(Map<String, String> config) throws DaoFactoryException {
        String tipoDao = config.get(TYPE_DAO);
        try {
            switch (tipoDao) {
                case DAO_TXT:
                    return new StudentDaoTxt(config.get(DAO_PATH_TXT));
                case DAO_SQL:
                    return new StudentDaoSql(config.get(SQL_URL), config.get(SQL_USER), config.get(SQL_PASS));
                default:
                    throw new DaoFactoryException("No implements yet");
            }
        } catch (DaoException e) {
            throw new DaoFactoryException("Error to creatdao " + e.getLocalizedMessage());
        }
    }
}
