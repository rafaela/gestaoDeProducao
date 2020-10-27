package br.com.fabrica.gerencia.ig;

import static br.com.fabrica.constantes.Constantes.CAD_INSUMO;
import static br.com.fabrica.constantes.Constantes.ERR_CAD_INSUMO;
import static br.com.fabrica.constantes.Constantes.ERR_NAO_CAD_INSUMO;
import static br.com.fabrica.constantes.Constantes.ERR_NOME_INSUMO;
import static br.com.fabrica.constantes.Constantes.ERR_QTD_INSUMO;
import static br.com.fabrica.constantes.Constantes.INSUMO;
import static br.com.fabrica.gui.EntradaESaida.msgErro;
import static br.com.fabrica.gui.EntradaESaida.msgInfo;
import static br.com.fabrica.validacoes.Validacoes.obtemCodigo;
import static br.com.fabrica.validacoes.Validacoes.verificaNome;
import static br.com.fabrica.validacoes.Validacoes.verificaPreco;
import static br.com.fabrica.validacoes.Validacoes.verificaQuantidade;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;

import br.com.fabrica.arquivos.ArquivoInsumo;
import br.com.fabrica.gerencia.modelo.GerenciaInsumo;
import br.com.fabrica.gui.IgInsumos;
import br.com.fabrica.modelo.Insumo;

public class GerenciaIgInsumo {
	private static ArquivoInsumo arquivoInsumo = new ArquivoInsumo();
	
	/**
	 * Obt�m os dados informados nos componentes presentes na tela {@link IgInsumos},
	 * atribui o objeto � classe {@link Insumo} e salva o insumo cadastrado no arquivo
	 * correspondente.
	 * @param comboBox - <code>JCombobox</code> : informa os produtos cadastrados. 
	 * A partir do produto escolhido, � poss�vel cadastrar os insumos referente ao produto.
	 * @param tfUnidade - <code>JTextField</code> : obt�m a unidade usada por cada insumo 
	 * para produzir uma unidade.
	 * @param jf - <code>JFrame</code> : janela respons�vel pela tela de cadastrar insummos.
	 * @param table - <code>JTable</code> : tabela onde ser�o cadastrados os dados dos insumos.
	 */
	public static void cadastraInsumo(JComboBox<String> comboBox, JTextField tfUnidade, 
			JFrame jf, JTable table) {
		int codigo = obtemCodigo(comboBox.getSelectedItem().toString());
		List<Insumo> ins = cadastraInsumosProduto(codigo, table, jf);
			
		boolean cadastrado = arquivoInsumo.escreveInsumosNoArquivo(ins);
		if(cadastrado)
			msgInfo(jf, CAD_INSUMO, INSUMO);
		else
			msgErro(jf, ERR_CAD_INSUMO, INSUMO);
	}
	
	public static void obtemInsumosProduto(int codigo, JTable table, JFrame jf){
		GerenciaInsumo gi = new GerenciaInsumo();
		
		List<Insumo> insumos = gi.obtemInsumosProduto(codigo); 
		System.out.println(insumos.size());
		if(insumos.size() > 0) {
			for(int i = 0; i < insumos.size(); i++) {
				table.setValueAt(insumos.get(i).getNome(), i, 0);
				table.setValueAt(insumos.get(i).getQuantidade(), i, 1);
				table.setValueAt(insumos.get(i).getPrecoUnitario(), i, 2);
				System.out.println(insumos.get(i));
			}
			System.out.println(insumos.size());
		}else
			msgInfo(jf, ERR_NAO_CAD_INSUMO, INSUMO);
	}

	public static List<Insumo> cadastraInsumosProduto(int codigo, JTable table, JFrame jf){
		
		GerenciaInsumo gi = new GerenciaInsumo();
		List<Insumo> insumos = gi.obtemInsumosProduto(codigo);
		
		if(table.getValueAt(0, 0).toString() == null) {
			msgErro(jf, ERR_NOME_INSUMO, INSUMO);
		}else {
			for (int i = 0; i < table.getRowCount(); i++) {
				String nome = (String) table.getValueAt(i, 0);
				if(nome == null){
					msgErro(jf, ERR_NAO_CAD_INSUMO, INSUMO);
					break;
				}
				else {
					Insumo insumo = new Insumo();
					insumo.setCodigo(codigo);
					String validaNome = verificaNome(table.getValueAt(i, 0).toString());
					if(validaNome == null) {
						msgErro(jf, ERR_NOME_INSUMO, INSUMO);
						break;
					}
					else
						insumo.setNome(validaNome);
					float validaQuantidade = verificaQuantidade(table.getValueAt(i, 2).toString());
					System.out.println(validaQuantidade);
						insumo.setQuantidade(validaQuantidade);
					float validaPreco = verificaPreco((String)table.getValueAt(i, 2));
					if(validaPreco == 0) {
						msgErro(jf, ERR_QTD_INSUMO, INSUMO);
						break;
					}	
					else
						insumo.setPrecoUnitario(validaPreco);
					
					insumo.setCodigoProduto(codigo);
					insumos.add(insumo);
				}
			}//for
			
				
		}
		
		return insumos;
		
	}
	
}
