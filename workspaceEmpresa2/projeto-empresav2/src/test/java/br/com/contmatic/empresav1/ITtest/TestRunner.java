package br.com.contmatic.empresav1.ITtest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import br.com.contmatic.empresav1.test.DepartamentoTest;
import br.com.contmatic.empresav1.test.EmpresaTest;
import br.com.contmatic.empresav1.test.FuncionarioTest;

public class TestRunner {

	public static void main(String[] args) {
		Result resultado = JUnitCore.runClasses(EmpresaTest.class, DepartamentoTest.class, FuncionarioTest.class);

		for (Failure falha : resultado.getFailures()) {
			System.out.println(falha.toString());
		}
		
		if(resultado.wasSuccessful()) {
			System.out.println("\n -> Testes Finalizados com Sucesso :) <- ");
		} else {
			System.out.println("\n -> Testes Finalizados com erros <- ");
		}
	}

}
