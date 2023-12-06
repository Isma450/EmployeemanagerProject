import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Employee } from './employee';
import { EmployeeService } from './employee.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  public employees: Employee[] = [];
  public editEmployee: Employee | null = null;
  public deleteEmployee: Employee | null = null;

  constructor(private employeeService: EmployeeService) {}

  ngOnInit() {
    this.getEmployees();
  }

  //important  cette fonction permet de recuperer les employes avec la methode getEmployees() du service
  // details de la methode getEmployees() dans le fichier employee.service.ts
  // la methode subscribe() permet de recuperer la reponse de la requete http
  // la methode subscribe() prend deux parametres :
  /// le premier parametre est une fonction qui prend en parametre la reponse de la requete http
  /// le deuxieme parametre est une fonction qui prend en parametre l'erreur de la requete http
  // la methode alert() permet d'afficher un message d'erreur
  public getEmployees(): void {
    this.employeeService.getEmployees().subscribe(
      (response: Employee[]) => {
        this.employees = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  //important openModal() permet d'ouvrir le modal
  /// en cas d'ajout d'un employe, le modal d'ajout est ouvert
  /// en cas de modification d'un employe, le modal de modification est ouvert
  /// en cas de suppression d'un employe, le modal de suppression est ouvert

  public onOpenModal(employee: Employee | null, mode: string): void {
    const container = document.getElementById('main_container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');

    if (mode === 'add') {
      button.setAttribute('data-target', '#addEmployeeModal');
    }
    if (employee != null) {
      if (mode === 'edit') {
        this.editEmployee = employee;
        button.setAttribute('data-target', '#updateEmployeeModal');
      }

      if (mode === 'delete') {
        this.deleteEmployee = employee;
        this.employeeService.deleteEmployees(employee.id);
        button.setAttribute('data-target', '#deleteEmployeeModal');
      }
    }

    container?.appendChild(button);
    button.click();
  }

  //important cette fonction permet d'ajouter un employe
  // ouvrir le modal d'ajout et permet de ecrire les informations de l'employe et de l'ajouter
  public onAddEmployee(addForm: NgForm): void {
    document.getElementById('add-employee-form')?.click();
    this.employeeService.addEmployees(addForm.value).subscribe(
      (response: Employee) => {
        console.log(response);
        this.getEmployees();
        addForm.reset();
        document.getElementById('closeAddEmployeeModal')?.click();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
      }
    );
  }

  //important cette fonction permet de modifier un employe
  // ouvrir le modal de modification et permet de modifier les informations de l'employe et de le modifier
  public onUpdateEmloyee(employee: Employee): void {
    document.getElementById('update-employee-form')?.click();
    this.employeeService.updateEmployees(employee).subscribe(
      (response: Employee) => {
        console.log(response);
        this.getEmployees();
        document.getElementById('closeUpdateEmployeeModal')?.click();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  //important cette fonction permet de supprimer un employe
  // ouvrir le modal de suppression et permet de supprimer les informations de l'employe et de le supprimer
  public onDeleteEmloyee(employeeId: number): void {
    document.getElementById('delete-employee-form')?.click();
    this.employeeService.deleteEmployees(employeeId).subscribe(
      (response: void) => {
        console.log(response);
        this.getEmployees();
        document.getElementById('closeDeleteEmployeeModal')?.click();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  //important cette fonction permet de rechercher un employe
  // la methode filter() permet de filtrer les employes
  // la methode toLowerCase() permet de mettre en minuscule les employes
  // la methode includes() permet de rechercher les employes
  // la methode indexOf() permet de rechercher les employes

  public searchEmployees(key: string): void {
    console.log(key);
    const results: Employee[] = [];
    for (const employee of this.employees) {
      if (
        employee.name.toLowerCase().indexOf(key.toLowerCase()) !== -1 ||
        employee.email.toLowerCase().indexOf(key.toLowerCase()) !== -1 ||
        employee.jobTitle.toLowerCase().indexOf(key.toLowerCase()) !== -1 ||
        employee.phoneNumber.toLowerCase().indexOf(key.toLowerCase()) !== -1
      ) {
        results.push(employee);
      }
    }
    this.employees = results;
    if (results.length === 0 || !key) {
      this.getEmployees();
    }
  }
}
