package grafica;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.MouseInputListener;

import logica.Caca;
import logica.Computador;
import logica.NavioEscolta;
import logica.PortaAviao;
import logica.Submarino;
import logica.Usuario;

public class TelaManual extends TelaTransicao implements ActionListener, MouseInputListener
{
	// escolha de embarcacao do usuario
	private int escolha = -1;
	private int totalEscolhido = 0;
	
	//vetor que armazena qtd de quad pra cada tipo de embarcacao
	private int[] qtdQuad = new int[4];
	
	//total de embracacoes por jogador
	private final int NUM_EMBARCACOES = 4;
	
	private JTextField caminhoArquivo;

	private JButton btnPortaAv;
	private JButton btnNavioEsc;
	private JButton btnCaca;
	private JButton btnSub;
	
	private JButton btnGerar; // gerar mapa de arquivo
	private TelaJogo telaJogo;
	
	private JLabel obs;
	
	public TelaManual() 
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
		titulo = new JLabel("Posicione as embarcações ou gere o jogo do arquivo!");
		titulo.setFont(new Font("Courier", Font.BOLD,14));
		titulo.setBounds(100,-10,450,80);
		titulo.setForeground(Color.blue);
		painel.add(titulo);
		
		// frase obs
		titulo = new JLabel("Coloque uma embarcação de cada tipo");
		titulo.setFont(new Font("Courier", Font.BOLD,14));
		titulo.setBounds(145,10,450,80);
		titulo.setForeground(Color.RED);
		painel.add(titulo);
		
		addMouseMotionListener(this); //necessario para detectar evento de movimento de mouse
		addMouseListener(this); //necessário para detectar evento de clique de mouse
		
		// jtextfield para colocar caminho do arquivo
		caminhoArquivo = new JTextField();
		caminhoArquivo.setBounds(410, 280,180, 20);
		painel.add(caminhoArquivo);
		
		//botao para gerar jogo do arquivo
		btnGerar = new JButton("Gerar jogo de arquivo");
		btnGerar.setFont(new Font("Courier", Font.BOLD,9));
		btnGerar.setForeground(Color.blue);
		btnGerar.setBounds(430,300,140,20);
		painel.add(btnGerar);
		btnGerar.addActionListener(this);
		
		// set botão zerar
		btnZerar = new JButton("Zerar");
		btnZerar.setFont(new Font("Courier", Font.BOLD,16));
		btnZerar.setBounds(430,340,140,20);
		btnZerar.setForeground(Color.blue); // seta cor da letra
		painel.add(btnZerar);
		btnZerar.addActionListener(this); 
		
		// set botao jogar
		btnJogar = new JButton("Jogar");
		btnJogar.setFont(new Font("Courier", Font.BOLD,16));
		btnJogar.setBounds(450,380,100,20);
		btnJogar.setForeground(Color.blue); // seta cor da letra
		painel.add(btnJogar);
		btnJogar.addActionListener(this); 
		
		// set botao btnPortaAv
		
		btnPortaAv = new JButton();
		btnPortaAv.setBackground(Color.BLUE);
		btnPortaAv.setBounds(450,60,120,30);
		painel.add(btnPortaAv);
		btnPortaAv.addActionListener(this); 
		
		btnNavioEsc = new JButton();
		btnNavioEsc.setBackground(Color.BLACK);
		btnNavioEsc.setBounds(450,100,90,30);
		painel.add(btnNavioEsc);
		btnNavioEsc.addActionListener(this); 
		
		btnCaca = new JButton();
		btnCaca.setBackground(Color.PINK);
		btnCaca.setBounds(450,140,60,30);
		painel.add(btnCaca);
		btnCaca.addActionListener(this); 
		
		btnSub = new JButton();
		btnSub.setBackground(Color.GRAY);
		btnSub.setBounds(450,180,60,30);
		painel.add(btnSub);
		btnSub.addActionListener(this);
		

		
		//imagens letras e numeros
        
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
		
		// qtd quad pra cada tipo de embarcacao
		qtdQuad[0] = 4; // porta aviao
		qtdQuad[1] = 3; // navio escolta
		qtdQuad[2] = 2; // caca
		qtdQuad[3] = 2; // submarino 
		
