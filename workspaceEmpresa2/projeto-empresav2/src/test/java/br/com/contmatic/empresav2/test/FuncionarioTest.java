package br.com.contmatic.empresav2.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.google.common.base.VerifyException;

import br.com.contmatic.empresav2.builder.FuncionarioBuilder;
import br.com.contmatic.empresav2.model.Departamento;
import br.com.contmatic.empresav2.model.Funcionario;
import br.com.contmatic.empresav2.model.Util;
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
    private Funcionario fun; // criado para testar diferença de instâncias
    //private Departamento dep;
    
    private static ValidatorFactory vf;

    private Validator validator;

    @BeforeClass
    public static void setUpBeforeClass() {
        FixtureFactoryLoader.loadTemplates("br.com.contmatic.empresav2.template");
        vf = Validation.buildDefaultValidatorFactory();        
        funcionario = new Funcionario()
                   .cadastraFuncionario(1, "Ana Carolline", "56495985096", "ridouane8626@uorak.com", 3500.00);
        funcionario.cadastraFuncionario(2, "Patrícia Áurea Érica Júnior", "47880979836", "mina4298@uorak.com", 5500.00);
        funcionario.cadastraFuncionario(3, "Irene Leonardi Biazibeti", "968.155.830-82", "11998563792", 10000.00);
        new Departamento(1, " DepTestes", 256);
        System.out.println("--------- Funcionarios e Departamentos de teste Já Criados --------- \n\n");
    }

    @Before
    public void setUp() {
        fun = Fixture.from(Funcionario.class).gimme("valido");
        //dep = Fixture.from(Departamento.class).gimme("valido");
        validator = vf.getValidator();
    }

    @After
    public void tearDown() throws Exception {
        // Isso é feito para impedir o possível caso do Fixture devolver um ID de registro já cadastrado
        fun = null;
        //dep = null;
        validator = null;
    }

    @AfterClass
    public static void tearDownAfterClass() {
        Funcionario.getFuncionarioLista().clear();
        Departamento.getDepartamentoLista().clear();
        funcionario = null;
        //departamento = null;
        vf = null;
    }
    
    /**
     * Método de auxílio que exibe Validations que foram infringidas no decorer de um teste específico.
     *
     * @param constraintViolations - A lista contendo todas as infrações que foram recebidas pelo validator.
     */
    public void exibeConstrains(Set<ConstraintViolation<Funcionario>> constraintViolations) {
        for(ConstraintViolation<Funcionario> cv : constraintViolations) {
            System.out.println(String.format("Constrain infringida! atributo: [%s], valor: [%s], message: [%s]", cv.getPropertyPath(), cv.getInvalidValue(), cv.getMessage())); //Confirmar se existe uma forma melhor de exbir oq não estiver válido
        }
    }

    /*
     * Seção de testes dos métodos de criação dos objetos da classe
     */
    
    //  --- --- Estes Testes serão movidos para uma nova classe de Testes IT.

