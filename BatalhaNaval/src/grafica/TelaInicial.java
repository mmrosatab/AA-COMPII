package grafica;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TelaInicial extends JFrame implements ActionListener
{
	private JPanel painel;
	private JButton btnJogar;
	private JButton btnSair;
	private JButton btnAleat;
	private JButton btnManual;
	private JLabel titulo;
	private TelaManual telaSelecao;
	private TelaAleatorio telaAleatorio;
	private TelaJogo telaJogo;
	
	
	public TelaInicial()
	{
		super("Batalha Naval");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setSize(400,400);
		setBounds(100, 100, 450, 400);
		painel = new JPanel();
		//painel.setBorder(new EmptyBorder(20, 20, 20, 20));
		setContentPane(painel);
		painel.setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);
	
		//botoes
		titulo = new JLabel("Batalha Naval");
		titulo.setFont(new Font("Courier", Font.BOLD,30));
		titulo.setBounds(120,100,300,80);
		titulo.setForeground(Color.blue);
		painel.add(titulo);

		btnAleat = new JButton("Aleatório");
		btnAleat.setFont(new Font("Courier", Font.BOLD,18));
		btnAleat.setBounds(60, 250,150, 40);
		btnAleat.setForeground(Color.blue);
		painel.add(btnAleat);
		
		btnManual = new JButton("Manual");
		btnManual.setFont(new Font("Courier", Font.BOLD,18));
		btnManual.setBounds(250, 250,150, 40);
		btnManual.setForeground(Color.blue);
		painel.add(btnManual);
		
		btnSair = new JButton("Sair");
		btnSair.setFont(new Font("Courier", Font.BOLD,18));
		btnSair.setBounds(190, 300, 80, 40);
		btnSair.setForeground(Color.blue);
		painel.add(btnSair);
		
		
		//add botoes para fazerem acoes
		btnAleat.addActionListener(this);
		btnManual.addActionListener(this);
		btnSair.addActionListener(this);
	
	}
		
	public void actionPerformed(ActionEvent e) 
	{
		
		if(e.getSource() == btnAleat)
		{
			telaAleatorio = new TelaAleatorio();
			telaAleatorio.setVisible(true);
			setVisible(false);
	
		}else if(e.getSource() == btnManual)
		{
			
			telaSelecao = new TelaManual();
			telaSelecao.setVisible(true);
			setVisible(false);
			
		}else  if(e.getSource() == btnSair)
		{
			System.exit(0);
		}
		
	}

 

}
