package br.com.contmatic.empresav2.template;

import java.util.Random;

import br.com.contmatic.empresav2.model.Departamento;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class DepartamentoTempleateLoader implements TemplateLoader {

    private static final String[] NOMES = {"Diretória", "Expedição", "Contábil", "Recepção","Segurança", "Qualidade", "Infraestrutura"};

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
        Fixture.of(Departamento.class).addTemplate("valido", new Rule(){{
            add("idDepartamento", random(Long.class, range(10L, 299L))); //Os primeiros 10 serão exclusivos.
            add("nome", random("Diretória", "Expedição", "Contábil", "Recepção","Segurança", "Qualidade", "Infraestrutura"));//new Random().nextInt(NOMES.length) Não Funcionou
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
