import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TypeSensitive {
  public static void main(String[] args) {
    String url = "jdbc:mysql://localhost:3306/stud";
    String username = "root";
    String password = "My29Sql@p";
    String create_query = """
        CREATE TABLE IF NOT EXISTS course(
        course_id INT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(50) NOT NULL,
        price DECIMAL(10,2)
        )
        """;
    // String insert_query = """
    //     INSERT INTO course(name,price) VALUES
    //     ("A",3000),
    //     ("B",4000),
    //     ("C",3500),
    //     ("D",4600),
    //     ("E",5000)
    //     """;
    String select_query = "SELECT * FROM course";

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection con = DriverManager.getConnection(url, username, password);
      Statement smt = con.createStatement(
          ResultSet.TYPE_SCROLL_SENSITIVE,
          ResultSet.CONCUR_READ_ONLY);
      smt.executeUpdate(create_query);
      // smt.executeUpdate(insert_query);
      ResultSet rs = smt.executeQuery(select_query);

      // next()
      System.out.println("next()->forward movement");
      while (rs.next()) {
        System.out.println(rs.getInt(1) + " " + rs.getString(2) + " "
            + rs.getLong(3));
      }
      System.out.println();


      // previous()
      System.out.println("previous()-> backward movement");
      while (rs.previous()) {
        System.out.println(rs.getInt(1) + " " + rs.getString(2) + " "
            + rs.getLong(3));
      }
      System.out.println();
      

      // first()
      System.out.println("first()->cursor at first row");
      rs.first();
      System.out.println(rs.getInt(1) + " "
          + rs.getString(2) + " "
          + rs.getLong(3));
      System.out.println();
      

      // last()
      System.out.println("last()->cursor at last row");
      rs.last();
      System.out.println(rs.getInt(1) + " "
          + rs.getString(2) + " "
          + rs.getLong(3));
      System.out.println();
      System.out.println();

      // absolute()
      System.out.println("absolute->cursor at specified row");
      rs.absolute(5);
      System.out.println(rs.getInt(1) + " "
          + rs.getString(2) + " "
          + rs.getLong(3));
      System.out.println();
      


      System.out.println("after updation");
      String update_query = "UPDATE course SET name = 'l' WHERE course_id = 5";
      Statement updatesmt = con.createStatement();
      updatesmt.executeUpdate(update_query);

      ResultSet rss = smt.executeQuery(select_query);

      rss.absolute(5);
      System.out.println(rss.getInt(1) + " "
          + rss.getString(2) + " "
          + rss.getLong(3));
      System.out.println();


    } catch (ClassNotFoundException | SQLException e) {
      System.out.println(e.getMessage());
    }
  }
}
