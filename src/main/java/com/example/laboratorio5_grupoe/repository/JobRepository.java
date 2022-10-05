package com.example.laboratorio5_grupoe.repository;

import com.example.laboratorio5_grupoe.entity.Employee;
import com.example.laboratorio5_grupoe.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
//Completar
public interface JobRepository extends  JpaRepository<Job, String> {
}
