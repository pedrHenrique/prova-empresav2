package br.com.contmatic.empresav2.test;

import static org.junit.Assert.*;

import org.junit.Test;

public class TimeoutTestExample {

	private boolean Test = true;

	@Test(timeout = 3000)
	public void teste_timeout_deve_falhar() {
		// Simulando um simples test de Timeout de 3 segundos
		assertTrue(Test);
		while(true);
			
	} 
}
