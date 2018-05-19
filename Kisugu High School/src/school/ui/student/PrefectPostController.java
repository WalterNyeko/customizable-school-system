package school.ui.student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import school.ui.finances.CashBookController;

public class PrefectPostController {

	
	Connection myConnection = null;
	
	public PrefectPostController() throws SQLException {
		myConnection =  CashBookController.getConnection();
	}

	public void addPrefectPost(PrefectPost post)throws SQLException{
		
		PreparedStatement myPreparedStatement = null;
		
		try {
			myPreparedStatement = myConnection.prepareStatement("insert into prefect_post(id,post) values(?,?)");
			
			myPreparedStatement.setInt(1, post.getId());
			myPreparedStatement.setString(2, post.getPostName());
			
			myPreparedStatement.executeUpdate();
			
			myPreparedStatement.close();
			myConnection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
