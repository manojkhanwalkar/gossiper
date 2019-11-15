package data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Subject {

    final String id ;
    final String name ;

    List<User> followers = new ArrayList<>();

    public Subject(String name)
    {
        this.name = name;
        this.id = generateId();
    }



    private String generateId()
    {
        return String.valueOf(name.hashCode());
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return id.equals(subject.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
