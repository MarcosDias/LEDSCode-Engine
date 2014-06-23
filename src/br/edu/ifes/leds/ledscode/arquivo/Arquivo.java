package br.edu.ifes.leds.ledscode.arquivo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Arquivo {
	private FileWriter fw;
	private BufferedWriter escritor;
	
	public void escreverArquivo(String texto) throws IOException{
		this.escritor.write(texto);
	}
	
	public void close() throws IOException{
		if(this.escritor != null) this.escritor.close();
		if(this.fw != null) this.fw.close();
	}
	
	public Arquivo(String nome, String extensao) throws IOException {
		this.fw = new FileWriter("arquivo/" + nome + extensao);
		this.escritor = new BufferedWriter(this.fw);
	}
}
