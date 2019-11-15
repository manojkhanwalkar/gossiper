package data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//TODO - for ease of testing duplicate names are assumed not to exist and id is hash of name.

public class User {

    final String id ;
    final String name ;

   /* List<User> followers = new ArrayList<>();
    List<User> follows = new ArrayList<>();
    List<Subject> followsSubject = new ArrayList<>();*/

    public String getId() {
        return id;
    }

    public User(String name)
    {
        this.name = name;
        this.id = generateId();
    }

    private String generateId()
    {
        return String.valueOf(name.hashCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
