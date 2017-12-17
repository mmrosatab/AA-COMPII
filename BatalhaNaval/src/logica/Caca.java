package logica;

public class Caca extends Embarcacao
{
	private Disparo disparo;
	
	public Caca(int i,int j)
	{
		super(i, j);
		this.disparo = Disparo.Estrela;
		this.nCel = 2;
		this.celD = 0;
		
	}

}
