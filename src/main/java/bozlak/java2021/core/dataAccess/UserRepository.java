package bozlak.java2021.core.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import bozlak.java2021.core.entities.User;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);
}