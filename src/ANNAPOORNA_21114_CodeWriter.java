package eoc_assignment4;

import java.util.*;
import java.io.*;

public class ANNAPOORNA_21114_CodeWriter {
	public BufferedWriter out;
	public String file;
	public int i = 1;

	public ANNAPOORNA_21114_CodeWriter(String outFile, boolean bootStrap) {
		if (outFile.contains(".vm")) {
			outFile = outFile.split(".vm")[0];
		}
		// sets file name
		setFileName(outFile);
		outFile = outFile + ".asm";

		try
		{
			out = new BufferedWriter(new FileWriter(new File(outFile)));

		}
		catch (IOException e) 
		{
			System.out.println(e);
		}
	}

	public void setFileName(String fileName)
	{
		// if a path
		if (fileName.contains("\\")) 
		{
			// to get Pointer/BasicTest.vm as file name
			fileName = fileName.substring(fileName.lastIndexOf("\\") + 1, fileName.length());
		}
		// if contains .vm at the end
		if (fileName.contains(".vm")) 
		{
			fileName = fileName.split(".vm")[0];
		}

		file = fileName;
	}
	
	public void writeLabel(String label)
	{
		try
		{
			writeComment(label);
			out.write("("+label.toUpperCase()+")");
			out.newLine();
		}
		catch (IOException e) 
		{
			System.out.println(e);
		}
	}
	
	public void writeIf(String l)
	{
		try 
		{
			out.write("// if_goto "+l);
			out.newLine();
			//writeComment(l);
			out.write("@SP\nM=M-1;\nA=M\nD=M\n@"+l+"\nD;JNE");
			out.newLine();
		}
		catch (IOException e) 
		{
			System.out.println(e);
		}
	}
	
	public void writeGoto(String g)
	{
		try 
		{
			writeComment(g);
			out.write("@" + g + "\n" + "0;JMP");
		}
		catch (IOException e) 
		{
			System.out.println(e);
		}
	}
	public void writeArithmetic(String arth) 
	{
		try {
			writeComment(arth);// writing as a comment
			if (arth.equals("add"))
			{
				out.write("@SP\nM=M-1\nA=M\nD=M\n@SP\nM=M-1\nA=M\nM=M+D\n@SP\nM=M+1");
				out.newLine();
			} else if (arth.equals("sub")) 
			{
				out.write("@SP\nM=M-1\nA=M\nD=M\n@SP\nM=M-1\nA=M\nM=M-D\n@SP\nM=M+1");
				out.newLine();
			} else if (arth.equals("neg")) 
			{
				out.write("@SP\nM=M-1\nA=M\nD=M\nM=-D\n@SP\nM=M+1");
				out.newLine();
			} else if (arth.equals("and")) 
			{
				out.write("@SP\nM=M-1\nA=M\nD=M\n@SP\nM=M-1\nA=M\nD=D&M\nM=D\n@SP\nM=M+1");
				out.newLine();
			} else if (arth.equals("or"))
			{
				out.write("@SP\nM=M-1\nA=M\nD=M\n@SP\nM=M-1\nA=M\nD=D|M\nM=D\n@SP\nM=M+1");
				out.newLine();
			} else if (arth.equals("not"))
			{
				out.write("@SP\nM=M-1\nA=M\nD=M\nM=!D\n@SP\nM=M+1");
				out.newLine();
			}
			// +"\nD;JEQ@SP\nA=M\nM=0\n@END"+Integer.toString(i)+"\n0;JMP\n(IF"+Integer.toString(i)+")\n@SP\nA=M\nM=-1\n(END"+Integer.toString(i)+")\n@SP\nM=M+1");
			// handling logical commands and their labels
			
			else if (arth.equals("eq") || arth.equals("gt") || arth.equals("lt")) 
			{
				out.write("@SP\nM=M-1\nA=M\nD=M\n@SP\nM=M-1\nA=M");
				out.newLine();
				
				out.write("D=D-M");
				out.newLine();
				
				out.write("@IF" + Integer.toString(i));
				out.newLine();
				
				if (arth.equals("eq")) 
				{
					out.write("D;JEQ");
					out.newLine();
				} 
				else if (arth.equals("gt"))
				{
					out.write("D;JGT");
					out.newLine();
				} 
				else if (arth.equals("lt")) 
				{
					out.write("D;JLT");
					out.newLine();
				}
				out.write("@SP");
				out.newLine();
				out.write("A = M");
				out.newLine();
				out.write("M = 0");
				out.newLine();
				
				out.write("@END" + Integer.toString(i));
				out.newLine();
				out.write("0;JMP");
				out.newLine();

				out.write("(IF" + Integer.toString(i) + ")");
				out.newLine();
				out.write("@SP");
				out.newLine();
				out.write("A = M");
				out.newLine();
				out.write("M = -1");
				out.newLine();
				out.write("(END" + Integer.toString(i) + ")");
				out.newLine();
				
				out.write("@SP");
				out.newLine();
				out.write("M = M + 1");
				out.newLine();
				i++;
				out.write("@END" + Integer.toString(i)+"\n0;JMP\n\n(IF"+Integer.toString(i)+")\n"
						+ "@SP\nA=M\nM=-1"
						+ "\n(END"+Integer.toString(i)+")\n@SP\nM=M+1");
		}
			else if(arth.equals("label"))
			{
				String[] arth1=arth.split(" ");
				out.write("("+arth1[1]+")");
				out.newLine();
			}
			else if(arth.equals("if_goto"))
			{
				String[] arth1=arth.split(" ");
				out.write("@SP\nM=M-1\nA=M\nA=M\nD=M\n@"+arth1[1]+"\n" + "D;JNE");
			}
			else if(arth.equals("go_to"))
			{
				String[] arth1=arth.split(" ");
				out.write("@"+arth1[1]+"\n0;JMP");
			}
			// Increment i for the different conditional statements
			
		}

		catch (IOException e) 
		{
			System.out.println(e);
		}

	}

