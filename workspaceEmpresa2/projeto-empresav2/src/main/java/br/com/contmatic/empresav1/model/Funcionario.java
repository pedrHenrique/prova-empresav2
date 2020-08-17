package br.com.contmatic.empresav1.model;

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

import org.hibernate.validator.constraints.br.CPF;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Verify.verify;

public class Funcionario {

    // Variáveis

    @NotNull
    @Max(1500)
    @Positive
    private long idFuncionario;

    @NotBlank
    @Size(min = 5, max = 50)
    @Pattern(regexp = "[a-zA-z]+", message = "Nome invalido. Recomenda-se mudar")
    private String nome;

    @NotEmpty
    @CPF
    // Adicionar mais uma necessária
    private String cpf;

    @NotNull
    @Min(8)
    private String cep; // será um enum no futuro

    @Email
    @Size(min = 7, max = 50)
    @NotNull
    @Pattern(regexp = ".+@.+\\.[a-z]+", message = "Email Invalido")
    private String contato;

    @NotEmpty
    @Valid
    private TipoContato tipoContato;

    @NotNull
    @Max(50000)
    private double salario;

    @NotNull
    @Valid
    private Departamento departamento = new Departamento();
    private static Collection<Funcionario> funcionarioLista = new HashSet<Funcionario>();

    // Construtores

    public Funcionario(long idFuncionario, String nome, String cpf, String cep, String contato, long dep, double salario) {
        setIdFuncionario(idFuncionario);
        setNome(nome);
        setCpf(cpf);
        setCep(cep);
        setContato(contato);
        buscaDepartamento(departamento.solicitaDep(dep));
        setSalario(salario);
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
        while (iterator.hasNext()) {
            obj = iterator.next();

            if (obj.getIdFuncionario() != id && !(iterator.hasNext())) {
                throw new IllegalArgumentException("O Funcionario com o ID: " + id + " não existe\n");
            } else if (obj.getIdFuncionario() == id) {
                break;
            }
        }
        return obj;
    }

    public Funcionario cadastraFuncionario(long id, String nome, String cpf, String cep, String email, long dep, double salario) {
        return new Funcionario(id, nome, cpf, cep, email, dep, salario);
    }

    public Funcionario removeFuncionario(long id) {
        Iterator<Funcionario> iterator = getFuncionarioLista().iterator();
        Funcionario obj = new Funcionario();

        while (iterator.hasNext()) {
            obj = iterator.next();

            if (obj.getIdFuncionario() != id && !(iterator.hasNext())) {
                throw new IllegalArgumentException(" com o ID: " + id + " não existe\n");
            } else if (obj.getIdFuncionario() == id) {
                iterator.remove();
                break;
            }
        }
        return obj;
    }

    private void salvaRegistro(Funcionario funcionario) {
        // Analisar melhor depois \/
        verify(!(funcionarioLista.contains(checkNotNull(funcionario))), "O Funcionario: " + getNome() + " de ID: " + getIdFuncionario() + " já possui registro\n");
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
        checkArgument(idFuncionario > 0, "ID para pessoa precisa ser mais de 0");
        this.idFuncionario = checkNotNull(idFuncionario);
    }

    public String getNome() {
        return nome;

    }

    public void setNome(String nome) {
        // Será modificado no futuro
        nome = checkNotNull(nome.replaceAll(("[ ]+"), " "));
        checkArgument(!(nome.matches("[^\\w ]|[\\d]")) && (nome.length() > 2 && nome.length() < 30), "Nome não pode conter caracteres especiais ou um tamanho muito grande"); // Créditos Andre.Crespo
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;

    }

    public void setCpf(String cpf) {
        cep = cpf.replaceAll("\\D", "");
        checkArgument(cep.length() == 11, "Digite apenas os números do CPF");
        this.cpf = checkNotNull(cep.substring(0, 3) + "." + cep.substring(3, 6) + "." + cep.substring(6, 9) + "-" + cep.substring(9, 11));
    }

    public String getContato() {
        return contato;

    }

    public void setContato(String contato) {
        if (contato.replaceAll("\\D", "").length() == 10) {
            this.contato = "(" + contato.substring(0, 2) + ") " + contato.substring(2, 6) + "-" + contato.substring(6);
            tipoContato = TipoContato.FIXO;

        } else if (contato.replaceAll("\\D", "").length() == 11) {
            this.contato = "(" + contato.substring(0, 2) + ") " + contato.substring(2, 7) + "-" + contato.substring(7);
            tipoContato = TipoContato.CELULAR;

        } else if (contato.contains("@") && contato.contains(".com") && !(contato.length() < 7 || contato.length() > 50)) {
            this.contato = contato;
            tipoContato = TipoContato.EMAIL;

        } else {
            throw new IllegalArgumentException("Telefone ou Email cadastrados de forma errada. Apenas utilize números para cadastrar um telefone Ex: 11941063792");
        }
    }

    public String getCep() {
        return cep;

    }

    public void setCep(String cep) {
        cep = cep.replaceAll("\\D", ""); // Cep deve sair no formato: 03575-090
        checkArgument(cep.length() == 8, "Digite apenas os números do CEP");
        this.cep = checkNotNull(cep.substring(0, 5) + "-" + cep.substring(5, 8));
    }

    public double getSalario() {
        return salario;

    }

    public void setSalario(double salario) {
        checkArgument(salario > 0 && salario <= 10000.00, "Salario está incorreto!");
        this.salario = checkNotNull(salario);
    }

    public Departamento getDepartamento() {
        return departamento;

    }

    public TipoContato getTipoContato() {
        return tipoContato;
    }

    public static Collection<Funcionario> getFuncionarioLista() {
        return funcionarioLista;

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (idFuncionario ^ (idFuncionario >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Funcionario))
            return false;
        Funcionario other = (Funcionario) obj;
        if (idFuncionario != other.idFuncionario)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Funcionario: [ID= " + getIdFuncionario() + ", Nome= " + getNome() + ", Cpf= " + getCpf() + " Cep= " + getCep() + ", Contato= " + getContato() + " Salario=  " + getSalario() + " " +
            getDepartamento() + "]";
    }

}
