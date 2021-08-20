package com.nagarro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.nagarro.entities.Employees;

public interface UserRepository extends JpaRepository<Employees, Integer> {
	
	@Query("select e from Employees e where e.email = :email")
	public Employees getUserByUserName(@Param("email") String email);

}
