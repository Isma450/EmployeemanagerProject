package tech.project.employeemanager.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;
import tech.project.employeemanager.bo.Employee;


import java.util.Optional;

/**
 * **** METHODS
 * {@code @deleteEmployeeById()} : supprime un employé existant de la base de données
 * {@code @findEmployeeById()} : retourne un employé spécifique en function de l'ID passé dans l'URL
 **** ANNOTATIONS
 * {@code @Query} : est une annotation qui permet d'écrire des requêtes personnalisées
 * {@code @Modifying} : est une annotation qui permet de définir une requête de modification
 * {@code @Transactional} : est une annotation qui permet de définir une transaction
 * {@code @Param} : est une annotation qui permet de mapper les paramètres de la requête à des paramètres de méthode
 */

public interface EmployeeDAO extends JpaRepository<Employee , Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Employee e WHERE e.id = :id")
    void deleteEmployeeById(@Param("id") Long id);


    @Query("SELECT e FROM Employee e WHERE e.id =:id")
    Optional<Employee> findEmployeeById(@Param("id") Long id);
}
