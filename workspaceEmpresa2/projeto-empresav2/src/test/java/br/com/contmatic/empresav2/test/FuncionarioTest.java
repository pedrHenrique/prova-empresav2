package br.com.contmatic.empresav2.test;

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

import com.google.common.base.VerifyException;

import br.com.contmatic.empresav2.model.Departamento;
import br.com.contmatic.empresav2.model.Funcionario;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FuncionarioTest {

    private static final Object NULLOBJ = null;
    private static final String NULLSTR = null;
    private static final String EMPTYSTR = "";
    private static final Long NULLONG = null;
    private static final Long EMPTYLONG = (long) 0;
    private static final Double NULLINT = null;
    private static final Double EMPTYINT = 0.0;
    private static final int EMPTYINTEGER = 0;

    private static Funcionario funcionario;
    private static Departamento departamento;
    private Funcionario fun; // criado para testar diferença de instâncias
    private Departamento dep;

    @BeforeClass
    public static void setUpBeforeClass() {
        FixtureFactoryLoader.loadTemplates("br.com.contmatic.empresav2.template");
        departamento = new Departamento(1, " DepTestes", 256);
        funcionario = new Funcionario(1, "Ana Carolline", "56495985096", "03575090", "ridouane8626@uorak.com", 1, 3500.00);
        funcionario.cadastraFuncionario(2, "Patrícia Áurea Érica Júnior", "23818613940", "27971764", "mina4298@uorak.com", 1, 5500.00);
        funcionario.cadastraFuncionario(3, "Irene Leonardi Biazibeti", "60178475971", "49082030", "11998563792", 1, 10000.00);

    }

    @Before
    public void setUp() {
        fun = Fixture.from(Funcionario.class).gimme("valido"); 
        dep = Fixture.from(Departamento.class).gimme("valido");   
    }

    @After
    public void tearDown() throws Exception {
        //Isso é feito para impedir o possível caso do Fixture devolver um ID de registro já cadastrado
        fun = null;
        dep = null;
    }
    
    @AfterClass
    public static void tearDownAfterClass() {
        Funcionario.getFuncionarioLista().clear();
        Departamento.getDepartamentoLista().clear();
        funcionario = null;
        departamento = null;
    }

    /*
     * Seção de testes dos métodos de criação dos objetos da classe
     */

    @Test
    public void deve_criar_funcionario_valido_atraves_doConstrutor_utilizando_Fixture() {
        dep.registraDep(dep.getIdDepartamento(), dep.getNome(), dep.getRamal());
        fun = new Funcionario(fun.getIdFuncionario(), fun.getNome(), fun.getCpf(), "03575090", fun.getContato(), dep.getIdDepartamento(), 1500.00);
        
        assertThat("O Funcionario deveria ter sido criado e aramazenado:", Funcionario.getFuncionarioLista().contains(fun), equalTo(true));
        assertNotNull("O objeto não deveria estar nulo", fun);
        System.out.println(fun.listaFuncionario());
    }

    @Test
    public void deve_criar_funcionario_valido_atraves_deMetodo_utilizando_Fixture() {
        dep.registraDep(dep.getIdDepartamento(), dep.getNome(), dep.getRamal());
        fun.cadastraFuncionario(fun.getIdFuncionario(), fun.getNome(), fun.getCpf(), "69915890", fun.getContato(), dep.getIdDepartamento(), 5000.00);
        
        assertThat("O Funcionario deveria ter sido criado e aramazenado:", Funcionario.getFuncionarioLista().contains(fun), equalTo(true));
        assertNotNull("O objeto não deveria estar nulo", fun);
        System.out.println(fun.listaFuncionario());
    }
    
    @Test(expected = VerifyException.class)
    public void nao_deve_registrar_funcionario_com_ID_ja_utilizado() {
        funcionario.cadastraFuncionario(1, fun.getNome(), fun.getCpf(), "69915890", fun.getContato(), dep.getIdDepartamento(), 50.79);
    }


    @Test(expected = NullPointerException.class)
    public void nao_deve_criar_funcionario_nulo() {
        fun.cadastraFuncionario(NULLONG, NULLSTR, NULLSTR, NULLSTR, NULLSTR, NULLONG, NULLINT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nao_deve_criar_funcionario_vazio() {
        fun.cadastraFuncionario(EMPTYLONG, EMPTYSTR, EMPTYSTR, EMPTYSTR, EMPTYSTR, EMPTYLONG, EMPTYINT);
    }
    
    /*
     * Seção de testes dos métodos de remoção de objetos da Collection.
     */

    @Test
    public void deve_remover_objeto_ja_existente_daCollection_com_sucesso() {
        dep.registraDep(dep.getIdDepartamento(), dep.getNome(), dep.getRamal());
        fun.cadastraFuncionario(fun.getIdFuncionario(), fun.getNome(), fun.getCpf(), "69915890", fun.getContato(), dep.getIdDepartamento(), 3500.00);
        funcionario.removeFuncionario(fun.getIdFuncionario());
        assertThat("O Funcionario não devia estar cadastrado", Funcionario.getFuncionarioLista().contains(fun), equalTo(false));

    }

    @Test(expected = VerifyException.class)
    public void nao_deve_remover_funcionario_nao_existente() {
        fun.removeFuncionario(1040);
    }

    /*
     * Seção de testes dos métodos de busca de objetos da Collection
     */
    
    @Test
    public void deve_retornar_funcionario_ja_cadastrado() {
        assertNotNull("Esperava receber um objeto", fun.solicitaFuncionario(1));
        assertNotNull("Esperava receber um objeto", fun.solicitaFuncionario(2));
        assertNotNull("Esperava receber um objeto", fun.solicitaFuncionario(3));
    }
    
    @Test(expected = VerifyException.class)
    public void nao_deve_retornar_funcionario_nao_existente() {
        fun.solicitaFuncionario(1041);
    }


    /*
     * Seção de testes dos getters/setters da classe
     */

    @Test
    public void teste_setId_e_getId_nome_correto() {
        funcionario.setIdFuncionario(fun.getIdFuncionario());
        assertThat("Os valores deveriam ser iguais", funcionario.getIdFuncionario(), equalTo(fun.getIdFuncionario()));
    }

    @Test(expected = NullPointerException.class)
    public void setId_nao_deve_aceitar_nulos() {
        fun.setIdFuncionario(NULLONG);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setId_nao_deve_aceitar_vazios() {
        fun.setIdFuncionario(EMPTYLONG);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void setId_nao_deve_aceitar_valores_incorretos() {
        fun.setIdFuncionario(-6);
    }

    @Test
    public void teste_setNome_e_getNome_nome_correto() {
        funcionario.setNome(fun.getNome());
        assertThat("Os valores deveriam ser iguais", funcionario.getNome(), equalTo(fun.getNome()));
    }

    @Test(expected = NullPointerException.class)
    public void setNome_nao_deve_aceitar_valor_nulo() {
        fun.setNome(NULLSTR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNome_nao_deve_aceitar_valor_vazio() {
        fun.setNome(EMPTYSTR);
    }

    @Test (expected = IllegalArgumentException.class)
    public void setNome_nao_deve_aceitar_caracteres_naoValidos() {
        String name = new String("PRËMØNÏÇÃØ");
        fun.setNome(name);
    }
    
    @Test
    public void teste_setCpf_e_getCpf_nome_correto() {
        funcionario.setCpf(fun.getCpf());                       //Remoção de pontuação para comparação efetiva dos dois valores
        assertThat("Os valores deveriam ser iguais", funcionario.getCpf().replaceAll("\\D", ""), equalTo(fun.getCpf()));
    }

    @Test(expected = NullPointerException.class)
    public void setCpf_nao_deve_aceitar_valor_nulo() {
        fun.setCpf(NULLSTR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setCpf_nao_deve_aceitar_valor_vazio() {
        fun.setCpf(EMPTYSTR);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void setCpf_nao_deve_aceitar_valor_naoValido() {
        String CPF = "ABCDFEHIJKL";
        fun.setCpf(CPF);
    }

//    @Test
//    public void teste_seCep_e_getCep_nome_correto() {
//        String cep = new String("03575090");
//        fun.setCep(cep);
//        assertThat("Os valores deveriam ser iguais", cep, equalTo(fun.getCep().replaceAll("\\D", ""))); // "\\D" remove formatação
//    }
//
//    @Test(expected = NullPointerException.class)
//    public void setCep_nao_deve_aceitar_valor_nulo() {
//        fun.setCep(NULLSTR);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void setCep_nao_deve_aceitar_valor_vazio() {
//        fun.setCep(EMPTYSTR);
//    }
    
//  @Test
//  public void setCep_nao_deve_aceitar_valor_naoValido() {
//      String cep = new String("03575090");
//      fun.setCep(cep);
//      assertThat("Os valores deveriam ser iguais", cep, equalTo(fun.getCep().replaceAll("\\D", ""))); // "\\D" remove formatação
//  }

    @Test
    public void teste_setContato_e_getContato_nome_correto() {
        funcionario.setContato(fun.getContato());
        assertThat("Os valores deveriam ser iguais", funcionario.getContato(), equalTo(fun.getContato()));

    }

    @Test(expected = java.lang.NullPointerException.class)
    public void teste_setContato_valor_nulo() {
        fun.setContato(NULLSTR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void teste_setContato_valor_vazio() {
        fun.setContato(EMPTYSTR);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void setEmail_nao_deve_aceitar_email_naoValido() {
        String email = new String("Rogerinho85.com.br"); //Email precisa conter um @
        fun.setContato(email);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void setEmail_nao_deve_aceitar_telefone_naoValido() {
        String telefone = new String("449968410186248"); //Telefone precisa ter o DDD + a numeração do tel/cel
        fun.setContato(telefone);
    }

    @Test
    public void teste_setSalario_e_getSalario_nome_correto() {
        funcionario.setSalario(fun.getSalario());
        assertThat("Os valores deveriam ser iguais", funcionario.getSalario(), equalTo(fun.getSalario()));
    }

    @Test(expected = NullPointerException.class)
    public void setSalario_nao_deve_aceitar_valor_naoNulo() {
        fun.setSalario(NULLINT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setSalario_nao_deve_aceitar_valor_naoVazio() {
        fun.setSalario(EMPTYINT);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void setSalario_nao_deve_aceitar_valor_naoValido() {
        fun.setSalario(-500); //Salario negativo ninguém merece né?
    }

    @Test
    public void teste_deve_buscar_departamento_existente() {
        dep.registraDep(dep.getIdDepartamento(), dep.getNome(), dep.getRamal());
        fun.cadastraFuncionario(fun.getIdFuncionario(), fun.getNome(), fun.getCpf(), fun.getCep(), fun.getContato(), dep.getIdDepartamento(), fun.getSalario());
        assertThat("O departamento deve existir", Departamento.getDepartamentoLista().contains(fun.buscaDepartamento(dep)), equalTo(true));
    }

    @Test(expected = VerifyException.class)
    public void teste_funcionario_busca_departamento_inexistente() {
        long id = 193;
        fun.buscaDepartamento(dep.solicitaDep(id));
    }

    @Test(expected = NullPointerException.class)
    public void teste_funcionario_busca_departamento_nulo() {
        fun.buscaDepartamento(dep.solicitaDep(NULLONG));
    }

    @Test(expected = NullPointerException.class)
    public void teste_buscaDepartamento_valor_nulo() {
        fun.buscaDepartamento(new Departamento(NULLONG, NULLSTR, (int) NULLOBJ));
    }

    @Test(expected = IllegalArgumentException.class)
    public void teste_buscaDepartamento_valor_vazio() {
        fun.buscaDepartamento(new Departamento(EMPTYLONG, EMPTYSTR, EMPTYINTEGER));
    }
    
    /*
     * Está seção de testes tem o intuito de testar os métodos de listagem
     */

    @Test
    public void teste_listar_pessoas() {
        assertNotNull("Esperava receber uma lista: ", funcionario.listaFuncionario());
    }

    @Test
    public void teste_toString() {
        assertNotNull("Os valores deveriam ser iguais", funcionario.toString());
    }
    
    /**
     * Teste Específico: Não deveria registrar Funcionario que já possui CPF existente.
     */
    
    @Test(expected = IllegalArgumentException.class)
    @Ignore("Teste_ignorado_pois_funcionalidade_ainda_não_está_presente")
    public void teste_pessoa_criada_com_cpf_ja_existente() {
        long id = 13;
        fun.cadastraFuncionario(id, "Rogerinho", "56495985096", "69915890", "junior@Junior.com", 1, 50.79);
    }

}
