package br.com.contmatic.empresav1.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.contmatic.empresav1.model.Departamento;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepartamentoTest {

	private static final String NULLSTR = null;
	private static final String EMPTYSTR = "";
	private static final Long NULLID = null;	//Object
	private static final Long EMPTYID = (long) 0;
	private static final Object NULLINT = null;
	private static final int EMPTYINT = 0;
	
	private static Departamento departamento; 
	private Departamento dep; // criado para testar diferença de instâncias 	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		departamento = new Departamento();
		
		departamento.registraDep(1, "Contábil", 155);
		departamento.registraDep(2, "Recursos Humanos", 285);
		departamento.registraDep(3, "Tecnologias", 405);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {	
		Departamento.getDepartamentoLista().clear();
		departamento = null;
	}
	
	@Before
	public void setUp() throws Exception{
		this.dep = new Departamento();
		
	}
	
	@After
	public void tearDown() throws Exception{
		this.dep = null;
	}
	
	
	/*
	 * Está seção de testes tem o intuito de testar os métodos de criação dos
	 * objetos da classe
	 */

	@Test // Testa criando o obj pelo construtor
	public void teste_objeto_criado_por_construtor() {
		long id = 10;

		dep = new Departamento(id, "Financeiro", 226);
		assertThat("O Obj esperado era: ", dep, equalTo(departamento.solicitaDep(id))); //what we got - expected
		assertNotNull("O objeto não deveria estar nulo", dep.solicitaDep(id));
	}

	@Test
	public void teste_objeto_criado_por_metodo_com_parametros() {
		long id = 11;

		
		assertThat("O Obj esperado era: ", dep.registraDep(id, "Expedição", 189), equalTo(departamento.solicitaDep(id)));

		assertNotNull("O objeto não deveria estar nulo", departamento.solicitaDep(id));

	}

	@Test(expected = IllegalArgumentException.class)
	public void teste_objeto_criado_ja_existente_() {
		long id = 1;
		dep.registraDep(id, "Financeiro", 226);
	}
	
	@Test(expected = NullPointerException.class)
	public void teste_objeto_sendo_criado_nulo_() {
		dep = new Departamento(NULLID, "Qualidade", 250);

	}

	/*
	 * Está seção de testes tem o intuito de testar os métodos de remoção dos
	 * objetos da classe
	 */

	@Test
	public void teste_remocao_objeto_existente() {
		long id = 250;
		assertThat("Os objetos deveriam ser iguais: ", new Departamento(id, "Rogerio", 145), equalTo(dep.removeDep(id)));

	}

	@Test(expected = IllegalArgumentException.class)
	public void teste_remocao_objeto_nao_existente() {
		long id = 179;
		dep.removeDep(id);
	}

	/*
	 * Está seção de testes tem o intuito de testar métodos de busca de objetos da
	 * classe
	 */

	@Test // Testando a busca por objetos num HashSet
	public void teste_busca_departamento_existente() {
		assertNotNull("Esperava receber um objeto", dep.solicitaDep(1));
		assertNotNull("Esperava receber um objeto", dep.solicitaDep(2));
		assertNotNull("Esperava receber um objeto", dep.solicitaDep(3));
	}

	@Test(expected = IllegalArgumentException.class)
	public void teste_busca_departamento_nao_existente() {
		dep.solicitaDep(50); // deve falhar

	}

	/*
	 * Está seção de testes tem o intuito de testar os getters/setters da classe
	 */

	@Test
	public void teste_setNome_e_getNome_nome_correto() {
		String name = new String("Gerencia");
		dep.setNome(name);
		assertThat("Os valores deveriam ser iguais: ", name, equalTo(dep.getNome()));
	}

	@Test(expected = NullPointerException.class)
	public void teste_setNome_valor_nulo() {
		dep.setNome(NULLSTR);
	}

	@Test(expected = IllegalArgumentException.class)
	public void teste_setNome_valor_vazio() {
		dep.setNome(EMPTYSTR);
	}

	@Test
	public void teste_setId_e_getId_correto() {
		long id = 25;
		dep.setIdDepartamento(id);
		assertThat("Os valores deveriam ser iguais: ", id, equalTo(dep.getIdDepartamento()));
	}

	@Test(expected = NullPointerException.class)
	public void teste_setId_valor_nulo() {
		dep.setIdDepartamento(NULLID);
	}

	@Test(expected = IllegalArgumentException.class)
	public void teste_setId_valor_vazio() {
		dep.setIdDepartamento(EMPTYID);
	}

	@Test
	public void teste_setRamal_e_getId_correto() {
		int num = 456;
		dep.setRamal(num);
		assertThat("Os valores deveriam ser iguais: ", num, equalTo(dep.getRamal()));
	}

	@Test(expected = NullPointerException.class)
	public void teste_setRamal_valor_nulo() {
		dep.setRamal((int) NULLINT);
	}

	@Test(expected = IllegalArgumentException.class)
	public void teste_setRamal_valor_vazio() {
		dep.setRamal(EMPTYINT);
	}

	/*
	 * Está seção de testes tem o intuito de testar os métodos de listagem
	 */

	@Test
	public void teste_listar_departamentos() {
		assertNotNull("Esperava receber uma lista", dep.listarDepartamentos());
	}

	@Test
	public void teste_toString() {
		assertNotNull("Os valores deveriam ser iguais", dep.toString());
	}
}
