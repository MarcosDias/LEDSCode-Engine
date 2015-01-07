package br.edu.ifes.leds.ledscode.ctrl;


import ctrl.SpringRooController;
import model.*;

import javax.xml.bind.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * @author Marcos Dias
 */
public class EngineController {

    /**
     * Dado um projeto, gera o código
     * @param specification modelo tem os parametros e diagramas para geracao de codigo
     * @return returna true caso nao haja nenhum erro
     */
    // TODO - Projeto gerado estaticamente em Spring Roo
    public boolean generateCodeProject(Specification specification) {

        SpringRooController springROO = new SpringRooController();
        springROO.createProject(specification);

        return true;
    }

    /**
     * Metodo usado apos a escrita do arquivo xml
     * necessario para "acordar" a engine e continuar o processo
     * @param nomeArquivo Nome do arquivo que sera lido
     * @return returna true caso nao haja nenhum erro
     */
    public boolean acordarEngine(String nomeArquivo) throws JAXBException {
        Specification specification = (Specification) lerArquivoXML(nomeArquivo);
        
        this.generateCodeProject(specification);
        
        return true;
    }

    /**
     * Converte um string com estrutura XML em um objeto.
     * @param nomeArquivo string com o conteudo XML a ser convertido em objeto.
     * @return retorna um novo objeto de clazz.
     */
    public Object lerArquivoXML(String nomeArquivo) throws JAXBException {
        JAXBContext context = null;
        File file = new File(nomeArquivo);

        context = JAXBContext.newInstance(new Specification().getClass());
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return unmarshaller.unmarshal(file);
    }

    /**
     * TODO - REMOVER ATRIBUTO E METODOS SEGUINTES
     * Estes atributos e metodos seguintes, seram obitido de forma dinamica nas proximas versoes,
     * portanto, por agora utilizaremos valores estaticos
     */
    private ArrayList<String> languages;
    private HashMap<String, ArrayList<String>> frameworks;
    private HashMap<String, ArrayList<String>> dataBase;
    private HashMap<String, ArrayList<String>> orms;
    private HashMap<String, ArrayList<String>> applicationTypes;
    
    public ArrayList<String> getLanguages(){
        return this.languages;
    }

    public ArrayList<String> getFrameworks(String language) {
        return this.frameworks.get(language);
    }

    public ArrayList<String> getDataBase(String framework) {
        return this.dataBase.get(framework);
    }

    public ArrayList<String> getOrms(String framework) {
        return this.orms.get(framework);
    }

    public ArrayList<String> getApplicationType(String framework) {
        return this.applicationTypes.get(framework);
    }

    public EngineController() {
        this.languages = new ArrayList<String>();
        this.languages.add("Java");

        ArrayList<String> listFramewoksJava = new ArrayList<String>();
        listFramewoksJava.add("Spring_Roo");
        this.frameworks = new HashMap<String, ArrayList<String>>();
        this.frameworks.put("Java", listFramewoksJava);

        ArrayList<String> listDataBaseROO = new ArrayList<String>();
        listDataBaseROO.add("HYPERSONIC_IN_MEMORY");
        this.dataBase = new HashMap<String, ArrayList<String>>();
        this.dataBase.put("Spring_Roo", listDataBaseROO);

        ArrayList<String> listOrmsROO = new ArrayList<String>();
        listOrmsROO.add("HIBERNATE");
        this.orms = new HashMap<String, ArrayList<String>>();
        this.orms.put("Spring_Roo", listOrmsROO);

        ArrayList<String> listApplicationTypes = new ArrayList<String>();
        listApplicationTypes.add("WEB");
        this.applicationTypes = new HashMap<String, ArrayList<String>>();
        this.applicationTypes.put("Spring_Roo", listApplicationTypes);
    }
}
