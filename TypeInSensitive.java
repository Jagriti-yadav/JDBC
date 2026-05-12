import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;

public class TypeInSensitive {
  public static void main(String[] args){
    String url = "jdbc:mysql://localhost:3306/stud";
    String username = "root";
    String password = "My29Sql@p";

    try{
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection con = DriverManager.getConnection(url,username,password);

      Statement smt = con.createStatement(
        ResultSet.TYPE_SCROLL_INSENSITIVE,
        ResultSet.CONCUR_UPDATABLE
      );

      
      ResultSet rs = smt.executeQuery("SELECT * FROM course");
      System.out.println("before updation");
      rs.first();
      System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" ");
      
      // Update database value
      Statement updateStmt = con.createStatement();
      updateStmt.executeUpdate(
        """
            UPDATE course SET name = "K" WHERE course_id = 1
            """
      );
      System.out.println("Database Updated!");

      System.out.println("after updation");
      rs.first();
      System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3));
      

    }catch(ClassNotFoundException | SQLException e){
      System.out.println(e.getMessage());
    }
  }
}
