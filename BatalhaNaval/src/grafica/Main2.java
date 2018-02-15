package grafica;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Main2
{
	public static void main(String[] args)
	{
		Random r = new Random(); 
		int num =  r.nextInt(1);
		
		int[][]m = new int[10][10];
		
		String arquivo = "tabela"+Integer.toString(num)+".txt";
		System.out.println("Nome do arquivo: "+arquivo);
		
		 try {
			 
		      FileReader arq = new FileReader(arquivo);
		      BufferedReader lerArq = new BufferedReader(arq);
		      String linha;
		 
		      ArrayList<String> array =  new ArrayList<String>();
		      
		      while ((linha = lerArq.readLine()) != null) 
		      {
		    	  array.add(linha);
		      }
		      
		      System.out.println(array.size());
		      
		      for (int i = 0; i < array.size(); i++)
		      {
		    	  String[] s = array.get(i).split(" ");
		    	  
		    	  for (int j = 0; j < s.length; j++)
		    	  {
					 m[i][j] = Integer.parseInt(s[j]);
		    	  }
		      }
		      
		      for (int i = 0; i < 10; i++)
		      {
		    	for (int j = 0; j < 10; j++)
				{
		    		System.out.print(" "+m[i][j]);
				}
		    	System.out.println();
		      }
		      
		      arq.close();
		    } catch (IOException e) {
		        System.err.printf("Erro na abertura do arquivo: %s.\n",
		          e.getMessage());
		    }
	}
}
