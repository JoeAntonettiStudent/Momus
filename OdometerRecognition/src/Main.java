import java.io.File;


public class Main {
	
	public final static String FILE_PATH = "/home/joe/Documents/OCR Project/Test Set/Cropped/1985.jpg";

	public static void main(String[] args) {
		File dir = new File(FILE_PATH);
		OCREngine ocr_engine = new OCREngine();
		System.out.println(ocr_engine.doOCR(dir));
	}

}
