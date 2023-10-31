package de.elenil.repository;

import de.elenil.entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class UserRepository {
    private List<User> users = new ArrayList<>(Arrays.asList(
            new User(1L,"Dima","Tkachuk","dima@gmail.com","+38095344"),
            new User(2L,"Roma","Lob","dima@gmail.com","+095344"),
            new User(3L,"Misha","Romanov","romanov@gmail.com","+5344")));

    public List<User> getAll() {
        return users;
    }
}
