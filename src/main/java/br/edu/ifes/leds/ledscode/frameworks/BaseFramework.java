package br.edu.ifes.leds.ledscode.frameworks;

import java.io.IOException;

import lombok.Getter;
import lombok.Setter;
import br.edu.ifes.leds.ledscode.arquivo.Arquivo;

@Getter
@Setter
public class BaseFramework {
	public final static char TERMINAL = 'T';
	public final static char ARQUIVO = 'A';
	protected String script;
	protected String nomeProjeto;

	/**
	 * Joga o script para uma saida, seja terminal ou arquivo texto
	 * 
	 * @param saida
	 *            - Tipo de saida: - T ~> Terminal - A ~> Arquivo
	 */
	public void print(char saida, String extensao) {
		switch (saida) {
		case 'T':
			System.out.println(this.script);
			break;

		case 'A':
			try {
				Arquivo arquivo = new Arquivo(this.nomeProjeto, "." + extensao);
				arquivo.escreverArquivo(this.script);
				arquivo.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}
	
	public BaseFramework(){
		this.script = "";
		this.nomeProjeto = "";
	}
}
