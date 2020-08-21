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

public class Departamento {

    // Variáveis
    @NotBlank
    @Max(300) 
    @Positive
    private long idDepartamento;

    @NotBlank
    @Length(min = 2, max = 40)
    // TODO modificar e testar o [^\\w]+
    @Pattern(regexp = "[^\\w]+", message = "Nome inapropriado. Recomenda-se mudar") // Nome não deveria ser registrado fora de um Padrão de A-z
    private String nome;

    @Range(min = 1, max = 999)
    @Positive
    private int ramal; // Adicionaro forma de contato recebendo Ramal e Email futuramente (se possível)

    // @NotEmpty // Maybe???
    private static Collection<Departamento> departamentoLista = new HashSet<>();

    // Construtores
    public Departamento(long idDepartamento, String nome, int ramal) {
        setIdDepartamento(idDepartamento);
        setNome(nome);
        setRamal(ramal);
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
        verify(!(departamentoLista.contains(checkNotNull(departamento))), getIdDepartamento() + " já possui registro\n");
        departamentoLista.add(departamento);
    }

    public Departamento solicitaDep(long id) {
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
        checkArgument(idDepartamento > 0 && idDepartamento <= 300, "O ID do departamento deve ser maior que zero e menor que 300!");
        this.idDepartamento = checkNotNull(idDepartamento, "O valor não pode estar nulo");
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        // Formata campos com espaço vazio
        // Impede a utilização de números, caracteres especiais e valores nulos
        nome = nome.replaceAll(("[ ]+"), " ");
        checkArgument(nome.matches("[a-zA-ZáéíóúâêîôûãõÁÉÍÓÚÂÊÎÔÛÃÕçÇ ]+") && (nome.length() > 2 && nome.length() < 30), "Departamento não pode conter caracteres especiais, acentos ou um tamanho muito grande");
        this.nome = checkNotNull(nome);
    }

    public int getRamal() {
        return ramal;
    }

    public void setRamal(int ramal) {
        checkArgument(ramal > 0 && ramal <= 999, "Ramal pode ser apenas de 1 a 999");
        this.ramal = checkNotNull(ramal, "Ramal não pode estar vazio");
    }

    public static Collection<Departamento> getDepartamentoLista() {
        return departamentoLista;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (idDepartamento ^ (idDepartamento >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Departamento other = (Departamento) obj;
        if (idDepartamento != other.idDepartamento)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return ("Departamento: " + nome + ", idDepartamento: " + idDepartamento + ", Ramal: " + ramal);

    }

}
