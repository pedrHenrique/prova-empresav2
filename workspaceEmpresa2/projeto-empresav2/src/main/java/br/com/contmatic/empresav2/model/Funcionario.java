package br.com.contmatic.empresav2.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.br.CPF;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Verify.verify;

//TODO JAutoDoc Funcionario
public class Funcionario {

    // Variáveis

    // Número máximo de funcionarios é 3000 e seus valores só podem ser positivos
    @Max(value = 3000, message = "Numero máximo de funcionarios não pode ser maior que 3000")
    @Positive(message = "ID não pode ser negativo")
    private long idFuncionario;

    // Nome não pode estar vazio, possui um tamanho específico, e uma recomendação de expressão regular
    @NotBlank(message = "Campo nome não pode ficar vázio")
    @Length(min = 3, max = 40, message = "Nome não pode ser desse tamanho.")
    @Pattern(regexp = "regexp = \"[a-zA-ZáéíóúâêîôûãõÁÉÍÓÚÂÊÎÔÛÃÕ ]+", message = "Nome invalido. Recomenda-se mudar")
    private String nome;

    // CPF não pode estar vazio e possui seu próprio tipo de annotation
    @NotEmpty(message = "CPF não pode ficar vázio")
    @CPF
    private String cpf;

    // Cep não pode ser vazio e deve sempre ter o valor mínimo de 8 caracteres
    /*
     * @NotBlank
     * 
     * @Min(8)
     * 
     * @Pattern(regexp = "[\\D-]") // Testar Futuramente
     */
    private Endereco endereco;

    // Email possui sua própria annotation, tamanho, expressão regular, e não deve estar vazio
    @Email
    @Size(min = 7, max = 50)
    @Pattern(regexp = ".+@.+\\.[a-z]+", message = "Email Invalido")
    private String contato;

    // tipoContato sempre será um valor de referência presente na classe, portanto ele não poderá ser nulo
    @NotNull
    @Valid
    private Util tipoContato;

    // Salario possui um valor mínimo, máximo e sempre deve ser positivo
    @Positive(message = "Salario não pode ser negativo")
    @NotBlank(message = "Salario não pode estar vazio")
    @Range(min = 1, max = 50000)
    private double salario;

    @NotNull
    @Valid
    private Departamento departamento = new Departamento();
    private static Collection<Funcionario> funcionarioLista = new HashSet<>();

    // Construtores

    public Funcionario(long idFuncionario, String nome, String cpf, String cep, String contato, long dep, double salario) {
        this.idFuncionario = idFuncionario;
        this.nome = nome;
        this.cpf = Util.formataCPF(cpf);
        // setCep(cep);
        this.contato = Util.formataContato(contato);
        this.tipoContato = Util.tipoContato(contato);
        this.departamento = buscaDepartamento(departamento.solicitaDep(dep));
        this.salario = salario;
        salvaRegistro(this);
    }

    public Funcionario() {

    }

    // Métodos

    public Collection<Funcionario> listaFuncionario() {
        return getFuncionarioLista();

    }

    public Funcionario solicitaFuncionario(long id) {
        Iterator<Funcionario> iterator = getFuncionarioLista().iterator();
        Funcionario obj = new Funcionario();
        while (iterator.hasNext() && obj.getIdFuncionario() != id) {
            obj = iterator.next();
        }
        verify(obj.getIdFuncionario() == id, "O Funcionario com o ID: " + id + " não existe\n");
        return obj;

    }

    public Funcionario cadastraFuncionario(long id, String nome, String cpf, String cep, String email, long dep, double salario) {
        Funcionario fn = new Funcionario(id, nome, cpf, cep, email, dep, salario);
        return fn;
    }

    public void removeFuncionario(long id) {
        Iterator<Funcionario> iterator = getFuncionarioLista().iterator();
        Funcionario obj = new Funcionario();

        while (iterator.hasNext() && obj.getIdFuncionario() != id) {
            obj = iterator.next();
        }
        verify(obj.getIdFuncionario() == id, "Funcionario com o ID: " + id + " não existe\n");
        iterator.remove();

    }

    private void salvaRegistro(Funcionario funcionario) {
        // Analisar melhor depois \/
        verify(!(funcionarioLista.contains(funcionario)), "O Funcionario: " + getNome() + " de ID: " + getIdFuncionario() + " já possui registro\n");
        funcionarioLista.add(funcionario);
    }

    public Departamento buscaDepartamento(Departamento departamento) { // Está Verificando
        checkArgument(Departamento.getDepartamentoLista().contains(checkNotNull(departamento)), "Este departamento não possui registro\n");
        this.departamento = departamento;
        return departamento;
    }

    // Getters and Setters

    public long getIdFuncionario() {
        return idFuncionario;

    }

    public void setIdFuncionario(long idFuncionario) {
        // checkArgument(idFuncionario > 0 && idFuncionario < 3000, "ID para pessoa precisa ser maior que 0 e menor que 3000");
        this.idFuncionario = idFuncionario;
    }

    public String getNome() {
        return nome;

    }

    public void setNome(String nome) {
        // Será modificado no futuro
        // nome = checkNotNull(nome.replaceAll(("[ ]+"), " "));
        // Nome poderá ser completo, possuir acentos, espaços, e caracteres de caixa alta e baixa.
        // checkArgument(nome.matches("[a-zA-ZáéíóúâêîôûãõÁÉÍÓÚÂÊÎÔÛÃÕ ]+") && (nome.length() >= 3 && nome.length() <= 40), "Nome não pode conter caracteres especiais ou um tamanho muito grande");
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;

    }

    public void setCpf(String cpf) {
        // cpf = cpf.replaceAll("\\D", "");
        // checkArgument(cpf.length() == 11, "Digite apenas os números do CPF");
        this.cpf = Util.formataCPF(cpf);
    }

    public String getContato() {
        return contato;

    }

    public void setContato(String contato) {
        this.contato = Util.formataContato(contato);
    }

    public String getCep() { // Alterar isso depois
        return endereco.toString();

    }

    // public void setCep(String cep) { Desativado
    // cep = checkNotNull(cep.replaceAll("\\D", "")); // Cep deve sair no formato: 03575-090
    // checkArgument(cep.length() == 8, "Digite apenas os números do CEP");
    // this.cep = cep.substring(0, 5) + "-" + cep.substring(5, 8);
    // }

    public double getSalario() {
        return salario;

    }

    public void setSalario(double salario) {
        // checkArgument(salario > 0 && salario <= 10000.00, "Salario está incorreto!");
        this.salario = salario;
    }

    public Departamento getDepartamento() {
        return departamento;

    }

    public Util getTipoContato() {
        return tipoContato;
    }

    public void setTipoContato(Util tipoContato) {
        this.tipoContato = tipoContato;
    }

    public static Collection<Funcionario> getFuncionarioLista() {
        return funcionarioLista;

    }

    @Override
    public int hashCode() { // Não entendi pq quando dando new, podemos utilizar o append e o hashcode...
        return new HashCodeBuilder().append(this.idFuncionario).append(this.cpf).hashCode();
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
        Funcionario fn = (Funcionario) obj;
        return new EqualsBuilder().append(this.idFuncionario, fn.idFuncionario).append(this.cpf, fn.cpf).isEquals();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE).concat("\n");
    }

}
