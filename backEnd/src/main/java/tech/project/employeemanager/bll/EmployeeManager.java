package tech.project.employeemanager.bll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.project.employeemanager.bo.Employee;
import tech.project.employeemanager.dal.EmployeeDAO;
import tech.project.employeemanager.exception.UserNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeManager {

  private final EmployeeDAO employeeDAO;

    @Autowired
    public EmployeeManager(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public Employee addEmployee(Employee employee){
        // UUID.randomUUID().toString() génère un identifiant unique
        employee.setEmployeeCode(UUID.randomUUID().toString());
        return employeeDAO.save(employee);
    }

    public List<Employee> findAllEmployees(){
        return  employeeDAO.findAll();
    }

    public Employee updateEmployee(Employee employee){
        return employeeDAO.save(employee);
    }

    public Employee findEmployeeById(Long id){
        return  employeeDAO.findEmployeeById(id)
                .orElseThrow(() -> new UserNotFoundException("l'utilisateur avec ce Id " + id + "n'est pas trouvé"));
    }

    public void deleteEmployee(Long id){
        employeeDAO.deleteEmployeeById(id);
    }
}
