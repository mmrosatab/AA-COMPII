package grafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

// esta eh a tela modelo para telaAleatorio e telaManual
public class TelaTransicao extends JFrame implements Action,ActionListener
{
	protected final int  ESP_X = 50;
	protected final int ESP_Y = 60;
	protected final int TAM_BOTAO = 30;
	protected final int DIM = 10;
	
	protected JPanel painel;
	protected JLabel titulo;
	protected JLabel letras;
	protected JLabel numeros;
	
	protected JButton btnJogar;
	protected JButton btnZerar;
	
	
	protected JButton[][] tabelaUsuario = new JButton[DIM][DIM];
	protected JButton[][] tabelaComputador = new JButton[DIM][DIM];
	protected int[][] matrizU = new int[DIM][DIM];
	protected ArrayList<Object> embarcacoesU = new ArrayList<>();
	protected int[][] matrizC = new int[DIM][DIM];
	protected ArrayList<Object> embarcacoesC = new ArrayList<>();
	
	//imagens letras e numeros
	protected String path1 = "letras.png";
	protected String path2 = "numeros.png";
	protected File file1 = new File(path1);
	protected File file2 = new File(path2);
	protected BufferedImage image1 = null;
	protected BufferedImage image2 = null;
    
	
	public TelaTransicao(String string)
	{
		super();
	}
	
	public void criarTabela()
	{
		for (int i = 0; i < DIM; i++)
		{
			for (int j = 0; j < DIM; j++)
			{
				this.tabelaUsuario[i][j] = new JButton();
				this.tabelaUsuario[i][j].setText("");
				this.tabelaUsuario[i][j].addActionListener(this);  
				this.tabelaUsuario[i][j].setBounds(TAM_BOTAO*j+ESP_X,TAM_BOTAO*i+ESP_Y,TAM_BOTAO,TAM_BOTAO);
				getContentPane().add(this.tabelaUsuario[i][j]);
				
			}
		}
	}
	
	public void zerarMatriz()
	{
		for (int i = 0; i < DIM; i++)
		{
			for (int j = 0; j < DIM; j++)
			{
				this.matrizU[i][j] = 5;
			}
		}
		
		//imprimirMatriz();
	}
	
	public void zerarTabela()
	{
		for (int i = 0; i < DIM; i++)
		{
			for (int j = 0; j < DIM; j++)
			{
				this.tabelaUsuario[i][j].setBackground(null);
				this.tabelaUsuario[i][j].setEnabled(true);
			}
		}
	}
	
	private void imprimirMatriz()
	{
		for (int i = 0; i < DIM; i++)
		{
			for (int j = 0; j < DIM; j++)
			{
				System.out.print(" "+this.matrizU[i][j]);
			}
			System.out.println();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getValue(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putValue(String key, Object value)
	{
		// TODO Auto-generated method stub
		
	}

}
