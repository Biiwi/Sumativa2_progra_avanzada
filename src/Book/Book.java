package src.Book;
import src.FilesManager.BookListFileManager;
import src.Tools.Tools;

public class Book {
    private String ISBN;
    private String title;
    private String autor;
    private int maxStock;
    private int actualStock;
    private String imagePath;

    public Book(String ISBN, String title, String autor, int maxStock, int actualStock, String imagePath){
        this.ISBN = ISBN;
        this.title = title;
        this.autor = autor;
        this.maxStock = maxStock;
        this.actualStock = actualStock;
        this.imagePath = imagePath;
    }

    public Book(String ISBN, String title, String autor, int maxStock, String imagePath){
        this (ISBN, title, autor, maxStock, maxStock, imagePath);
    }

    public boolean ISDNInArray(){
        return BookListFileManager.MatchesISBNInList(this.ISBN);
    }

    public boolean MaxStockValidation(){
        if(this.maxStock <= 0 || !(this.actualStock <= this.maxStock && this.actualStock >= 0)){
            Tools.SendErrorMessage("El número de copias no es correcto.");
            return false;
        }
        return true;
    }

    public void LoanBook() {
        if(this.actualStock > 0){
            this.actualStock -= 1;
            return;
        }
        Tools.SendErrorMessage("No quedan copias de este libro disponibles.");
    }

    public void ReturnBook(){
        if(this.actualStock < maxStock){
            this.actualStock += 1;
            return;
        }
        Tools.SendErrorMessage("Ya se encuentran todas las copias de este libro en la biblioteca.");
        SaveBook();
    }

    public boolean DeleteBook(){
        if (!BookListFileManager.RemoveBook(this.ISBN)){
            Tools.SendMessage("Id del libro no se encuentra en el listado");
            return false;
        }
        Tools.SendMessage("Se ha eliminado el libro Id: " + this.ISBN);
        return true;
    }

    public void SaveBook(){
        BookListFileManager.AddBookToList(this);
    }

    public String GetISBN(){
        return this.ISBN;
    }

    public String GetTitle(){
        return this.title;
    }

    public String GetAutor(){
        return this.autor;
    }

    public int GetMaxStock(){
        return this.maxStock;
    }

    public int GetActualStock(){
        return this.actualStock;
    }

    public String GetImagePath(){
        return this.imagePath;
    }

    public void SetISBN(String n){
        if(n.equals("")){return;}
        this.ISBN = n;
    }

    public void SetTitle(String n){
        if(n.equals("")){return;}
        this.title = n;
    }

    public void SetAutor(String n){
        if(n.equals("")){return;}
        this.autor = n;
    }

    public void SetMaxStock(String n){
        if(n.equals("")){return;}
        this.maxStock = Integer.parseInt(n);
    }

    public void SetActualStock(String n){
        if(n.equals("")){return;}
        this.actualStock = Integer.parseInt(n) > this.maxStock ? Integer.parseInt(n): this.maxStock;
    }

    public void SetImagePath(String n){
        if(n.equals("")){return;}
        this.imagePath = n;
    }
    public void PrintBookData(){
        System.out.println("Libro\nNombre: " + this.title +"\nAutor: " + this.autor + "\nISBN: " + this.ISBN + "\nCantidad disponible: " + this.actualStock + " de " + this.maxStock);
    }
    
}
