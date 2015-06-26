import java.io.File;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/*
 * Wrapper for Tesseract object which handles interface with Tess4j
 */

public class OCREngine {
	
	private Tesseract tesseract_instance = Tesseract.getInstance();
	
	public OCREngine(){
	//	tesseract_instance.setDatapath("");
	//	tesseract_instance.setLanguage("eng");
	}
	
	public String doOCR(File image_file){
		try {
			return tesseract_instance.doOCR(image_file);
		} catch (TesseractException e) {
			System.out.println("Couldn't do OCR on file!");
		}
		return null;
	}

}
