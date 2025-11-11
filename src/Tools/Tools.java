package src.Tools;

public class Tools {
    private Tools(){}

    public static void SendErrorMessage(String errMessage){
        if(errMessage != ""){
            System.out.println("*********************************");
            System.out.println("ERROR: " + errMessage);
            System.out.println("*********************************");
        }
    }

    public static void SendMessage(String Message){
        if(Message != ""){
            System.out.println("*********************************");
            System.out.println(Message);
            System.out.println("*********************************");
        }
    }
}