	// write the translation of push/pop command
	public void writePushPop(String type, String arg1, String index)
	{
		// adding command as a comment
		String com;
		if (type.equals("C_POP"))
		{
			com = "pop";
		} else {
			com = "push";
		}
		writeComment(com + " " + arg1 + " " + index);

		String seg = "";

		// store the appropriate segment
		// @segment
		if (arg1.equals("argument")) 
		{
			seg = "ARG";
		} 
		else if (arg1.equals("local"))
		{
			seg = "LCL";
		} 
		else if (arg1.equals("this"))
		{
			seg = "THIS";
		}
		else if (arg1.equals("that"))
		{
			seg = "THAT";
		} 
		else if (arg1.equals("temp"))
		{
			seg = "5";
		}

		try 
		{
			// Pop command
			// addr = segmentPointer + i
			// SP--
			// *addr = *SP
			if (type.equals("C_POP")) 
			{

				if (arg1.equals("pointer")) 
				{
					if (index.equals("0"))
					{
						out.write("@SP\nM=M-1\nA=M\nD=M\n@THIS\nM=D");
					} 
					else 
					{
						out.write("@SP\nM=M-1\nA=M\nD=M\n@THAT\nM=D");
					}
					out.newLine();
				}
				else if (arg1.equals("static"))
				{
					out.write("@SP\nM=M-1\nA=M\nD=M\n@" + file + "." + index + "\nM=D");
					out.newLine();
				} 
				else if (arg1.equals("temp"))
				{
					out.write("@" + seg + "\nD=M\n@" + index
							+ "\nD=D+A\n@addr\nM=D\n@SP\nM=M-1\nA=M\nD=M\n@addr\nA=M\nM=D");
					out.newLine();
				} 
				else if (arg1.equals("that"))
				{
					out.write("@" + seg + "\nD=M\n@" + index
							+ "\nD=D+A\n@addr\nM=D\n@SP\nM=M-1\nA=M\nD=M\n@addr\nA=M\nM=D");
					out.newLine();
				} 
				else if (arg1.equals("this")) 
				{
					out.write("@" + seg + "\nD=M\n@" + index
							+ "\nD=D+A\n@addr\nM=D\n@SP\nM=M-1\nA=M\nD=M\n@addr\nA=M\nM=D");
					out.newLine();
				} 
				else if (arg1.equals("arguement"))
				{
					out.write("@" + seg + "\nD=M\n@" + index
							+ "\nD=D+A\n@addr\nM=D\n@SP\nM=M-1\nA=M\nD=M\n@addr\nA=M\nM=D");
					out.newLine();
				}
				else 
				{
					out.write("@" + seg + "\nD=M\n@" + index
							+ "\nD=D+A\n@addr\nM=D\n@SP\nM=M-1\nA=M\nD=M\n@addr\nA=M\nM=D");
					out.newLine();
				}
			}

			// Push command
			// addr = segmentPointer + i
			// *SP = *addr
			// SP++
			else 
			{
				if (arg1.equals("pointer")) 
				{
					if (index.equals("0")) 
					{
						out.write("@THIS\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1");
					} 
					else 
					{
						out.write("@THAT\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1");
					}
					out.newLine();
				}
				else if (arg1.equals("static")) 
				{
					out.write("@" + file + "." + index + "\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1");
					out.newLine();
				} 
				else if (arg1.equals("temp"))
				{
					out.write("@" + seg + "\nD=A\n@" + index
							+ "\nD=D+A\n@addr\nM=D\nA=M\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1");
					out.newLine();
				} 
				else if (arg1.equals("that"))
				{
					out.write("@" + seg + "\nD=M\n@" + index
							+ "\nD=D+A\n@addr\nM=D\nA=M\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1");
					out.newLine();
				} 
				else if (arg1.equals("this"))
				{
					out.write("@" + seg + "\nD=M\n@" + index
							+ "\nD=D+A\n@addr\nM=D\nA=M\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1");
					out.newLine();
				} 
				else if (arg1.equals("arguement"))
				{
					out.write("@" + seg + "\nD=M\n@" + index
							+ "\nD=D+A\n@addr\nM=D\nA=M\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1");
					out.newLine();
				}
				else if (arg1.equals("constant"))
				{
					out.write("@" + index + "\nD=A\n@SP\nA=M\nM=D\n@SP\nM=M+1");
					out.newLine();
				}
				else
				{
					out.write("@" + seg + "\nD=M\n@" + index
							+ "\nD=D+A\n@addr\nM=D\nA=M\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1");
					out.newLine();
				}
			}
			
			
		} 
		catch (IOException e) 
		{
			System.out.println(e);
		}

	}

	// close the output file
	public void close()
	{
		try
		{
			out.close();
		} 
		catch (IOException e)
		{
			System.out.println(e);
		}
	}

	// Write one line comments with string provided
	private void writeComment(String line)
	{
		try 
		{
			out.write("// " + line);
			out.newLine();
		} 
		catch (IOException e) 
		{
			System.out.println(e);
		}
	}

}
