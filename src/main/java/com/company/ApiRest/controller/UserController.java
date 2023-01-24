package com.company.ApiRest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.ApiRest.model.User;
import com.company.ApiRest.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/user")
	public List<User> listAll() {
		return userService.findAll();
	}

	@PostMapping("/user")
	public ResponseEntity<?> create(@RequestBody User user) {
		User userCustom = null;
		Map<String, Object> response = new HashMap<>();

		try {
			userCustom = userService.save(user);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la inserccion en la base de datos");
			response.put("error", e);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El usuario se ha creado con exito");
		response.put("usuario", userCustom);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		User userCustom = null;
		Map<String, Object> response = new HashMap<>();

		try {
			userCustom = userService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al buscar al usuario");
			response.put("error", e);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (userCustom == null) {
			response.put("mensaje", "El cliente con Id".concat(" ").concat("No existe"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<User>(userCustom, HttpStatus.OK);
	}

	@PutMapping("/user/{id}")
	public ResponseEntity<?> update(@RequestBody User user, @PathVariable Long id) {
		User userCurrent = userService.findById(id);
		User userUpdate = null;
		Map<String, Object> response = new HashMap<>();

		if (userCurrent == null) {
			response.put("mensaje", "El usuario con Id: ".concat(" ").concat("No existe"));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			userCurrent.setNombre(user.getNombre());
			userCurrent.setApellido(user.getApellido());
			userUpdate = userService.save(userCurrent);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar en la base de datos");
			response.put("error", e);
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El usario ha sido actualizado con exito");
		response.put("usuario", userUpdate);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("user/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		Map<String,Object> response = new HashMap<>();
		try {
			userService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar al usuario");
			response.put("error", e);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El usuario ha sido eliminado exitosamente");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}

}
