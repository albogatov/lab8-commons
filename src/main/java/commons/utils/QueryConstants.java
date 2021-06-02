package commons.utils;

public class QueryConstants {
    public final static String USER_INSERTION = "INSERT INTO users VALUES (?,?)";

    public final static String ELEMENT_INSERTION = "INSERT INTO worker (name, x, y, salary, enddate, creationdate, position, status, organizationname, " +
            "orgtype, annualturnover, street, postalcode, username) VALUES ( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?);";

    public final static String ELEMENT_UPDATE = "UPDATE worker SET name = ? , x = ? , y = ? , salary = ? , enddate = ? , position = ? , status = ? , " +
            "organizationname = ? , orgtype = ? , annualturnover = ? , street = ? , postalcode = ? WHERE id = ? AND username = ?;";

    public final static String ELEMENT_SELECT = "SELECT ? FROM worker WHERE id = ?;";

    public final static String ELEMENT_DELETE = "DELETE FROM worker WHERE id = ? AND username = ?;";

    public final static String ID_GENERATING_SEQUENCE = "CREATE SEQUENCE IF NOT EXISTS workerid START 1;";

    public final static String TABLE = "CREATE TABLE IF NOT EXISTS worker (id BIGINT NOT NULL UNIQUE DEFAULT nextval('workerid'), name VARCHAR (50) NOT NULL , " +
            "x INT NOT NULL CHECK (x <= 627), y BIGINT NOT NULL CHECK (y <= 990), salary INT NOT NULL CHECK (salary >= 0), enddate DATE, creationdate TIMESTAMP NOT NULL, " +
            "position VARCHAR(50), status VARCHAR(50), organizationname VARCHAR(50), orgtype VARCHAR(50), annualturnover INT CHECK (annualturnover >= 0), street VARCHAR (50), " +
            "postalcode VARCHAR(50), username VARCHAR(50));";

    public final static String USER_TABLE = "CREATE TABLE IF NOT EXISTS users (username VARCHAR(50) UNIQUE, password VARCHAR(100));";
}
