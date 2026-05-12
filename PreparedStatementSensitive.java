import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


//preparedStatement - sensitive 
public class PreparedStatementSensitive {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/stud";
        String username = "root";
        String password = "My29Sql@p";

        try {

            // Load Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create Connection
            Connection con = DriverManager.getConnection(
                    url, username, password);

            String query = "SELECT * FROM course";

            // Create PreparedStatement with Scrollable ResultSet
            PreparedStatement pst =
                    con.prepareStatement(
                            query,
                            ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE
                    );

            // Execute Query
            ResultSet rs = pst.executeQuery();

            // ============================================
            // next()
            // ============================================

            System.out.println("Forward Movement");

            while (rs.next()) {

                System.out.println(
                        rs.getInt(1) + " " +
                        rs.getString(2) + " " +
                        rs.getDouble(3)
                );
            }

            System.out.println();

            // ============================================
            // previous()
            // ============================================

            System.out.println("Backward Movement");

            while (rs.previous()) {

                System.out.println(
                        rs.getInt(1) + " " +
                        rs.getString(2) + " " +
                        rs.getDouble(3)
                );
            }

            System.out.println();

            // ============================================
            // first()
            // ============================================

            System.out.println("First Row");

            rs.first();

            System.out.println(
                    rs.getInt(1) + " " +
                    rs.getString(2) + " " +
                    rs.getDouble(3)
            );

            System.out.println();

            // ============================================
            // last()
            // ============================================

            System.out.println("Last Row");

            rs.last();

            System.out.println(
                    rs.getInt(1) + " " +
                    rs.getString(2) + " " +
                    rs.getDouble(3)
            );

            System.out.println();

            // ============================================
            // absolute()
            // ============================================
            System.out.println("before updation");
            System.out.println("5th Row");

            rs.absolute(5);

            System.out.println(
                    rs.getInt(1) + " " +
                    rs.getString(2) + " " +
                    rs.getDouble(3)
            );
            System.out.println();

            // ============================================
            // after updation
            // ============================================
            System.out.println("after updation");
            System.out.println("5th Row");

            String update = "UPDATE course SET name = ? WHERE course_id=?";
            PreparedStatement up = con.prepareStatement(update);
                up.setString(1, "d");
                up.setInt(2, 5);
                up.executeUpdate();
            PreparedStatement updatePre = con.prepareStatement(
                "SELECT * FROM course",
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE
            );
            ResultSet rsNew = updatePre.executeQuery();
            rsNew.absolute(5);
            System.out.println(
                    rsNew.getInt(1) + " " +
                    rsNew.getString(2) + " " +
                    rsNew.getDouble(3)
            );

            // Close Resources
            rs.close();
            pst.close();
            con.close();





        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
}
