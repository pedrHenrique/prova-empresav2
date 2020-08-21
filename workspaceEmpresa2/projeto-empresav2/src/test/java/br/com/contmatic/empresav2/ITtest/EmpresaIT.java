package br.com.contmatic.empresav2.ITtest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.contmatic.empresav2.test.DepartamentoTest;
import br.com.contmatic.empresav2.test.EmpresaTest;
import br.com.contmatic.empresav2.test.FuncionarioTest;

@RunWith(Suite.class)
@SuiteClasses({EmpresaTest.class, DepartamentoTest.class, FuncionarioTest.class})
public class EmpresaIT {

}
