package br.com.contmatic.empresav2.model;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Endereco { //Endereco ainda está em fase de testes. Porém já possui uma estrutura relativamente montada

    /*
     * Desenvolver Endereco com calma e ser Simples na Construção - 1º Endereço Precisar ser um Enum - 2º Endereço Precisa ser um SET. E Permitir somente um item do mesmo tipo (Mesmo Endereco) - 3º Já
     * que Endereço vai ser o responsável por armazenar os objetos, deve-se se ter as opções de > Registrar/Remover/Solicitar
     */

    public enum EnderecoEnum { // Rua Mangericao - N.: 83 - Jardim Eliane -
        ;

       protected static EnumSet<EnderecoEnum> estadoLista = EnumSet.allOf(EnderecoEnum.class); //Talvez será uma boa forma de armazenar todas as constantes

        private String descricao;
        private String valor;

        EnderecoEnum(String descricao, Endereco valor) {
            this.descricao = descricao;
        }
        
        EnderecoEnum(String descricao) {
            this.descricao = descricao;
        }
        
        EnderecoEnum() {
            
        }
        
        public String formata() {
            return null;
        }

    }

    private String cep;
    private String rua;
    private String bairro;
    private String cidade;
    private Estado estado;
    private static Set<Endereco> cepMap = new HashSet<>();
    // Map Regras.
    // Chaves Não Podem ser repetidas.

    public Endereco(String rua, String bairro, String cep, String cidade, Estado estado) {
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.cep = cep;
        this.estado = estado;
        registraEndereco(this);
    }
    
    public Endereco() {

    }

    private void registraEndereco(Endereco end) {
        if (cepMap.contains(end)) {
            System.out.println("Endereço já cadastrado");
        } else {
            cepMap.add(end);
            System.out.println(end.toString());
        }
    }

    public Endereco cadastraEndereco(String rua, String bairro, String cep, String cidade, Estado estado) {
        return new Endereco(rua, bairro, cep, cidade, estado);
    }
    
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(cep);
    }

    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
    
    @Override
    public String toString() {
        return "Endereco [cep=" + cep + ", rua=" + rua + ", bairro=" + bairro + ", cidade=" + cidade + ", estado=" + estado + "]";
    }

    //Para Testes
    public static void main(String[] args) {
        //Estados.estadoLista.add(Estados.SC);
        System.out.println();
        //new Endereco().cadastraEndereco("Mangericao", "Eliane", "03575090", "São Paulo", "SP");
        //new Endereco().cadastraEndereco("Mangericao", "Eliane", "03575090", "São Paulo", "SP");
        Endereco end = new Endereco().cadastraEndereco("Nespereira", "Eliane", "03575090","São Paulo", Estado.SC);
        System.out.println(end.estado.getDescricaoViaLista());
    }



    // public String toString() {
    // return ToStringBuilder.reflectionToString(EnderecoEnum.ENDERECO + " " + Cep + " " + Rua + ", Cidade=" + Cidade + ", Estado=" + Estado + "]");
    // }
}
