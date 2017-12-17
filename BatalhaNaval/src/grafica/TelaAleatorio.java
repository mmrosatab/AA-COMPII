package grafica;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import logica.Caca;
import logica.Computador;
import logica.NavioEscolta;
import logica.PortaAviao;
import logica.Submarino;
import logica.Usuario;

public class TelaAleatorio extends TelaTransicao implements ActionListener, MouseInputListener
{
	
	//vetor que armazena qtd de quad pra cada tipo de embarcacao
	private int[] qtdQuad = new int[4];

	private JButton btnAleatorio;
	private TelaJogo telaJogo;
	private int numTabela = 0;
	private boolean tabelaCarregada = false;
	
	public TelaAleatorio() 
	{
		super("Batalha Naval");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600,450);
		//setBounds(100, 100, 450, 400);
		painel = new JPanel();
		setContentPane(painel);
		painel.setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);
		
		// frase para exibição na tela
		titulo = new JLabel("Clique no botão aleatório!");
		titulo.setFont(new Font("Courier", Font.BOLD,14));
		titulo.setBounds(100,-10,300,80);
		titulo.setForeground(Color.blue);
		painel.add(titulo);
		
		
		addMouseListener(this); //necessário para detectar evento de clique de mouse
		
		try
		{
			image1 = ImageIO.read(file1);
			image2 = ImageIO.read(file2);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		letras = new JLabel(new ImageIcon(image1));
		letras.setBounds(10,60,30,300);
		painel.add(letras);
		
		
		numeros = new JLabel(new ImageIcon(image2));
		numeros.setBounds(50,230,300,300);
		painel.add(numeros);
		// set botao Aleatorio
		btnAleatorio = new JButton("Aleatório");
		btnAleatorio.setFont(new Font("Courier", Font.BOLD,16));
		btnAleatorio.setBounds(400,260,140,20);
		btnAleatorio.setForeground(Color.blue); // seta cor da letra
		painel.add(btnAleatorio);
		btnAleatorio.addActionListener(this); 
		
		// set botão zerar
		
		btnZerar = new JButton("Zerar");
		btnZerar.setFont(new Font("Courier", Font.BOLD,16));
		btnZerar.setBounds(400,300,140,20);
		btnZerar.setForeground(Color.blue); // seta cor da letra
		painel.add(btnZerar);
		btnZerar.addActionListener(this); 
		
		// set botao jogar
		btnJogar = new JButton("Jogar");
		btnJogar.setFont(new Font("Courier", Font.BOLD,16));
		btnJogar.setBounds(420,340,100,20);
		btnJogar.setForeground(Color.blue); // seta cor da letra
		painel.add(btnJogar);
		btnJogar.addActionListener(this); 
			
		// qtd quad pra cada tipo de embarcacao
		qtdQuad[0] = 4; // embarcacao
		qtdQuad[1] = 3;
		qtdQuad[2] = 2;
		qtdQuad[3] = 2;
		
		criarTabela();
		zerarMatriz();
	}
	
	public void preencherTabela()
	{

		Color cor = null;
		
		for (int i = 0; i < DIM; i++)
		{
			for (int j = 0; j < DIM; j++)
			{
				switch (this.matrizU[i][j])
				{
					case 0:
						cor = new Color(Color.BLUE.getRGB());
						break;
					case 1:
						cor = new Color(Color.BLACK.getRGB());
						break;
					case 2:
						cor = new Color(Color.PINK.getRGB());
						break;
					case 3:
						cor = new Color(Color.GRAY.getRGB());
						break;
					default:
						cor = null;
						break;
				}
				
				if (cor != null)
				{
					this.tabelaUsuario[i][j].setBackground(cor);
				}
			}
		}	
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		
		if(e.getSource() == btnZerar)
		{
			zerarTabela();
			zerarMatriz();
			this.tabelaCarregada = false;
			this.embarcacoesU.clear();
			
			
		}else if(e.getSource() == btnAleatorio)
		{
			
			if(this.tabelaCarregada == false)
			{	
				carregarMatrizLogica();
				preencherTabela();
				this.tabelaCarregada = true;
				
			}else
			{
				JOptionPane.showMessageDialog(null,"Você deve zerar a tabela!");
			}
			
		}else if(e.getSource() == btnJogar)
		{
			if(this.tabelaCarregada)
			{
				Usuario usuario = new Usuario(this.embarcacoesU);
				usuario.carregarMatrizLogica(this.matrizU);
				Computador computador = new Computador();
				computador.carregarMatrizLogica(this.embarcacoesC);
				
				telaJogo = new TelaJogo(usuario,computador);
				telaJogo.setVisible(true);
				setVisible(false);
				
			}else
			{
				JOptionPane.showMessageDialog(null,"Você deve colocar as embarcações!");
			}
		}
				
				
	}
	
	
	public void carregarMatrizLogica()
	{
		
		String[] nome = new String[2];
		
		nome[0] = "t0.txt";
		nome[1] = "t1.txt";
	
		
		if (this.numTabela == 2)
			this.numTabela = 0;
		
		String arquivo = nome[this.numTabela];
		//System.out.println("Nome do arquivo: "+arquivo);
		
		this.numTabela++;
		
		try {
			 
			FileReader arq = new FileReader(arquivo);
			BufferedReader lerArq = new BufferedReader(arq);
			
			String linha;
		 
		    ArrayList<String> array =  new ArrayList<String>();
		      
		    while ((linha = lerArq.readLine()) != null) 
		    {
		    	array.add(linha);
		    }
		      
	
		    for (int i = 0; i < array.size(); i++)
		    {
	    	 	String[] s = array.get(i).split(" ");
		  
	    	 	for (int j = 0; j < s.length; j++)
	    	 	{
	    	 		this.matrizU[i][j] = (int)Integer.parseInt(s[j]);
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
    	 		
    	 		if(this.matrizU[i][j] == 1)
    	 		{
    	 			if(!entrei)
    	 			{
    	 				NavioEscolta n1 = new NavioEscolta(i, j);
    	 				//System.out.println("add navio");
    	 				this.embarcacoesU.add(n1);
    	 				entrei = true;
    	 			}
    	 			
    	 			cont++;
    	 			
    	 			if(cont == 2)
    	 			{
    	 				entrei = false;
    	 				cont = -1;
    	 			}
    	 			
    	 		}else if(this.matrizU[i][j] == 0)
    	 		{
    	 			if(!entrei)
    	 			{
    	 				PortaAviao p1 = new PortaAviao(i, j);
    	 				//System.out.println("add porta av");
    	 				this.embarcacoesU.add(p1);
    	 				
    	 				entrei = true;
    	 			}
    	 			
    	 			cont++;
    	 			
    	 			if(cont == 3)
    	 			{
    	 				entrei = false;
    	 				cont = -1;
    	 			}
    	 			
    	 		}else if(this.matrizU[i][j] == 2)
    	 		{
    	 			if(!entrei)
    	 			{
    	 				Caca c1 = new Caca(i, j);
    	 				//System.out.println("add caca");
    	 				this.embarcacoesU.add(c1);
    	 				entrei = true;
    	 			}
    	 			
    	 			cont++;
    	 			
    	 			if(cont == 1)
    	 			{
    	 				entrei = false;
    	 				cont = -1;
    	 			}
    	 			
    	 			
    	 			
    	 		}else if(this.matrizU[i][j] == 3)
    	 		{
    	 			if(!entrei)
    	 			{
    	 				Submarino s1 = new Submarino(i, j);
    	 				//System.out.println("add sub");
    	 				this.embarcacoesU.add(s1);
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
		
		//System.out.println("tamanho vetor de embarcacoes usuario: "+this.embarcacoesU.size());

	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		// TODO Auto-generated method stub
		//int x = (int) e.getX();
		//int y = (int) e.getY();
		   
		//System.out.println("Mouse moved x: "+x+"mouse y: "+y);
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		// TODO Auto-generated method stub
		//int x = (int) e.getX();
		//int y = (int) e.getY();
		   
		//System.out.println("Mouse clicked x: "+x+"mouse y: "+y);

	}


	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseDragged(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
		
}

