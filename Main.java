import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main{
  public static void main(String[] args){
    String url ="jdbc:mysql://localhost:3306/stud";
    String username ="root";
    String password ="My29Sql@p";
    String create_table = """
      CREATE TABLE IF NOT EXISTS details(
      id INT PRIMARY KEY AUTO_INCREMENT,
      name VARCHAR(50) NOT NULL,
      course VARCHAR(50),
      marks DECIMAL(10,2)
      )
      """;

    // String insert_data = """
    //     INSERT INTO details(id,name,course,marks)
    //     VALUES(1,"rahul","java",89),
    //     (2,"rahul","java",89),
    //     (3,"aman","java",89),
    //     (4,"radhika","java",89),
    //     (5,"ravi","java",89),
    //     (6,"samarth","java",89)
    //     """;
      // String insert_data = """
      //   INSERT INTO details(id,name,course,marks)
      //   VALUES(7,"riyan","dbms",90)
      //   """;

      String select_query = "SELECT * FROM details";

      String update_query = """
          UPDATE details set course = 'cnt' WHERE id = 1
          """;

      String delete_query = """
         DELETE FROM details WHERE id = 5 
          """;
    try{
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection con = DriverManager.getConnection(url,username,password);
      System.out.println("connection established");

      Statement smt = con.createStatement(
        ResultSet.TYPE_SCROLL_SENSITIVE,
        ResultSet.CONCUR_UPDATABLE
      );
      smt.executeUpdate(create_table);
      System.out.println("table created successfully");

      // smt.executeUpdate(insert_data);
      // System.out.println("data inserted successfully");
      // int rowAffected = smt.executeUpdate(insert_data);
      // if(rowAffected>0){
      //   System.out.println("record inserted");
      // }else{
      //   System.out.println("record not inserted");
      // }
      smt.executeUpdate(update_query);
      smt.executeUpdate("""
          UPDATE details SET course = "AI" WHERE id = 2
          """);
      smt.executeUpdate(delete_query);
      ResultSet rs = smt.executeQuery(select_query);
      System.out.println("id\tname\tcourse\tmarks");
      while(rs.next()){

        
          int marks = rs.getInt("marks");
          if(marks<90){
            rs.updateInt("marks",marks+1);
            rs.updateRow();
          }
        
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String course = rs.getString("course");
        marks = rs.getInt("marks");

        System.out.println(String.format("%d\t%s\t%s\t%d",id,name,course,marks));
      }

      con.close();
      smt.close();
      rs.close();
    }catch(ClassNotFoundException | SQLException e){
      System.out.println(e.getMessage());
    }
  }
}
