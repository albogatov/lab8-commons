package commons.elements;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

/**
 * Класс Worker.
 */
public class Worker implements Comparable<Worker>, Serializable {
    /**
     * Идентификатор работника. Значение поля больше 0, Значение этого поля уникально, Значение этого поля генерируется автоматически.
     */
    private long id;
    /**
     * Имя работника. Поле не может быть null, Строка не может быть пустой.
     */
    private final String name;
    /**
     * Координаты работника. Поле не может быть null.
     */
    private final Coordinates coordinates;
    /**
     * Дата добавления работника в базу. Поле не может быть null. Значение этого поля генерируется автоматически.
     */
    private ZonedDateTime creationDate;
    /**
     * Оклад работника. Поле не может быть null, Значение поля должно быть больше 0.
     */
    private final Integer salary;
    /**
     * Дата расторжения контракта. Может быть null.
     */
    private final LocalDate endDate;
    /**
     * Должность сотрудника. Может быть null.
     */
    private final Position position;
    /**
     * Статус сотрудника. Может быть null.
     */
    private final Status status;
    /**
     * Организация сотрудника. Может быть null.
     */
    private final Organization organization; //Поле может быть null

    /**
     * Конструктор без ID.
     *
     * @param name         имя рабочего.
     * @param coordinates  координаты.
     * @param creationDate дата добавления в базу.
     * @param salary       оклад.
     * @param endDate      дата расторжения контракта.
     * @param position     должность.
     * @param status       статус.
     * @param organization организация.
     */
    public Worker(String name, Coordinates coordinates, ZonedDateTime creationDate, Integer salary, LocalDate endDate, Position position, Status status, Organization organization) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.salary = salary;
        this.endDate = endDate;
        this.position = position;
        this.status = status;
        this.organization = organization;
    }

    /**
     * Конструктор c ID.
     *
     * @param name         имя рабочего.
     * @param coordinates  координаты.
     * @param creationDate дата добавления в базу.
     * @param salary       оклад.
     * @param endDate      дата расторжения контракта.
     * @param position     должность.
     * @param status       статус.
     * @param organization организация.
     */
    public Worker(long id, String name, Coordinates coordinates, ZonedDateTime creationDate, Integer salary, LocalDate endDate, Position position, Status status, Organization organization) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.salary = salary;
        this.endDate = endDate;
        this.position = position;
        this.status = status;
        this.organization = organization;
    }

    /**
     * Конструктор без Creation Date
     *
     * @param name         имя рабочего.
     * @param coordinates  координаты.
     * @param salary       оклад.
     * @param endDate      дата расторжения контракта.
     * @param position     должность.
     * @param status       статус.
     * @param organization организация.
     */
    public Worker(String name, Coordinates coordinates, Integer salary, LocalDate endDate, Position position, Status status, Organization organization) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.salary = salary;
        this.endDate = endDate;
        this.position = position;
        this.status = status;
        this.organization = organization;
        this.creationDate = null;
    }

    /**
     * Метод, возвращающий имя рабочего.
     *
     * @return имя.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Метод, возвращающий координаты рабочего.
     *
     * @return координаты.
     */
    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    /**
     * Метод, возвращающий координату X рабочего.
     *
     * @return X.
     */
    public int getCoordinateX() {
        return coordinates.getX();
    }

    /**
     * Метод, возвращающий координату Y рабочего.
     *
     * @return Y.
     */
    public long getCoordinateY() {
        return coordinates.getY();
    }

    public long getCoordinatesValue() {
        return this.getCoordinateX() * this.getCoordinateY();
    }

    /**
     * Метод, возвращающий дату добавления рабочего в базу в строковом виде.
     *
     * @return дата добавления.
     */
    public String getCreationDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss z");
        return creationDate.format(formatter);
    }

    public ZonedDateTime getCreationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
