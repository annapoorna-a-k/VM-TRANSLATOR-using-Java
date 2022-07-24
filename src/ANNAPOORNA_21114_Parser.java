package eoc_assignment4;
import java.util.*;
import java.io.*;
public class ANNAPOORNA_21114_Parser 
{

	public String command;
	public String type;
	public BufferedReader in;

	// Opens the file and gets ready to parse it
	public ANNAPOORNA_21114_Parser(File inFile) 
	{
        try
        {
            in = new BufferedReader(new FileReader(inFile));
        } 
        catch (IOException e) 
        {
            System.out.println(e);
        }
    }

	public boolean hasMoreCommands()
	{
		try {
			while ((command = in.readLine()) != null) {
				// skip empty lines
				if (command.isEmpty()) 
				{
					continue;
				}
				//to get what type the command is
				type = command.trim().split(" ")[0];

				//to check for one line and multi line comments and skip past them
				command = command.trim().toLowerCase();

				//Single line comments
				if (command.charAt(0) == '/' && command.charAt(1) == '/')
				{
					continue;
				}
				//multi-line comments
				else if (command.charAt(0) == '/' && command.charAt(1) == '*') 
				{
					while ((command = in.readLine()) != null) 
					{
						int len = command.length();
						if (command.charAt(len - 1) == '/' && command.charAt(len - 2) == '*')
						{
							break;
						}
						continue;
					}
					continue;
				}
				//in-line comments
				if (command.contains("//"))
				{
					command = command.split("//")[0].trim();
				}
				return true; //no-errors
			}

			in.close();
		} 
		catch (IOException e) 
		{
			System.out.println(e);
		}
		return false;
	}

	public String advance()
	{
		return command;
	}

	public String commandType() 
	{
		if (type.equals("push"))
		{
			type = "C_PUSH";
		} 
		else if (type.equals("pop")) 
		{
			type = "C_POP";
		}
		else if (type.equals("label"))
		{
			type="C_LABEL";
		}
		else if(type.equals("if-goto"))
		{
			type="C_IF";
		}
		else if(type.equals("goto"))
		{
			type="C_GOTO";
		}
		else 
		{
			type = "C_ARITHMETIC";
		}
		return type;
	}

	public String arg1() 
	{
		if (type.equals("C_ARITHMETIC"))
		{
			return command;
		} 
		else if (type.equals("C_PUSH") || type.equals("C_POP"))
		{
			return command.split(" ")[1]; //
		}
		else
		{
			return command.split(" ")[1];
		}
	}

	public String arg2()
	{
		return command.split(" ")[2];
	}

}
