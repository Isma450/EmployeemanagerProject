package tech.project.employeemanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.project.employeemanager.bll.EmployeeManager;
import tech.project.employeemanager.bo.Employee;

import java.util.List;

/**
 *** METHODS
 * {@code @getAllEmployees()} : retourne la liste de tous les employés de la base de données
 * ResponseEntity<List<Employee>> : ResponseEntity est une classe générique qui nous permet de retourner des réponses HTTP personnalisées
 * HttpStatus.OK : retourne un code de statut 200
 * {@code @getAllEmployeeById()} : retourne un employé spécifique en function de l'ID passé dans l'URL
 * {@code @addEmployee()} : ajoute un nouvel employé à la base de données
 * {@code @updateEmployee()} : met à jour un employé existant dans la base de données
 * {@code @deleteEmployee()} : supprime un employé existant de la base de données
 **** ANNOTATIONS
 * {@code @RestController} : est une annotation qui combine @Controller et @ResponseBody
 * {@code @RequestMapping} : est une annotation qui permet de mapper les requêtes HTTP aux méthodes de gestion
 * {@code @GetMapping} : est une annotation qui permet de mapper les requêtes HTTP GET aux méthodes de gestion
 * {@code @PostMapping} : est une annotation qui permet de mapper les requêtes HTTP POST aux méthodes de gestion
 * {@code @PutMapping} : est une annotation qui permet de mapper les requêtes HTTP PUT aux méthodes de gestion
 * {@code @DeleteMapping} : est une annotation qui permet de mapper les requêtes HTTP DELETE aux méthodes de gestion
 * {@code @RequestBody} : est une annotation qui permet de mapper le corps de la requête HTTP à un objet Java
 * {@code @PathVariable} : est une annotation qui permet de mapper les variables de chemin de la requête HTTP à des paramètres de méthode
 * {@code @Autowired} : est une annotation qui permet d'injecter des dépendances dans les beans gérés par Spring
 */


@RestController
@RequestMapping("/employee")
public class EmployeeResource {

    private final EmployeeManager employeeManager;

    @Autowired
    public EmployeeResource(EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;
    }

    // GET
    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        List<Employee> employees = employeeManager.findAllEmployees();
        return new ResponseEntity<>(employees , HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Employee> getAllEmployeeById(@PathVariable("id") Long id){
        Employee employee= employeeManager.findEmployeeById(id);
        return new ResponseEntity<>(employee , HttpStatus.OK);
    }

    // POST
    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
        Employee newEmployee = employeeManager.addEmployee(employee);
        return new ResponseEntity<>(newEmployee , HttpStatus.CREATED);
    }

    // UPDATE
    @PutMapping ("/update")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee){
        Employee updateEmployee = employeeManager.updateEmployee(employee);
        return new ResponseEntity<>(updateEmployee , HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id){
            employeeManager.deleteEmployee(id);
            return  new ResponseEntity<>(HttpStatus.OK );
    }


}
