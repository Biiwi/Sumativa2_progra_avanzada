package src.Users;

import src.FilesManager.UserListFileManager;
import src.Tools.Tools;

public abstract class User {
    private String run;
    private String firstName;
    private String lastName;
    private String gender;
    private String career;
    private String loan;

    public User(String run, String firstName, String lastName, String gender, String career, String loan) {
        this.run = run.toUpperCase();
        this.firstName = firstName.toUpperCase();
        this.lastName = lastName.toUpperCase();
        this.gender = gender.toUpperCase();
        this.career = career.toUpperCase();
        this.loan = loan.toUpperCase();
    }

    public User(String run, String firstName, String lastName, String gender, String career) {
        this(run, firstName, lastName, gender, career, "0");
    }

    public boolean RUNValidation() {
        String RUNStructure = "^\\d{8}-[0-9K]$";
        if (this.run.length() != 10 || !this.run.matches(RUNStructure)) {
            Tools.SendErrorMessage("Rut ingresado con formato incorrecto.");
            return false;
        } else if (RUNInArray()){
            Tools.SendErrorMessage("Rut ingresado ya existe en el listado");
            return false;
        }
        return true;
    }

    private boolean RUNInArray(){
        return UserListFileManager.MatchesRUNInList(this.run);
    }

    public boolean GenderValidation() {
        if (!(this.gender.equals("M")|| this.gender.equals("F"))) {
            Tools.SendErrorMessage("Género ingresado no corresponde a las opciones establecidas.");
            return false;
        }
        return true;
    }

    public String GetLoan() {
        return this.loan;
    }

    public void SetLoan(String idBook) {
        this.loan = idBook;
    }


    public boolean DeleteUser() {
        
        if (!UserListFileManager.RemoveUser(this.run)) {
            Tools.SendMessage("RUT ingresado no se encuentra en la lista.");
            return false;
        }
        Tools.SendMessage("Se ha eliminado al usuario RUT: " + this.run);
        return true;
    }

    public void SaveUser(){
        UserListFileManager.AddUsertToList(this);
    }

    public int GetMaxLoanDays() {
        return 0;
    };

    public String GetRUN(){
        return this.run;
    }

    public void SetRUN(String run){
        if(run.equals("")){return;}
        this.run = run;

    }

    public String GetFirstName(){
        return this.firstName;
    }

    public void SetFirstName(String name){
        if(name.equals("")){ return;}
        this.firstName = name;
    }

    public String GetLastName(){
        return this.lastName;
    }

    public void SetLastName(String name){
        if(name.equals("")){ return;}
        this.lastName = name;
    }

    public String GetGender(){
        return this.gender;
    }

    public void SetGender(String gender){
        if(gender.equals("")){ return;}
        this.gender = gender;
    }

    public String GetCareer(){
        return this.career;
    }

    public void SetCareer(String career){
        if(career.equals("")){ return;}
        this.career = career;
    }

    public String GetProfessionalCareer(){
        return null;
    }

    public void SetProfessionalCareer(String professionalCareer){
        return;
    }

    public String GetEducation(){
        return null;
    }

    public void SetEducation(String education){
        return;
    }

    public void PrintUserData(){
        if(this.loan.equals("0")){
            System.out.println("Usuario\nNombre: " + this.firstName + " " + this.lastName + "\nRUN: " + this.run + "\nGenero: " + this.gender + "\nCarrera: " + this.career + "\nPrestamo: Sin prestamo vigente");
        }
        else{
            System.out.println("Usuario\nNombre: " + this.firstName + " " + this.lastName + "\nRUN: " + this.run + "\nGenero: " + this.gender + "\nCarrera: " + this.career + "\nPrestamo: " + this.loan);
        }
        
    }
}
