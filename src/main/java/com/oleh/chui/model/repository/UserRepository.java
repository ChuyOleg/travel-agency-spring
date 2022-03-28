package com.oleh.chui.model.repository;

import com.oleh.chui.model.entity.Role;
import com.oleh.chui.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    List<User> findAllByRoleValue(Role.RoleEnum roleEnum);

    boolean existsByUsername(String username);

}
