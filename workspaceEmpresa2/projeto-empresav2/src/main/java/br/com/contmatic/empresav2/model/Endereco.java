package br.com.contmatic.empresav2.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.log4j.Level;
import org.hibernate.validator.constraints.Length;

import com.google.common.base.VerifyException;
import com.sun.istack.internal.NotNull;

import br.com.parg.viacep.ViaCEP;
import br.com.parg.viacep.ViaCEPException;
import org.apache.commons.lang3.NotImplementedException;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Verify.verify;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.LogManager;

// Classe Endereco Será futuramente trabalhada de melhor forma...
public class Endereco { // Não consegui fazer com que Endereco seja uma ENUM

    // Variáveis

    @NotBlank(message = "Sua rua não tem nome???")
    @Length(min = 1, max = 60, message = "Nome da rua não deve ser tão grande.")
    @Pattern(regexp = "[\\wªáéíóúâêîôûãõÁÉÍÓÚÂÊÎÔÛÃÕ- ]+", message = "Este tipo de nome de rua não pode ser aceito. Verifique se você não digitou nada de errado")
    private String rua; // VIACEP Logradouro

    @NotBlank
    @Length(min = 5, max = 40, message = "Nome da bairro não deve ser tão grande.")
    @Pattern(regexp = "[a-zA-ZáéíóúâêîôûãõÁÉÍÓÚÂÊÎÔÛÃÕ ]+", message = "Este tipo de nome de bairro não pode ser aceito. Verifique se você não digitou nada de errado")
    private String bairro;

    @NotBlank(message = "Numero de identificação não pode ficar vazio")
    @Max(value = 5, message = "O tamanho máximo para um número de identificação é de 5")
    @Pattern(regexp = "[\\dA-Z]+", message = "Numero só pode conter dígitos e uma a letra de identificação")
    private String num;

    @NotBlank(message = "CEP não pode ficar vazio")
    @Length(min = 9, message = "O tamanho mínimo do CEP deveria ser de 9 caracteres")
    @Pattern(regexp = "[\\D-]+", message = "CEP deve conter apenas números") // Teste promissor
    private String cep;

    @NotBlank(message = "Cidade não pode ficar vazia")
    @Length(min = 4, max = 30, message = "Nome da cidade não deve ser tão grande")
    @Pattern(regexp = "[a-zA-ZáéíóúâêîôûãõÁÉÍÓÚÂÊÎÔÛÃÕ ]+", message = "Este tipo de nome de cidade não pode ser aceito, verifique se você não digitou nada de errado") // Teste promissor
    private String cidade; // VIACEP Localidade

    @NotNull
    @Valid
    private Estado estado;

    // Variável logger estática que referencia uma instancia de Logger da minha classe
    private static final Logger logger = LogManager.getLogger(Endereco.class.getName());

    @NotEmpty
    private static Set<Endereco> enderecoLista = new HashSet<>();
    // Map Regras.
    // Chaves Não Podem ser repetidas.

    // Construtores

    /**
     * Instância uma nova classe endereco recebendo os valores pelo usuário.
     *
     * @param rua Nome da sua rua
     * @param bairro Nome do seu bairro
     * @param num Numero da sua residência
     * @param cep Seu CEP
     * @param cidade Nome da sua cidade
     * @param estado O estado informado atráves da classe Estados. <br>
     *        Exemplo: <code>Estado.(O seu Estado)</code>
     * 
     */
    public Endereco(String rua, String bairro, String num, String cep, String cidade, Estado estado) {
        this.rua = rua.replaceAll(("^[Rr][Uu][Aa] "), "");
        this.bairro = bairro;
        this.num = num;
        this.cep = Util.formataCEP(cep);
        this.cidade = cidade;
        this.estado = estado;
        registraEndereco(this);
    }

    /**
     * Instância uma nova classe endereco recebendo os valores do VIACEP.
     *
     * @param num Numero da sua residência informado por você
     * @param cep CEP informado por você
     * @param rua Nome da sua rua obtido pelo viaCEP
     * @param bairro Nome do seu bairro obtido pelo viaCEP
     * @param cidade Nome da sua cidade obtido pelo viaCEP
     * @param estado Nome do estado obtido pelo viaCEP
     */
    public Endereco(String rua, String bairro, String num, String cep, String cidade, String estado) { // recebe os dados do via cep
        this.rua = rua.replaceAll(("^[Rr][Uu][Aa] "), "");
        this.bairro = bairro;
        this.num = num;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = Estado.formata(estado);
        registraEndereco(this);
    }

    public Endereco() {

    }

    // Métodos

    /**
     * Registra endereco se o mesmo já não existir dentro da lista de enderecos cadastrados
     *
     * @param end - O endereço passado.
     */
    private void registraEndereco(Endereco end) {
        verify(!(getEnderecoLista().contains(end)), "Este endereço:\n" + end + "já está cadastrado. Insira um endereço alternativo, ou verifique se vc digitou a numeração correta.\n"); // Verifique se o enderecoLista já não contem esse tipo de objeto
        getEnderecoLista().add(end);
        logger.info("Endereco Cadastrado", end.toString());

    }
    
