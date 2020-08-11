package br.com.contmatic.empresav1.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class Funcionario {

	// Variáveis
	
	private long idFuncionario;
	private String nome;
	private String cpf;
	private String cep;
	private String contato;
	private TipoContato tipoContato;
	private double salario;
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
		while(iterator.hasNext()) {
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
		if (funcionarioLista.contains(funcionario)) {
			throw new IllegalArgumentException("O Funcionario: " + getNome() + " de ID: " + getIdFuncionario() + " já possui registro\n");
		} else {
			funcionarioLista.add(funcionario);
		}
		
	}
	
	public Departamento buscaDepartamento(Departamento departamento) { // Está Verificando
		if (Departamento.getDepartamentoLista().contains(departamento)) {
			this.departamento = departamento;
			return departamento;
		} else {
			throw new IllegalArgumentException("Este departamento não possui registro\n");
		}
	}

	// Getters and Setters
	
	public long getIdFuncionario() {
		return idFuncionario;
		
	}

	public void setIdFuncionario(long idFuncionario) {
		if (idFuncionario > 0) {
			this.idFuncionario = idFuncionario;
		} else {
			throw new IllegalArgumentException("ID para pessoa precisa ser mais de 0");
		}
	}
	
	public String getNome() {
		return nome;
		
	}

	public void setNome(String nome) {
		if (nome.length() >= 3 && !(nome.isEmpty())) { 
			this.nome = nome;// adicione nome
		} else {
			throw new IllegalArgumentException("Nome deve ter 3 ou mais caracteres!");
		}
	}

	
	public String getCpf() {
		return cpf;
		
	}

	public void setCpf(String cpf) {
		String aux = cpf.replaceAll("\\D", "");
		if (aux.length() == 11) {
			this.cpf = aux.substring(0, 3) + "." + aux.substring(3, 6) + "." + aux.substring(6, 9) + "-"
					+ aux.substring(9, 11);
		} else {
			throw new IllegalArgumentException("Digite apenas os números do CPF");
		}
	}


	public String getContato() {
		return contato;
		
	}


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
	
	public String getCep() {
		return cep;
		
	}

	public void setCep(String cep) {
		String aux = cep.replaceAll("\\D", "");
		if (aux.length() == 8) {
			this.cep = aux.substring(0, 5) + "-" + aux.substring(5, 8);
		} else {
			throw new IllegalArgumentException("Digite apenas os números do CEP"); // Ex CNPJ: 03575-090
		}
	}
	
	public double getSalario() {
		return salario;
		
	}
	

	public void setSalario(double salario) {
		if (salario > 0 && salario <= 10000.00) {
			this.salario = salario;
		} else {
			throw new IllegalArgumentException("Salario está incorreto!");
		}
		
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
		return "Funcionario: [ID= " + getIdFuncionario() + ", Nome= " + getNome() + ", Cpf= " + getCpf() + " Cep= "
				+ getCep() +  ", Contato= " + getContato() + " Salario=  " + getSalario() 
				+ " " + getDepartamento() + "]";
	}

}

