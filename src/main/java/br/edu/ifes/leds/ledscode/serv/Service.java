package br.edu.ifes.leds.ledscode.serv;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by marcosdias on 12/11/14.
 */
@Getter
public class Service {

    /**
     * TODO - REMOVER ATRIBUTO E METODOS SEGUINTES
     * Estes atributos e metodos seguintes, seram obitido de forma dinamica nas proximas versoes,
     * portanto, por agora utilizaremos valores staticos
     */
    private ArrayList<String> languages;
    private HashMap<String, ArrayList<String>> frameworks;
    private HashMap<String, ArrayList<String>> dataBase;
    private HashMap<String, ArrayList<String>> orms;
    private HashMap<String, ArrayList<String>> applicationTypes;

    public ArrayList<String> getFrameworks(String language){
        return this.frameworks.get(language);
    }

    public ArrayList<String> getDataBase(String framework){
        return this.dataBase.get(framework);
    }

    public ArrayList<String> getOrms(String framework){
        return this.orms.get(framework);
    }

    public ArrayList<String> getApplicationType(String framework){
        return this.applicationTypes.get(framework);
    }

    public Service() {
        this.languages = new ArrayList<String>();
        this.languages.add("Java");

        ArrayList<String> listFramewoksJava = new ArrayList<String>();
        listFramewoksJava.add("SPRINGROO");
        this.frameworks = new HashMap<String, ArrayList<String>>();
        this.frameworks.put("Java", listFramewoksJava);

        ArrayList<String> listDataBaseROO = new ArrayList<String>();
        listDataBaseROO.add("HYPERSONIC_IN_MEMORY");
        this.dataBase = new HashMap<String, ArrayList<String>>();
        this.dataBase.put("SPRINGROO", listDataBaseROO);

        ArrayList<String> listOrmsROO = new ArrayList<String>();
        listOrmsROO.add("HIBERNATE");
        this.orms = new HashMap<String, ArrayList<String>>();
        this.orms.put("SPRINGROO", listOrmsROO);

        ArrayList<String> listApplicationTypes = new ArrayList<String>();
        listApplicationTypes.add("WEB");
        this.applicationTypes = new HashMap<String, ArrayList<String>>();
        this.applicationTypes.put("SPRINGROO", listApplicationTypes);
    }
}
