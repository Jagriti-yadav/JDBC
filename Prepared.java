import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class Prepared {
  public static void main(String[] args) {
    String url = "jdbc:mysql://localhost:3306/stud";
    String username = "root";
    String password = "My29Sql@p";

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection con = DriverManager.getConnection(url, username, password);



      // =================================================
      // 3. CREATE TABLE
      // =================================================
      String createQuery = """
            CREATE TABLE IF NOT EXISTS employee(
            id INT PRIMARY KEY AUTO_INCREMENT,
            name VARCHAR(50) NOT NULL,
            salary DECIMAL(10,2)
            )
          """;
      PreparedStatement pstCreate = con.prepareStatement(createQuery);
      pstCreate.execute();




      // =================================================
      // 4. INSERTION
      // =================================================
      String insertQuery = "INSERT INTO employee VALUES(?,?,?)";
      PreparedStatement pstInsert = con.prepareStatement(insertQuery);
      
        pstInsert.setInt(1, 101);
        pstInsert.setString(2, "Alice");
        pstInsert.setDouble(3, 5000);
        pstInsert.executeUpdate();

        pstInsert.setInt(1,102);
        pstInsert.setString(2,"Bob");
        pstInsert.setDouble(3,6000);
        pstInsert.executeUpdate();

        pstInsert.setInt(1,103);
        pstInsert.setString(2,"Clob");
        pstInsert.setDouble(3,6500);
        pstInsert.executeUpdate();

        pstInsert.setInt(1,104);
        pstInsert.setString(2,"Anony");
        pstInsert.setDouble(3,5600);
        pstInsert.executeUpdate();



      // =================================================
      // 5. UPDATION
      // =================================================
      String updateQuery = "UPDATE employee SET salary = ? WHERE id = ?";
      PreparedStatement pstUpdate = con.prepareStatement(updateQuery);
      pstUpdate.setDouble(1,6500);
      pstUpdate.setInt(2,101);
      pstUpdate.executeUpdate();


      // =================================================
      // 6. DELETION
      // =================================================
      String deleteQuery = "DELETE FROM employee WHERE id = ?";
      PreparedStatement pstDelete = con.prepareStatement(deleteQuery);
      pstDelete.setInt(1,104);
      pstDelete.executeUpdate();


      // =================================================
      // 7. FETCH
      // =================================================
      String selectQuery = "SELECT * FROM employee";
      PreparedStatement pstSelect = con.prepareStatement(selectQuery);
      ResultSet rs = pstSelect.executeQuery();
      System.out.println("employee table data");
      while(rs.next()){
        System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getDouble(3)+" ");
      }
      

    } catch (ClassNotFoundException | SQLException e) {
      System.out.println(e.getMessage());
    }
  }
}
