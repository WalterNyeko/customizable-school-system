package clarion.academics.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class TestTwo {
public static void main(String[] args) throws Exception {
    Class.forName("com.mysql.jdbc.Driver");
    Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/kisugu?autoReconnect=true&useSSL=false", "root", "");

    Statement statement = connect.createStatement();
    ResultSet resultSet = statement.executeQuery("select * from `Boys Dormitory`");
    HSSFWorkbook workbook = new HSSFWorkbook();
    HSSFSheet spreadsheet = workbook.createSheet("engine report");
    HSSFRow row = spreadsheet.createRow(1);
    HSSFCell cell;
    cell = row.createCell(1);
    cell.setCellValue("engine_code");
    cell = row.createCell(2);
    cell.setCellValue("var1");
    cell = row.createCell(3);
    cell.setCellValue("var2");
    cell = row.createCell(4);
    cell.setCellValue("var3");
    cell = row.createCell(5);
    cell.setCellValue("var4");
    cell = row.createCell(6);
    cell.setCellValue("var5");
    int i = 2;
    while (resultSet.next()) {
        row = spreadsheet.createRow(i);
        cell = row.createCell(1);
        cell.setCellValue(resultSet.getInt("id"));
        cell = row.createCell(2);
        cell.setCellValue(resultSet.getString("class_number"));
        cell = row.createCell(3);
        cell.setCellValue(resultSet.getString("payment_code"));
        cell = row.createCell(4);
        cell.setCellValue(resultSet.getString("student_name"));
        cell = row.createCell(5);
        cell.setCellValue(resultSet.getString("student_class"));
        cell = row.createCell(6);
        cell.setCellValue(resultSet.getString("year"));
        i++;
    }
    FileOutputStream out = new FileOutputStream(new File("C:\\Users\\WalterNyeko\\Desktop\\exceldatabase.xlsx"));
    workbook.write(out);
    out.close();
    System.out.println("exceldatabase.xlsx written successfully");
 }
}