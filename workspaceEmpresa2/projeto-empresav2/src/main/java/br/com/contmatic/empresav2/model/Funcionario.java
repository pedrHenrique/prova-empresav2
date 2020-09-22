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
    @Pattern(regexp = "[a-zA-ZáéíóúâêîôûãõÁÉÍÓÚÂÊÎÔÛÃÕ ]+", message = "Nome invalido. Recomenda-se mudar")
    private String nome;

    // CPF não pode estar vazio e possui seu próprio tipo de annotation
    @NotBlank(message = "CPF não pode ficar vázio")
    @CPF
    private String cpf;
   
    // Endereco Precisa ser válido, seguindo as regras das annotations de sua classe
    @Valid
    private Endereco endereco;

    // tipoContato sempre será um valor de referência presente na classe, portanto ele não poderá ser nulo
    @NotNull
    @Valid
    private Util tipoContato;

    // contato pode ser um telefone, celular, ou até mesmo um email.
    @Size(min = 7, max = 50)
    @Pattern(regexp = "[\\w@._()-]+", message = "Contato não permitido. Você pode fornecer tanto um Telefone quanto um email.")
    private String contato;

    // Salario possui um valor mínimo, máximo e sempre deve ser positivo
   
    @Positive(message = "Salario não pode ser negativo")
    @Range(min = 1, max = 50000)
    private double salario;

    @NotNull
    @Valid
    private Departamento departamento;
    private static Collection<@NotEmpty Funcionario> funcionarioLista = new HashSet<>();

    // Construtores

//    public Funcionario(long idFuncionario, String nome, String cpf, String contato, long dep, double salario) {
//        this.idFuncionario = idFuncionario;
//        this.nome = nome;
//        this.cpf = Util.formataCPF(cpf);
//        this.endereco = Endereco.naoCadastrado();
//        this.contato = Util.formataContato(contato);
//        this.tipoContato = Util.tipoContato(contato);
//        this.departamento = buscaDepartamento(Departamento.solicitaDep(dep));
//        this.salario = salario;
//        salvaRegistro(this);
//    }

    public Funcionario(long idFuncionario, String nome, String cpf, String contato, double salario) {
        this.idFuncionario = idFuncionario;
        this.nome = nome;
        this.cpf = Util.formataCPF(cpf);
        this.endereco = Endereco.naoCadastrado();
        this.contato = Util.formataContato(contato);
        this.tipoContato = Util.tipoContato(contato);
        this.departamento = Departamento.naoCadastrado();
        this.salario = salario;
        salvaRegistro(this);
    }

    public Funcionario(long id, String nome, String cpf, String contato, Endereco endereco, Departamento departamento, double salario) {
        this.idFuncionario = id;
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.contato = contato;
        this.tipoContato = Util.tipoContato(contato); //testar no futuro se esse método não apresenta problemas
        this.departamento = departamento;
        this.salario = salario;
        salvaRegistro(this);
    }
    
    public Funcionario() {
        
    }

    // Métodos

    public Collection<Funcionario> listaFuncionario() {
        return getFuncionarioLista();

    }

    public static Funcionario solicitaFuncionario(long id) {
        Iterator<Funcionario> iterator = getFuncionarioLista().iterator();
        Funcionario obj = new Funcionario();

        while (iterator.hasNext() && obj.getIdFuncionario() != id) {
            obj = iterator.next();
        }

        try {
            checkArgument(obj.getIdFuncionario() == id, "O Funcionario com o ID: " + id + " não existe\n");
            return obj;
        } catch (IllegalArgumentException | NullPointerException ie) {
            // logger.info("O Departamento solicitado não foi encontrado");
            return null;
        }
    }

    public Funcionario cadastraFuncionario(long id, String nome, String cpf, String contato, double salario) {
        return new Funcionario(id, nome, Util.formataCPF(cpf), contato, salario);// Ficou desta forma para evitar bugs
    } //eu preciso arrumar uma forma de remover esse Util.FormataCPF

    public void cadastraFuncionarioEndereco(Funcionario funcionario, String cep, String numero) {
        checkArgument(Funcionario.getFuncionarioLista().contains(funcionario), "Não foi possível encontrar este funcionario");
        Funcionario.getFuncionarioLista().remove(funcionario);
        funcionario.endereco = checkNotNull(Endereco.cadastraEnderecoViaCEP(cep, numero), "O Endereco solicitado nao foi encontrado");
        Funcionario.getFuncionarioLista().add(funcionario);
        System.out.println(funcionario.toString());
        // Logger ficaria legal aqui
    }

    public void cadastraFuncionarioDepartamento(Funcionario funcionario, long idDepartamento) {
        checkArgument(Funcionario.getFuncionarioLista().contains(funcionario), "Não foi possível encontrar este funcionario");
        Funcionario.getFuncionarioLista().remove(funcionario);
        funcionario.departamento = checkNotNull(Departamento.solicitaDep(idDepartamento), "O Departamento solicitado não foi encontrado");
        Funcionario.getFuncionarioLista().add(funcionario);
        System.out.println(funcionario.toString());
        // Logger ficaria legal aqui
    }

    public void removeFuncionario(long id) {
        Funcionario obj = solicitaFuncionario(id);
        checkArgument(obj != null, "O Funcionario de ID: " + id + " que você tentou remover não existe");
        checkArgument(obj.getIdFuncionario() == id, "Funcionario com o ID: " + id + " não existe\n");
        getFuncionarioLista().remove(obj);

    }

    private void salvaRegistro(Funcionario funcionario) {
        //Lógica abaixo: Se a lista de funcionários NÂO conter o funcionario (hash:CPF) e o funcionario NÃO tiver com o ID já sendo utilizado, então adicione ele na lista.
        checkArgument(!getFuncionarioLista().contains(funcionario) && !idUtilizado(funcionario), "O Funcionario: " + getNome() + " de CPF: " + getCpf() + " já possui registro\n");
        funcionarioLista.add(funcionario);
        System.out.println("Funcionario Registrado: " + funcionario.toString());
    }

    public Departamento buscaDepartamento(Departamento departamento) { // Está Verificando
        checkArgument(Departamento.getDepartamentoLista().contains(checkNotNull(departamento)), "Este departamento não possui registro\n");
        this.departamento = departamento;

        return this.departamento;
    }

    /**
     * Está é uma simples função que verifica se o ID inserido já não esteja sendo utilizado por algum outro funcionario.
     *
     * @param funcionario - extrai o ID deste objeto.
     * @return falso caso o ID do funcionario não esteja sendo utilizado.
     * @throws IllegalArgumentException 
     *         Caso o ID já esteja sendo utilizado.
     */
    private boolean idUtilizado(Funcionario funcionario) {
        checkArgument(Funcionario.solicitaFuncionario(funcionario.idFuncionario) == null, "O ID " + funcionario.idFuncionario + " já está sendo utilizado");

        return false;
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
        this.cpf = Util.formataCPF(cpf); //intencional
    }

    public String getContato() {
        return contato;

    }

    public void setContato(String contato) {
        this.contato = Util.formataContato(contato); //intencional
    }

    public Endereco getEndereco() { // Alterar isso depois
        return endereco;

    }

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
        return HashCodeBuilder.reflectionHashCode(this.cpf.replaceAll("\\D", ""));
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
        return (EqualsBuilder.reflectionEquals(this.idFuncionario, fn.idFuncionario) || EqualsBuilder.reflectionEquals(this.cpf, fn.cpf));
        // new EqualsBuilder().append(this.idFuncionario, fn.idFuncionario).append(this.cpf, fn.cpf).isEquals();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE).concat("\n");
    }
}
