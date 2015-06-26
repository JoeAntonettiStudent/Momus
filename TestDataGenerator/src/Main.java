import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Main {
	
	public static Number[] digits = {
			new Number('0'),
			new Number('1'),
			new Number('2'),
			new Number('3'),
			new Number('4'),
			new Number('5'),
			new Number('6'),
			new Number('7'),
			new Number('8'),
			new Number('9'),
			new Number('.')
	};

	public static void main(String[] args) {
		int digits_created = (11000);
		File outFile = new File("output.txt");
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
		
		String out = "";
		while(digits_created > 0){
			int rand = (int) ((Math.random()) * 12);
			if(rand != 11 && digits[rand].count > 0){
				out += digits[rand].character;
				digits_created--;
			}else
				out += " ";
			if(out.length() > 100){
				writer.append(out);
				out = "";
				writer.newLine();
			}
		}
		writer.close();
		System.out.println(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class Number{
	public int count = 1000;
	public char character;
	
	public Number(char c){
		character = c;
	}
}