		criarTabela();
		zerarMatriz();
		
	}
	
	
	
	public void preencherTabela(int escolha,int lin,int col)
	{
		
		//System.out.println("conteudo do vetor: "+qtdQuad[escolha]);
		Color cor = null;
		
		switch (escolha)
		{
			case 0:
				PortaAviao p1 = new PortaAviao(lin, col);
				this.embarcacoesU.add(p1);
				cor = new Color(Color.BLUE.getRGB());
				break;
			case 1:
				NavioEscolta n1 = new NavioEscolta(lin, col);
				this.embarcacoesU.add(n1);
				cor = new Color(Color.BLACK.getRGB());
				break;
			case 2:
				Caca c1 = new Caca(lin, col);
				this.embarcacoesU.add(c1);
				cor = new Color(Color.PINK.getRGB());
				break;
			case 3:
				Submarino s1 = new Submarino(lin, col);
				this.embarcacoesU.add(s1);
				cor = new Color(Color.GRAY.getRGB());
				break;
		}
		
		for (int j = 0; j < qtdQuad[escolha]; j++)
		{
			// seta cor do botao
			
			this.tabelaUsuario[lin][j+col].setBackground(cor);
			this.tabelaUsuario[lin][j+col].setEnabled(false);
			//joga posicoes escolhidas na matriz logica
			this.matrizU[lin][j+col] = escolha;
		}
		
		this.escolha = -1;
		this.totalEscolhido++;

		
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btnPortaAv)
		{
			this.escolha = 0;

		}else if (e.getSource() == btnNavioEsc)
		{
			this.escolha = 1;
			
			
		}else if (e.getSource() == btnCaca)
		{
			this.escolha = 2;
			
		
		}else if (e.getSource() == btnSub)
		{
			this.escolha = 3;
			
		
		}else if(e.getSource() == btnZerar)
		{
			this.totalEscolhido = 0;
			
			//habilito botoes novamente
			btnPortaAv.setEnabled(true);
			btnNavioEsc.setEnabled(true);
			btnCaca.setEnabled(true);
			btnSub.setEnabled(true);
			
			zerarTabela();
			zerarMatriz();
		
			// zerar lista de embarcacoes
			this.embarcacoesU.clear();
			
		}else if(e.getSource() == btnJogar)
		{
			if (this.totalEscolhido == 4)
			{
				// passe o array de embarcacoes 
				//telaJogo = new TelaJogo(this.matrizLogica,this.embarcacoes);
				Usuario usuario = new Usuario(this.embarcacoesU);
				usuario.carregarMatrizLogica(this.matrizU);
				Computador computador = new Computador();
				computador.carregarMatrizLogica(this.embarcacoesC);
				
				telaJogo = new TelaJogo(usuario,computador);
				telaJogo.setVisible(true);
				setVisible(false);
				
			}else
			{
				JOptionPane.showMessageDialog(null, "Você não posicionou todas as embarcações!");
			}
						
		}else if(e.getSource() == btnGerar)
		{
			if(this.totalEscolhido > 0)
			{
				zerarTabela();
				zerarMatriz();
				this.totalEscolhido = 0;
				
				if(caminhoArquivo.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(null,"Preencha o caminho do arquivo");
				}else
				{
					String caminho = caminhoArquivo.getText();
					carregarMapaArquivo(caminho);
					JOptionPane.showMessageDialog(null,"Arquivo carregado");
					caminhoArquivo.setText("");
				}
				
			}else
			{
				if(caminhoArquivo.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(null,"Preencha o caminho do arquivo");
				}else
				{
					String caminho = caminhoArquivo.getText();
					carregarMapaArquivo(caminho);
					JOptionPane.showMessageDialog(null,"Arquivo carregado");
					caminhoArquivo.setText("");
				}
			}
				
		}
		else
		{
			if(escolha != -1)
			{
				for (int i = 0; i < DIM; i++)
				{
					for (int j = 0; j < DIM; j++)
					{
						if (e.getSource() == tabelaUsuario[i][j])
						{
							if(!posicaoInvalida(escolha,i,j))
							{
								//System.out.println("Botao i: "+i+"botao j: "+j);
								preencherTabela(escolha,i,j);
								
								//nao permite que jogar escolha um mesma embarcacao
								if (this.escolha == 0)
								{
									btnPortaAv.setEnabled(false);
									
								}else if (this.escolha == 1)
								{
									
									btnNavioEsc.setEnabled(false);
									
								}else if (this.escolha == 2)
								{
									btnCaca.setEnabled(false);
								
								}else if (this.escolha == 3)
								{
									btnSub.setEnabled(false);
								}
								
							}
							
						}
					}
					
				}
				
			}else
			{
				JOptionPane.showMessageDialog(null,"Você deve escolher o tipo de embarcação primeiro");
			}

		}
				
				
	}

	public boolean posicaoInvalida(int escolha,int lin,int col)
	{
		if (this.totalEscolhido < 4)
		{
			if (col + this.qtdQuad[escolha] <= DIM)
			{
				for (int j = 0; j < qtdQuad[escolha]; j++)
				{
					//caso a posicao esteja preenchida na matriz logica nao eh permitido colocar um embarcacao
					if (this.matrizU[lin][j+col] != 5)
					{
						JOptionPane.showMessageDialog(null,"Posição inválida");
						return true;
					}
				}
				
			}else
			{
				JOptionPane.showMessageDialog(null,"Posição inválida");
				return true;
			}
		}else
		{
			JOptionPane.showMessageDialog(null, "Você já atingiu o limite max de embarcações");
			return true;
		}
		
		return false;
	}
	
	public void carregarMapaArquivo (String caminho)
	{
		
		Map<String,Integer> mapa = new HashMap<String,Integer>();
		ArrayList<String> array = null;
		boolean controle = false; 
	
		mapa.put("A", new Integer(0));
		mapa.put("B", new Integer(1));
		mapa.put("C", new Integer(2));
		mapa.put("D", new Integer(3));
		mapa.put("E", new Integer(4));
		mapa.put("F", new Integer(5));
		mapa.put("G", new Integer(6));
		mapa.put("H", new Integer(7));
		mapa.put("I", new Integer(8));
		mapa.put("J", new Integer(9));
         
		try 
		{ 
			FileReader arq = new FileReader(caminho);
			BufferedReader lerArq = new BufferedReader(arq);
			
			String linha;
		 
		    array =  new ArrayList<String>();
		      
		    while ((linha = lerArq.readLine()) != null) 
		    {
		    	array.add(linha);
		    }
		      
		    //System.out.println(array.size());
		    arq.close();
		} catch (IOException e) 
		{
		    System.err.printf("Erro na abertura do arquivo: %s.\n",e.getMessage());
		}
		
		for (int i = 0; i < array.size(); i++)
	    {
    	 	String[] s = array.get(i).split(" ");
    	 	
    	 	char[] charEmb = s[0].toCharArray();
    	 	
    	 	Integer qtdQ = Integer.parseInt(Character.toString(charEmb[0]));
    	 	
    	 	//System.out.println(qtdQ);
    	 	
    	 	//System.out.println("s[1]"+s[1]);
    	 	
    	 	char[] aux = s[1].toCharArray();
    	 	
    	 	//System.out.println(aux[0]);
    	 	Integer lin = (Integer)mapa.get(Character.toString(aux[0])); 
    	 	
    	 	//System.out.println("linha"+lin);
    	 	Integer col = Integer.parseInt(Character.toString(aux[1]));
    	 	
    	 	//System.out.println(col);
    	 	
    	 	if(lin == null || col >= DIM || (qtdQ < 2 && qtdQ > 5))
    	 	{
    	 		JOptionPane.showMessageDialog(null, "Altere o txt!");
				zerarMatriz();
				zerarTabela();
				this.totalEscolhido = 0;
				break;
    	 	}
    	 	int indice = -1;
    	 	
    	 	switch (qtdQ)
			{
				case 2:
					
					if(controle)
					{
						indice = 3;
						
					}else
					{
						indice = 2;
						controle = true;
					}
					
					break;
					
				case 3:
					indice = 1;
					break;
					
				case 4:
					indice = 0;
					break;	
			}
    	 	
    	 	
    	 	if(indice != -1)
			{
				
				if(!posicaoInvalida(indice,lin,col))
				{
					//System.out.println("Botao i: "+i+"botao j: "+j);
					preencherTabela(indice,lin,col);
					
				}else
				{
					JOptionPane.showMessageDialog(null, "Altere o txt!");
					zerarMatriz();
					zerarTabela();
					this.totalEscolhido = 0;
					break;
				}
					
			}
    	
	    }
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
