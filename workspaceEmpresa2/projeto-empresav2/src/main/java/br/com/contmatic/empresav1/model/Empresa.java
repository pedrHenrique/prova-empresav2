package br.com.contmatic.empresav1.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.HashSet;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Hours;
import org.hibernate.validator.constraints;

public class Empresa {

	// Variáveis
    @NotNull
	private long idEmpresa;
	private String nome;
	private String cnpj;
	private String cep;
	private DateTimeZone dtFundacao;
	private String contato;
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
		if (empresaLista.contains(departamento)) {
			throw new IllegalArgumentException("A empresa: " + getIdEmpresa() + " já possui registro\n");
		} else {
			empresaLista.add(departamento);
		}
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
		if (idEmpresa > 0 && idEmpresa <= 500) {
			this.idEmpresa = idEmpresa;
		} else {
			throw new IllegalArgumentException("O ID da empresa deve ser maior que zero e menor que 500!");
		}
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if ((nome.length() >= 5) && !(nome.isEmpty())) {
			this.nome = nome;
		} else {
			throw new IllegalArgumentException("Nome deve ter 5 ou mais caracteres!");
		}
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		String aux = cnpj.replaceAll("\\D", "");
		if (aux.length() == 14) {
			this.cnpj = aux.substring(0,2) + "." + aux.substring(2,5) + "." + aux.substring(5,8) +
					"/" + aux.substring(8,12) + "-" + aux.substring(12,14);
		} else {
			throw new IllegalArgumentException("Digite apenas os números do CNPJ!!"); //Ex CNPJ: 00.000.000/0001-00
		}
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		String aux = cep.replaceAll("\\D", "");
		if (aux.length() == 8) {
			this.cep = aux.substring(0,5) + "-" + aux.substring(5,8);
		} else {
			throw new IllegalArgumentException("Digite apenas os números do CEP"); //Ex CNPJ: 03575-090
		}
		
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
