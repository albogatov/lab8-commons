package commons.utils;

import commons.elements.Status;
import commons.elements.Worker;

import java.util.HashSet;
import java.util.List;

/**
 * Интерфейс для классов, управляющих взаимодействием с коллекцией.
 */
public interface InteractionInterface {

    String info();

    String show();

    void add(Worker worker);

    void update(long id, Worker worker);

    void removeById(long id);

    void clear();

    void save();

    void addIfMin(Worker worker);

    List<Long> removeGreater(Worker worker);

    List<Long> removeLower(Worker worker);

    long countByStatus(Status status);

    String printAscending();

    List<String> printUniqueOrganization();

    int getSize();

    boolean findById(long key);

    String returnSeparator();

    void addAll(HashSet<Worker> collection);

    Storage getStorage();
}
