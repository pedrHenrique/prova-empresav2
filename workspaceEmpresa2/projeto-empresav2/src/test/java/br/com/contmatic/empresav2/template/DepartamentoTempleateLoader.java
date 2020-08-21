package br.com.contmatic.empresav2.template;

import java.util.Random;

import br.com.contmatic.empresav2.model.Departamento;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class DepartamentoTempleateLoader implements TemplateLoader {

    private static final String[] NOMES = {"Gilberto Araujo", "Pedro Henrique", "Andre Crespo", "Ana Oliveira","Julio Machado", "Gabriel Bueno", "Paula Cristina"};

    private String nome;
    /*
     * Departamento Possui
     * ID
     * NOME
     * RAMAL
     *      
     */
    
    @Override
    public void load() {
        Fixture.of(Departamento.class).addTemplate("valid", new Rule(){{
            add("id", random(Long.class, range(10L, 300L))); //Os primeiros 10 serão exclusivos.
            add("nome", random((new Random().nextInt(NOMES.length))));
            add("ramal", random(Integer.class, range(1, 999)));
        }});
        
    }
    
    public static String[] getNomes() {
        return NOMES;
    }

    public String getNome() {
        return nome;
    }
    

}
