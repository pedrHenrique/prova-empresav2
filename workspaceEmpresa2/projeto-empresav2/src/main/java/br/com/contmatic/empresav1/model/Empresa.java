package br.com.contmatic.empresav1.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.HashSet;

import org.hibernate.validator.constraints.br.CNPJ;
import org.joda.time.DateTime;

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

public class Empresa {

	// Variáveis
    
    @NotNull
    @Max(500)
    @Positive
	private long idEmpresa;
    
    @NotBlank
    @Pattern(regexp="[a-zA-z]+", message = "Nome invalido. Recomenda-se mudar")
	private String nome;
    
    @NotNull
    @CNPJ
	private String cnpj;
    
    @NotEmpty
    @Min(8)
    @Positive
    //@Pattern(regexp = "\\D")
	private String cep; //Terá de ser um enum
    
	private DateTime dtFundacao;
	
	@NotNull
	@Email
	@Size(min = 7, max = 50)
	@Pattern(regexp=".+@.+\\.[a-zA-z]+", message="Email Invalido")
	private String contato;
	
	@NotEmpty
	@Valid
	private TipoContato tipoContato;
	
	private static Collection<Empresa> empresaLista = new HashSet<Empresa>();

	
	// Construtores
	public Empresa(long idEmpresa, String nome, String cnpj, String endereco, String contato) {
		setIdEmpresa(idEmpresa);
		setNome(nome);
		setCnpj(cnpj);
		setCep(endereco);
		setContato(contato);
		salvaRegistro(this);
	}

	public Empresa() {

	}

	// Métodos

	public Collection<Empresa> listaEmpresas() {
		return empresaLista;
	}
	
	public Empresa solicitaEmpresa(long id) {
		Iterator<Empresa> iterator = getEmpresaLista().iterator();
		Empresa obj = new Empresa();
		while (iterator.hasNext()) {
			obj = iterator.next();
			
			if (obj.getIdEmpresa() != id && !(iterator.hasNext())) {
				throw new IllegalArgumentException("Departamento " + id + " não existe\n");
			} else if (obj.getIdEmpresa() == id) {
				break;
				
			}
		}
		return obj;
	}

	public Empresa registraEmpresa(long idEmpresa, String nome, String cnpj, String endereco, String telefone) {
		return new Empresa(idEmpresa, nome, cnpj, endereco, telefone);
	}

	private void salvaRegistro(Empresa departamento) {
	    verify(!(empresaLista.contains(checkNotNull(departamento))), "A empresa: " + getIdEmpresa() + " já possui registro\n");
		empresaLista.add(departamento);
	}

	public Empresa removeEmpresa(long id) {
		Iterator<Empresa> iterator = empresaLista.iterator();
		Empresa obj = new Empresa();
		while (iterator.hasNext()) {
			obj = iterator.next();

			if (obj.getIdEmpresa() != id && !(iterator.hasNext())) {
				throw new IllegalArgumentException("A Empresa " + id + " não existe\n");
			} else if (obj.getIdEmpresa() == id) {
				iterator.remove();
				break;
			}
		}
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
	    //Será modificado no futuro
        nome = checkNotNull(nome.replaceAll(("[ ]+"), " "));
        checkArgument(!(nome.matches("[^\\w ]|[\\d]")) && (nome.length() > 2 && nome.length() < 30), "Nome não pode conter caracteres especiais ou um tamanho muito grande"); // Créditos Andre.Crespo
        this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		cnpj = checkNotNull(cnpj.replaceAll("\\D", ""));
		checkArgument(cnpj.length() == 14, "Digite apenas os números do CNPJ!!");
		this.cnpj = cnpj.substring(0,2) + "." + cnpj.substring(2,5) + "." + cnpj.substring(5,8) +
					"/" + cnpj.substring(8,12) + "-" + cnpj.substring(12,14);
		
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		cep = checkNotNull(cep.replaceAll("\\D", ""));
		checkArgument(cep.length() == 8, "Digite apenas os números do CEP");
		this.cep = cep.substring(0,5) + "-" + cep.substring(5,8);
	}

	public String getContato() { //(11) 4564-9304 
		return contato;
	}

	
	//TESTE PARA CONTATO
	public void setContato(String contato) {	
		if (contato.replaceAll("\\D", "").length() == 10) {
			this.contato = "(" + contato.substring(0,2) + ") " + contato.substring(2,6) + "-" + contato.substring(6);
			tipoContato = TipoContato.FIXO;

		} else if (contato.replaceAll("\\D", "").length() == 11 ){
		    this.contato = "(" + contato.substring(0,2) + ") " + contato.substring(2,7) + "-" + contato.substring(7);
            tipoContato = TipoContato.CELULAR;
            
		} else if(contato.contains("@") && contato.contains(".com") && !(contato.length() < 7 || contato.length() > 50)) {
		    this.contato = contato;
		    tipoContato = TipoContato.EMAIL;
            
		} else {
			throw new IllegalArgumentException("Telefone ou Email cadastrados de forma errada. Apenas utilize números para cadastrar um telefone Ex: 11941063792");
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
		return "Empresa: [" + getIdEmpresa() + ", Nome: " + getNome() + ", CNPJ: " + getCnpj() + 
				", Cep: " + getCep() + ", " + getTipoContato() + ":"+ getContato() + "]";
	}

}
