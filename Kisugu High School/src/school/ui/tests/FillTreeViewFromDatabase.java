package school.ui.tests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import school.ui.finances.CashBookController;

/**
 * 
 * @author Narayan
 */

public class FillTreeViewFromDatabase extends Application{

    //TABLE VIEW AND DATA
    private ObservableList<ObservableList> data;
    private TableView tableview;
    private TreeView tree;//declaration of the treeView
    HashMap<Integer, Tests> node = new HashMap<>(); //for child nodes
    HashMap<Integer, Tests> pere = new HashMap<>(); //for parent nodes
    Tests c; //object from Test class
	private Connection cnx;
	private PreparedStatement ps;
	private ResultSet rs;
	private Connection con;
	private Statement stm;


    //MAIN EXECUTOR
    public static void main(String[] args) {
        launch(args);
    }

    //CONNECTION DATABASE
    public void buildData(){
          java.sql.Connection c ;
          data = FXCollections.observableArrayList();
          try{
            c = CashBookController.getConnection();
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "select requirement_name as Name,standard_quantity as Quantity,ID from requirementslist";
            //ResultSet
            ResultSet rs = c.createStatement().executeQuery(SQL);

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;                
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
                    }                    
                });

                tableview.getColumns().addAll(col); 
                System.out.println("Column ["+i+"] ");
            }

            /********************************
             * Data added to ObservableList *
             ********************************/
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added "+row );
                data.add(row);

            }

            //FINALLY ADDED TO TableView
            tableview.setItems(data);
          }catch(Exception e){
              e.printStackTrace();
              System.out.println("Error on Building Data");             
          }
      }


      @Override
      public void start(Stage stage) throws Exception {
        //TableView
        tableview = new TableView();
        tree=new TreeView();
        
        HBox box=new HBox(5);
        box.getChildren().addAll(tableview,tree);
        
        buildData();
        fillTree();

        //Main Scene
        Scene scene = new Scene(box);        

        stage.setScene(scene);
        stage.setTitle("Title");
//        stage.setFullScreen(true);
        stage.show();
      }
      
      
    
      private void fillTree() {
          String query = "SELECT catid,catname FROM category";
          try {
        	  cnx=CashBookController.getConnection();
              ps = cnx.prepareStatement(query);
              rs = ps.executeQuery();
              
              try {
					con = CashBookController.getConnection();
					stm = con.createStatement();
				} catch (Exception ex) {
					ex.printStackTrace();
				}

              while (rs.next()) {
                 
            	  ResultSet result=stm.executeQuery("select scatname from subcategory where catid='"+rs.getInt(1)+"'");
            	  while(result.next()) {
            		  int code = rs.getInt("catid");
                      String composant = rs.getString("catname");
                      int parent = rs.getInt("catid");
                      String niveau = result.getString("scatname");

                      c = new Tests(code,composant, niveau);
                      node.put(code, c);
                      pere.put(parent, c);
            	  }
              }
              ps.close();
              rs.close();
          } catch (Exception e) {
        	  e.printStackTrace();
              System.err.println("Error" + e);
          }

          TreeItem<String> system = new TreeItem<>("Home");
          //brows and fill parents node
          for (Integer k : pere.keySet()) {
              Tests p = pere.get(k);
              TreeItem<String> parent = new TreeItem<>();
              parent.setValue(p.getDepartment());

              //brows and fill child hashmap
              for (Integer i : node.keySet()) {
                  Tests c = node.get(i);
                  TreeItem<String> noeud = new TreeItem<>();
                  noeud.setValue(c.getName());

                  if (c.getDeptID() == k) {
                      //if the parent > 0 it must attach to the root node
                      if (k >= 0) {
                    	  if(i>0) {
                    		  system.getChildren().add(noeud);
                    		  noeud.getChildren().add(parent);
                    	  }
                          
                      } else {
                          
                      }
                  }
              }
          }
          tree.setRoot(system);
      }
      
      public static class Tests {

  		private final SimpleStringProperty name;
  		private final SimpleStringProperty department;
  		private final Integer deptID;

  		private Tests(Integer id,String name, String department) {
  			this.deptID=id;
  			this.name = new SimpleStringProperty(name);
  			this.department = new SimpleStringProperty(department);
  		}

  		
  		
  		public Integer getDeptID() {
			return deptID;
		}



		public String getName() {
  			return name.get();
  		}

  		public void setName(String fName) {
  			name.set(fName);
  		}

  		public String getDepartment() {
  			return department.get();
  		}

  		public void setDepartment(String fName) {
  			department.set(fName);
  		}
  	}
}