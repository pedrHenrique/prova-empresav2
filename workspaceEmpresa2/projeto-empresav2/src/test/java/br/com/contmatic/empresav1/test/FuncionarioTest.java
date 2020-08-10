package br.com.contmatic.empresav1.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.contmatic.empresav1.model.Departamento;
import br.com.contmatic.empresav1.model.Funcionario;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FuncionarioTest {

	private static final Object NULLOBJ = null;
	private static final String NULLSTR = null;
	private static final String EMPTYSTR = "";
	private static final Long NULLID = (Long) null;
	private static final Long EMPTYID = (long) 0;
	private static final Double NULLINT = null;
	private static final Double EMPTYINT = 0.0;
	private static final int EMPTYINTEGER = 0;

	private static Funcionario funcionario;
	private Funcionario fun; // criado para testar diferença de instâncias 
	private static Departamento dep;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		funcionario = new Funcionario();

		dep = new Departamento(1, " DepTestes", 256);
		funcionario.cadastraFuncionario(1, "Ana", "56495985096", "03575090", 
				"testeMatic@cont.com", 1,3500.00);
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Funcionario.getFuncionarioLista().clear();
		Departamento.getDepartamentoLista().clear();
		funcionario = null;
		dep = null;
	}

	@Before
	public void setUp() throws Exception {
		this.fun = new Funcionario();
	}

	@After
	public void tearDown() throws Exception {
		this.fun = null;
	}

	/*
	 * Está seção de testes tem o intuito de testar os métodos de principais da
	 * classe
	 */

	@Test
	public void teste_criando_objeto_construtor() {
		long id = 2;
		fun = new Funcionario(id, "Joana", "45495985096", "03575090",
				"testeMatic@cont.com", 1, 1500.00);
		assertThat("O Obj esperado era:", fun, equalTo(funcionario.solicitaFuncionario(id)));
	}

	@Test
	public void teste_objeto_criado_por_metodo_com_parametros() {
	long id = 3;
	assertThat("O Obj esperado era:",fun.cadastraFuncionario(id, "Cleber", "71477403000", "69915890", "testeMatic@cont.com", 1, 1500.79), equalTo(funcionario.solicitaFuncionario(id)));
	assertNotNull("O objeto não deveria estar nulo", Funcionario.getFuncionarioLista().contains(funcionario.solicitaFuncionario(id)));
	}
	
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_pessoa_criada_ja_existente() {
		long id = 1;
		fun.cadastraFuncionario(id, "Rogerinho", "45664899804", "69915890", "junior@Junior.com", 1, 50.79);
	}
	
	@Test(expected = java.lang.IllegalArgumentException.class)
	@Ignore("Teste ignorado pois funcionalidade ainda não está presente") 
	public void teste_pessoa_criada_com_cpf_ja_existente() {
		long id = 4;
		fun.cadastraFuncionario(id, "Rogerinho", "56495985096", "69915890", "junior@Junior.com", 1, 50.79);
	}
	
	@Test(expected = NullPointerException.class)
	public void teste_pessoa_sendo_criada_nula() {
		fun.cadastraFuncionario(NULLID, NULLSTR, NULLSTR, NULLSTR, NULLSTR, NULLID, NULLINT);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void teste_pessoa_sendo_criada_vazia() {
		fun.cadastraFuncionario(EMPTYID, EMPTYSTR, EMPTYSTR, EMPTYSTR, EMPTYSTR, EMPTYID, EMPTYINT);
	}
	
	@Test
	public void teste_remocao_pessoa_existente() {
		long id = 250;
		assertThat("Os objetos deveriam ser iguais", new Funcionario(id, "Rogerinho", "45664899804", "69915890", "junior@Junior.com", 1, 50.79), equalTo(fun.removeFuncionario(id)));

	}
	
	@Test(expected = IllegalArgumentException.class)
	public void teste_remocao_pessoa_nao_existente() {
		long id = 500;
		fun.removeFuncionario(id);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void teste_solicita_pessoa_nao_existente() {
		long id = 500;
		fun.solicitaFuncionario(id);
	}
	
	/*
	 * Está seção de testes tem o intuito de testar métodos de listagem da classe
	 */

	@Test
	public void teste_listar_pessoas() {
		assertNotNull("Esperava receber uma lista: ", funcionario.listaFuncionario());
	}
	
	@Test
	public void teste_toString() {
		assertNotNull("Os valores deveriam ser iguais", funcionario.toString());
	}
	
	/*
	 * Está seção de testes tem o intuito de testar os getters/setters da classe
	 */
	
	@Test
	public void teste_setId_e_getId_nome_correto() {
		long id = 45;
		fun.setIdFuncionario(id);
		assertThat("Os valores deveriam ser iguais", id, equalTo(fun.getIdFuncionario()));
	}

	@Test(expected = NullPointerException.class)
	public void teste_setId_valor_nulo() {
		fun.setIdFuncionario(NULLID);
	}

	@Test(expected = IllegalArgumentException.class)
	public void teste_setId_valor_vazio() {
		fun.setIdFuncionario(EMPTYID);
	}
	
	@Test
	public void teste_setNome_e_getNome_nome_correto() {
		String name = new String("Carlos Alberto");
		fun.setNome(name);
		assertThat("Os valores deveriam ser iguais", name, equalTo(fun.getNome()));
	}

	@Test(expected = NullPointerException.class)
	public void teste_setNome_valor_nulo() {
		fun.setNome(NULLSTR);
	}

	@Test(expected = IllegalArgumentException.class)
	public void teste_setNome_valor_vazio() {
		fun.setNome(EMPTYSTR);
	}
	
	@Test
	public void teste_setCpf_e_getCpf_nome_correto() {
		String cpf = new String("04517788040");
		fun.setCpf(cpf);
		assertThat("Os valores deveriam ser iguais", cpf, equalTo(fun.getCpf().replaceAll("\\D", "")));// "\\D" remove formatação
	}

	@Test(expected = NullPointerException.class)
	public void teste_setCpf_valor_nulo() {
		fun.setCpf(NULLSTR);
	}

	@Test(expected = IllegalArgumentException.class)
	public void teste_setCpf_valor_vazio() {
		fun.setCpf(EMPTYSTR);
	}
	
	@Test
	public void teste_seCep_e_getCep_nome_correto() {
		String cep = new String("03575090");
		fun.setCep(cep);
		assertThat("Os valores deveriam ser iguais", cep, equalTo(fun.getCep().replaceAll("\\D", ""))); // "\\D" remove formatação
	}

	@Test(expected = NullPointerException.class)
	public void teste_setCep_valor_nulo() {
		fun.setCep(NULLSTR);
	}

	@Test(expected = IllegalArgumentException.class)
	public void teste_setCep_valor_vazio() {
		fun.setCep(EMPTYSTR);
	}
	
	@Test
	public void teste_setEmail_e_getEmail_nome_correto() {
		String email = new String("andre.crespo@contmatic.com");
		fun.setContato(email);
		assertThat("Os valores deveriam ser iguais", email, equalTo(fun.getContato())); // "\\D" remove formatação
		
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void teste_setEmail_valor_nulo() {
		fun.setContato(NULLSTR);
	}

	@Test(expected = IllegalArgumentException.class)
	public void teste_setEmail_valor_vazio() {
		fun.setContato(EMPTYSTR);
	}
	
	@Test
	public void teste_setSalario_e_getSalario_nome_correto() {
		//Forma encontrada de não gerar markers pelo Sonar
		Double valorActual = 5000.50; 
		Double valorExpected;
		
		fun.setSalario(valorActual);
		valorExpected = fun.getSalario();
		assertThat("Os valores deveriam ser iguais", valorActual, equalTo(valorExpected));
	}

	@Test(expected = NullPointerException.class)
	public void teste_setSalario_valor_nulo() {
		fun.setSalario(NULLINT);
	}

	@Test(expected = IllegalArgumentException.class)
	public void teste_setSalario_valor_vazio() {
		fun.setSalario(EMPTYINT);
	}
	
	@Test
	public void teste_funcionario_busca_departamento_existente() {
		long id = 1;
		Departamento depart = new Departamento();
		assertThat("Os departamentos deveriam ser iguais", fun.buscaDepartamento(dep.solicitaDep(id)), equalTo(depart.solicitaDep(id)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void teste_funcionario_busca_departamento_inexistente() {
		long id = 193;
		fun.buscaDepartamento(dep.solicitaDep(id));
	}

	@Test(expected = NullPointerException.class)
	public void teste_buscaDepartamento_valor_nulo() {
		fun.buscaDepartamento(new Departamento(NULLID,NULLSTR,(int) NULLOBJ));
	}

	@Test(expected = IllegalArgumentException.class)
	public void teste_buscaDepartamento_valor_vazio() {
		fun.buscaDepartamento(new Departamento(EMPTYID,EMPTYSTR,EMPTYINTEGER));
	}
	
}
