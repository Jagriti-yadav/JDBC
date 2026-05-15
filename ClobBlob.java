import java.io.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClobBlob {
  public static void main(String[] args){
    String url = "jdbc:mysql://localhost:3306/stud";
    String username = "root";
    String password = "My29Sql@p";
    String insert_Query = """
        INSERT INTO StudentInfo(name,photo,resume) values
        (?,?,?)
        """;
    String select_query = "SELECT * FROM studentInfo";
    try{
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection con = DriverManager.getConnection(url,username,password);

      File frName = new File("C:\\Users\\MY  PC\\OneDrive\\Desktop\\clob.txt"); 
      File fPhoto = new File("C:\\Users\\MY  PC\\OneDrive\\Desktop\\myPic.jpeg");
      FileReader fread = new FileReader(frName);
      FileInputStream fiphoto = new FileInputStream(fPhoto);

      // PreparedStatement psmt = con.prepareStatement(insert_Query);
      // psmt.setString(1,"Rahul");
      // psmt.setBinaryStream(2,fiphoto,fPhoto.length());
      // psmt.setCharacterStream(3,fread,frName.length());
      // psmt.executeUpdate();
      // System.out.println("inserted successfully");



      //reading data
      Statement smt = con.createStatement();
      ResultSet rs = smt.executeQuery(select_query);

      while(rs.next()){

        int id = rs.getInt("id");
        String name = rs.getString("name");

        //read photo from DB
        InputStream is = rs.getBinaryStream("photo");
        //print photo
        FileOutputStream fos = new FileOutputStream("photo.png");
        byte[] buff = new byte[1024];
        int len;
        while((len=is.read(buff))!=-1){
          fos.write(buff,0,len);
        }


        //read resume from DB
        Reader rd = rs.getCharacterStream("resume");
        BufferedReader br = new BufferedReader(rd);
        String line;
        System.out.println("ID = " + id + ", Name = " + name);
        while((line = br.readLine()) != null) {

        System.out.println(line);
    }
      }



      //psmt.close();
      con.close();

    }catch(ClassNotFoundException | SQLException | IOException e){
      System.out.println(e.getMessage());
    }
  }
}
