package com.company.ApiRest.service;

import java.util.List;

import com.company.ApiRest.model.User;

public interface UserService {

	List<User> findAll();
	User findById(Long id);
	User save(User user);
	void delete(Long id);
}
