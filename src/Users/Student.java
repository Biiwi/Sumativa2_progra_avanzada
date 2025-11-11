package src.Users;

public class Student extends User {
    private int loanDays = 10;

    public Student(String run, String firstName, String lastName, String gender, String career, String loan){
        super(run, firstName, lastName, gender, career, loan);
    }

    public Student(String run, String firstName, String lastName, String gender, String career){
        this(run, firstName, lastName, gender, career, "0");
    }

    @Override
    public int GetMaxLoanDays(){
        return this.loanDays;
    }

    @Override
    public void PrintUserData(){
        if(this.GetLoan().equals("0")){
            System.out.println("Usuario-Estudiante\nNombre: " + this.GetFirstName() + " " + this.GetLastName() + "\nRUN: " + this.GetRUN() + "\nGenero: " + this.GetGender() + "\nCarrera: " + this.GetCareer() + "\nPrestamo: Sin prestamo vigente");
        }
        else{
            System.out.println("Usuario-Estudiante\nNombre: " + this.GetFirstName() + " " + this.GetLastName() + "\nRUN: " + this.GetRUN() + "\nGenero: " + this.GetGender() + "\nCarrera: " + this.GetCareer() + "\nPrestamo: " + this.GetLoan());
        }
        
    }
}
