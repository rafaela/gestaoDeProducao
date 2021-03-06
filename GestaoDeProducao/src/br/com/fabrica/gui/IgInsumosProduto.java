package br.com.fabrica.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import br.com.fabrica.arquivos.ArquivoProduto;
import br.com.fabrica.gerencia.ig.GerenciaIgInsumoProduto;
import br.com.fabrica.modelo.Produto;
import br.com.fabrica.validacoes.Validacoes;
import static br.com.fabrica.constantes.Constantes.*;
/**
 * Classe responsavel por criar a tela de cadastro de Insumos.
 * @author Rafaela e Guilherme
 *
 */
@SuppressWarnings("serial")
public class IgInsumosProduto extends JFrame {
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNome;
	private JButton btnGravar;
	private JButton btnCancelar;
	private JFrame jf;
	private JTextField tfTamanho;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JTable table;
	private JComboBox<String> comboBox;
	private List<Produto> listaProdutos;
	public static ArquivoProduto arqProduto = new ArquivoProduto();
	private DefaultTableModel defaultTableModel;
	/**
	 * Create the panel.
	 */
	public IgInsumosProduto() {
		getContentPane().setLayout(null);
		jf = new JFrame();
		jf.setAutoRequestFocus(false);
		jf.getContentPane().setLayout(null);

		// Define que o programa deve ser finalizado quando o usu�rio clicar no bot�o Fechar da janela.
		jf.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Define a janela como n�o redimension�vel.
		jf.setResizable(false);

		jf.setSize(501, 595);
		jf.setLocationRelativeTo(null);
		
		jf.setTitle("Cadastro de Insumos do produto");

		btnGravar = new JButton("Gravar");
		jf.getContentPane().add(btnGravar);
		btnGravar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnGravar.setBounds(260, 495, 96, 25);

		btnCancelar = new JButton("Cancelar");
		jf.getContentPane().add(btnCancelar);
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnCancelar.setBounds(366, 495, 96, 25);

		lblNome = new JLabel("Nome do Produto:");
		jf.getContentPane().add(lblNome);
		lblNome.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNome.setBounds(38, 67, 122, 16);

		lblNewLabel_1 = new JLabel("Tamanho da unidade:");
		jf.getContentPane().add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(38, 108, 139, 16);


		lblNewLabel = new JLabel("Insumo");
		jf.getContentPane().add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(202, 11, 83, 19);

		tfTamanho = new JTextField();
		tfTamanho.setColumns(10);
		tfTamanho.setBounds(187, 102, 141, 30);
		jf.getContentPane().add(tfTamanho);

		panel = new JPanel();
		panel.setBounds(38, 215, 424, 257);
		jf.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		scrollPane.setFocusTraversalKeysEnabled(false);
		scrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		scrollPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		scrollPane.setAutoscrolls(true);
		scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(scrollPane);

		table = new JTable();
		
		String[] colunas = new String[] {"Nome", "Quantidade"};
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					//TODO Fazer o cursor ficar na primeira coluna de todas as novas linhas.
					defaultTableModel.addRow(new String[2]);
				}
			}
		});

		table.setBounds(new Rectangle(22, 0, 300, 300));
		scrollPane.setViewportView(table);
		defaultTableModel = new DefaultTableModel(colunas, 1);
		table.setModel(defaultTableModel);

		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(117);
		table.getColumnModel().getColumn(1).setPreferredWidth(95);
		
		comboBox = new JComboBox<String>();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				GerenciaIgInsumoProduto.obtemInsumosProduto(Validacoes.obtemCodigo(
						comboBox.getSelectedItem().toString()), defaultTableModel, jf,tfTamanho);
			}
		});
		
		comboBox.setBounds(187, 61, 277, 30);
		jf.getContentPane().add(comboBox);

		listaProdutos = new ArquivoProduto().leProdutosNoArquivo();
		comboBox.addItem(VALOR_DEFAULT_COMBOBOX);
		for (Produto prod : listaProdutos)
			comboBox.addItem(String.format("%d - %s", prod.getCodigo(),prod.getNome()));

		

		JLabel lblListaDeInsumos = new JLabel("Lista de Insumos");
		lblListaDeInsumos.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblListaDeInsumos.setBounds(190, 165, 141, 19);
		jf.getContentPane().add(lblListaDeInsumos);

		JLabel lblNewLabel_2 = new JLabel("A quantidade de insumo  \u00E9 por tamanho de cada unidade do produto.");
		lblNewLabel_2.setBounds(48, 190, 414, 14);
		jf.getContentPane().add(lblNewLabel_2);

		jf.setVisible(true);

		
		btnGravar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GerenciaIgInsumoProduto.cadastraInsumo(comboBox, tfTamanho, jf, defaultTableModel);
				defaultTableModel.setNumRows(1);
				defaultTableModel.setValueAt("", 0, 0);
				defaultTableModel.setValueAt("", 0, 1);
				comboBox.setSelectedIndex(0);
				tfTamanho.setText("");
				

			}
		});

		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jf.setVisible(false);
				IgProdutos igProdutos = new IgProdutos();
				igProdutos.getJf().setVisible(true);
			}
		});

	}

	/**
	 * Obt�m a janela
	 * @return janela contendo 
	 */
	public JFrame getJf() {
		return jf;
	}

	/**
	 * Fornece a janela
	 * @param jf Janela
	 */
	public void setJf(JFrame jf) {
		this.jf = jf;
	}
}
