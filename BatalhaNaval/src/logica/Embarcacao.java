package logica;

public class Embarcacao
{
	
	protected int id;
	protected int nCel;
	protected int celD;
	protected int i;
	protected int j;
	
	public Embarcacao(int i, int j)
	{
		this.i = i;
		this.j = j;
	}
	
	// se um celula foi descoberta celD++
	public boolean descobrir()
	{
		this.celD++;
		if (this.celD == nCel)
		{
			return true;
		}else
		{
			return false;
		}
	}
}
