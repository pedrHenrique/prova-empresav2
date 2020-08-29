package br.com.contmatic.empresav2.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
    
    //funcional porém precisa ser refatorado
    public static Estado formata(String uf) {
        List<Estado> list = new ArrayList<Estado>(EnumSet.allOf(Estado.class));
        System.out.println(list);
        for (int i = 0; i < list.size(); i++){
            if(list.get(i).name().equals(uf)) {
               System.out.println("Deu Certo");
               break;
            }
            return Enum.valueOf(Estado.class, uf);
        }
        return null;
    }

    /**
     * Retorna a descrição da instância que solicitou a descrição. 
     *
     * @return the descricao via lista
     */
    public String getDescricaoViaLista() { 
        checkArgument(estadoLista.contains(this));
        return this.descricao;
    }
}
