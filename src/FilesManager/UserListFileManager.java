package src.FilesManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import src.Tools.Tools;
import src.Users.*;

public class UserListFileManager {
    private static ArrayList<User> userList = new ArrayList<>();
    private static String userListFilePath = "src/Files/userList.txt";

    UserListFileManager(){};

    public static void LoadUserList(){
        try (BufferedReader br = new BufferedReader(new FileReader(userListFilePath))){
            String row;
            
            while ((row = br.readLine()) != null){
                User us = PackagingUser(row);
                if (us != null){ 
                    userList.add(us);
                }
            }
            Tools.SendMessage("Listado de usuarios cargado exitosamente");
        }
        catch(Exception e){
            
        }
    }

    public static void SaveUserList(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(userListFilePath))){
            for(User us : userList){
                bw.write(PackagingRowList(us));
                bw.newLine();
            }
            LoadUserList();
            Tools.SendMessage("Guardado exitoso del listado usuarios");
        } catch(Exception e){

        }
    }
    private static User PackagingUser(String row) throws Exception{
        String[] data = row.split(",");
        if (!(data.length > 5 && data.length <= 8)){
            throw new Exception("Error en listado. Número de campos incorrecto");
        }

        if (data.length == 6){
            return new Student(data[0], data[1], data[2], data[3], data[4], data[5]);
        }
        else{
            return new Teacher(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]);
        }
    }

    private static String PackagingRowList(User user) throws Exception{
        if(user == null){
            throw new Exception("Error en objeto.");
        }
        String userRow;
        if (user instanceof Teacher){
            userRow = user.GetRUN() + "," + user.GetFirstName() + "," + user.GetLastName() + "," + user.GetGender() + "," + 
                    user.GetCareer() + "," + user.GetLoan() + "," + user.GetProfessionalCareer() + "," + user.GetEducation();
            return userRow;
        }
        userRow = user.GetRUN() + "," + user.GetFirstName() + "," + user.GetLastName() + "," + user.GetGender() + "," + 
                    user.GetCareer() + "," + user.GetLoan();
            return userRow;
        
        
        
    }

    public static boolean MatchesRUNInList(String run){
        for(User us: userList){
            if(us.GetRUN().equals(run)){
                return true;
            }
        }
        return false;
    } 

    public static boolean RemoveUser(String run){
        for(int i = userList.size() -1; i >= 0; i--){
            User us = userList.get(i);
            if(us.GetRUN().equals(run)){
                userList.remove(i);
                SaveUserList();
                return true;
            }
        }
        SaveUserList();
        return false;
    }
    
    public static User SearchUser(String run){
        for (int i = userList.size() - 1; i >= 0; i--){
            User us = userList.get(i);
            if(us.GetRUN().equals(run)){
                return us;
            }
        }
        return null;
    }

    public static void AddUsertToList(User user){
        if(SearchUser(user.GetRUN()) == null){
            userList.add(user);
            SaveUserList();
            return;
        }
        RemoveUser(user.GetRUN());
        userList.add(user);
        SaveUserList();
    }
}


/// ESTRUCTURA DE GUARDADO 
/// RUN, NOMBRE, APELLIDO, GENERO, CARRERA, PRESTAMO, PROFESION, GRADO