package LoginClass;

public class UserLoginInfo extends Login{
    private static String username;
    private static String password;

    public UserLoginInfo(String u, String p){
        username = u;
        password = p;
        dispose();
    }

    public static String getUsername(){
        return username;
    }

    public static String getPassword(){
        return password;
    }
}
