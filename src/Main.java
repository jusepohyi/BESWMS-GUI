import LoginClass.*;


import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, FontFormatException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            System.out.println("Unable to find and load driver");
            System.exit(1);
        }

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        Font customFont = Font.createFont(Font.PLAIN, new File("Fonts\\Montserrat-Regular.otf")).deriveFont(20f);
        ge.registerFont(customFont);
        Font customFont1 = Font.createFont(Font.PLAIN, new File("Fonts\\Oswald-Regular.ttf")).deriveFont(20f);
        ge.registerFont(customFont1);

        new Login();

    }
}
