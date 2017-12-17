package grafica;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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
import util.Tupla;

public class TelaJogo extends JFrame implements ActionListener, MouseInputListener
{
	
	private final int  ESP_X = 50;
	private final int  ESP_XC = 460;
	private final int ESP_Y = 60;
	private final int TAM_BOTAO = 30;
	private final int DIM = 10;
	
	// escolha de embarcacao do jogador
	private int escolha = -1;
	
	private JPanel painel;
	private JLabel titulo;
	private JLabel letrasU;
	private JLabel numerosU;
	private JLabel letrasC;
	private JLabel numerosC;
	
	private JLabel tituloUsuario;
	private JLabel tituloComputador;

	private JButton[][] tU = new JButton[DIM][DIM];
	private JButton[][] tC = new JButton[DIM][DIM];
	
	private Usuario usuario;
	private Computador computador;
	private ArrayList<Tupla> posicoes = new ArrayList<>();

	private JButton btnSair;
	private JButton btnNovoJogo;
	private boolean iniciado = false;
	
	private JButton btnNavioEsc;
	private JButton btnCaca;
	private JButton btnSub;
	private JButton btnIniciar;
	private TelaInicial t1;
	
	private ImageIcon xImage = new ImageIcon("x.png");
	
	public TelaJogo(Usuario usuario,Computador computador) 
	{
		super("Batalha Naval");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,600);
		//setBounds(100, 100, 450, 400);
		painel = new JPanel();
		//painel.setBorder(new EmptyBorder(20, 20, 20, 20));
		setContentPane(painel);
		painel.setLayout(null);
		//painel.setLayout(new GridLayout(10,10));
		setLocationRelativeTo(null);
		setResizable(false);

		
		addMouseMotionListener(this); //necessario para detectar evento de movimento de mouse
		addMouseListener(this); //necessário para detectar evento de clique de mouse 
		
		// set botao novoJogo
		btnNovoJogo = new JButton("Novo Jogo");
		btnNovoJogo.setFont(new Font("Courier", Font.BOLD,16));
		btnNovoJogo.setBounds(480,540,150,20);
		btnNovoJogo.setForeground(Color.blue); // seta cor da letra
		painel.add(btnNovoJogo);
		btnNovoJogo.addActionListener(this);
		
		// set botao sair
		btnSair = new JButton("Sair");
		btnSair.setFont(new Font("Courier", Font.BOLD,16));
		btnSair.setBounds(640,540,100,20);
		btnSair.setForeground(Color.blue); // seta cor da letra
		painel.add(btnSair);
		btnSair.addActionListener(this); 
		
		btnIniciar = new JButton("Iniciar");
		btnIniciar.setBounds(100,450,90,30);
		painel.add(btnIniciar);
		btnIniciar.addActionListener(this);
		
		//botoes embarcacoes
		btnNavioEsc = new JButton();
		btnNavioEsc.setBackground(Color.BLACK);
		btnNavioEsc.setBounds(10,500,90,30);
		painel.add(btnNavioEsc);
		btnNavioEsc.addActionListener(this); 
		
		btnCaca = new JButton();
		btnCaca.setBackground(Color.PINK);
		btnCaca.setBounds(120,500,60,30);
		painel.add(btnCaca);
		btnCaca.addActionListener(this); 
		
		btnSub = new JButton();
		btnSub.setBackground(Color.GRAY);
		btnSub.setBounds(200,500,60,30);
		painel.add(btnSub);
		btnSub.addActionListener(this);
		
		tituloUsuario = new JLabel("Sua Tabela");
		tituloUsuario.setFont(new Font("Courier", Font.BOLD,18));
		tituloUsuario.setBounds(140,5,300,80);
		tituloUsuario.setForeground(Color.blue);
		painel.add(tituloUsuario);
		
		tituloComputador = new JLabel("Tabela Computador");
		tituloComputador.setFont(new Font("Courier",Font.BOLD,18));
		tituloComputador.setBounds(520,5,300,80);
		tituloComputador.setForeground(Color.blue);
		painel.add(tituloComputador);
		
		String path1 = "letras.png";
		String path2 = "numeros.png";
		File file1 = new File(path1);
		File file2 = new File(path2);
        BufferedImage image1 = null;
        BufferedImage image2 = null;
        
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
		