//        return String.valueOf(ZonedDateTime.parse(creationDate.format(formatter), DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S", Locale.ENGLISH)));
        return creationDate;
    }

    /**
     * Метод, возвращающий оклад сотрудника.
     *
     * @return оклад.
     */
    public Integer getSalary() {
        return this.salary;
    }

    /**
     * Метод, возвращающий дату окончания действия контракта в строковом виде.
     *
     * @return дата окончания действия контракта.
     */
    public String getEndDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (this.endDate == null)
            return "null";
        return this.endDate.format(formatter);
    }

    /**
     * Метод, возвращающий дату окончания действия контракта.
     *
     * @return дата окончания действия контракта.
     */
    public String getEndDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (this.endDate == null)
            return null;
        return this.endDate.format(formatter);
    }

    /**
     * Метод, возвращающий должность рабочего.
     *
     * @return должность.
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * Метод, возвращающий статус рабочего.
     *
     * @return статус.
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * Метод, возвращающий должность рабочего в строковом виде.
     *
     * @return должность.
     */
    public String getPositionString() {
        if (this.position == null)
            return "null";
        else return this.position.toString();
    }

    /**
     * Метод, возвращаюший статус рабочего в строковом виде.
     *
     * @return статус.
     */
    public String getStatusString() {
        if (getStatus() == null)
            return "null";
        else return this.status.toString();
    }

    /**
     * Метод, возвращающий организацию рабочего.
     *
     * @return организация.
     */
    public Organization getOrganization() {
        return this.organization;
    }

    /**
     * Метод, возвращающий название организации сотрудника.
     *
     * @return название организации.
     */
    public String getOrganizationName() {
        if (organization == null || organization.getOrganizationName() == null)
            return null;
        else return this.organization.getOrganizationName();
    }

    /**
     * Метод, возвращающий название организации сотрудника и пустую строку, если орзанизация сотрудника не указана.
     *
     * @return название организации.
     */
    public String getOrganizationNameString() {
        if (organization == null || organization.getOrganizationName() == null)
            return "null";
        else return this.organization.getOrganizationName();
    }

    /**
     * Метод, возвращаюший годовую выручку организации сотрудника.
     *
     * @return годовая выручка.
     */
    public String getAnnualTurnover() {
        if (organization == null || organization.getAnnualTurnover() == null)
            return null;
        else return String.valueOf(organization.getAnnualTurnover());
    }

    /**
     * Метод, возвращаюший годовую выручку организации сотрудника и пустую строку, если она не указана.
     *
     * @return годовая выручка.
     */
    public String getAnnualTurnoverString() {
        if (organization == null || organization.getAnnualTurnover() == null)
            return "null";
        else return String.valueOf(organization.getAnnualTurnover());
    }

    /**
     * Метод, возвращающий тип организации рабочего.
     *
     * @return тип организации.
     */
    public String getOrganizationType() {
        if (organization == null || organization.getOrganizationType() == null)
            return null;
        else return organization.getOrganizationType().toString();
    }

    /**
     * Метод, возвращающий тип организации рабочего и пустую строку, если она не указана.
     *
     * @return тип организации.
     */
    public String getOrganizationTypeString() {
        if (organization == null || organization.getOrganizationType() == null)
            return "null";
        else return organization.getOrganizationType().toString();
    }

    /**
     * Метод, возвращающий адрес организации рабочего.
     *
     * @return адрес.
     */
    public String getPostalAddress() {
        if (organization == null || organization.getPostalAddress() == null)
            return null;
        else return organization.getPostalAddress().toString();
    }

    /**
     * Метод, возвращающий улицу, на которой расположена организация сотрудника.
     *
     * @return улица.
     */
    public String getAddressStreet() {
        if (organization == null || organization.getPostalAddress() == null)
            return "null";
        else return organization.getPostalAddress().getStreet();
    }

    /**
     * Метод, возвращающий индекс адреса, по которому расположена организация сотрудника.
     *
     * @return улица.
     */
    public String getAddressZipCode() {
        if (organization == null || organization.getPostalAddress() == null)
            return "null";
        else return organization.getPostalAddress().getZipCode();
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Метод, задающий ID рабочего.
     *
     * @param id идентификатор.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Метод, возвращающий ID рабочего.
     *
     * @return ID рабочего.
     */
    public long getId() {
        return this.id;
    }

    /**
     * Метод, выводящий пользователю все данные о рабочем.
     */
    public String displayWorker() {
        return "------------------------------" + "\n" +
                "ID рабочего - " + this.getId() + "\n" +
                "Имя рабочего - " + this.getName() + "\n" +
                "Координаты рабочего - " + this.getCoordinates().toString() + "\n" +
                "Добавлен в базу - " + this.getCreationDateString() + "\n" +
                "Зарплата рабочего - " + this.getSalary().toString() + "\n" +
                "Контракт истекает - " + this.getEndDate() + "\n" +
                "Должность - " + this.getPosition() + "\n" +
                "Статус - " + this.getStatus() + "\n" +
                "Организация - " + this.getOrganizationName() + "\n" +
                "Тип организации - " + this.getOrganizationType() + "\n" +
                "Годовая выручка организации - " + this.getAnnualTurnover() + "\n" +
                "Адрес организации - " + this.getPostalAddress() + "\n";
    }

    /**
     * Метод сравнения объектов класса по умолчанию.
     *
     * @param comparedWorker второй объект сравнения.
     * @return результат сравения.
     */
    @Override
    public int compareTo(Worker comparedWorker) {
        return this.getSalary() - comparedWorker.getSalary();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass())
            return false;
        if (this == obj)
            return true;
        Worker other = (Worker) obj;
        return Objects.equals(name, other.getName()) && Objects.equals(coordinates, other.getCoordinates())
                && Objects.equals(salary, other.getSalary()) && Objects.equals(this.getEndDate(), other.getEndDate())
                && (position == other.getPosition()) && (status == other.getStatus())
                && Objects.equals(organization, other.getOrganization());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name) + getCoordinateX() + (int) getCoordinateY() + salary + Objects.hashCode(endDate)
                + Objects.hashCode(status) + Objects.hashCode(position) + Objects.hashCode(organization);
    }
}