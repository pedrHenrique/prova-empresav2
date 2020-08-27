package br.com.contmatic.empresav2.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Iterator;
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
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.google.common.base.VerifyException;

import static org.apache.log4j.BasicConfigurator.configure;

import br.com.contmatic.empresav2.model.Departamento;
import br.com.contmatic.empresav2.template.FixtureTempleateLoader;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepartamentoTest {
    
    // Variáveis

    private static final String NULLSTR = null;
    private static final String EMPTYSTR = "";
    private static final Long NULLONG = null;
    private static final Long EMPTYLONG = (long) 0;
    private static final Integer NULLINT = null;
    private static final int EMPTYINT = 0;

    private static Departamento departamento;
    private Departamento dep; // criado para testar diferença de instâncias

    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    private Validator validator;
    
    //Configuração do Teste

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        FixtureFactoryLoader.loadTemplates("br.com.contmatic.empresav2.template");
        departamento = new Departamento(1, "Financeiro", 155);
        departamento = new Departamento(2, "Recursos Humanos", 285);
        departamento = new Departamento(3, "Tecnologias", 405);
    }

    @Before
    public void setUp() throws Exception {
        this.dep = new Departamento();
        dep = Fixture.from(Departamento.class).gimme("valido");
        // ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        // this.validator = factory.getValidator();
    }

    @After
    public void tearDown() throws Exception {
        this.dep = null;
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        Departamento.getDepartamentoLista().clear();
        departamento = null;
    }

    /*
     * Seção de testes dos métodos de criação dos objetos da classe
     */

    // TODO Testes de Departamento Precisam ser revistos e alguns removidos

    
    @Test
    public void deve_criar_departamento_valido_atraves_deConstrutor_utilizando_Fixture() {
        dep = new Departamento(dep.getIdDepartamento(), dep.getNome(), dep.getRamal()); // Sobrescrita de Objeto!!
        assertThat("O departamento devia ter sido criado e armazenado: ", Departamento.getDepartamentoLista().contains(dep), equalTo(true));
        assertNotNull("O objeto não deveria estar nulo", dep);
    }
    
    @Test
    public void deve_criar_departamento_valido_atraves_deMetodo_utilizando_Fixture() {
        dep.registraDep(dep.getIdDepartamento(), dep.getNome(), dep.getRamal());
        assertThat("O departamento devia ter sido criado e armazenado: ", Departamento.getDepartamentoLista().contains(dep), equalTo(true));
        assertNotNull("O objeto não deveria estar nulo", dep);
    }

    @Test(expected = VerifyException.class)
    public void nao_deve_registrar_departamento_com_ID_ja_utilizado() {
        dep.registraDep(1, "Financeiro", 226);
    }

    @Test(expected = NullPointerException.class)
    public void nao_deve_criar_departamento_nulo() {
        dep = new Departamento(NULLONG, NULLSTR, NULLINT);
    }

    /*
     * Seção de testes dos métodos de remoção de objetos da Collection.
     */

    @Test
    public void deve_remover_objeto_ja_existente_daCollection_com_sucesso() {
        //Registra para depois remover
        dep.registraDep(dep.getIdDepartamento(), dep.getNome(), dep.getRamal());
        departamento.removeDep(dep.getIdDepartamento());
        assertThat("O departamento não deve estar registrado", Departamento.getDepartamentoLista().contains(dep), equalTo(false));

    }

    @Test(expected = VerifyException.class)
    public void nao_deve_remover_departamento_nao_existente() {
        dep.removeDep(1050);
    }

    /*
     * Seção de testes dos métodos de busca de objetos da Collection
     */

    @Test // Testando a busca por objetos num HashSet
    public void deve_retornar_objeto_ja_cadastrado() {
        assertNotNull("Esperava receber um objeto", dep.solicitaDep(1));
        assertNotNull("Esperava receber um objeto", dep.solicitaDep(2));
        assertNotNull("Esperava receber um objeto", dep.solicitaDep(3));
    }

    @Test(expected = VerifyException.class)
    public void nao_deve_retornar_departamento_nao_existente() {
        dep.solicitaDep(1050);
    }

    /*
     * Seção de testes dos getters/setters da classe
     */

    @Test
    public void teste_setId_e_getId_corretos() {
        departamento.setIdDepartamento(dep.getIdDepartamento());
        assertThat("Os valores deveriam ser iguais: ", departamento.getIdDepartamento(), equalTo(dep.getIdDepartamento()));
    }

    @Test(expected = NullPointerException.class)
    public void setIdDepartamento_nao_deve_aceitar_valores_nulos() {
        dep.setIdDepartamento(NULLONG);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setIdDepartamento_nao_deve_aceitar_valores_vazios() {
        dep.setIdDepartamento(EMPTYLONG);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setIdDepartamento_nao_deve_aceitar_valores_incorretos() {
        dep.setIdDepartamento(-5);
    }
    
    @Test
    public void teste_setNome_e_getNome_corretos() {
        departamento.setNome(dep.getNome());
        assertThat("Os valores deveriam ser iguais: ", departamento.getNome(), equalTo(dep.getNome())); // recebemos/esperado
    }

    @Test(expected = NullPointerException.class)
    public void setNome_nao_deve_aceitar_valor_nulo() {
        dep.setNome(NULLSTR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNome_nao_deve_aceitar_valor_vazio() {
        dep.setNome(EMPTYSTR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNome_nao_deve_aceitar_caracteres_especiais() {
        dep.setNome("¥$Õ¨¬Q@#%");
    }

    @Test
    public void teste_setRamal_e_getRamal_corretos() {
        departamento.setRamal(dep.getRamal());
        assertThat("Os valores deveriam ser iguais: ", departamento.getRamal(), equalTo(dep.getRamal()));
    }

    @Test(expected = NullPointerException.class)
    public void setRamal_nao_deve_aceitar_valores_nulos() {
        dep.setRamal(NULLINT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setRamal_nao_deve_aceitar_valores_vazios() {
        dep.setRamal(EMPTYINT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void teste_setRamal_valor_nao_permitidos() {
        dep.setRamal(-9420);
    }

    /*
     * Está seção de testes tem o intuito de testar os métodos de listagem
     */

    @Test
    public void deve_retornar_lista_de_departamentos() {
        // System.out.println(dep.listarDepartamentos());
        assertNotNull("Esperava receber uma lista", dep.listarDepartamentos());
    }

    @Test
    public void toString_deve_retornar_lista_de_departamentos() { // teste com falha
        // System.out.println(Departamento.getDepartamentoLista().toString());
        assertNotNull("Esperava receber uma lista", Departamento.getDepartamentoLista().toString());
    }
    
    /**
     * Teste Específico: Não Registra nome apenas com espaços em branco.
     */
    @Test(expected = IllegalArgumentException.class)
    public void nao_deve_registra_nome_com_espacos_emBranco() {
        dep.registraDep(4, "                ", 99);
    }

    /**
     * Teste Específico: Não Registra nome que apresente números.
     */
    @Test(expected = IllegalArgumentException.class)
    public void nao_deve_registra_nome_com_numeros() {
        dep.registraDep(5, "Cleber4578Araujo", 405);
    }

    // @Test // (expected = IllegalArgumentException.class)
    // public void teste_notifica_annotation_validator_lista_incorreto() {
    // System.out.println("Testes Iniciais de ValidatorFactory:");
    // ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
    // Validator validator = vf.getValidator();
    //
    // System.out.println("\n //Before Clear: " + Departamento.getDepartamentoLista().toString());
    // Departamento.getDepartamentoLista().clear();
    // System.out.println("\n //After Clear: " + Departamento.getDepartamentoLista().toString());
    //
    // try
    // {
    // System.out.println("-----Cadastrando Novos Departamentos invalidos------\n");
    // new Departamento(dep.getIdDepartamento(), dep.getNome(), dep.getRamal());
    //
    // } catch (IllegalArgumentException e) {
    // for(int i = 0 ; i < 4 ; i++) {
    // dep = Fixture.from(Departamento.class).gimme("invalido");
    // new Departamento(dep.getIdDepartamento(), dep.getNome(), dep.getRamal());
    // }
    //
    // System.out.println("\nLista Concluída. Partindo para seleção de constraintViolation\n");
    //
    // Iterator<Departamento> iterator = Departamento.getDepartamentoLista().iterator();
    // Set<ConstraintViolation<Departamento>> constraintViolations;
    // Departamento obj = new Departamento();
    //
    // while (iterator.hasNext()) {
    // obj = iterator.next();
    // constraintViolations = validator.validate(obj);
    // try
    // {
    // for(ConstraintViolation<Departamento> cv : constraintViolations) {
    // System.out.println(String.format("Erro Encontrado! propriedade: [%s], value: [%s], message: [%s]", cv.getPropertyPath(), cv.getInvalidValue(), cv.getMessage()));
    // }
    // } catch(Throwable f) {
    // System.out.println("e");
    // }
    //
    // }
    // }

    // @Test TESTES DESATIVADOS PARA REFORMULAÇÃO
    // public void teste_annotation_validator_correto() { // Código Retirado do Stack Overflow
    // ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
    // Validator validator = vf.getValidator();
    // dep = new Departamento();
    //
    // Set<ConstraintViolation<Departamento>> constraintViolations = validator.validate(dep.registraDep(5, "Gilberto", 450));
    //
    // for(ConstraintViolation<Departamento> cv : constraintViolations) {
    // System.out.println(String.format("Erro Encontrado! propriedade: [%s], value: [%s], message: [%s]", cv.getPropertyPath(), cv.getInvalidValue(), cv.getMessage()));
    // }
    // }
}
