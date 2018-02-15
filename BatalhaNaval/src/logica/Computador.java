package logica;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import util.Tupla;

public class Computador extends Jogador
{

	private int numTabela = 0;
	private ArrayList<Tupla>  possiveisJogadas = new ArrayList<>(); // alternativa ao random indices i j para se jogar
	private ArrayList<Integer> possiveisEscolhas = new ArrayList<>(); // alternativa ao random para escolhas de embarcacao para se atirar

	public Computador()
	{
		initComputador();
	}
	
	public void initComputador()
	{
		this.vez = false;
		
		for (int i = 0; i < DIM; i++)
		{
			for (int j = 0; j < DIM; j++)
			{
				Tupla t1 =  new Tupla(i, j);
				//System.out.println("i: "+i+"j: "+j);
				this.possiveisJogadas.add(t1);
			}
		}
		
		this.possiveisEscolhas.add(1);
		this.possiveisEscolhas.add(2);
		this.possiveisEscolhas.add(3);
	}
	
	public void removeEscolha(int k)
	{
		this.possiveisEscolhas.remove(k);
	}
	
	public void removeJogada(int k)
	{
		this.possiveisJogadas.remove(k);
	}
	public ArrayList<Integer> getPossiveisEscolhas()
	{
		return possiveisEscolhas;
	}

	public void setPossiveisEscolhas(ArrayList<Integer> posiveisEscolhas)
	{
		this.possiveisEscolhas = posiveisEscolhas;
	}

	public ArrayList<Tupla> getPossiveisJogadas()
	{
		return possiveisJogadas;
	}
	
	public void setPossiveisJogadas(ArrayList<Tupla> posiveisJogadas)
	{
		this.possiveisJogadas = posiveisJogadas;
	}
	
	
	public void carregarMatrizLogica(ArrayList<Object> embarcacao)
	{
		if (this.numTabela == 1)
		{
			this.numTabela = 0;
		}
		
		String arquivo = "tabelaComputador"+numTabela+".txt";
		//System.out.println(numTabela);
		//System.out.println("Nome do arquivo: "+arquivo);
		
		this.numTabela++;
		
		try 
		{
			 
			FileReader arq = new FileReader(arquivo);
			BufferedReader lerArq = new BufferedReader(arq);
			
			String linha;
		 
		    ArrayList<String> array =  new ArrayList<String>();
		      
		    while ((linha = lerArq.readLine()) != null) 
		    {
		    	array.add(linha);
		    }
		      
		    //System.out.println(array.size());
	      
		    for (int i = 0; i < array.size(); i++)
		    {
	    	 	String[] s = array.get(i).split(" ");
		  
	    	 	for (int j = 0; j < s.length; j++)
	    	 	{
	    	 		this.matrizLogica[i][j] = Integer.parseInt(s[j]);
	    	 	}
		    }

		  arq.close();
		} catch (IOException e) {
		    System.err.printf("Erro na abertura do arquivo: %s.\n",
		      e.getMessage());
		}
		
		boolean entrei = false;
		int cont = -1;
		
		for (int i = 0; i < DIM; i++)
	    {
    	 	for (int j = 0; j < DIM; j++)
    	 	{
    	 		if(this.matrizLogica[i][j] == 0)
    	 		{
    	 			if(!entrei)
    	 			{
    	 				PortaAviao p1 = new PortaAviao(i, j);
    	 				//System.out.println("add porta av");
    	 				this.embarcacoes.add(p1);
    	 				entrei = true;
    	 			}
    	 			
    	 			cont++;
    	 			
    	 			if(cont == 3)
    	 			{
    	 				entrei = false;
    	 				cont = -1;
    	 			}
    	 			
    	 		}else if(this.matrizLogica[i][j] == 1)
    	 		{
    	 			if(!entrei)
    	 			{
    	 				NavioEscolta n1 = new NavioEscolta(i, j);
    	 				//System.out.println("add navio");
    	 				this.embarcacoes.add(n1);
    	 				entrei = true;
    	 			}
    	 			
    	 			cont++;
    	 			
    	 			if(cont == 2)
    	 			{
    	 				entrei = false;
    	 				cont = -1;
    	 			}
    	 			
    	 		}else if(this.matrizLogica[i][j] == 2)
    	 		{
    	 			if(!entrei)
    	 			{
    	 				Caca c1 = new Caca(i, j);
    	 				//System.out.println("add caca");
    	 				this.embarcacoes.add(c1);
    	 				entrei = true;
    	 			}
    	 			
    	 			cont++;
    	 			
    	 			if(cont == 1)
    	 			{
    	 				entrei = false;
    	 				cont = -1;
    	 			}
    	 			
    	 			
    	 			
    	 		}else if(this.matrizLogica[i][j] == 3)
    	 		{
    	 			if(!entrei)
    	 			{
    	 				Submarino s1 = new Submarino(i, j);
    	 				//System.out.println("add sub");
    	 				this.embarcacoes.add(s1);
    	 				entrei = true;
    	 			}
    	 			
    	 			if(cont == 1)
    	 			{
    	 				entrei = false;
    	 				cont = -1;
    	 			}
    	 			
    	 		}
    	 	}
	    }

	}
	
}
