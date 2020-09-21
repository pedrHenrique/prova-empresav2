package br.com.contmatic.empresav2.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//TODO JAutoDoc Departamento
public class Departamento {

    // Variáveis
    @NotNull(message = "Departamento não pode possuir um valor vazio")
    @Max(value = 300, message = "O número máximo de departamentos que podem ser cadastrados é 300")
    @Positive(message = "Id não pode ser um valor negativo ou zero")
    private long idDepartamento;

    @NotBlank(message = "Campo nome não pode ficar vázio")
    @Length(max = 40, message = "Nome de departamento não deve ser tão grande.")
    @Pattern(regexp = "[a-zA-ZáéíóúâêîôûãõÁÉÍÓÚÂÊÎÔÛÃÕçÇ ]+", message = "Este nome para o departamento é inapropriado. Recomenda-se mudar") // Nome não deveria ser registrado fora de um Padrão de A-z
    private String nome;

    @NotNull(message = "O Ramal não pode ficar vazio")
    @Range(min = 1, max = 999, message = "Ramal só pode conter até 3 dígitos.")
    @Positive(message = "Ramal não pode ser um valor negativo nem zero.")
    private int ramal; // Adicionaro forma de contato recebendo Ramal e Email futuramente (se possível)

    // NotEmpty -> nesta posição serve para ensinuar que a Collection Departamento não pode estar vazia.
    private static Collection<@NotEmpty Departamento> departamentoLista = new HashSet<>();

    private static final Logger logger = LogManager.getLogger(Departamento.class);

    // Construtores
    public Departamento(long idDepartamento, String nome, int ramal) {
        this.idDepartamento = idDepartamento;
        this.nome = nome;
        this.ramal = ramal;
        salvaRegistro(this);
    }

    public Departamento() { //Teste
        this.nome = "Não Cadastrado";  
    }

    // Métodos

    public Collection<Departamento> listarDepartamentos() {
        // TODO Me lembrar de remover esse método
        for(Departamento departamento : getDepartamentoLista()) {
            // logger.fine("An exception occurred with message: {}", message); right way to do it
            logger.info(departamento);

        }
        return departamentoLista;
    }

    public Departamento registraDep(long id, String nome, int ramal) {
        return new Departamento(id, nome, ramal);
    }

    private void salvaRegistro(Departamento departamento) {
        checkArgument(!(getDepartamentoLista().contains(departamento)), getIdDepartamento() + " já possui registro\n");
        getDepartamentoLista().add(departamento);
        logger.info("Departamento Registrado com Sucesso");
    }

    /**
     * Busca o departamento informado pelo ID.<br>
     * Caso o departamento não for encontrado. O Método retornará nulo.
     *
     * @param id - O ID do departamento.
     * @return O Departamento se encontrado, caso contrário, retornará <b>null</b>
     */
    public static Departamento solicitaDep(long id) {
        Iterator<Departamento> iterator = getDepartamentoLista().iterator();
        Departamento obj = new Departamento();

        while (iterator.hasNext() && obj.getIdDepartamento() != id) {
            obj = iterator.next();
        }

        try {
            checkArgument(obj.getIdDepartamento() == id, "O Departamento " + id + " não foi encontrado\n");
            return obj;
        } catch (IllegalArgumentException | NullPointerException ie) {
            logger.info("O Departamento solicitado não foi encontrado");
            return null;
        }
    }

    /**
     * Busca o departamento solicitado pelo ID.
     *
     * @param id - o ID do departamento.
     * @throws IllegalArgumentException caso o departamento que tentou remover não exista
     */
    public void removeDep(long id) {
        Departamento obj = solicitaDep(id);
        checkArgument(obj != null, "O Departamento de ID: " + id + " que você tentou remover não está cadastrado");
        getDepartamentoLista().remove(obj);
        logger.info("Departamento Removido com Sucesso!");
    }

    /**
     * Método alternativo para remoção de departamento.
     *
     * @param departamento - você pode fornecer o próprio departamento que deseja remover
     */
    public static void removeDep(Departamento departamento) {
        checkArgument(getDepartamentoLista().contains(departamento), "O Departamento que tentou remover não está cadastrado");
        getDepartamentoLista().remove(departamento);
        logger.info("Departamento Removido com Sucesso!");
    }
    
    public static Departamento naoCadastrado() {        
        return new Departamento();
    }

    // Getters And Setters

    public long getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(long idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;

    }

    public int getRamal() {
        return ramal;
    }

    public void setRamal(int ramal) {
        this.ramal = ramal;
    }

    public static Collection<Departamento> getDepartamentoLista() {
        return departamentoLista;
    }

    /* //TODO O ideal seria que:
     * 
     * -> Um Departamento não pode ser registrado se seu ID já estiver sendo utilizado.
     * Ou se o Ramal informado já estiver sendo utilizado também.
     */
    
    
    @Override 
    public int hashCode() { // Não entendi pq quando dando new, podemos utilizar o append e o hashcode...
        return new HashCodeBuilder().append(this.idDepartamento).append(this.ramal).hashCode();
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
        Departamento dp = (Departamento) obj;
        return new EqualsBuilder()
                // .appendSuper(super.equals(obj)), remover isso, faz com que o código funcione como esperado
                .append(this.idDepartamento, dp.idDepartamento).append(this.ramal, dp.ramal).isEquals();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
