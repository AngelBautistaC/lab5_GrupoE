package com.example.laboratorio5_grupoe.repository;

import com.example.laboratorio5_grupoe.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Completar
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
