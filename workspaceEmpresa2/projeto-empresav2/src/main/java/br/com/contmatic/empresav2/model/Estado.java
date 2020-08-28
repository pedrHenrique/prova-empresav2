package br.com.contmatic.empresav2.model;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

/*
 * Classe Estado.
 * Contem a lista de todos os estados do Brasil, mais os seus respectivos nomes.
 */

public enum Estado {
     
    // Definição de Todos os estados mais a sua descrição
    AC("Acre"),
    AL("Alagoas"),
    AP("Amapá"),
    AM("Amazonas"),
    BA("Bahia"),
    CE("Ceará"),
    DF("Distrito Federal"),
    ES("Espírito Santo"),
    GO("Goiás"),
    MA("Maranhão"),
    MT("Mato Grosso"),
    MS("Mato Grosso do Sul"),
    MG("Minas Gerais"),
    PA("Pará"),
    PB("Paraíba"),
    PR("Paraná"),
    PE("Pernanbuco"),
    PI("Piauí"),
    RJ("Rio de Janeiro"),
    RN("Rio Grande do Norte"),
    RS("Rio Grande do Sul"),
    RO("Rondônia"),
    RR("Roraima"),
    SC("Santa Catarina"),
    SP("São Paulo"),
    SE("Sergipe"),
    TO("Tocantins");
                    
    private static EnumSet<Estado> estadoLista = EnumSet.allOf(Estado.class); // Talvez será uma boa forma de armazenar todas as constantes
    private String descricao; // nome do estado

    Estado(String descricao) {
        this.descricao = descricao;
    }

    Estado() {

    }

    public String getDescricaoViaLista() { // método mais efetivo na busca de comportamentos, porém ficará visualmente menos elegante.
        checkArgument(estadoLista.contains(this));
        return this.descricao;
    }
}
