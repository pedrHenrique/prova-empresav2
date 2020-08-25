package br.com.contmatic.empresav2.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.HashSet;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

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

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Verify.verify;

//TODO JAutoDoc Empresa
public class Empresa {

    // Variáveis

    // ID terá de ser positivo e possui um valor máximo que pode ser atribuido
    @Max(500)
    @Positive
    private long idEmpresa;

    // O nome da empresa não pode estar vazio, possui um tamanho específico, e uma recomendação de expressão regular
    @NotBlank
    @Length(min = 2, max = 50)
    @Pattern(regexp = "[a-zA-Z0-9_-!&. ]+", message = "Nome invalido. Recomenda-se mudar")
    private String nome;

    // CNPJ não pode estar vazio e possui seu próprio tipo de annotation
    @NotEmpty
    @CNPJ
    private String cnpj;

    @NotBlank
    @Min(8)
    @Pattern(regexp = "[\\D-]") // Testar Futuramente
    private String cep; // TODO Cep será um enum no futuro

    @Valid
    private DateTime dtFundacao;

    // Email possui sua própria annotation, tamanho, expressão regular, e não deve estar vazio
    @Email
    @Size(min = 7, max = 50)
    @Pattern(regexp = ".+@.+\\.[a-zA-z]+", message = "Email Invalido")
    private String contato;

    // tipoContato sempre será um valor de referência presente na classe, portanto ele não poderá ser nulo
    @NotNull
    @Valid
    private TipoContato tipoContato;

    private static Collection<Empresa> empresaLista = new HashSet<>();

    // Construtores
    public Empresa(long idEmpresa, String nome, String cnpj, String endereco, String contato, String dtFundacao) {
        setIdEmpresa(idEmpresa);
        setNome(nome);
        setCnpj(cnpj);
        //setCep(endereco);
        setContato(contato);
        setDtFundacao(dtFundacao);
        salvaRegistro(this);
    }

    public Empresa() {

    }

    // Métodos

    public Collection<Empresa> listaEmpresas() {
        return empresaLista;
    }

    public Empresa registraEmpresa(long idEmpresa, String nome, String cnpj, String endereco, String telefone, String dtFundacao) { 
        return new Empresa(idEmpresa, nome, cnpj, endereco, telefone, dtFundacao);
    }

    private void salvaRegistro(Empresa departamento) {
        verify(!(empresaLista.contains(checkNotNull(departamento))), "A empresa: " + getIdEmpresa() + " já possui registro\n");
        empresaLista.add(departamento);
    }

    public Empresa solicitaEmpresa(long id) {
        Iterator<Empresa> iterator = getEmpresaLista().iterator();
        Empresa obj = new Empresa();

        while (iterator.hasNext() && obj.getIdEmpresa() != id) {
            obj = iterator.next();
        }
        verify(obj.getIdEmpresa() == id, "A Empresa com ID:  " + id + " não existe\n");
        return obj;
    }

    public Empresa removeEmpresa(long id) {
        Iterator<Empresa> iterator = empresaLista.iterator();
        Empresa obj = new Empresa();

        while (iterator.hasNext() && obj.getIdEmpresa() != id) {
            obj = iterator.next();
        }
        verify(obj.getIdEmpresa() == id, "A Empresa " + id + " não existe\n");
        iterator.remove();

        return obj;
    }

    // Getters and Setters

    public long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(long idEmpresa) {
        checkArgument(idEmpresa > 0 && idEmpresa <= 500, "O ID da empresa deve ser maior que zero e menor que 500!");
        this.idEmpresa = checkNotNull(idEmpresa);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        // Será modificado no futuro
        nome = checkNotNull(nome.replaceAll(("[ ]+"), " "));
        // Nome de empresa neste formato, aceita uma grande variedade de caracteres especiais que seria mais interessante eles não serem aceitos. 24/08: Wtf??
        checkArgument(!(nome.matches("[\\W ]+")) && (nome.length() > 2 && nome.length() < 50), "Nome muito pequeno ou muito grande"); // Créditos Andre.Crespo
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        cnpj = checkNotNull(cnpj.replaceAll("\\D", ""));
        checkArgument(cnpj.length() == 14, "Digite apenas os números do CNPJ!!");
        this.cnpj = cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." + cnpj.substring(5, 8) + "/" + cnpj.substring(8, 12) + "-" + cnpj.substring(12, 14);

    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        cep = checkNotNull(cep.replaceAll("\\D", ""));
        checkArgument(cep.length() == 8, "Digite apenas os números do CEP");
        this.cep = cep.substring(0, 5) + "-" + cep.substring(5, 8);
    }

    public DateTime getDtFundacao() {
        return dtFundacao;
    }

    public void setDtFundacao(String dtFundacao) {
        checkNotNull(dtFundacao, "Data não pode estar vazia");
        //Se a Data for apenas conter números e ter um tamanho igual a 10
        checkArgument(dtFundacao.matches("[\\d/]+") && (dtFundacao.length() == 10), "Data deveria ser em número e estar no formato dd/mm/yyyy");
        
        DataJoda dt = new DataJoda(dtFundacao);
        this.dtFundacao = dt.getData();
        
    }

    public String getContato() {
        return contato;
    }

    // Pensar em alguma forma de transformar estes if's para guava
    public void setContato(String contato) {
        // Selo Gambiarra Removido :D
        checkNotNull(contato, "Contato não pode estar vazio");
        int aux = (contato.replaceAll("\\D", "").length());
        
        switch (aux) {
            case 10:
                this.contato = "(" + contato.substring(0, 2) + ") " + contato.substring(2, 6) + "-" + contato.substring(6);
                tipoContato = TipoContato.FIXO; break;
                
            case 11:
                this.contato = "(" + contato.substring(0, 2) + ") " + contato.substring(2, 7) + "-" + contato.substring(7);
                tipoContato = TipoContato.CELULAR; break;
                
            default:
                checkArgument(contato.contains("@") && contato.contains(".com") && !(contato.length() < 7 || contato.length() > 50),
                    "O contato inserido não corresponde a nenhum email ou telefone/celular." + "\n Digite apenas os números do telefone. ");
                this.contato = contato;
                tipoContato = TipoContato.EMAIL;
        }
    }

    public TipoContato getTipoContato() {
        return tipoContato;
    }

    public static Collection<Empresa> getEmpresaLista() {
        return empresaLista;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (idEmpresa ^ (idEmpresa >>> 32));
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
        Empresa other = (Empresa) obj;
        if (idEmpresa != other.idEmpresa)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "\nEmpresa: [" + getIdEmpresa() + ", Nome: " + getNome() + ", CNPJ: " + getCnpj() + ", Cep: " /*+ getCep() */ + "CEP está desativado para está versão " + ", " + getTipoContato() + ":" + getContato() + ", Data Fundação: " + getDtFundacao();
    }

}
