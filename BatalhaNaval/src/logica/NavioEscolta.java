package logica;

public class NavioEscolta extends Embarcacao
{
	private Disparo disparo;
	
	public NavioEscolta(int i,int j)
	{
		super(i, j);
		this.disparo = Disparo.Cascata;
		this.nCel = 3;
		this.celD = 0;
	}
	

}
