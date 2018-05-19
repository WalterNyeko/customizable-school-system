package school.ui.tests;

import java.io.InputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.awt.Desktop;

public class SampleCode {

    public static void main(String[] args) throws Exception {
    	new SampleCode();
        
    }
    public SampleCode() throws IOException {
    	String inputPdf = "/javaprac.pdf";
        InputStream manualAsStream = SampleCode.class.getClass().getClassLoader().getResourceAsStream(inputPdf);


        Path tempOutput;
		try {
			tempOutput = Files.createTempFile("TempManual", ".pdf");
			 tempOutput.toFile().deleteOnExit();
		        System.out.println("tempOutput: " + tempOutput);
		        tempOutput.toFile().deleteOnExit();
		        try (InputStream is = SampleCode.class.getClassLoader().getResourceAsStream(inputPdf)) {
		            Files.copy(manualAsStream, tempOutput, StandardCopyOption.REPLACE_EXISTING);
		        }
		        File userManual = new File (tempOutput.toFile().getPath());
		        if (userManual.exists())
		        {
		            Desktop.getDesktop().open(userManual);
		        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        
    }
}