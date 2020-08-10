package br.com.contmatic.empresav1.ITtest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.contmatic.empresav1.test.DepartamentoTest;
import br.com.contmatic.empresav1.test.EmpresaTest;
import br.com.contmatic.empresav1.test.FuncionarioTest;

@RunWith(Suite.class)
@SuiteClasses({EmpresaTest.class, DepartamentoTest.class, FuncionarioTest.class})
public class EmpresaIT {

}