		letrasU = new JLabel(new ImageIcon(image1));
		letrasU.setBounds(10,60,30,300);
		painel.add(letrasU);
		
		numerosU = new JLabel(new ImageIcon(image2));
		numerosU.setBounds(50,230,300,300);
		painel.add(numerosU);
		
		letrasC = new JLabel(new ImageIcon(image1));
		letrasC.setBounds(420,60,30,300);
		painel.add(letrasC);
		
		numerosC = new JLabel(new ImageIcon(image2));
		numerosC.setBounds(460,230,300,300);
		painel.add(numerosC);
		
		this.usuario = usuario; 
		this.computador = computador;
		
		criarTabela(this.tU,ESP_X);
		criarTabela(this.tC,ESP_XC);
		
	}
	
	public void Verificar() {        
		if (iniciado) {            
			//System.out.println("iniciei");         
			jogo();           
		} else {          
			System.out.println("terminei");        
		}  
	} 
	
	
	public void jogo() {   
		new Thread() {     
			public void run() {  
				while (iniciado) {        
					//System.out.println("Looping");   
					try {
						Thread.sleep(100 * 3);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				//System.out.println("Terminado!");
				javax.swing.JOptionPane.showMessageDialog(null, "Jogo encerrado!");
			}       
		}.start();
	} 
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == btnIniciar)
		{
			this.iniciado = !this.iniciado; 
			Verificar();
			
		}else if(e.getSource() == btnSair)
		{
			this.iniciado = !this.iniciado; 
			Verificar();	
			System.exit(0);
		
		}else if(e.getSource() == btnNovoJogo)
		{
			this.t1 = new TelaInicial();
			this.t1.setVisible(true);
			setVisible(false);
		}
		else if(e.getSource() == btnCaca)
		{
			if(this.usuario.getVez() && iniciado)
			{
				this.escolha = 2;
				
				//System.out.println("entrei jogada usuario");
				
			}else if(this.usuario.getVez() && !iniciado)
			{
				JOptionPane.showMessageDialog(null,"Aperte iniciar");
				
			}else
			{
				JOptionPane.showMessageDialog(null,"Nao eh a sua vez");
			}
			
		}else if(e.getSource() == btnNavioEsc)
		{
			if(this.usuario.getVez() && iniciado)
			{
				this.escolha = 1;
				
			}else if(this.usuario.getVez() && !iniciado)
			{
				JOptionPane.showMessageDialog(null,"Aperte iniciar");
				
			}else
			{
				JOptionPane.showMessageDialog(null,"Nao eh a sua vez");
			}
			
		}else if(e.getSource() == btnSub)
		{
			if(this.usuario.getVez() && iniciado)
			{
				this.escolha = 3;
				
			}else if(this.usuario.getVez() && !iniciado)
			{
				JOptionPane.showMessageDialog(null,"Aperte iniciar");
				
			}else
			{
				JOptionPane.showMessageDialog(null,"Nao eh a sua vez");
			}
			
		}
		else if(this.escolha != -1 && this.usuario.getVez())
		{
			for (int i = 0; i < DIM; i++)
			{
				for (int j = 0; j < DIM; j++)
				{
					if (e.getSource() == this.tC[i][j])
					{
						
						//System.out.println("button i: "+i+"button j: "+j);
						if (escolha == 2)
						{
						
							posicoes = this.computador.receberJogada(this.usuario.atirar(i, j, recEmbarcacao(2,usuario)));
		
							jogar(posicoes, this.computador,this.tC);
							
							posicoes.clear();
							this.usuario.setVez(false);
							this.computador.setVez(true);
							this.escolha = -1;
							
							
						}else if (escolha == 3)
						{
							//System.out.println("escolha 3");
							
							posicoes = this.computador.receberJogada(this.usuario.atirar(i, j, recEmbarcacao(3,usuario)));
							jogar(posicoes, this.computador,this.tC);
							
							posicoes.clear();
							this.usuario.setVez(false);
							this.computador.setVez(true);
							this.escolha = -1;
							
							
						}else if (escolha == 1)
						{
							
							posicoes = this.computador.receberJogada(this.usuario.atirar(i, j, recEmbarcacao(1,usuario)));
							jogar(posicoes, this.computador,this.tC);
							
							posicoes.clear();
							this.usuario.setVez(false);
							this.computador.setVez(true);
							this.escolha = -1;
							
							
						}
					}
				}
				
			}
			
			if(this.computador.getVez() && iniciado)
			{
				//System.out.println("eh a vez do pc");
				jogadaCpu();
			}
				
		}
			 
	}
	
	public void criarTabela(JButton[][] tabela,int espX)
	{
		for (int i = 0; i < DIM; i++)
		{
			for (int j = 0; j < DIM; j++)
			{
				
				tabela[i][j] = new JButton();
				tabela[i][j].setText("");
				tabela[i][j].addActionListener(this);  
				tabela[i][j].setBounds(TAM_BOTAO*j+espX,TAM_BOTAO*i+ESP_Y,TAM_BOTAO,TAM_BOTAO);
				getContentPane().add(tabela[i][j]);
			}
		}
	}
	

	
	public void jogar(ArrayList<Tupla> posicoes, Object jogador, JButton [][]matrizBotoes)
	{
		
		if (jogador instanceof Usuario)
		{
			int l,c;
			
			Usuario u = (Usuario) jogador;
			int[][] matrizLogica = u.getMatrizLogica();
			
			for (int i = 0; i < posicoes.size(); i++)
			{
				l = posicoes.get(i).getI();
				c = posicoes.get(i).getJ();
				
				
				if (l >= 0 && l < DIM && c >= 0 && c < DIM)
				{
					if(matrizLogica[l][c] == 2)
					{
						if (matrizBotoes[l][c].isEnabled())
						{
							
							Caca c1 = (Caca) recEmbarcacao(2,u);
							
							if (c1.descobrir())
							{
								u.getEmbarcacoes().remove(u.indexEmbarcacao(c1));
								this.btnCaca.setEnabled(false);
								this.btnCaca.setForeground(Color.RED);
								this.btnCaca.setText("X");
						
							}
							
							matrizBotoes[l][c].setBackground(Color.PINK); 
							matrizBotoes[l][c].setEnabled(false);
						}
						
					}else if(matrizLogica[l][c] == 0)
					{
						if (matrizBotoes[l][c].isEnabled())
						{
							PortaAviao p1 = (PortaAviao) recEmbarcacao(0,u);
							
							if (p1.descobrir())
							{
								u.getEmbarcacoes().remove(u.indexEmbarcacao(p1));
							}
							
							matrizBotoes[l][c].setBackground(Color.BLUE); 
							matrizBotoes[l][c].setEnabled(false);
						}
						
					}else if(matrizLogica[l][c] == 1)
					{
						if (matrizBotoes[l][c].isEnabled())
						{
							NavioEscolta n1 = (NavioEscolta) recEmbarcacao(1,u);
							
							if (n1.descobrir())
							{
								u.getEmbarcacoes().remove(u.indexEmbarcacao(n1));
								this.btnNavioEsc.setEnabled(false);
								this.btnNavioEsc.setForeground(Color.RED);
								this.btnNavioEsc.setText("X");
							}
							
							matrizBotoes[l][c].setBackground(Color.BLACK); 
							matrizBotoes[l][c].setEnabled(false);
						}
						
					}else if(matrizLogica[l][c] == 3)
					{
						Submarino s1 = (Submarino) recEmbarcacao(1,u);
						
						if (s1.descobrir())
						{
							u.getEmbarcacoes().remove(u.indexEmbarcacao(s1));
							this.btnSub.setEnabled(false);
							this.btnSub.setForeground(Color.RED);
							this.btnSub.setText("X");
							
						}
						matrizBotoes[l][c].setBackground(Color.GRAY); 
						
						matrizBotoes[l][c].setEnabled(false);
						
					}else if(matrizLogica[l][c] == 5)
					{
						if (matrizBotoes[l][c].isEnabled())
						{
							matrizBotoes[l][c].setIcon(xImage); 
							matrizBotoes[l][c].setEnabled(false);
						}
					}
				
				}
			}
			
			if(u.getEmbarcacoes().isEmpty()|| (btnCaca.isEnabled() == false && btnNavioEsc.isEnabled() == false && btnSub.isEnabled() == false))
			{
				this.iniciado = !this.iniciado; 
				JOptionPane.showMessageDialog(null,"Cpu ganhou");
			}
			
		}else
		{
			int l,c;
			
			Computador cpu = (Computador) jogador;
			int[][] matrizLogica = cpu.getMatrizLogica();
			
			for (int i = 0; i < posicoes.size(); i++)
			{
				l = posicoes.get(i).getI();
				c = posicoes.get(i).getJ();
				
				if (l >= 0 && l < DIM && c >= 0 && c < DIM)
				{
					if(matrizLogica[l][c] == 2)
					{
						if (matrizBotoes[l][c].isEnabled())
						{
							
							Caca c1 = (Caca) recEmbarcacao(2,cpu);
							
							
							if (c1.descobrir())
							{
								cpu.getEmbarcacoes().remove(cpu.indexEmbarcacao(c1));
								
								int rmv = 0;
								
								for (int j = 0; j < cpu.getPossiveisEscolhas().size(); j++)
								{
									if (cpu.getPossiveisEscolhas().get(j)  == 2)
									{
										rmv = j;
										break;
									}
								}
								
								cpu.getPossiveisEscolhas().remove(rmv);
								
								
							}
							
							
							
							matrizBotoes[l][c].setBackground(Color.PINK); 
							matrizBotoes[l][c].setEnabled(false);
						}
						
					}else if(matrizLogica[l][c] == 0)
					{
						if (matrizBotoes[l][c].isEnabled())
						{
							PortaAviao p1 = (PortaAviao) recEmbarcacao(0,cpu);
							
							
							if (p1.descobrir())
							{
								cpu.getEmbarcacoes().remove(cpu.indexEmbarcacao(p1));
							}
							
							
							matrizBotoes[l][c].setBackground(Color.BLUE); 
							matrizBotoes[l][c].setEnabled(false);
						}
						
					}else if(matrizLogica[l][c] == 1)
					{
						if (matrizBotoes[l][c].isEnabled())
						{
							NavioEscolta n1 = (NavioEscolta) recEmbarcacao(1,cpu);
							
							if (n1.descobrir())
							{
								cpu.getEmbarcacoes().remove(cpu.indexEmbarcacao(n1));
								
								int rmv = 0;
								
								for (int j = 0; j < cpu.getPossiveisEscolhas().size(); j++)
								{
									if (cpu.getPossiveisEscolhas().get(j)  == 1)
									{
										rmv = j;
										break;
									}
								}
								
								cpu.getPossiveisEscolhas().remove(rmv);
							}
							
							matrizBotoes[l][c].setBackground(Color.BLACK); 
							matrizBotoes[l][c].setEnabled(false);
						}
						
					}else if(matrizLogica[l][c] == 3)
					{
						Submarino s1 = (Submarino) recEmbarcacao(3,cpu);
						
						
						if (s1.descobrir())
						{
							cpu.getEmbarcacoes().remove(cpu.indexEmbarcacao(s1));
							int rmv = 0;
							
							for (int j = 0; j < cpu.getPossiveisEscolhas().size(); j++)
							{
								if (cpu.getPossiveisEscolhas().get(j) == 3)
								{
									rmv = j;
									break;
								}
							}
							
							cpu.getPossiveisEscolhas().remove(rmv);
						}
						
						matrizBotoes[l][c].setBackground(Color.GRAY); 
						matrizBotoes[l][c].setEnabled(false);
						
					}else if(matrizLogica[l][c] == 5)
					{
						if (matrizBotoes[l][c].isEnabled())
						{
							matrizBotoes[l][c].setIcon(xImage); 
							matrizBotoes[l][c].setEnabled(false);
						}
					}
				
				}
			}
			
			if(cpu.getEmbarcacoes().isEmpty()|| cpu.getPossiveisEscolhas().isEmpty())
			{
				this.iniciado = !this.iniciado; 
				Verificar();
				JOptionPane.showMessageDialog(null,"Voce ganhou");
			}
		}
		
		
		//System.out.println("entrei");
		
		posicoes.clear();
	}
	
	public void jogadaCpu()
	{
		
	    int escolha = this.computador.getPossiveisEscolhas().get(0); // 1navio  2 caca  3 sub
	    
	    int lin = this.computador.getPossiveisJogadas().get(0).getI();
	    int col = this.computador.getPossiveisJogadas().get(0).getJ();
	    
	    this.computador.getPossiveisJogadas().remove(0);
	
		if (this.tU[lin][col].isEnabled())
		{
			
			//System.out.println("button i: "+i+"button j: "+j);
			if (escolha == 2)
			{
			
				posicoes = this.usuario.receberJogada(this.computador.atirar(lin, col, recEmbarcacao(2,computador)));

				jogar(posicoes, this.usuario,this.tU);
				
				posicoes.clear();
				this.computador.setVez(false);
				this.usuario.setVez(true);
				this.escolha = -1;
				
				
			}else if (escolha == 3)
			{
				//System.out.println("escolha 3");
				
				posicoes = this.usuario.receberJogada(this.computador.atirar(lin, col, recEmbarcacao(3,computador)));
				jogar(posicoes, this.usuario,this.tU);
				
				posicoes.clear();
				this.computador.setVez(false);
				this.usuario.setVez(true);
				this.escolha = -1;
				
			}else if (escolha == 1)
			{
				
				posicoes = this.usuario.receberJogada(this.computador.atirar(lin, col, recEmbarcacao(1,computador)));
				jogar(posicoes, this.usuario,this.tU);
				
				posicoes.clear();
				this.computador.setVez(false);
				this.usuario.setVez(true);
				this.escolha = -1;
				
				
			}
			
		}
		else
		{
			System.out.println("entrei");
			jogadaCpu();
			this.computador.setVez(false);
		}
		
	}

	public Object recEmbarcacao(int escolha,Object jogador)
	{
		Object embarcacao = null;
		if (jogador instanceof Usuario)
		{
			
			if (escolha == 1)
			{
				for(int i=0; i < this.usuario.getEmbarcacoes().size() ; i++)
				{
					if (this.usuario.getEmbarcacoes().get(i) instanceof NavioEscolta)
					{
						//System.out.println("rec emb");
						//System.out.println(this.usuario.getEmbarcacoes().get(i));
						return this.usuario.getEmbarcacoes().get(i);
					}
				}
			}else if (escolha == 2)
			{
				for(int i=0; i < this.usuario.getEmbarcacoes().size() ; i++)
				{
					if (this.usuario.getEmbarcacoes().get(i) instanceof Caca)
					{
						return this.usuario.getEmbarcacoes().get(i);
					}
				}
				
			}else if (escolha == 3)
			{
				for(int i=0; i < this.usuario.getEmbarcacoes().size() ; i++)
				{
					if (this.usuario.getEmbarcacoes().get(i) instanceof Submarino)
					{
						return this.usuario.getEmbarcacoes().get(i);
					}
				}
				
			}else if (escolha == 0)
			{
				for(int i=0; i < this.usuario.getEmbarcacoes().size() ; i++)
				{
					if (this.usuario.getEmbarcacoes().get(i) instanceof PortaAviao)
					{
						return this.usuario.getEmbarcacoes().get(i);
					}
				}
			}
			
			
		}else
		{
			if (escolha == 1)
			{
				for(int i=0; i < this.computador.getEmbarcacoes().size() ; i++)
				{
					if (this.computador.getEmbarcacoes().get(i) instanceof NavioEscolta)
					{
						//System.out.println("rec emb");
						//System.out.println(this.usuario.getEmbarcacoes().get(i));
						return this.computador.getEmbarcacoes().get(i);
					}
				}
			}else if (escolha == 2)
			{
				for(int i=0; i < this.computador.getEmbarcacoes().size() ; i++)
				{
					if (this.computador.getEmbarcacoes().get(i) instanceof Caca)
					{
						return this.computador.getEmbarcacoes().get(i);
					}
				}
				
			}else if (escolha == 3)
			{
				for(int i=0; i < this.computador.getEmbarcacoes().size() ; i++)
				{
					if (this.computador.getEmbarcacoes().get(i) instanceof Submarino)
					{
						return this.computador.getEmbarcacoes().get(i);
					}
				}
				
			}else if (escolha == 0)
			{
				for(int i=0; i < this.computador.getEmbarcacoes().size() ; i++)
				{
					if (this.computador.getEmbarcacoes().get(i) instanceof PortaAviao)
					{
						return this.computador.getEmbarcacoes().get(i);
					}
				}
			}
			
		}
		
		//System.out.println("rec emb");
		return embarcacao;
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
		//System.out.println("mouse cliked");

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
		//System.out.println("mouse pressed");
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
