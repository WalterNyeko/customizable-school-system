package school.ui.finances;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import school.ui.main.LoginHelperClass;
public class CashBookController {
	
	
	
	
      private static Connection con;


	public static Connection getConnection() {
          try  {
        	  LoginHelperClass helperClass=new LoginHelperClass();
        	  
        	  
        	ResultSet result=  helperClass.displayDataBaseLoginCredentials();
        	if(result.next()) {
        		
        		String dbHost=result.getString(1);
        		String dbUser=result.getString(2);
        		String dbPass=result.getString(3);
//        		 Class.forName("com.mysql.jdbc.Driver");
                  con = DriverManager.getConnection
                         (dbHost,
                         dbUser,dbPass);
        	}
        	  
             
              return con;
          }
          catch(Exception ex) {
              System.out.println("Database.getConnection() Error -->" + ex.getMessage());
              return null;
          }
      }

       public static void close(Connection con) {
          try  {
              con.close();
          }
          catch(Exception ex) {
          }
      }
       
       public void CreateCategory(String querries) {

      		

 			java.sql.Connection conn = null;
 			java.sql.PreparedStatement pst = null;

      		try {

      			conn = getConnection();
      			pst = conn.prepareStatement(querries);

      			pst.executeUpdate();

      			JOptionPane.showMessageDialog(null, "Category Created Successfully");

      		} catch (Exception ex) {
      			ex.printStackTrace();
      			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

      		}finally{
    			if(conn!=null){
    				try {
    					conn.close();
    				} catch (SQLException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			}
    		}
      	}
       
       
       
       public void updateLedger(String querries) {


			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;

     		try {

     			conn = getConnection();
     			pst = conn.prepareStatement(querries);

     			pst.executeUpdate();


     		} catch (Exception ex) {
     			ex.printStackTrace();
     			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

     		}finally{
   			if(conn!=null){
   				try {
   					conn.close();
   				} catch (SQLException e) {
   					// TODO Auto-generated catch block
   					e.printStackTrace();
   				}
   			}
   		}
     	}
       
       
       public void updateCashBookTx(String querries) {

     		

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;

     		try {

     			conn = getConnection();
     			pst = conn.prepareStatement(querries);

     			pst.executeUpdate();

//     			JOptionPane.showMessageDialog(null, "Category Created Successfully");

     		} catch (Exception ex) {
     			ex.printStackTrace();
     			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

     		}finally{
   			if(conn!=null){
   				try {
   					conn.close();
   				} catch (SQLException e) {
   					// TODO Auto-generated catch block
   					e.printStackTrace();
   				}
   			}
   		}
     	}
       public void CreateSubCategory(String querries) {

     		
 			java.sql.Connection conn = null;
 			java.sql.PreparedStatement pst = null;

     		try {

     			conn = getConnection();
     			pst = conn.prepareStatement(querries);

     			pst.executeUpdate();

     			JOptionPane.showMessageDialog(null, "Sub-Category Created Successfully");

     		} catch (Exception ex) {
     			ex.printStackTrace();
     			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

     		}finally{
    			if(conn!=null){
    				try {
    					conn.close();
    				} catch (SQLException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			}
    		}
     	}
       
       
       public void InsertCashBookEntries(String querries) {

   		

   		try {

   			java.sql.Connection conn = null;
   			java.sql.PreparedStatement pst = null;
   			conn = getConnection();
   			pst = conn.prepareStatement(querries);

   			pst.executeUpdate();

   			JOptionPane.showMessageDialog(null, "Entry Saved Successfully");

   		} catch (Exception ex) {
   			ex.printStackTrace();
   			JOptionPane.showMessageDialog(null, "Request Was Not Executed" + ex.getMessage());

   		}
   	}
       
       
       public void InsertCashBookEntriesSilently(String querries) {

      		

      		try {

      			java.sql.Connection conn = null;
      			java.sql.PreparedStatement pst = null;
      			conn = getConnection();
      			pst = conn.prepareStatement(querries);

      			pst.executeUpdate();


      		} catch (Exception ex) {
      			ex.printStackTrace();
      			JOptionPane.showMessageDialog(null, "Request Was Not Executed" + ex.getMessage());

      		}
      	}
       public void updateBookReturns(String querries) {


			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;

    		try {

    			conn = getConnection();
    			pst = conn.prepareStatement(querries);

    			pst.executeUpdate();
    			JOptionPane.showMessageDialog(null, "Book Returned Successfully " );


    		} catch (Exception ex) {
    			ex.printStackTrace();
    			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

    		}finally{
  			if(conn!=null){
  				try {
  					conn.close();
  				} catch (SQLException e) {
  					// TODO Auto-generated catch block
  					e.printStackTrace();
  				}
  			}
  		}
    	}
       
}