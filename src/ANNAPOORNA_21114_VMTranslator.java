package eoc_assignment4;

import java.util.*;
import java.io.*;

public class ANNAPOORNA_21114_VMTranslator {

	public ANNAPOORNA_21114_Parser parser;
	public ANNAPOORNA_21114_CodeWriter code;

	private void parse(File in) {
		// construct a parser with the file
		parser = new ANNAPOORNA_21114_Parser(in);

		// iterate through each command
		while (parser.hasMoreCommands()) {
			String ctype = parser.commandType();

			// Arithmetic command
			if (ctype.equals("C_ARITHMETIC")) {
				code.writeArithmetic(parser.arg1());
			}
			// Push/Pop command
			else if (ctype.equals("C_PUSH") || ctype.equals("C_POP"))
			{
				code.writePushPop(ctype, parser.arg1(), parser.arg2());
			} 
			else if (ctype.equals("C_LABEL") || ctype.equals("C_IF")) 
			{
				if (ctype.equals("C_LABEL"))
					code.writeLabel(parser.arg1());
				else if (ctype.equals("C_IF"))
					code.writeIf(parser.arg1());
				else
					code.writeGoto(parser.arg1());
			}
			
			else if(ctype.equals("C_GOTO")) 
			{ 
				code.writeGoto(parser.arg1()); 
			}
			 
		}
	}

	public static void main(String[] args) {

		File path = new File(
				"C:\\Users\\ANNAPOORNA\\Desktop\\ANNAPOORNA AK_AM.EN.U4AIE21114\\nand2tetris\\projects\\07\\StackArithmetic\\SimpleAdd\\SimpleAdd.vm");

		ANNAPOORNA_21114_VMTranslator vmt = new ANNAPOORNA_21114_VMTranslator();

		// create CodeWriter
		vmt.code = new ANNAPOORNA_21114_CodeWriter(
				"C:\\Users\\ANNAPOORNA\\Desktop\\ANNAPOORNA AK_AM.EN.U4AIE21114\\nand2tetris\\projects\\07\\StackArithmetic\\SimpleAdd\\SimpleAdd.vm",
				false);
		vmt.code.setFileName(path.getPath());
		vmt.parse(path);
		vmt.code.close();
	}

}
