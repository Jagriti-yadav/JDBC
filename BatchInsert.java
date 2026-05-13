import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class BatchInsert {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/stud";
        String username = "root";
        String password = "your_password";

        String query =
        "INSERT INTO DOCUMENT(filename, content) VALUES(?, ?)";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con =
            DriverManager.getConnection(url, username, password);

            PreparedStatement psmt =
            con.prepareStatement(query);

            // Record 1
            psmt.setString(1, "moon.txt");
            psmt.setString(2, "Moon content");
            psmt.addBatch();

            // Record 2
            psmt.setString(1, "sun.txt");
            psmt.setString(2, "Sun content");
            psmt.addBatch();

            // Record 3
            psmt.setString(1, "earth.txt");
            psmt.setString(2, "Earth content");
            psmt.addBatch();

            // Execute all together
            psmt.executeBatch();

            System.out.println("Batch inserted successfully");

            psmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
