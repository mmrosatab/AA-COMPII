package logica;

import java.util.ArrayList;

public class Usuario extends Jogador
{
	
	public Usuario(ArrayList<Object> embarcacoes)
	{
		super(embarcacoes);
		this.setVez(true);
	}
	public void carregarMatrizLogica(int[][] matrizLogica)
	{
		for (int i = 0; i < DIM; i++)
		{
			for (int j = 0; j < DIM; j++)
			{
				this.matrizLogica[i][j] = matrizLogica[i][j];
			}
		}
	}
}