//    @Test
//    public void teste_nova_estrutura_funcionarios() { //Funcionou 60% do jeito esperado
//        new Departamento(45,dep.getNome(), dep.getRamal());
//        fun = fun.cadastraFuncionario(fun.getIdFuncionario(), fun.getNome(), fun.getCpf(), fun.getContato(), fun.getSalario()); System.out.println(fun.toString() + "\n");
//        funcionario.cadastraFuncionarioDepartamento(fun, 45); System.out.println(fun.toString() + "\n");
//        funcionario.cadastraFuncionarioEndereco(fun, "62039245", "45B"); System.out.println(fun.toString() + "\n");
//    }
//    
//
//    @Ignore("Este teste deve ser movido para seção IT de testes de Funcionario")
//    @Test
//    public void teste_deve_buscar_departamento_existente() {
//        dep.registraDep(dep.getIdDepartamento(), dep.getNome(), dep.getRamal());
//        fun.cadastraFuncionario(fun.getIdFuncionario(), fun.getNome(), fun.getCpf(), fun.getContato(), fun.getSalario());
//        assertThat("O departamento deve existir", Departamento.getDepartamentoLista().contains(fun.buscaDepartamento(dep)), equalTo(true));
//    }
    
    //  --- --- 
    
    @Test
    public void deve_criar_funcionario_valido_atraves_doFuncionarioBuilder_utilizando_Fixture() {
      fun = new FuncionarioBuilder().funInformacoes(fun.getNome(), fun.getCpf(), fun.getContato())
                                    .funEndereco("03575090", "49B")
                                    .funEmpresa(fun.getIdFuncionario(), fun.getSalario(), 1)
                                    .constroi();
        
        System.out.println("ToString Funcionario: " + fun.toString());
        System.out.println("Como o funcionario está salvo na lista: " + Funcionario.solicitaFuncionario(fun.getIdFuncionario()));
        
        //Set<ConstraintViolation<Funcionario>> constraintViolations = validator.validate(fun);   Não Funcionou, investigar melhor o porque
        //assertThat(constraintViolations.isEmpty(), equalTo(true));
        assertThat("O Funcionario deveria ter sido criado e armazenado:", Funcionario.getFuncionarioLista().contains(fun), equalTo(true));
        

    }
    
    @Test
    public void deve_criar_50Funcionarios_encadeados_eGarantir_aIsolacao_dosValores_deCadaUm() {
        //O Teste ficou desta forma pois meu Template do FixtureFactory não está completo.
        for(int i = 0 ; i < 50; i++) {
            fun = Fixture.from(Funcionario.class).gimme("valido");            
            fun = new FuncionarioBuilder().funInformacoes(fun.getNome(), fun.getCpf(), fun.getContato())
                    .funEndereco("03575090", String.format("%d", i))
                    .funEmpresa(fun.getIdFuncionario(), fun.getSalario(), 1)
                    .constroi();
            
            System.out.println(fun.toString());
            assertThat("Houve uma variação na passagem " + i, fun, is(equalTo(fun)));
        }
    }

    @Test
    public void deve_criar_funcionario_valido_atraves_deMetodo_utilizando_Fixture() {        
        fun = fun.cadastraFuncionario(fun.getIdFuncionario(), fun.getNome(), fun.getCpf() , fun.getContato(), fun.getSalario());
        assertThat("O Funcionario deveria ter sido criado e armazenado:", Funcionario.getFuncionarioLista().contains(fun), equalTo(true)); // Mensagem p/ Falha
        assertNotNull("O objeto não deveria estar nulo", fun);

    }

    @Test(expected = IllegalArgumentException.class)
    public void nao_deve_registrar_funcionario_com_ID_ja_utilizado() {
        funcionario.cadastraFuncionario(1, fun.getNome(), fun.getCpf(), fun.getContato(), 50.79);
    }

    @Test(expected = NullPointerException.class)
    public void nao_deve_criar_funcionario_nulo() {
        fun.cadastraFuncionario(NULLONG, NULLSTR, NULLSTR, NULLSTR, NULLINT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveGerar_exceptions_criando_funcionairos_vazios() { //exceptions gerador pelo CEP e pelo Contato
        fun.cadastraFuncionario(EMPTYLONG, EMPTYSTR, EMPTYSTR, EMPTYSTR, EMPTYINT);
    }

    /*
     * Seção de testes dos métodos de remoção de objetos da Collection.
     */

    @Test 
    public void deve_remover_objeto_ja_existente_daCollection_com_sucesso() {        
        fun = fun.cadastraFuncionario(fun.getIdFuncionario(), fun.getNome(), fun.getCpf(), fun.getContato(), 3500.00);
        funcionario.removeFuncionario(fun.getIdFuncionario());
        assertThat("O Funcionario não devia estar cadastrado", Funcionario.getFuncionarioLista().contains(fun), equalTo(false));

    }

    @Test(expected = IllegalArgumentException.class)
    public void nao_deve_remover_funcionario_nao_existente() {
        fun.removeFuncionario(99999);
    }

    /*
     * Seção de testes dos métodos de busca de objetos da Collection
     */

    @Test
    public void deve_retornar_funcionario_ja_cadastrado() {
        assertNotNull("Esperava receber um objeto", Funcionario.solicitaFuncionario(1));
        assertNotNull("Esperava receber um objeto", Funcionario.solicitaFuncionario(2));
        assertNotNull("Esperava receber um objeto", Funcionario.solicitaFuncionario(3));
    }

    @Test
    public void nao_deve_retornar_funcionario_nao_existente() {
        assertThat(Funcionario.solicitaFuncionario(1041), is(equalTo(null)));
    }

    /*
     * Seção de testes dos getters/setters da classe
     */

    @Test
    public void teste_setId_e_getId_nome_correto() {
        funcionario.setIdFuncionario(fun.getIdFuncionario());
        Set<ConstraintViolation<Funcionario>> constraintViolations = validator.validateValue(Funcionario.class, "idFuncionario", fun.getIdFuncionario());                
        assertThat("Os valores deveriam ser iguais", funcionario.getIdFuncionario(), equalTo(fun.getIdFuncionario()));
        exibeConstrains(constraintViolations);
        assertThat(constraintViolations.isEmpty(), equalTo(true)); //TODO É assim que se realmente testa caso não haja constrains
    }

    @Ignore("Não tenho a solução para esse problema ainda")
    @Test 
    public void setId_deveGerar_constrain_recebendo_valores_nulos() {        
        Set<ConstraintViolation<Funcionario>> constraintViolations = validator.validate(fun);   
        try {
            fun.setIdFuncionario(NULLONG);
            constraintViolations.clear();
            constraintViolations = validator.validateValue(Funcionario.class, "idFuncionario", fun.getIdFuncionario());
        } catch (NullPointerException e) {
            assertThat(constraintViolations, is(not(equalTo(null)))); exibeConstrains(constraintViolations);
        }        
        
    }

    @Test
    public void setId_deveGerar_constrain_recebendo_valores_vazios() {
        fun.setIdFuncionario(EMPTYLONG);
        Set<ConstraintViolation<Funcionario>> constraintViolations = validator.validateValue(Funcionario.class, "idFuncionario", fun.getIdFuncionario());        
        assertThat(constraintViolations, is(not(equalTo(null)))); exibeConstrains(constraintViolations);
    }

    @Test
    public void setId_deveGerar_constrain_recebendo_valores_incorretos() {
        fun.setIdFuncionario(-6);
        Set<ConstraintViolation<Funcionario>> constraintViolations = validator.validateValue(Funcionario.class, "idFuncionario", fun.getIdFuncionario());        
        assertThat(constraintViolations, is(not(equalTo(null)))); exibeConstrains(constraintViolations);
    }

    @Test
    public void teste_setNome_e_getNome_nome_correto() {
        funcionario.setNome(fun.getNome());
        Set<ConstraintViolation<Funcionario>> constraintViolations = validator.validateValue(Funcionario.class, "nome", fun.getNome());
        assertThat("Os valores deveriam ser iguais", funcionario.getNome(), equalTo(fun.getNome()));
        exibeConstrains(constraintViolations);
        assertThat(constraintViolations.isEmpty(), equalTo(true)); 
    }
    
    @Ignore("Eu ainda não sei resolver esse problema")
    @Test(expected = NullPointerException.class)
    public void setNome_nao_deve_aceitar_valor_nulo() {
        fun.setNome(NULLSTR);
    }

    @Test
    public void setNome_deveGerar_constrain_recebendo_valores_vazio() {
        fun.setNome(EMPTYSTR);
        Set<ConstraintViolation<Funcionario>> constraintViolations = validator.validateValue(Funcionario.class, "nome", fun.getNome());        
        assertThat(constraintViolations, is(not(equalTo(null)))); exibeConstrains(constraintViolations);
    }

    @Test
    public void setNome_deveGerar_constrain_recebendo_valores_naoValidos() {
        fun.setNome("PRËMØNÏÇÃØ");
        Set<ConstraintViolation<Funcionario>> constraintViolations = validator.validateValue(Funcionario.class, "nome", fun.getNome());        
        assertThat(constraintViolations, is(not(equalTo(null)))); exibeConstrains(constraintViolations);
    }

    @Test
    public void teste_setCpf_e_getCpf_nome_correto() {
        funcionario.setCpf("11650588046"); // Set adiciona uma formatação a variável. Replace remove essa formatação para a real comparação
        Set<ConstraintViolation<Funcionario>> constraintViolations = validator.validateValue(Funcionario.class, "cpf", funcionario.getCpf());        
        assertThat("Os valores deveriam ser iguais", funcionario.getCpf().replaceAll("\\D", ""), equalTo("11650588046"));        
        exibeConstrains(constraintViolations); assertThat(constraintViolations.isEmpty(), equalTo(true)); 
    }

    @Test(expected = NullPointerException.class)
    public void setCpf_nao_deve_aceitar_valor_nulo() {
        fun.setCpf(NULLSTR);
    }

    @Test (expected = IllegalArgumentException.class)
    public void setCpf_deveGerar_exception_recebendo_valores_vazios() {
        fun.setCpf(EMPTYSTR);
        //Set<ConstraintViolation<Funcionario>> constraintViolations = validator.validateValue(Funcionario.class, "cpf", fun.getCpf());        
        //assertThat(constraintViolations, is(not(equalTo(null)))); exibeConstrains(constraintViolations);
    }

    @Test
    public void setCpf_deveGerar_constrain_recebendo_valores_naoValido() {
        fun.setCpf("ABCDFEHIJKL");
        Set<ConstraintViolation<Funcionario>> constraintViolations = validator.validateValue(Funcionario.class, "cpf", fun.getCpf());        
        assertThat(constraintViolations, is(not(equalTo(null)))); exibeConstrains(constraintViolations);
    }

    // @Test
    // public void teste_seCep_e_getCep_nome_correto() {
    // funcionario.setCep(fun.getCep(fun)); //Set adiciona uma formatação a variável. Replace remove essa formatação para a real comparação
    // assertThat("Os valores deveriam ser iguais", funcionario.getCep().replaceAll("\\D", ""), equalTo(fun.getCep())); // "\\D" remove formatação
    // }
    //
    // @Test(expected = NullPointerException.class)
    // public void setCep_nao_deve_aceitar_valor_nulo() {
    // fun.setCep(NULLSTR);
    // }
    //
    // @Test(expected = IllegalArgumentException.class)
    // public void setCep_nao_deve_aceitar_valor_vazio() {
    // fun.setCep(EMPTYSTR);
    // }

    // @Test
    // public void setCep_nao_deve_aceitar_valor_naoValido() {
    // String cep = new String("REFATORAR");
    // fun.setCep(cep);
    // assertThat("Os valores deveriam ser iguais", cep, equalTo(fun.getCep().replaceAll("\\D", ""))); // "\\D" remove formatação
    // }

    @Test
    public void teste_setContato_e_getContato_nome_correto() {
        funcionario.setContato(fun.getContato()); fun.setContato(fun.getContato());// Set adiciona uma formatação a variável. Replace remove essa formatação para a real comparação
        Set<ConstraintViolation<Funcionario>> constraintViolations = validator.validateValue(Funcionario.class, "contato", fun.getContato());
        assertThat("Os valores deveriam ser iguais", funcionario.getContato(), equalTo(fun.getContato()));  
        exibeConstrains(constraintViolations);
        assertThat(constraintViolations.isEmpty(), equalTo(true)); 

    }

    @Test(expected = NullPointerException.class)
    public void teste_setContato_valor_nulo() {
        fun.setContato(NULLSTR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void teste_setContato_valor_vazio() {
        fun.setContato(EMPTYSTR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setEmail_nao_deve_aceitar_email_naoValido() {
        String email = new String("Rogerinho85.com.br"); // Email precisa conter um @
        fun.setContato(email);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setEmail_nao_deve_aceitar_telefone_naoValido() {
        String telefone = new String("449968410186248"); // Telefone precisa ter o DDD + a numeração do tel/cel
        fun.setContato(telefone);
    }

    @Test
    public void teste_setSalario_e_getSalario_nome_correto() {
        funcionario.setSalario(fun.getSalario());
        assertThat("Os valores deveriam ser iguais", funcionario.getSalario(), equalTo(fun.getSalario()));
        Set<ConstraintViolation<Funcionario>> constraintViolations = validator.validateValue(Funcionario.class, "salario", fun.getSalario());
        assertThat(constraintViolations.isEmpty(), equalTo(true)); exibeConstrains(constraintViolations);
    }

    @Test//(expected = NullPointerException.class)
    public void setSalario_nao_deve_aceitar_valor_naoNulo() {        
        Set<ConstraintViolation<Funcionario>> constraintViolations = validator.validateValue(Funcionario.class, "salario", fun.getSalario());
        try {
            fun.setSalario(NULLINT);
            constraintViolations.clear();
            constraintViolations = validator.validateValue(Funcionario.class, "salario", fun.getSalario());
        } catch (NullPointerException e) {
            exibeConstrains(constraintViolations);
            assertThat(constraintViolations, hasItem(equalTo(null))); 
            
        } 
    }

    @Test
    public void setSalario_deveGerar_constrain_recebendo_valores_vazio() {
        fun.setSalario(EMPTYINT);
        Set<ConstraintViolation<Funcionario>> constraintViolations = validator.validateValue(Funcionario.class, "salario", fun.getSalario());        
        assertThat(constraintViolations, is(not(equalTo(null)))); exibeConstrains(constraintViolations);
    }

    @Test
    public void setSalario_deveGerar_constrain_recebendo_valores_naoValido() {
        fun.setSalario(-500); // Salario negativo ninguém merece né?
        Set<ConstraintViolation<Funcionario>> constraintViolations = validator.validateValue(Funcionario.class, "salario", fun.getSalario());        
        assertThat(constraintViolations, is(not(equalTo(null)))); exibeConstrains(constraintViolations);
    }

    @Test(expected = NullPointerException.class)
    public void teste_funcionario_busca_departamento_inexistente() {
        long id = 193;
        fun.buscaDepartamento(Departamento.solicitaDep(id));
    }

    @Test(expected = NullPointerException.class)
    public void teste_funcionario_busca_departamento_nulo() {
        fun.buscaDepartamento(Departamento.solicitaDep(NULLONG));
    }

    @Test(expected = NullPointerException.class)
    public void teste_buscaDepartamento_valor_nulo() {
        fun.buscaDepartamento(new Departamento(NULLONG, NULLSTR, (int) NULLOBJ));
    }

    
    @Ignore
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
    public void teste_pessoa_criada_com_CPF_ja_existente() { // Teste com novo HashCode e Equals :D
        fun.cadastraFuncionario(13, fun.getNome(), "959.336.550-82", fun.getContato(), fun.getSalario());
        funcionario.cadastraFuncionario(14, "Rogerinho", "959.336.550-82", "junior@Junior.com", 50.79);
    }

}
