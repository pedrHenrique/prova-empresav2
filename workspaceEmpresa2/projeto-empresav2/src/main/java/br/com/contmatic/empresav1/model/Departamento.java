package br.com.contmatic.empresav1.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Verify.verify;

public class Departamento {

    // Variáveis
    @Max(300) // 0 Valor de ADM
    @PositiveOrZero
    private long idDepartamento;

    @Size(max = 40)
    // Modificação Final \/
    @Pattern(regexp = "[a-zA-z ]+", message = "Nome inapropriado. Recomenda-se mudar") // Nome não deveria ser registrado fora de um Padrão de A-z
    private String nome;

    @Max(999)
    @PositiveOrZero
    private int ramal; // Adicionaro forma de contato recebendo Ramal e Email futuramente (se possível)

    //@NotEmpty // Maybe???
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
        for(Departamento departamento : departamentoLista) {
            System.out.println(departamento); // remover depois

        }
        return departamentoLista;
    }

    public Departamento registraDep(long id, String nome, int ramal) {
        return new Departamento(id, nome, ramal);
    }

    private void salvarRegistro(Departamento departamento) {
        System.out.println("\n--Departamento: " + departamento); // <- Para Testes
        verify(!(departamentoLista.contains(checkNotNull(departamento))), getIdDepartamento() + " já possui registro\n"); 
        departamentoLista.add(departamento);
    }

    public Departamento solicitaDep(long id) {
        Iterator<Departamento> iterator = getDepartamentoLista().iterator();
        Departamento obj = new Departamento();

        while (iterator.hasNext()) {
            obj = iterator.next();
            if (obj.getIdDepartamento() != id && !(iterator.hasNext())) {
                throw new IllegalArgumentException("Departamento " + id + " não foi encontrado\n");
            } else if (obj.getIdDepartamento() == id) {
                return obj;
            }
        }
        return null;

    }

    public Departamento removeDep(long id) {
        Iterator<Departamento> iterator = getDepartamentoLista().iterator();
        Departamento obj = new Departamento();
        while (iterator.hasNext()) {
            obj = iterator.next();

            if (obj.getIdDepartamento() != id && !(iterator.hasNext())) {
                throw new IllegalArgumentException("O Departamento " + id + " não existe\n");
            } else if (obj.getIdDepartamento() == id) {
                iterator.remove();
                break;
            }
            //checkArgument(obj.getIdDepartamento() != id && !(iterator.hasNext()), "O Departamento " + id + " não existe\n");
            //verify(obj.getIdDepartamento() == id);
        }
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
        checkArgument(!(nome.matches("[^\\w ]|[\\d]")) && (nome.length() > 2 && nome.length() < 30), "Nome não pode conter caracteres especiais ou um tamanho muito grande"); // Créditos Andre.Crespo
        this.nome = checkNotNull(nome);

        // Código Antigo
        // nome = nome.replaceAll(("[ ]+"), " ");
        // if (nome.matches("[^\\w ]|[\\d]")) { //Créditos Andre.Crespo
        // throw new IllegalArgumentException("Por Favor, insira um nome sem caracteres especiais");
        // } else if ((nome.length() < 2 || nome.length() > 30) || nome.isEmpty()) {
        // throw new IllegalArgumentException("Nome deve ter 2 ou mais caracteres!");
        // } else {
        // this.nome = nome;

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
