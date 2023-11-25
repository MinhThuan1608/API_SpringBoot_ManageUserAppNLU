package com.fit.usernluappsbapi.reponsitory;

import com.fit.usernluappsbapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);
    List<User> findAllByRoleNot(User.Role role);

    @Override
    List<User> findAll();

    @Override
    boolean existsById(String id);


}
