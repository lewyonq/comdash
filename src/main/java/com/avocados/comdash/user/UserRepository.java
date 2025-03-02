package com.avocados.comdash.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avocados.comdash.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
}
