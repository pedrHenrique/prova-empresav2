/**
 * 
 */
package br.com.contmatic.empresav2.builder;

import br.com.contmatic.empresav2.model.Departamento;
import br.com.contmatic.empresav2.model.Endereco;
import br.com.contmatic.empresav2.model.Funcionario;
import br.com.contmatic.empresav2.model.Util;

/**
 * @author Pedro
 *
 */
public class FuncionarioBuilder {
    
    private long idFuncionario;
    
    private String nome;
    
    private String cpf;
    
    private Endereco endereco;
    
    //private Util tipoContato;
    
    private String contato;
    
    private double salario;
    
    private Departamento departamento;
    
    public FuncionarioBuilder funInformacoes(String nome, String cpf, String contato) {
        this.nome = nome;
        this.cpf = Util.formataCPF(cpf);
        this.contato = Util.formataContato(contato);
        //this.tipoContato = Util.tipoContato(contato);
        return this;
    }
    
    public FuncionarioBuilder funEndereco(String cep, String numero) {
        this.endereco = Endereco.cadastraEnderecoViaCEP(cep, numero);
        return this;
    }
    
    public FuncionarioBuilder funEmpresa(long numeroIdentificador, double salario, long codigoDepartamento) {
        this.idFuncionario = numeroIdentificador;
        this.departamento = Departamento.solicitaDep(codigoDepartamento);
        this.salario = salario;
        return this;
    }
    
    public Funcionario constroi() {
        return new Funcionario(idFuncionario, nome, cpf, contato, endereco, departamento, salario);
    }
    
    

}
