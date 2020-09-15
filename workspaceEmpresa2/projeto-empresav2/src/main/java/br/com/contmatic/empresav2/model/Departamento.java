package br.com.contmatic.empresav2.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Verify.verify;

/*
 * From StackOverflow
 * 
 * Verify is used to ensure that Code which has been engineered to do a 
 * certain thing is actually doing that thing. In spirit:
 * 
 * Preconditions are expected to fail when bad input is passed to a certain portion of the program 
 * A precondition check ensures that the caller of a public method has obeyed the requirements of the method's specification
 * 
 * P.S.: Eu sei que isso é rambiarra, mas será útil ter essa informação para quando eu precisar decidir
 */

//TODO JAutoDoc Departamento
public class Departamento {

    // Variáveis
    @Max(value = 300, message = "O número máximo de departamentos que podem ser cadastrados é 300")
    @Positive(message = "Id não pode ser um valor negativo")
    private long idDepartamento;

    @NotBlank(message = "Campo nome não pode ficar vázio")
    @Length(min = 2, max = 40, message = "Nome de departamento não deve ser tão grande.")
    @Pattern(regexp = "[a-zA-ZáéíóúâêîôûãõÁÉÍÓÚÂÊÎÔÛÃÕçÇ ]+", message = "Nome para departamento inapropriado. Recomenda-se mudar") // Nome não deveria ser registrado fora de um Padrão de A-z
    private String nome;

    @Range(min = 1, max = 999, message = "Ramal não deve ser tão longo assim, respeite a regra de 3 dígitos apenas.")
    @Positive(message = "Ramal não pode ser um valor negativo")
    private int ramal; // Adicionaro forma de contato recebendo Ramal e Email futuramente (se possível)

    // @NotEmpty // Maybe???
    private static Collection<Departamento> departamentoLista = new HashSet<>();

    // Construtores
    public Departamento(long idDepartamento, String nome, int ramal) {
        this.idDepartamento = idDepartamento;
        this.nome = nome;
        this.ramal = ramal;
        salvarRegistro(this);
    }

    public Departamento() {

    }

    // Métodos

    public Collection<Departamento> listarDepartamentos() {
        for(Departamento departamento : departamentoLista) {// remover depois
            System.out.println(departamento); // remover depois

        }
        return departamentoLista;
    }

    public Departamento registraDep(long id, String nome, int ramal) {
        return new Departamento(id, nome, ramal);
    }

    private void salvarRegistro(Departamento departamento) {
        verify(!(departamentoLista.contains(departamento)), getIdDepartamento() + " já possui registro\n");
        departamentoLista.add(departamento);
    }

    public Departamento solicitaDep(long id) {
        /*
         * -> Está Forma de buscar departamentos não é muito "adequada" Pensar em outra forma de solicitar departamentos, seja atráves de uma enum ou até mesmo atraves de um contain this
         */
        Iterator<Departamento> iterator = getDepartamentoLista().iterator();
        Departamento obj = new Departamento();

        while (iterator.hasNext() && obj.getIdDepartamento() != id) {
            obj = iterator.next();
        }
        verify(obj.getIdDepartamento() == id, "O Departamento " + id + " não foi encontrado\n");
        return obj;
    }

    public Departamento removeDep(long id) {
        Iterator<Departamento> iterator = getDepartamentoLista().iterator();
        Departamento obj = new Departamento();

        while (iterator.hasNext() && obj.getIdDepartamento() != id) {
            obj = iterator.next();
        }
        verify(obj.getIdDepartamento() == id, "Departamento " + id + " não existe\n");
        iterator.remove();
        listarDepartamentos().toString();

        return obj;
    }

    // Getters And Setters

    public long getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(long idDepartamento) {
        // checkArgument(idDepartamento > 0 && idDepartamento <= 300, "O ID do departamento deve ser maior que zero e menor que 300!");
        this.idDepartamento = idDepartamento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        // Formata campos com espaço vazio
        // Impede a utilização de números, caracteres especiais e valores nulos
        // nome = nome.replaceAll(("[ ]+"), " ");
        // checkArgument(nome.matches("[a-zA-ZáéíóúâêîôûãõÁÉÍÓÚÂÊÎÔÛÃÕçÇ ]+") && (nome.length() > 2 && nome.length() < 30),
        // "Departamento não pode conter caracteres especiais, ou um tamanho muito grande");
        this.nome = nome;

    }

    public int getRamal() {
        return ramal;
    }

    public void setRamal(int ramal) {
        // checkArgument(ramal > 0 && ramal <= 999, "Ramal pode ser apenas de 1 a 999");
        this.ramal = ramal;
    }

    public static Collection<Departamento> getDepartamentoLista() {
        return departamentoLista;
    }

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
