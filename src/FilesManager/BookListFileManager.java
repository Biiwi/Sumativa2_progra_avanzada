package src.FilesManager;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import src.Book.Book;
import src.Tools.Tools;

public class BookListFileManager {
    private static ArrayList<Book> bookList = new ArrayList<>();
    private static String bookListFilePath = "src/Files/bookList.txt";

    public static void LoadBookList(){
        try (BufferedReader br = new BufferedReader(new FileReader(bookListFilePath))){
            String row;
            
            while ((row = br.readLine()) != null){
                Book bk = PackagingBook(row);
                if (bk != null){ 
                    bookList.add(bk);
                }
            }
            Tools.SendMessage("Listado de libros cargado exitosamente");
        }
        catch(Exception e){
            
        }
    }

    public static void SaveBookList(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(bookListFilePath))){
            for(Book bk : bookList){
                bw.write(PackagingRowList(bk));
                bw.newLine();
            }
            LoadBookList();
            Tools.SendMessage("Guardado exitoso del listado usuarios");
        } catch(Exception e){

        }
    }
    private static Book PackagingBook(String row) throws Exception{
        String[] data = row.split(",");
        if (!(data.length == 6)){
            throw new Exception("Error en listado. Número de campos incorrecto");
        }

        return new Book(data[0], data[1], data[2], Integer.parseInt(data[3]), Integer.parseInt(data[4]), data[5]);
    }

    private static String PackagingRowList(Book book) throws Exception{
        if(book == null){
            throw new Exception("Error en objeto.");
        }
        return book.GetISBN() + "," + book.GetTitle() + "," + book.GetAutor() + "," +
                book.GetMaxStock() + "," + book.GetActualStock() + "," + book.GetImagePath();
    }

    public static boolean MatchesISBNInList(String bookId){
        for(Book bk: bookList){
            if(bk.GetISBN().equals(bookId)){
                return true;
            }
        }
        return false;
    } 

    public static boolean RemoveBook(String bookId){
        for(int i = bookList.size() -1; i >= 0; i--){
            Book bk = bookList.get(i);
            if(bk.GetISBN().equals(bookId)){
                bookList.remove(i);
                SaveBookList();
                return true;
            }
        }
        SaveBookList();
        return false;
    }
    
    public static Book SearchBook(String id){
        for (int i = bookList.size() - 1; i >= 0; i--){
            Book bk = bookList.get(i);
            if(bk.GetISBN().equals(id)){
                return bk;
            }
        }
        return null;
    }

    public static void AddBookToList(Book book){
        if(SearchBook(book.GetISBN()) == null){
            bookList.add(book);
            SaveBookList();
            return;
        }
        RemoveBook(book.GetISBN());
        bookList.add(book);
        SaveBookList();
        }

    public static int NumberBookInList(){
        return bookList.size();
    }
}
