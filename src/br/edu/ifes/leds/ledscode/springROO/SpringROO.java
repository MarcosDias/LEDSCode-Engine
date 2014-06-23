package br.edu.ifes.leds.ledscode.springROO;

import java.io.IOException;
import java.util.List;

import org.eclipse.uml2.uml.Property;

import br.edu.ifes.leds.ledscode.arquivo.Arquivo;
import br.edu.ifes.leds.ledscode.metadominio.ClassDom;

public class SpringROO {
	private String scriptRoo;
	private String nomeProjeto;
	
	public void criarProjeto(String string) {
		this.scriptRoo += "// CRIANDO O NOVO PROJETO\n";
		this.scriptRoo += "project --topLevelPackage " + string + "\n\n";
		this.nomeProjeto = string;
	}

	public void configBanco(String string) {
		this.scriptRoo += "// CONFIGURANDO O BANCO DE DADOS COM JPA\n";
		this.scriptRoo += "jpa setup --provider " + string + " --database HYPERSONIC_IN_MEMORY\n\n";
	}

	public void print(char saida) {
		switch (saida) {
		case 'T':
			System.out.println(this.scriptRoo);
			break;
			
		case 'A':
			try {
				Arquivo arquivo = new Arquivo(this.nomeProjeto, ".roo");
				arquivo.escreverArquivo(this.scriptRoo);
				arquivo.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
		
	}
	
	public void configEntidades(List<ClassDom> classes) {
		this.scriptRoo += "// CRIANDO AS ENTIDADES DE DOMINIO\n";
		
		for(ClassDom dom: classes){
			if(dom.getClassDom().getName().equals("Jogador")){
				String className = dom.getClassDom().getName();
				this.scriptRoo += "entity jpa --class ~.domain." + className + " --activeRecord true --testAutomatically\n";
				
				for(Property prop: dom.getAtributos()){
					this.scriptRoo += "field "+ prop.getType().getName().toLowerCase() + " --fieldName "+ prop.getName() + " --notNull --sizeMin 2 --sizeMax 100";
				}
			}
		}
		this.scriptRoo += "\n\n";
		for(ClassDom dom: classes){
			if(!dom.getClassDom().getName().equals("Jogador")){
				String className = dom.getClassDom().getName();
				this.scriptRoo += "entity jpa --class ~.domain." + className + " --activeRecord true --testAutomatically\n";
				
				for(Property prop: dom.getAtributos()){
					if(prop.getType().getName().equals("Jogador")){
						this.scriptRoo += "field set --fieldName " + prop.getName() + 
							" --type ~.domain."+prop.getType().getName() + " --cardinality ONE_TO_MANY\n";
					}
					else{
						this.scriptRoo += "field "+ prop.getType().getName().toLowerCase() + 
								" --fieldName "+ prop.getName() + " --notNull --sizeMin 2 --sizeMax 100\n";
					}
				}
			}
		}
		this.scriptRoo += "\n";
	}
	
	public void criarTestIntegracao(List<ClassDom> classes) {
		this.scriptRoo += "// CRIANDO OS TESTES DE INTEGRACAO\n";
		
		for(ClassDom dom: classes){
			this.scriptRoo+= "test integration --entity ~.domain." + dom.getClassDom().getName() +"\n";
		}
		
		this.scriptRoo += "\n";
	}
	
	public void webMvcSetup(String tipoWeb) {
		this.scriptRoo += "// CRIANDO A PARTE WEB\n";
		this.scriptRoo += "web " + tipoWeb + " setup\n";
		this.scriptRoo += "web "+ tipoWeb +" all --package ~.web\n\n";
		
		if(tipoWeb.equals("mvc")){
			this.scriptRoo += "// INTERNACIONALIZACAO\n";
			this.scriptRoo += "web "+ tipoWeb + " language --code de\n";
			this.scriptRoo += "web "+ tipoWeb + " language --code es\n\n";
		}
	}
	
	public void testSelenium(List<ClassDom> classes, String tipoWeb){
		if(tipoWeb.equals("mvc")){
			this.scriptRoo += "// CRIANDO OS TESTES SELENIUM\n";
			
			for(ClassDom dom: classes){
				this.scriptRoo+= "selenium test --controller ~.web." + dom.getClassDom().getName() +"Controller\n";
			}
			
			this.scriptRoo += "\n";
		}
	}

	public void configLog(){
		this.scriptRoo += "// CONFIGURACAO DO LOG\n";
		this.scriptRoo += "logging setup --level INFO\n\n";
	}
	
	public void quit(){
		this.scriptRoo += "quit";
	}
	
	public SpringROO() {
		this.scriptRoo = "";
	}
	
	public String getScriptRoo() {
		return scriptRoo;
	}
	public void setScriptRoo(String scriptRoo) {
		this.scriptRoo = scriptRoo;
	}

}
