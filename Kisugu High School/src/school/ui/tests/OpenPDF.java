package school.ui.tests;

import java.io.InputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.awt.Desktop;

public class OpenPDF {

    public static void main(String[] args) throws Exception {
    	new OpenPDF();
        
    }
    public OpenPDF() throws IOException {
    
    	
    	if (Desktop.isDesktopSupported()) {
            try {
            	String inputPdf = "/javaprac.pdf";
                InputStream manualAsStream = OpenPDF.class.getClass().getClassLoader().getResourceAsStream(inputPdf);

                File myFile = new File(manualAsStream+"");
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }
		
       
        
    }
}