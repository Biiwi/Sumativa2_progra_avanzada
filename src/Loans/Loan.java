package src.Loans;

import java.time.LocalDate;
import src.Book.Book;
import src.FilesManager.BookListFileManager;
import src.FilesManager.LoanListFileManager;
import src.FilesManager.UserListFileManager;
import src.Tools.Tools;
import src.Users.*;

public class Loan {
    private String ISBN;
    private String run;
    private LocalDate startLoan;
    private LocalDate finalLoan;
    private int numberLoanDays;
    private User user;
    private Book book;
    private int fineMountPerDay = 1000;

    public Loan(String ISBN, String run, int numberLoanDays, LocalDate startLoan, LocalDate finalLoan){
        this.ISBN = ISBN;
        this.run = run;
        this.startLoan = startLoan;
        this.finalLoan = finalLoan;
        this.numberLoanDays = numberLoanDays;
    }

    public Loan(String ISBN, String run, int numberLoanDays){
        this(ISBN, run, numberLoanDays, LocalDate.now(), LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth() + numberLoanDays));
    }

    public boolean ValidateLoan(){
        this.user = UserListFileManager.SearchUser(this.run);
        if(user == null){
            Tools.SendErrorMessage("Rut no corresponde a ningún usuario registrado.");
            return false;
        }
        this.book = BookListFileManager.SearchBook(this.ISBN);
        if(book == null){
            Tools.SendErrorMessage("Id de libro no corresponde a ningún libro registrado.");
            return false;
        }
        
        if (book.GetActualStock() <= 0){
            Tools.SendErrorMessage("Stock de libros incorrecto o insuficiente");
            return false;
        }

        if (!user.GetLoan().equals("0")){
            Tools.SendErrorMessage("Usuario no disponible para solicitar libros");
            return false;
        }
        if (numberLoanDays > user.GetMaxLoanDays()){
            Tools.SendErrorMessage("Días de solicitud supera los días máximos de prestamo para este usuario");
            return false;
        }
        return true;
    }

    public String GetISBN(){
        return this.ISBN;
    }

    public String GetRun(){
        return this.run;
    }

    public int GetNumberLoanDays(){
        return this.numberLoanDays;
    }

    public LocalDate GetStartLoanDate(){
        return this.startLoan;
    }

    public LocalDate GetFinalLoanDate(){
        return this.finalLoan;
    }
    
    public void SaveLoan(){
        LoanListFileManager.AddLoantToList(this);

    }

    public void LateLoan(LocalDate date){
        System.out.println("La entrega presenta atraso de :" + (date.getDayOfMonth() - this.finalLoan.getDayOfMonth()));
        CalculateFine((date.getDayOfMonth() - this.finalLoan.getDayOfMonth()));
    }

    private void CalculateFine(int lateDays){
        System.out.println("La multa por el atraso de " + lateDays + " días equivale a $" + lateDays*fineMountPerDay);
    }

    public boolean DeleteLoan(){
        if (!LoanListFileManager.RemoveLoan(this.ISBN)){
            Tools.SendMessage("Id del libro no se encuentra en el listado de prestamos");
            return false;
        }
        Tools.SendMessage("Se ha eliminado el prestamo del libro Id: " + this.ISBN);
        return true;
    }

    public void PrintLoanTicket(){
        book = BookListFileManager.SearchBook(this.ISBN);
        if(book== null){ System.out.println("error libro");
            return;}
        user = UserListFileManager.SearchUser(this.run);
        if(user== null){ System.out.println("error usuario");
            return;}
        System.out.println("__________________________________________________________");
        System.out.println("                    Ticket de prestamo                    ");
        System.out.println("                                                          ");
        System.out.println("    Libro : " + book.GetTitle() + "    Cod: " + this.ISBN      );
        System.out.println("  A nombre de:                                                   ");
        if(user instanceof Teacher){
            System.out.println("            " + user.GetFirstName() + " " + user.GetLastName() + ", "
                + user.GetProfessionalCareer() + " " + user.GetEducation());
            System.out.println("          docente de la carrera " + user.GetCareer());
        }
        else{
            System.out.println("            " + user.GetFirstName() + " " + user.GetLastName());
            System.out.println("          estudiante de la carrera " + user.GetCareer());
        }
        System.out.println("");
        System.out.println("Fecha de solicitud : " + this.startLoan);
        System.out.println("Días de prestamo : " + this.numberLoanDays);
        System.out.println("Fecha de entrega : " + this.finalLoan);
        System.out.println("");
        System.out.println("Se recuerda que la multa por día de atraso es de $1000. Favor entregar dentro del plazo establecido.");
        
        System.out.println("__________________________________________________________");
    }

     
}
