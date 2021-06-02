package commons.utils;

import commons.app.User;
import commons.elements.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Locale;

public class DataBaseCenter {
    private final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private String user = "postgres";
    //    private final String URL = "jdbc:postgresql://pg:5432/studs";
//    private final String user = "s312418";
    private String password = "";

    public DataBaseCenter() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean addUser(User newUser) {
        try (Connection connection = DriverManager.getConnection(URL, user, password)) {
//            String query = "INSERT INTO users VALUES ('" + newUser.getLogin() + "','" + newUser.getPassword() + "')";
            PreparedStatement preparedStatement = connection.prepareStatement(QueryConstants.USER_INSERTION);
            preparedStatement.setString(1, newUser.getLogin());
            preparedStatement.setString(2, newUser.getPassword());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean loginUser(User loggingUser) {
        try (Connection connection = DriverManager.getConnection(URL, user, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                if (resultSet.getString("username").equals(loggingUser.getLogin()) && resultSet.getString("password").equals(loggingUser.getPassword())) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addWorker(Worker worker, User loggedUser) {
        try (Connection connection = DriverManager.getConnection(URL, user, password)) {
            worker.setCreationDate(ZonedDateTime.now());
//            String query = "INSERT INTO worker (name, x, y, salary, enddate, creationdate, position, status, organizationname, " +
//                    "orgtype, annualturnover, street, postalcode, \"username\") VALUES ('" + worker.getName() + "'," + worker.getCoordinateX() +
//                    "," + worker.getCoordinateY() + "," + worker.getSalary() + "," + worker.getEndDateString() + ",'" +
//                    worker.getCreationDate() + "'," + worker.getPositionString() + "," + worker.getStatusString() + "," +
//                    worker.getOrganizationNameString() + "," + worker.getOrganizationTypeString() + "," + worker.getAnnualTurnover() +
//                    "," + worker.getAddressStreet() + "," + worker.getAddressZipCode() + ",'" + loggedUser.getLogin() + "');";
            PreparedStatement preparedStatement = connection.prepareStatement(QueryConstants.ELEMENT_INSERTION, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, worker.getName());
            preparedStatement.setInt(2, worker.getCoordinateX());
            preparedStatement.setLong(3, worker.getCoordinateY());
            preparedStatement.setInt(4, worker.getSalary());
            if (worker.getEndDate() == null)
                preparedStatement.setNull(5, Types.DATE);
            else preparedStatement.setDate(5, Date.valueOf(worker.getEndDate()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(worker.getCreationDate().toLocalDateTime()));
            if (worker.getPosition() == null)
                preparedStatement.setNull(7, Types.VARCHAR);
            else preparedStatement.setString(7, worker.getPositionString());
            if (worker.getStatus() == null)
                preparedStatement.setNull(8, Types.VARCHAR);
            else preparedStatement.setString(8, worker.getStatusString());
            if (worker.getOrganizationName() == null)
                preparedStatement.setNull(9, Types.VARCHAR);
            else preparedStatement.setString(9, worker.getOrganizationNameString());
            if (worker.getOrganizationType() == null)
                preparedStatement.setNull(10, Types.VARCHAR);
            else preparedStatement.setString(10, worker.getOrganizationTypeString());
            if (worker.getAnnualTurnover() == null)
                preparedStatement.setNull(11, Types.BIGINT);
            else preparedStatement.setLong(11, Long.parseLong(worker.getAnnualTurnover()));
            preparedStatement.setString(12, worker.getAddressStreet());
            preparedStatement.setString(13, worker.getAddressZipCode());
            preparedStatement.setString(14, loggedUser.getLogin());
            preparedStatement.execute();
            ResultSet generated = preparedStatement.getGeneratedKeys();
            if (generated.next())
                worker.setId(generated.getLong(1));
            else throw new SQLException();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateWorker(Worker worker, long id, User loggedUser) {
        try (Connection connection = DriverManager.getConnection(URL, user, password)) {
//            String query = "SELECT creationdate FROM worker WHERE id = " + id;
            PreparedStatement statement = connection.prepareStatement("SELECT creationdate FROM worker WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            ZonedDateTime creationDate = null;
            while (resultSet.next()) {
                creationDate = ZonedDateTime.parse(String.valueOf(resultSet.getTimestamp(1)), DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.SSSSSS", Locale.ENGLISH)
                        .withZone(ZoneOffset.UTC));
            }
//            String queryUpdate = "UPDATE worker SET name = '" + worker.getName() + "', x = " + worker.getCoordinateX() + ", y ="
//                    + worker.getCoordinateY() + ", salary = " + worker.getSalary() + ", enddate = " + worker.getEndDateString() + ", position = " + worker.getPositionString() + ", status = " + worker.getStatusString() +
//                    ", organizationname = " + worker.getOrganizationNameString() + ", orgtype = " + worker.getOrganizationTypeString() + ", annualturnover = " +
//                    worker.getAnnualTurnover() + ", street = " + worker.getAddressStreet() + ", postalcode = " + worker.getAddressZipCode() + " WHERE id = "
//                    + id + " AND username = '" + loggedUser.getLogin() + "';";
            worker.setCreationDate(creationDate);
            PreparedStatement preparedStatement = connection.prepareStatement(QueryConstants.ELEMENT_UPDATE);
            preparedStatement.setString(1, worker.getName());
            preparedStatement.setInt(2, worker.getCoordinateX());
            preparedStatement.setLong(3, worker.getCoordinateY());
            preparedStatement.setInt(4, worker.getSalary());
            if (worker.getEndDate() == null)
                preparedStatement.setNull(5, Types.DATE);
            else preparedStatement.setDate(5, Date.valueOf(worker.getEndDate()));
            if (worker.getPosition() == null)
                preparedStatement.setNull(6, Types.VARCHAR);
            else preparedStatement.setString(6, worker.getPositionString());
            if (worker.getStatus() == null)
                preparedStatement.setNull(7, Types.VARCHAR);
            else preparedStatement.setString(7, worker.getStatusString());
            if (worker.getOrganizationName() == null)
                preparedStatement.setNull(8, Types.VARCHAR);
            else preparedStatement.setString(8, worker.getOrganizationNameString());
            if (worker.getOrganizationType() == null)
                preparedStatement.setNull(9, Types.VARCHAR);
            else preparedStatement.setString(9, worker.getOrganizationTypeString());
            if (worker.getAnnualTurnover() == null)
                preparedStatement.setNull(10, Types.BIGINT);
            else preparedStatement.setLong(10, Long.parseLong(worker.getAnnualTurnover()));
            preparedStatement.setString(11, worker.getAddressStreet());
            preparedStatement.setString(12, worker.getAddressZipCode());
            preparedStatement.setLong(13, id);
            preparedStatement.setString(14, loggedUser.getLogin());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeWorker(long id, User loggedUser) {
        try (Connection connection = DriverManager.getConnection(URL, user, password)) {
//            String query = "SELECT * FROM worker WHERE id = " + id + ";";
            PreparedStatement statement = connection.prepareStatement(QueryConstants.ELEMENT_SELECT);
            statement.setString(1, "*");
            statement.setLong(2, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (!resultSet.getString("username").equals(loggedUser.getLogin()))
                    return false;
            }
//            String query2 = "DELETE FROM worker WHERE id = " + id +
//                    " AND username = '" + loggedUser.getLogin() + "';";
            PreparedStatement preparedStatement = connection.prepareStatement(QueryConstants.ELEMENT_DELETE);
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, "'" + loggedUser.getLogin() + "'");
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void retrieveCollectionFromDB(InteractionInterface interaction) {
        HashSet<Worker> collection = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(URL, user, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM worker");
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                if (!interaction.findById(id)) {
                    interaction.getStorage().getIdList().add(id);
                }
                String name = resultSet.getString(2);
                Coordinates coordinates = new Coordinates(resultSet.getInt(3), resultSet.getLong(4));
                Integer salary = resultSet.getInt(5);
                LocalDate endDate;
                if (!(resultSet.getString(6) == null))
                    endDate = LocalDate.parse(String.valueOf(resultSet.getDate(6)));
                else endDate = null;
                ZonedDateTime creationDate = ZonedDateTime.parse(String.valueOf(resultSet.getTimestamp(7)), DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.SSSSSS", Locale.ENGLISH)
                        .withZone(ZoneOffset.UTC));
                Position position = null;
                if (!(resultSet.getString(8) == null))
                    position = Position.valueOf(resultSet.getString(8));
                Status status = null;
                if (!(resultSet.getString(9) == null))
                    status = Status.valueOf(resultSet.getString(9));
                String organizationName = "";
                if (!(resultSet.getString(10) == null))
                    organizationName = resultSet.getString(10);
                OrganizationType orgType = null;
                if (!(resultSet.getString(11) == null))
                    orgType = OrganizationType.valueOf(resultSet.getString(11));
                Long annualTurnover = null;
                if (!(resultSet.getString(12) == null))
                    annualTurnover = resultSet.getLong(12);
                Address address = new Address(resultSet.getString(13), resultSet.getString(14));
                Organization organization = new Organization(annualTurnover, orgType, address, organizationName);
                Worker worker = new Worker(id, name, coordinates, creationDate, salary, endDate, position, status, organization);
                collection.add(worker);
            }
            interaction.clear();
            interaction.addAll(collection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean clearCollection(User loggedUser) {
        try (Connection connection = DriverManager.getConnection(URL, user, password)) {
            String query = "SELECT * FROM worker";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
//                String deletionQuery = "DELETE FROM worker WHERE id = " + id + " AND username = '" + loggedUser.getLogin() + "';";
                PreparedStatement deletion = connection.prepareStatement(QueryConstants.ELEMENT_DELETE);
                deletion.setLong(1, id);
                deletion.setString(2, "'" + loggedUser.getLogin() + "'");
                deletion.execute();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createTable() {
        try (Connection connection = DriverManager.getConnection(URL, user, password)) {
//            String query1 = "CREATE SEQUENCE IF NOT EXISTS workerid START 1;";
//            String query2 = "CREATE TABLE IF NOT EXISTS worker " +
//                    "(id BIGINT NOT NULL UNIQUE DEFAULT nextval('workerid'), name VARCHAR (50) NOT NULL , x INT NOT NULL CHECK (x <= 627), y BIGINT NOT NULL CHECK (y <= 990), " +
//                    "salary INT NOT NULL CHECK (salary >= 0), enddate DATE, creationdate TIMESTAMP NOT NULL, position VARCHAR(50)," +
//                    "status VARCHAR(50), organizationname VARCHAR(50), orgtype VARCHAR(50), annualturnover INT CHECK (annualturnover >= 0)," +
//                    "street VARCHAR (50), postalcode VARCHAR(50), username VARCHAR(50));";
//            String query3 = "CREATE TABLE IF NOT EXISTS users" + "(username VARCHAR(50) UNIQUE, password VARCHAR(100));";
            PreparedStatement statement = connection.prepareStatement(QueryConstants.ID_GENERATING_SEQUENCE);
            statement.execute();
            statement = connection.prepareStatement(QueryConstants.TABLE);
            statement.execute();
            statement = connection.prepareStatement(QueryConstants.USER_TABLE);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setPassword(String pwd) {
        password = pwd;
    }

}
