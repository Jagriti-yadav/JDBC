import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PreparedStatementInSensitive {
  public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/stud";
        String username = "root";
        String password = "My29Sql@p";

        try {

            // ============================================
            // Load Driver
            // ============================================

            Class.forName("com.mysql.cj.jdbc.Driver");

            // ============================================
            // Create Connection
            // ============================================

            Connection con = DriverManager.getConnection(
                    url, username, password);

            // ============================================
            // TYPE_SCROLL_INSENSITIVE
            // ============================================

            PreparedStatement pst =
                    con.prepareStatement(
                            "SELECT * FROM course",
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    );

            ResultSet rs = pst.executeQuery();

            // ============================================
            // BEFORE UPDATE
            // ============================================

            System.out.println("Before Updation");

            rs.absolute(5);

            System.out.println(
                    rs.getInt(1) + " " +
                    rs.getString(2) + " " +
                    rs.getDouble(3)
            );

            System.out.println();

            // ============================================
            // UPDATE DATABASE
            // ============================================

            String updateQuery =
                    "UPDATE course SET name = ? WHERE course_id = ?";

            PreparedStatement up =
                    con.prepareStatement(updateQuery);

            up.setString(1, "Z");
            up.setInt(2, 5);

            int rows = up.executeUpdate();

            System.out.println(
                    rows + " Row Updated in Database");

            System.out.println();

            // ============================================
            // AFTER UPDATE
            // ============================================

            System.out.println("After Updation");

            // Move again to same row in OLD ResultSet
            rs.absolute(5);

            // Still shows OLD value
            System.out.println(
                    rs.getInt(1) + " " +
                    rs.getString(2) + " " +
                    rs.getDouble(3)
            );

            System.out.println();

            // ============================================
            // ACTUAL DATABASE VALUE
            // ============================================

            System.out.println("Actual Database Value");

            PreparedStatement freshPst =
                    con.prepareStatement(
                            "SELECT * FROM course WHERE course_id = ?"
                    );

            freshPst.setInt(1, 5);

            ResultSet freshRs =
                    freshPst.executeQuery();

            if (freshRs.next()) {

                System.out.println(
                        freshRs.getInt(1) + " " +
                        freshRs.getString(2) + " " +
                        freshRs.getDouble(3)
                );
            }

            // ============================================
            // Close Resources
            // ============================================

            rs.close();
            freshRs.close();

            pst.close();
            up.close();
            freshPst.close();

            con.close();

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
      }
}
