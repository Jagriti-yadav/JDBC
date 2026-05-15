import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;

public class TransactionEx {
  public static void main(String[] args){

    String url = "jdbc:mysql://localhost:3306/stud";
    String username = "root";
    String password = "My29Sql@p";
    String select_query = "SELECT * FROM salaryDetails";
    String debit_query = "UPDATE salaryDetails SET salary = salary - ? WHERE id = ? ";
    String credit_query = "UPDATE salaryDetails SET salary = salary + ? WHERE id = ?";
    Savepoint sp = null;
    try{
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection con = DriverManager.getConnection(url,username,password);
      con.setAutoCommit(false);


      System.out.println("before transaction");
      Statement smt = con.createStatement();
      ResultSet rs = smt.executeQuery(select_query);
      System.out.println("id"+" "+"name"+" "+"salary");
      while(rs.next()){
        System.out.println(rs.getInt(1)+" "+
        rs.getString(2)+" "+
        rs.getLong(3));
      }
      
      PreparedStatement debit = con.prepareStatement(debit_query);
      debit.setInt(1,1000);
      debit.setInt(2,1);
      int rowAffected1 = debit.executeUpdate();
      System.out.println("deducted where id = 1 ");
      sp = con.setSavepoint("after debit");

      PreparedStatement credit = con.prepareStatement(credit_query);
      credit.setInt(1,1000);
      credit.setInt(2,6);
      int rowAffected2 = credit.executeUpdate();
      System.out.println("credited where id = 2");

      if(rowAffected1>0 && rowAffected2>0){
        con.commit();
        System.out.println("commited all changes in database");
      }else{
        con.rollback(sp);
        System.out.println("rollback to savepoint");
      }

      Statement smt2 = con.createStatement();
      ResultSet rs2 = smt2.executeQuery(select_query);
      System.out.println("id"+" "+"name"+" "+"salary");
      while(rs2.next()){
        System.out.println(rs2.getInt(1)+" "+
        rs2.getString(2)+" "+
        rs2.getLong(3));
      }

    }catch(ClassNotFoundException | SQLException e){
      System.out.println(e.getMessage());
    }
  }
}
