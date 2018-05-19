package school.ui.tests;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Optional;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PieChartDemo extends Application {

    //PIE CHART DATA
    private ObservableList data;
	private PieChart chart;

    //MAIN EXECUTOR
    public static void main(String[] args) {
        Application.launch(PieChartDemo.class, args);
    }

    //CONNECTION DATABASE SAVING DATA
    public void buildData(){
          Connection c ;
          data = FXCollections.observableArrayList();
          try{
            c = DBConnect.connect();
            //SQL FOR SELECTING NATIONALITY OF CUSTOMER
            String SQL = "SELECT COUNT(nationality_id), type FROM CUSTOMER c,NATIONALITY na WHERE na.id=c.nationality_id GROUP BY type";

            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                //adding data on piechart data
                data.add(new PieChart.Data(rs.getString(2),rs.getDouble(1)));
            }
          }catch(Exception e){
              System.out.println("Error on DB connection");
              return;
          }

      }

      @Override
      public void start(Stage stage) throws Exception {
        //PIE CHART
        PieChart pieChart = new PieChart();
        buildData();
        pieChart.getData().addAll(data);

        chart = new PieChart(data) {
        	  @Override
        	  protected void layoutChartChildren(double top, double left, double contentWidth, double contentHeight) {
        	    if (getLabelsVisible()) {
        	      getData().forEach(d -> {
        	        Optional<Node> opTextNode = chart.lookupAll(".chart-pie-label").stream().filter(n -> n instanceof Text && ((Text) n).getText().contains(d.getName())).findAny();
        	        if (opTextNode.isPresent()) {
        	          ((Text) opTextNode.get()).setText(d.getName() + ": " + d.getPieValue() + " Students");
        	        }
        	      });
        	    }
        	    super.layoutChartChildren(top, left, contentWidth, contentHeight);
        	  }
        	};
        
        //Main Scene
        Scene scene = new Scene(chart);        

        stage.setScene(scene);
        stage.show();
      }
}
