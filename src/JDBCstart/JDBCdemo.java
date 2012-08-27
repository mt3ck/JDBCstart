/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBCstart;

import java.sql.*;

/**
 *
 * @author MTECK
 */
public class JDBCdemo {
    
    private static String url = "jdbc:oracle:thin:@//acaddb2.asu.edu:1521/orcl";
    //Change for other databases / users.
    private static String database = dbCredentials.database;
    private static String user = dbCredentials.user;
    private static String pass = dbCredentials.pass;
    
    public static void main(String[] args) throws SQLException{
        Connection con = null;
        con = getConnection(url, user, pass);
        
        String createString =
        "create table " + database +
        ".SUPPLIERS " +
        "(SUP_ID integer NOT NULL, " +
        "SUP_NAME varchar(40) NOT NULL, " +
        "STREET varchar(40) NOT NULL, " +
        "CITY varchar(20) NOT NULL, " +
        "STATE char(2) NOT NULL, " +
        "ZIP char(5), " +
        "PRIMARY KEY (SUP_ID))";
        
        String insertString =
        "insert into " + database +
        ".SUPPLIERS " +
        "values(2, 'Marissas Coffee ', " +
        "'34534 North Way', " +
        "'San Fran', 'CA', '95123')";
        
        String SuppliersQuery =
        "select SUP_ID, SUP_NAME, STREET, " +
        "CITY, STATE, ZIP " +
        "from " + database + ".SUPPLIERS";
        String SuppliersQuery2 =
        "select * " +
        "from " + database + ".SUPPLIERS";
        
        //execSQL(con, createString);
        //execSQL(con, insertString);
        
        ResultSet suppliers = getResults(con, SuppliersQuery2);
        while(suppliers.next()){
            int supplierID = suppliers.getInt("SUP_ID");
            String supplierName = suppliers.getString("SUP_NAME");
            String supplierStreet = suppliers.getString("STREET");
            String supplierCity = suppliers.getString("CITY");
            String supplierState = suppliers.getString("STATE");
            String supplierZip = suppliers.getString("ZIP");
            System.out.println(supplierName + "\t" + supplierStreet +
                               "\t" + supplierCity + "\t" + supplierState +
                               "\t" + supplierZip);
        }
        suppliers.close();
    }
 
    public static Connection getConnection(String url, String user, String pass) throws SQLException {
        Connection con = null;
        
        //Need to begin use of driver. ojdb5.jar downloaded via Oracles website and added to libraries. 
        try {
           Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ex) {
            System.err.println("Issue finding JDBC driver. ");
            return null;
        }
        
        System.out.println("Initializing JDBC driver successful.");
        System.out.println("Beginning connection to database.");
        
        //Establishing connection to DBMS with given information.
        try {
            con = DriverManager.getConnection(url, user, pass);
            
        } catch (SQLException e) {
 
            System.out.println("Connection to database failed. ");
            e.printStackTrace();
            return null;
	}
        
        //Success, returning connection.
        System.out.println("Connected to database successfully! ");
        return con;
    }
    
    public static void execSQL(Connection con, String sqlstmt) throws SQLException {
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(sqlstmt);
            System.out.println("Successful sql execution. ");
        } catch (SQLException e) {
            System.err.print(e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    
    public static ResultSet getResults(Connection con, String query) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

        } catch (SQLException e ) {
            System.err.print(e);
        } 
        
        return rs;
    }
}
