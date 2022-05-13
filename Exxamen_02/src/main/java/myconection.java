import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class myconection {
    //Cambie el tipo de conexión para hacerlo de una mejor manera
    public static Connection connection(){
        try {
            Connection myConn = null;
            String dbUrl = "jdbc:mysql://localhost:3306/login?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String user = "root";
            String pass = "Uriel19@";
            myConn = DriverManager.getConnection(dbUrl, user, pass);
            if (myConn != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }
            return myConn;
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
