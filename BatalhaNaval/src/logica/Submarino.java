package logica;

public class Submarino extends Embarcacao
{
	private Disparo disparo;
	
	public Submarino(int i,int j)
	{
		super(i, j);
		this.disparo = Disparo.Comum;
		this.nCel = 2;
		this.celD = 0;
	}

}