    /**
     * Busca e verifica se seu endereço existe nos registros do VIACEP. <br>
     * Está é a forma recomendada e mais prática de cadastrar seu endereco.
     *
     * @param cep - Ex.: 03575090
     * @param numero - O Número da sua residência Ex.: 406, 2091, 107A, 1017B.
     * @throws ViaCEPException caso o CEP não seja encontrado
     */
    public static Endereco cadastraEnderecoViaCEP(String cep, String numero) {
        ViaCEP viaCEP = new ViaCEP();

        try {
            viaCEP.buscar(cep.replaceAll("\\D", ""));
        } catch (Exception viaCEPException) {
            throw new IllegalArgumentException("O CEP inserido não é válido, ou não pode ter sido encontrado.\nSe estiver sem internet, utilize outro método de cadastro");
        }

        return new Endereco(viaCEP.getLogradouro(), viaCEP.getBairro(), numero, viaCEP.getCep(), viaCEP.getLocalidade(), viaCEP.getUf());
    }

    /**
     * Cadastra o endereco inserido. <br>
     * Método alternativo caso o VIACEP pare de funcionar eventualmente.
     *
     * @param rua - Ex.: Alameda dos Cisnes
     * @param bairro - Ex.: Vila Jardim Vitória
     * @param numero - O Número da sua residência
     * @param cep - Ex.: 74865355 ou 74865-355
     * @param cidade - Ex.: Goiânia
     * @param estado - Ex.: GO
     * @return retorna o objeto Endereco.
     */
    public static Endereco cadastraEndereco(String rua, String bairro, String numero, String cep, String cidade, Estado estado) {
        return new Endereco(rua, bairro, numero, cep, cidade, estado);
    }
    
    /**
     * Solicita endereco irá procurar dentro da lista de Enderecos pelo cep e número fornecido.
     * 
     *
     * @param cep - Ex.: 03575090 ou 03575-090
     * @param numero - O Número da sua residência Ex.: 406, 2091, 107A, 1017B.
     * 
     * @return Caso a função tenha encontrado o endereço solicitado, ela retornará ele.<br>
     *         Caso ela não tenha o encontrado, ela retornará null.
     */
    public Endereco solicitaEndereco(String cep, String numero) {
        Iterator<Endereco> iterator = getEnderecoLista().iterator();
        Endereco obj = new Endereco();

        cep = Util.formataCEP(cep);
        while (iterator.hasNext() && (obj.getCep() != cep && obj.getNumero() != numero)) {
            obj = iterator.next();
        }
        try {
            checkArgument(obj.getCep().equals(cep) && obj.getNumero().equals(numero));
            return obj;
        } catch (IllegalArgumentException | NullPointerException ie) { // O NullPointer pode ser gerado caso a lista esteja vazia na hora da busca e o objeto obtido esteja nulo.
            ie.printStackTrace();
            logger.error("O Endereço solicitado não foi encontrado ou não existe\n");
            return null;
        }
    }

    /**
     * Remove endereco com base em um CEP e a numeração da residência.
     *
     * @param cep - utilizado para descobrir a localização da residência
     * @param numero - numero utilizado para descobrir qual a residência daquele endereço deve ser removida
     */
    public static void removeEndereco(String cep, String numero) {
        Endereco obj = new Endereco().solicitaEndereco(cep, numero);

        checkNotNull(obj, "O endereço que tentou remover não existe"); // O objeto recebido de solicitaEndereco, não deve ser nulo

        // Caso o retorno não tenha sido nulo, o objeto esperado foi encontrado.
        try {
            getEnderecoLista().remove(obj);
        } catch (IllegalArgumentException ie) {
            logger.error("Algo deu errado.... É possível que alguém já tenha removido esse endereço");
        }

    }

    /**
     * Método alternativo de remoção de Endereco.
     *
     * @param endereco - você pode fornecer o próprio objeto que deseja remover.
     */
    public static void removeEndereco(Endereco endereco) {
        try {
            checkArgument(getEnderecoLista().contains(endereco));
            getEnderecoLista().remove(endereco);
        } catch (IllegalArgumentException ie) {
            // ie.printStackTrace();
            logger.error("O Endereço que tentou remover não existe");
        }
    }

    // Getters and Setters

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumero() {
        return num;
    }

    public void setNumero(String numero) {

        this.num = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public static Set<Endereco> getEnderecoLista() {
        return enderecoLista;
    }

    @Override
    public int hashCode() { // Não entendi pq quando dando new, podemos utilizar o append e o hashcode...
        return new HashCodeBuilder().append(this.cep).append(this.num).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Endereco fn = (Endereco) obj;
        return new EqualsBuilder().append(this.cep, fn.cep).append(this.num, fn.num).isEquals();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE).concat("\n");
    }

}
