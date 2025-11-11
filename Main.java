import java.time.LocalDate;
import java.util.Scanner;
import src.Book.Book;
import src.FilesManager.*;
import src.Loans.*;
import src.Users.*;

public class Main {
    public static void main(String[] args) {
        Book book;
        String ISBN, ISBN_1, title, autor, imagePath;
        int maxStock;
        User user;
        String run, run_1, gender, career, professionalCareer, education;
        String[] name;
        Loan loan;
        int numberLoanDays;
        BookListFileManager.LoadBookList();
        UserListFileManager.LoadUserList();
        LoanListFileManager.LoadLoanList();
        int input = 0;
        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println("Bienvenido al menú. Elija la acción que quiera realizar: ");
            System.out.println("1. Crear usuario \n2. Buscar usuario \n3. Eliminar usuario \n4. Editar usuario \n5. Registrar Libro \n6. Buscar Libro \n7. Eliminar Libro \n8. Editar Libro \n9. Generar prestamo \n10. Generar devolución \n0. Cerrar menú");
            System.out.println("");
            input = Integer.parseInt(scanner.nextLine().toUpperCase());
            switch(input){
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    System.out.println("Ingrese el rut del usuario (Formato: 12345678-9): ");
                    run = scanner.nextLine().toUpperCase();
                    System.out.println("Ingrese nombre y apellido: ");
                    name = scanner.nextLine().toUpperCase().split(" ");
                    System.out.println("Ingrese el género del usuario (M: masculino, F: femenino): ");
                    gender = scanner.nextLine().toUpperCase();
                    System.out.println("Ingrese su carrera: ");
                    career = scanner.nextLine().toUpperCase();
                    System.out.println("Si el usuario es un docente, ingrese su carrera profesional. Si es estudiante, deje el campo en blanco:");
                    professionalCareer = scanner.nextLine().toUpperCase();
                    System.out.println("Si el usuario es un docente, ingrese su nivel de educación. Si es estudiante, deje el campo en blanco:");
                    education = scanner.nextLine().toUpperCase();

                    if(professionalCareer.equals("")){
                        user = new Student(run, name[0], name[1], gender, career);
                    }
                    else{
                        user = new Teacher(run, name[0], name[1], gender, career, professionalCareer, education);
                    }
                    
                    if(!user.RUNValidation() || !user.GenderValidation()){
                        break;
                    }
                    user.SaveUser();
                    System.out.println("Usuario creado correctamente");
                    break;
                case 2:
                    System.out.println("Ingrese el rut del usuario que desea buscar: ");
                    run = scanner.nextLine().toUpperCase();
                    user = UserListFileManager.SearchUser(run);
                    if(user == null) {break;}
                    user.PrintUserData();
                    break;
                case 3:
                    System.out.println("Ingrese rut del usuario que desea eliminar: ");
                    run = scanner.nextLine().toUpperCase();
                    if(run == null){
                        System.out.println("Usuario no existe.");
                        break;
                    }
                    UserListFileManager.RemoveUser(run);
                    System.out.println("Usuario eliminado correctamente");
                    break;
                case 4:
                    System.out.println("Ingrese el rut del usuario que desea modificar: ");
                    run = run_1 = scanner.nextLine().toUpperCase();
                    user = UserListFileManager.SearchUser(run);
                    if(user == null){ 
                        System.out.println("Usuario no existe.");
                        break;}
                    user.PrintUserData();
                    
                    System.out.println("Escriba el nuevo nombre (Formato: Juan) que desea ingresar(si no quiere modificar este campo, deje en blanco): ");
                    user.SetFirstName(scanner.nextLine().toUpperCase());
                    System.out.println("Escriba el nuevo apellido (Formato: Perez) que desea ingresar(si no quiere modificar este campo, deje en blanco): ");
                    user.SetLastName(scanner.nextLine().toUpperCase());
                    System.out.println("Indique M o F si desea cambiar el género del usuario(si no quiere modificar este campo, deje en blanco): ");
                    user.SetGender(scanner.nextLine().toUpperCase());
                    System.out.println("Escriba la nueva carrera que desea ingresar(si no quiere modificar este campo, deje en blanco): ");
                    user.SetCareer(scanner.nextLine().toUpperCase());
                    
                    if(user instanceof Teacher){
                        System.out.println("Escriba la nueva profesion que desea ingresar(si no quiere modificar este campo, deje en blanco): ");
                        user.SetProfessionalCareer(scanner.nextLine().toUpperCase());
                        System.out.println("Escriba el nuevo nivel educacional que desea ingresar(si no quiere modificar este campo, deje en blanco): ");
                        user.SetEducation(scanner.nextLine().toUpperCase());
                    }

                    System.out.println("Escriba el nuevo RUN (Formato: 12345678-9) que desea ingresar(si no quiere modificar este campo, deje en blanco): ");
                    user.SetRUN(scanner.nextLine().toUpperCase());
                    if(!user.GetRUN().equals(run_1)){
                        if(!user.RUNValidation()){
                            break;
                        }
                    }
                    else if(!user.GenderValidation()){
                        break;
                    }
                    
                    UserListFileManager.RemoveUser(run_1);
                    user.SaveUser();
                    System.out.println("Usuario modificado correctamente.");
                    break;
                case 5:
                    System.out.println("Ingrese el ISBN del libro: ");
                    ISBN = scanner.nextLine().toUpperCase();
                    System.out.println("Ingrese el título del libro: ");
                    title = scanner.nextLine().toUpperCase();
                    System.out.println("Ingrese el nombre del autor: ");
                    autor = scanner.nextLine().toUpperCase();
                    System.out.println("Ingrese el numero total de copias en biblioteca: ");
                    maxStock = Integer.parseInt(scanner.nextLine().toUpperCase());
                    System.out.println("Ingrese la ruta de la imagen de portada: ");
                    imagePath = scanner.nextLine().toUpperCase();
                    
                    book = new Book(ISBN,title,autor,maxStock,imagePath);
                    if(book.ISDNInArray() || !book.MaxStockValidation()){
                        break;
                    }
                    book.SaveBook();
                    System.out.println("Libro registrado correctamente");
                    break;
                case 6:
                    System.out.println("Ingrese el ISBN del libro que desea buscar: ");
                    ISBN = scanner.nextLine().toUpperCase();
                    book = BookListFileManager.SearchBook(ISBN);
                    if(book == null){break;}
                    book.PrintBookData();
                    break;
                case 7:
                    System.out.println("Ingrese el ISBN del libro que desea eliminar: ");
                    ISBN = scanner.nextLine().toUpperCase();
                    BookListFileManager.RemoveBook(ISBN);
                    System.out.println("Libro eliminado correctamente");
                    break;
                case 8:
                    System.out.println("Ingrese el ISBN del libro que desea modificar: ");
                    ISBN = ISBN_1 = scanner.nextLine().toUpperCase();
                    book = BookListFileManager.SearchBook(ISBN);
                    if(book==null){break;}
                    book.PrintBookData();
                    
                    System.out.println("Escriba el nuevo nombre que desea ingresar(si no quiere modificar este campo, deje en blanco): ");
                    book.SetTitle(scanner.nextLine().toUpperCase());
                    System.out.println("Escriba el nuevo autor(si no quiere modificar este campo, deje en blanco): ");
                    book.SetAutor(scanner.nextLine().toUpperCase());
                    System.out.println("Ingrese el nuevo número de copias que posee(si no quiere modificar este campo, deje en blanco): ");
                    book.SetMaxStock(scanner.nextLine().toUpperCase());
                    System.out.println("Escriba la nueva cantidad de copias disponibles, descontando las que ya tiene en prestamo(si no quiere modificar este campo, deje en blanco): ");
                    book.SetActualStock(scanner.nextLine().toUpperCase());

                    System.out.println("Escriba el nuevo ISBN que desea ingresar(si no quiere modificar este campo, deje en blanco): ");
                    book.SetISBN(scanner.nextLine().toUpperCase());

                    if(book.ISDNInArray() || !book.MaxStockValidation()){
                        break;
                    }

                    BookListFileManager.RemoveBook(ISBN_1);
                    book.SaveBook();
                    System.out.println("Libro editado correctamente");
                    break;
                case 9:
                    System.out.println("Indique el rut del usuario que solicita un libro: ");
                    run = scanner.nextLine().toUpperCase();
                    user = UserListFileManager.SearchUser(run);
                    if(!user.GetLoan().equals("0")){
                        System.out.println("El usuario ya tiene un prestamo en curso.");
                        break;
                    }
                    System.out.println("Ingrese el ISBN del libro que solicita: ");
                    ISBN = scanner.nextLine().toUpperCase();
                    book = BookListFileManager.SearchBook(ISBN);
                    if(book.GetActualStock() <= 0){
                        System.out.println("No quedan copias disponibles del libro.");
                        break;
                    }
                    System.out.println("Ingrese número de días que desea solicitar el libro: ");
                    numberLoanDays = Integer.parseInt(scanner.nextLine().toUpperCase());
                    if(numberLoanDays > user.GetMaxLoanDays()){
                        System.out.println("Días solicitados superan máximo de días para el usuario. Se agregará el máximo de días disponibles: " + user.GetMaxLoanDays() + " días");
                    }
                    loan = new Loan(ISBN, run, numberLoanDays);
                    loan.ValidateLoan();
                    book.LoanBook();
                    user.SetLoan(ISBN);
                    loan.PrintLoanTicket();

                    loan.SaveLoan();
                    System.out.println("Prestamo realizado correctamente.");
                    break;
                case 10:
                    System.out.println("Indique el ISBN del libro a devolver: ");
                    ISBN = scanner.nextLine().toUpperCase();
                    loan = LoanListFileManager.SearchLoan(ISBN);
                    if(loan == null){
                        System.out.println("Prestamo no encontrado");
                        break;
                    }
                    user = UserListFileManager.SearchUser(loan.GetRun());
                    book = BookListFileManager.SearchBook(ISBN);
                    if(!ISBN.equals(user.GetLoan())){
                        System.out.println("El ISBN del libro no corresponde al que indica el prestamo del usuario.");
                        break;
                    }
                    loan.PrintLoanTicket();
                    if(LocalDate.now().isAfter(loan.GetFinalLoanDate()) && !LocalDate.now().equals(loan.GetFinalLoanDate())){
                        loan.LateLoan(LocalDate.now());
                    }
                    
                    user.SetLoan("0");
                    book.ReturnBook();
                    System.out.println("Entrega realizada con fecha : " + LocalDate.now());
                    break;

                
                
                }        
        }   

    }
}
