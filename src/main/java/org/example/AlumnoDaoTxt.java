package org.example;

import org.apache.commons.lang3.math.NumberUtils;
import persona.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class AlumnoDaoTxt extends Dao<Alumno, Integer> {
    private RandomAccessFile raf;
    private static final String HEADER_TEXT = "    DNI   |      NAME       |     SURNAME     | GENDER |  BIRTHDAY  |";
    private static final String HEADER_SEPARATOR = "----------|-----------------|-----------------|--------|------------|";
    private static final String ROW_SEPARATOR = "__________|_________________|_________________|________|____________|";

    public AlumnoDaoTxt(String fullPath) throws DaoException {
        try {
            raf = new RandomAccessFile(new File(fullPath), "rws");
            raf.writeBytes(HEADER_TEXT + System.lineSeparator());
            raf.writeBytes(HEADER_SEPARATOR + System.lineSeparator());
        } catch (IOException e) {
            Logger.getLogger(AlumnoDaoTxt.class.getName()).log(Level.SEVERE, null, e);
            throw new DaoException("Error al instanciar dao: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void create(Alumno alumno) throws DaoException {
        if (exist(alumno.getDni())) {
            close();
            throw new DaoException("El alumno con dni " + alumno.getDni() + " ya existe");
        }
        try {
            raf.seek(raf.length());
            raf.writeBytes(" " + alumno.toString() + System.lineSeparator());
            raf.writeBytes(ROW_SEPARATOR + System.lineSeparator());
            close();
        } catch (IOException e) {
            throw new DaoException("Error al insertar: " + e.getLocalizedMessage());
        }
    }

    @Override
    public Alumno read(Integer id) throws DaoException {
        try {
            raf.seek(0);
            String line, idReaded;
            while ((line = raf.readLine()) != null) {
                idReaded = line.substring(1, 9);
                if (id.equals(NumberUtils.toInt(idReaded, 0))) {
                    String separador = Pattern.quote(" | ");
                    List<String> studentRow = Arrays.asList(line.split(separador));
                    studentRow.replaceAll(String::trim);
                    return parseStudent(studentRow);
                }
            }
            close();
        } catch (IOException | AlumnoException | PersonaNombreException | PersonaDniException | MiCalendarioException e) {
            Logger.getLogger(AlumnoDaoTxt.class.getName()).log(Level.SEVERE, null, e);
            throw new DaoException("Error into read operation: " + e.getLocalizedMessage());
        }
        return new Alumno();
    }

    @Override
    public void update(Alumno student) throws DaoException {
        try {
            raf.seek(0);
            String line, idReaded;
            long pos = 0;
            while ((line = raf.readLine()) != null) {
                idReaded = line.substring(1, 9);
                if (student.getDni() == NumberUtils.toInt(idReaded, 0)) {
                    raf.seek(pos);
                    raf.writeBytes(" " + student.toString() + System.lineSeparator());
                    return;
                }
                pos = raf.getFilePointer();
            }
            close();
        } catch (IOException e) {
            Logger.getLogger(AlumnoDaoTxt.class.getName()).log(Level.SEVERE, null, e);
            throw new DaoException("Error into update operation: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void delete(Integer dni) throws DaoException {
        Alumno student = read(dni);
        student.setActivo(false);
        update(student);
    }

    @Override
    public List<Alumno> findAll(boolean onlyActive) throws DaoException {
        return null;
    }

    @Override
    public void close() throws DaoException {
        try {
            raf.close();
        } catch (IOException e) {
            Logger.getLogger(AlumnoDaoTxt.class.getName()).log(Level.SEVERE, null, e);
            throw new DaoException("Error when it close the connection: " + e.getLocalizedMessage());
        }
    }

    @Override
    public boolean exist(Integer dni) throws DaoException {
        try {
            //raf.seek(HEADER_TEXT.length() + HEADER_SEPARATOR.length() + 2);
            raf.seek(0);
            String line, idReaded;
            while ((line = raf.readLine()) != null) {
                idReaded = line.substring(1, 9);
                if (dni.equals(NumberUtils.toInt(idReaded, 0))) {
                    return true;
                }
            }
        } catch (IOException e) {
            Logger.getLogger(AlumnoDaoTxt.class.getName()).log(Level.SEVERE, null, e);
            throw new DaoException("Error to dni validation: " + e.getLocalizedMessage());
        }
        return false;
    }

    public Alumno parseStudent(List<String> studentRow) throws AlumnoException, PersonaNombreException, PersonaDniException, MiCalendarioException {
        char gender = studentRow.get(3).charAt(0);
        Integer dni = Integer.valueOf(studentRow.get(0));
        String name = studentRow.get(1);
        String surname = studentRow.get(2);
        MiCalendario birthday = parseStudentDates(studentRow.get(4));
        return new Alumno(null, 0, 0, gender, dni, name, surname, birthday);
    }

    public MiCalendario parseStudentDates(String date) throws MiCalendarioException {
        String separador = Pattern.quote("/");
        String[] birthdaySeparate = date.split(separador);
        int day = Integer.parseInt(birthdaySeparate[0]);
        int month = Integer.parseInt(birthdaySeparate[1]);
        int year = Integer.parseInt(birthdaySeparate[2]);
        return new MiCalendario(day, month, year);
    }
}
