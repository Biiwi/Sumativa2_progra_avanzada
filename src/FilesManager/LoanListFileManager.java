package src.FilesManager;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import src.Loans.Loan;
import src.Tools.Tools;

public class LoanListFileManager {
    private static ArrayList<Loan> loanList = new ArrayList<>();
    private static String loanListFilePath = "src/Files/loanList.txt";

    public static void LoadLoanList(){
        try (BufferedReader br = new BufferedReader(new FileReader(loanListFilePath))){
            String row;
            
            while ((row = br.readLine()) != null){
                Loan ln = PackagingLoan(row);
                if (ln != null){ 
                    loanList.add(ln);
                }
            }
            Tools.SendMessage("Listado de prestamos cargado exitosamente");
        }
        catch(Exception e){
            
        }
    }

    public static void SaveLoanList(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(loanListFilePath))){
            for(Loan ln : loanList){
                bw.write(PackagingRowList(ln));
                bw.newLine();
            }
            LoadLoanList();
            Tools.SendMessage("Guardado exitoso del listado usuarios");
        } catch(Exception e){

        }
    }
    private static Loan PackagingLoan(String row) throws Exception{
        String[] data = row.split(",");
        if (!(data.length == 6)){
            throw new Exception("Error en listado. Número de campos incorrecto");
        }

        return new Loan(data[0], data[1], Integer.parseInt(data[2]), LocalDate.parse(data[3]), LocalDate.parse(data[4]));
    }

    private static String PackagingRowList(Loan loan) throws Exception{
        if(loan == null){
            throw new Exception("Error en objeto.");
        }
        return loan.GetISBN() + "," + loan.GetRun() + "," + loan.GetNumberLoanDays() + "," +
                loan.GetStartLoanDate() + "," + loan.GetFinalLoanDate();
    }


    public static boolean RemoveLoan(String ISBN){
        for(int i = loanList.size() -1; i >= 0; i--){
            Loan ln = loanList.get(i);
            if(ln.GetISBN().equals(ISBN)){
                loanList.remove(i);
                SaveLoanList();
                return true;
            }
        }
        return false;
    }
    
    public static Loan SearchLoan(String ISBN){
        for (int i = loanList.size() - 1; i >= 0; i--){
            Loan ln = loanList.get(i);
            if(ln.GetISBN().equals(ISBN)){
                return ln;
            }
        }
        return null;
    }
    

    public static void AddLoantToList(Loan loan){
        if(SearchLoan(loan.GetRun()) == null){
            loanList.add(loan);
            SaveLoanList();
            return;
        }
        RemoveLoan(loan.GetRun());
        loanList.add(loan);
        SaveLoanList();
    }
}
