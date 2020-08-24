package br.com.contmatic.empresav2.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Endereco {

    // Ainda será muuiito refatorado
    public enum EnderecoEnum { // Rua Mangericao - N.: 83 - Jardim Eliane -

                              ENDERECO("Endereço:"),
                              CEP("Cep:"),
                              RUA("Rua:");
        // CIDADE,
        // ESTADO;

        private String descricao;
        private String valor;

        EnderecoEnum(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    private String Cep;
    private String Rua;
    private String Cidade;
    private String Estado;
    private static Map<Endereco, EnderecoEnum> cepMap = new HashMap<>();
    // Map Regras.
    // Chaves Não Podem ser repetidas.

    public Endereco(String cep, String rua, String cidade, String estado) {
        Cep = cep;
        Rua = rua;
        Cidade = cidade;
        Estado = estado;
        registraEndereco(this);
    }

    private void registraEndereco(Endereco end) {
        if (cepMap.containsKey(end)) {
            System.out.println("Endereço já cadastrado");
        } else {
            cepMap.put(end, EnderecoEnum.CEP);
        }
    }

    public static void main(String[] args) {
        Endereco end = new Endereco("03575090", "Mangericao", "São Paulo", "SP");
        Endereco end1 = new Endereco("0459789", "Barros", "São Paulo", "SP");
        Endereco end2 = new Endereco("0459789", "Barros2", "São Paulo", "SP");
        System.out.println(cepMap);

    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return "Endereco [Cep=" + Cep + ", Rua=" + Rua + ", Cidade=" + Cidade + ", Estado=" + Estado + "]";
    }

//    public String toString() {
//        return ToStringBuilder.reflectionToString(EnderecoEnum.ENDERECO + " " + Cep + " " + Rua + ", Cidade=" + Cidade + ", Estado=" + Estado + "]");
//    }
}
