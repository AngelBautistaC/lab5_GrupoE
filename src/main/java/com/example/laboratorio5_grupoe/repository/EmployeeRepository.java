package com.example.laboratorio5_grupoe.repository;

import com.example.laboratorio5_grupoe.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

//Completar
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {




    @Transactional
    @Query(value = """
            select * from employees e
            inner join jobs j
            on (e.job_id=j.job_id)
            inner join departments dep
            on (e.department_id=dep.department_id)
            inner join locations loc
            on (dep.location_id=loc.location_id)
            where LOWER(first_name) like LOWER(concat('%',?1,'%')) OR LOWER(last_name) like LOWER(concat('%',?1,'%')) OR LOWER(j.job_title) LIKE LOWER (concat('%',?1,'%')) OR LOWER(dep.department_name) LIKE LOWER(concat('%',?1,'%')) OR LOWER(loc.city) like LOWER(concat('%',?1,'%'))\s""",nativeQuery = true)
    List<Employee> buscarEmpleado(String searchField);





}
