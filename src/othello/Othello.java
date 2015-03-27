package othello;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Scanner;

import logic.Controller;

public class Othello {
	public static void main(String[] args) throws FileNotFoundException{
		Controller controller = Controller.getInstance();
		int task,depth,j=0;
		String player;
		char inputState[][] = new char[8][8];
		String line;
	try{
		
		FileReader fr = new FileReader("/home/rakesh/Desktop/input.txt");
		BufferedReader br = new BufferedReader(fr);
        
		task = Integer.parseInt(br.readLine());
		player = br.readLine();
		depth = Integer.parseInt(br.readLine());
		
		while((line=br.readLine())!=null){
			char input[] = line.toCharArray();
			
			for(int i=0;i<input.length;i++){
				
				inputState[j][i] = input[i];
			}
			j++;
		}
		
		switch(task)
		{
		case 1: controller.init(inputState);
				break;
				
		
		}
		
	}
	catch(Exception ex)
	{
			
	}
	
	}	
}
