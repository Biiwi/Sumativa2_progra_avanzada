package src.Users;

public class Teacher extends User {
    
    private String professionalCareer;
    private String education;
    private int loanDays = 20;

    public Teacher(String run, String firstName, String lastName, String gender, String career, String loan, String education, String professionalCareer){
        super(run, firstName, lastName, gender, career, loan);
        this.professionalCareer = professionalCareer;
        this.education = education;
    }


    public Teacher(String run, String firstName, String lastName, String gender, String career, String education, String professionalCareer){
        this(run, firstName, lastName, gender, career, "0", education, professionalCareer);
    }

    @Override
    public int GetMaxLoanDays(){
        return this.loanDays;
    }

    @Override
    public String GetProfessionalCareer(){
        return this.professionalCareer;
    }

    @Override
    public String GetEducation(){
        return this.education;
    }

    @Override

    public void SetProfessionalCareer(String professionalCareer){
        if(professionalCareer.equals("")) {return;}
        this.professionalCareer = professionalCareer;
    }

    @Override
    public void SetEducation(String education){
        if(education.equals("")){return;}
        this.education = education;
    }

    @Override
    public void PrintUserData(){
        if(this.GetLoan().equals("0")){
            System.out.println("Usuario-Docente\nNombre: " + this.GetFirstName() + " " + this.GetLastName() + "\nRUN: " + this.GetRUN() + "\nGenero: " + this.GetGender() + "\nCarrera: " + this.GetCareer() + ", "+ this.GetProfessionalCareer() + ", " + this.GetEducation() + "\nPrestamo: Sin prestamo vigente");
        }
        else{
            System.out.println("Usuario-Docente\nNombre: " + this.GetFirstName() + " " + this.GetLastName() + "\nRUN: " + this.GetRUN() + "\nGenero: " + this.GetGender() + "\nCarrera: " + this.GetCareer() + ", "+ this.GetProfessionalCareer() + ", " + this.GetEducation() + "\nPrestamo: " + this.GetLoan());
        }
        
    }
}
