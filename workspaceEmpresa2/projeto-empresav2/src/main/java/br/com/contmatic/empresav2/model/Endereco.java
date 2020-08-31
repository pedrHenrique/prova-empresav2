package br.com.contmatic.empresav2.model;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.com.parg.viacep.ViaCEP;
import br.com.parg.viacep.ViaCEPException;
import org.apache.commons.lang3.NotImplementedException;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class Endereco { // Não consegui fazer com que Endereco seja uma ENUM

    // Variáveis

    private String rua; // VIACEP Logradouro
    private String bairro;
    private String num;
    private String cidade; // VIACEP Localidade
    private String cep;
    private Estado estado;
    private Set<Endereco> enderecoLista = new HashSet<>();
    // Map Regras.
    // Chaves Não Podem ser repetidas.

    // Construtores

    public Endereco(String rua, String bairro, String num, String cep, String cidade, Estado estado) {
        setRua(rua);
        setBairro(bairro);
        setNumero(num);
        setCep(cep);
        setCidade(cidade);
        setEstado(estado);
        registraEndereco(this);
    }

    public Endereco(String rua, String bairro, String num, String cep, String cidade, String estado) {
        setRua(rua);
        setBairro(bairro);
        setNumero(num);
        setCep(cep);
        setCidade(cidade);
        setEstado((Estado.formata(estado)));
        registraEndereco(this);
    }

    public Endereco() {

    }

    // Métodos

    private void registraEndereco(Endereco end) {
        if (enderecoLista.contains(end)) {
            System.out.println("Endereço já cadastrado");
        } else {
            enderecoLista.add(end);
            System.out.println(end.toString());

        }
    }

    /**
     * Verifica e busca seu endereço se existente nos bancos do VIACEP. <br>
     * Está é a forma recomendada e mais simples de cadastrar seu endereco.
     *
     * @param cep Ex.: 03575090
     * @throws ViaCEPException caso o CEP não seja encontrado
     */
    public Endereco cadastraEnderecoViaCEP(String cep, String numero) throws ViaCEPException {
        ViaCEP viaCEP = new ViaCEP();

        try {
            viaCEP.buscar(cep.replaceAll("\\D", ""));
        } catch (Exception viaCEPException) {
            throw new IllegalArgumentException("O CEP inserido não é válido");
        }

        return new Endereco(viaCEP.getLogradouro(), viaCEP.getBairro(), numero, viaCEP.getCep(), viaCEP.getLocalidade(), viaCEP.getUf());
    }

    public Endereco alteraEndereco(String cep) {
        throw new NotImplementedException("Método ainda não implementado");
    }

    /**
     * Cadastra o endereco inserido. <br>
     * Método alternativo caso o VIACEP pare de funcionar eventualmente.
     *
     * @param rua - Ex.: Alameda dos Cisnes
     * @param bairro - Ex.: Vila Jardim Vitória
     * @param numero - O Número da sua residência
     * @param cep - Ex.: 74865355 (<b> Sem o traço </b>)
     * @param cidade - Ex.: Goiânia
     * @param estado - Ex.: GO
     * @return retorna o objeto Endereco.
     */
    public Endereco cadastraEndereco(String rua, String bairro, String numero, String cep, String cidade, Estado estado) {
        return new Endereco(rua, bairro, numero, cep, cidade, estado);
    }

    // Getters and Setters

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        rua = rua.replaceAll("[ ]+", " "); //remove os espaços em brancos
        rua = checkNotNull(rua.replaceAll(("^[Rr][Uu][Aa]"), ""), "Rua não deve estar vazia");
        checkArgument(rua.matches("[\\wªáéíóúâêîôûãõÁÉÍÓÚÂÊÎÔÛÃÕ ]+") && (rua.length() >= 2 && rua.length() <= 40),
            "Este tipo de nome de rua não pode ser aceito, verifique se você não digitou nada de errado");
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        bairro = checkNotNull(bairro.replaceAll(("[ ]+"), " "), "Bairro não pode ficar vazio");
        checkArgument(bairro.matches("[a-zA-ZáéíóúâêîôûãõÁÉÍÓÚÂÊÎÔÛÃÕ ]+") && (bairro.length() >= 5 && bairro.length() <= 40),
            "Este tipo de nome de bairro não pode ser aceito, verifique se você não digitou nada de errado");
        this.bairro = bairro;
    }

    public String getNumero() {
        return num;
    }

    public void setNumero(String numero) {
        checkArgument(numero.matches("[\\dA-Z]+") && (numero.length() <= 5),
            numero + " não pode ser registrado como um número de casa. Numero pode conter 5 dígitos contando com a letra de identificação");
        this.num = checkNotNull(numero, "O numero da residência precisa ser inserido");
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        cidade = checkNotNull(cidade.replaceAll(("[ ]+"), " "), "Cidade não pode ficar em branco");
        checkArgument(cidade.matches("[a-zA-ZáéíóúâêîôûãõÁÉÍÓÚÂÊÎÔÛÃÕ ]+") && (cidade.length() >= 5 && cidade.length() <= 60),
            "Este tipo de nome de cidade não pode ser aceito, verifique se você não digitou nada de errado");
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        cep = checkNotNull(cep.replaceAll("\\D", ""), "CEP não pode ficar em branco"); // Cep deve sair no formato: 03575-090
        checkArgument(cep.length() == 8, "Digite apenas os números do CEP");
        this.cep = cep.substring(0, 5) + "-" + cep.substring(5, 8);
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = checkNotNull(estado, "O estado " + estado + " não pode ter sido encontrado. Tente novamente");
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    // Para Testes
    public static void main(String[] args) throws ViaCEPException {
        Endereco end = new Endereco();
        end.cadastraEnderecoViaCEP("58052232", "105");
        Endereco end2 = new Endereco();
        end2.cadastraEnderecoViaCEP("58020673", "2098");
        Endereco end3 = new Endereco();
        end3.cadastraEnderecoViaCEP("41502-280", "21A");
        end.cadastraEnderecoViaCEP("41502-280", "304C");

        new Endereco().cadastraEndereco("rua São José", "Eliane", "83", "03575-090", "Santa Catarina", Estado.SC);
        new Endereco().cadastraEndereco("RUA Vila Viana", "Boa Vista", "205", "60860-660", "Ceara", Estado.CE);
        new Endereco().cadastraEndereco("                           ", "Boa Vista", "205", "60860-660", "Ceara", Estado.CE); // deve falhar
    }

    public Set<Endereco> getEnderecoLista() {
        return enderecoLista;
    }

    @Override
    public String toString() {
        return "Endereco [Rua:" + rua + ", Bairro: " + bairro + ", Numeração: " + num +  ", CEP: " + cep + ", Cidade: " + cidade +", Estado: " + estado + "]";
    }

}
