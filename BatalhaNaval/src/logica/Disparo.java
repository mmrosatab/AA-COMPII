package logica;

public enum Disparo
{	
	Cascata(2),Estrela(5),Comum(1);
	
	public int tipoDisparo;
	
	Disparo(int tipo)
	{
		tipoDisparo = tipo;
	}

}
