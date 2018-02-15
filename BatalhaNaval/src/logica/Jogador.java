package logica;

import java.util.ArrayList;

import util.Tupla;

public class Jogador 
{
	protected final int DIM = 10;
	protected final int qtdEmbarcacao = 4;
	protected int matrizLogica[][] = new int[DIM][DIM];
	protected boolean vez;
	protected int pontuacao = 0;
	protected ArrayList<Object> embarcacoes = new ArrayList<>();
	
	// prind
	public ArrayList<Object> getEmbarcacoes()
	{
		return embarcacoes;
	}
	
	public Jogador(ArrayList<Object> embarcacoes)
	{
		this.embarcacoes = embarcacoes;
	}
	
	public void removeK(int k)
	{
		this.embarcacoes.remove(k);
	}
	
	public Jogador()
	{
		
	}
	
	public ArrayList<Object> atirar(int i, int j, Object embarcacao)
	{
		ArrayList<Object> array = new ArrayList<>();
		
		//verificando tipo de embarcacao foi escolhida para atirar
		if(embarcacao instanceof Caca)
		{
			Caca c1 = (Caca) embarcacao;
			
			if(c1.nCel > 0)
			{
				array.add(i);
				array.add(j);
				array.add(Disparo.Estrela);
			}
		}else if(embarcacao instanceof NavioEscolta)
		{
			NavioEscolta n1 = (NavioEscolta) embarcacao;
			
			if(n1.nCel > 0)
			{
				array.add(i);
				array.add(j);
				array.add(Disparo.Cascata);
			}
			
		}else if(embarcacao instanceof Submarino)
		{
			Submarino s1 = (Submarino) embarcacao;
			
			if(s1.nCel > 0)
			{
				array.add(i);
				array.add(j);
				array.add(Disparo.Comum);
			}
		}
		
		return array;
	}
	
	public ArrayList<Tupla> receberJogada(ArrayList<Object> array)
	{
		int linha = (int) array.get(0);
		int coluna = (int) array.get(1);
	
		Disparo disparo = (Disparo) array.get(2);
		ArrayList<Tupla> lista = new ArrayList<>();
		
		if (disparo == Disparo.Estrela)
		{
			Tupla t1 = new Tupla(linha,coluna);
			Tupla t2 = new Tupla(linha+1,coluna);
			Tupla t3 = new Tupla(linha-1,coluna);
			Tupla t4 = new Tupla(linha,coluna+1);
			Tupla t5 = new Tupla(linha,coluna-1);
			
			lista.add(t1);
			lista.add(t2);
			lista.add(t3);
			lista.add(t4);
			lista.add(t5);
			
			return lista;
			
		}else if (disparo == Disparo.Cascata)
		{
			Tupla t1 = new Tupla(linha,coluna);
			Tupla t2 = new Tupla(linha,coluna+1);
		
			lista.add(t1);
			lista.add(t2);
		
			
			return lista;
			
		}else if (disparo == Disparo.Comum)
		{
			Tupla t1 = new Tupla(linha,coluna);
			lista.add(t1);
			return lista;
		}
		
		
		//System.out.println("lista posicoes eh nula");
		return lista;
		

	}
	
	public void addEmbarcacao(Object embarcacao)
	{
		this.embarcacoes.add(embarcacao);
	}
	
	public int indexEmbarcacao(Object embarcacao)
	{
		
		for (int i = 0; i < this.embarcacoes.size(); i++)
		{
			if (embarcacao instanceof Caca && this.embarcacoes.get(i) instanceof Caca)
			{
				return i;
			}
			else if (embarcacao instanceof NavioEscolta && this.embarcacoes.get(i) instanceof NavioEscolta)
			{
				return i;
			}
			else if (embarcacao instanceof Submarino && this.embarcacoes.get(i) instanceof Submarino)
			{
				return i;
			}
			else if (embarcacao instanceof PortaAviao && this.embarcacoes.get(i) instanceof PortaAviao)
			{
				return i;
			}
			
		}
		//System.out.println("to fora");
		return -1;
	}
	
	public int[][] getMatrizLogica()
	{
		return matrizLogica;
	}

	public void setMatrizLogica(int[][] matrizLogica)
	{
		this.matrizLogica = matrizLogica;
	}
	
	
	public boolean getVez()
	{
		return vez;
	}

	public void setVez(boolean vez)
	{
		this.vez = vez;
	}
	
	
	
}
