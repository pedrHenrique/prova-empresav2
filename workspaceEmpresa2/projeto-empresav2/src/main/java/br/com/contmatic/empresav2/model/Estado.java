package br.com.contmatic.empresav2.model;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import static com.google.common.base.Preconditions.checkArgument;
/*
 * Desenvolver Endereco com calma e ser Simples na Construção - 1º Endereço Precisar ser um Enum - 2º Endereço Precisa ser um SET. E Permitir somente um item do mesmo tipo (Mesmo Endereco) - 3º Já
 * que Endereço vai ser o responsável por armazenar os objetos, deve-se se ter as opções de > Registrar/Remover/Solicitar
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
