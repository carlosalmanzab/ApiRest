package com.company.ApiRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.ApiRest.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
