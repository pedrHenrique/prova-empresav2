package br.com.contmatic.empresav1.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class Departamento {

	// Variáveis
	private long idDepartamento;
	private String nome;
	private int ramal;
	private static Collection<Departamento> departamentoLista = new HashSet<Departamento>();

	// Construtores
	public Departamento(long idDepartamento, String nome, int ramal) {
		setIdDepartamento(idDepartamento);
		setNome(nome);
		setRamal(ramal);
		salvarRegistro(this);
	}

	public Departamento() {

	}
	
//	public enum TelefoneType {
//
//	    RAMAL("Ramal", 3);
//
//	    private String descricao;
//	    private int tamanho;
//
//	    private TelefoneType(String descricao, int tamanho) {
//	        this.descricao = descricao;
//	        this.tamanho = tamanho;
//	    }

	// Métodos

	public Collection<Departamento> listarDepartamentos() {
		return departamentoLista;
	}

	public Departamento registraDep(long id, String nome, int ramal) {
		return new Departamento(id, nome, ramal);
	}

	private void salvarRegistro(Departamento departamento) {
		if (departamentoLista.contains(departamento)) {
			throw new IllegalArgumentException(getIdDepartamento() + " já possui registro\n");
		} else {
			departamentoLista.add(departamento);
		}
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
		}
		return obj;
	}

	// Getters And Setters

	public long getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(long idDepartamento) {
		if (idDepartamento > 0 && idDepartamento <= 300) {
			this.idDepartamento = idDepartamento;
		} else {
			throw new IllegalArgumentException("O ID do departamento deve ser maior que zero e menor que 300!");
		}
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if ((nome.length() >= 3) && !(nome.isEmpty())) {
			this.nome = nome;
		} else {
			throw new IllegalArgumentException("Nome deve ter 5 ou mais caracteres!");
		}

	}

	public int getRamal() {
		return ramal;
	}

	public void setRamal(int ramal) {
		if (ramal > 0 && ramal <= 999) {
			this.ramal = ramal;
		} else {
			throw new IllegalArgumentException("Número de ramal precisa ser entre 1 a 999!");
		}

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
