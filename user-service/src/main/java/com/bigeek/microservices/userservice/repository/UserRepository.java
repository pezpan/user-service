package com.bigeek.microservices.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bigeek.microservices.userservice.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

}
