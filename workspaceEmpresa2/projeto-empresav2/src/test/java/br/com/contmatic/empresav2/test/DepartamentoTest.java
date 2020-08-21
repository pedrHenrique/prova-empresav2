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
import br.com.contmatic.empresav2.template.DepartamentoTempleateLoader;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepartamentoTest {

    private static final String NULLSTR = null;
    private static final String EMPTYSTR = "";
    private static final Long NULLONG = null;
    private static final Long EMPTYID = (long) 0;
    private static final Object NULLINT = null;
    private static final int EMPTYINT = 0;

    private static Departamento departamento;
    private Departamento dep; // criado para testar diferença de instâncias
    
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    
    private Validator validator;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        FixtureFactoryLoader.loadTemplates("br.com.contmatic.empresav2.template");
        departamento = new Departamento();

        departamento.registraDep(1, "Financeiro", 155);
        departamento.registraDep(2, "Recursos Humanos", 285);
        departamento.registraDep(3, "Tecnologias", 405);

    }

    @Before
    public void setUp() throws Exception {
        this.dep = new Departamento();
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        this.validator = factory.getValidator();
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
     * Está seção de testes tem o intuito de testar os métodos de criação dos objetos da classe
     */

    //TODO Testes de Departamento Precisam ser revistos e alguns removidos
    
    @Test (expected = IllegalArgumentException.class)
    public void teste_registra_departamento_nome_espacosEmBranco() { 
        dep.registraDep(50, "                ", 99);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void teste_registra_departamento_nome_comNumeros() { 
        dep.registraDep(88, "Cleber4578Araujo", 405);  
    }
    
//    @Test // Testa criando o obj pelo construtor
//    public void teste_objeto_criado_por_construtor() {
//        Departamento depart = Fixture.from(Departamento.class).gimme("valid");
//        assertThat("O Obj esperado era: ", depart, equalTo(departamento.solicitaDep(depart.getIdDepartamento()))); // what we got - expected
//        assertNotNull("O objeto não deveria estar nulo", depart.solicitaDep(depart.getIdDepartamento()));
//    }
    
    @Test
    public void teste_cria_departamento_atraves_do_Fixture() {
        dep = Fixture.from(Departamento.class).gimme("valido"); //dep recebe os valores, porém de nenhuma forma eles são registrados e armazenados (como deveria de ser esperado)....
        System.out.println("Valor pego do Fixture: " + dep);
        System.out.println(Departamento.getDepartamentoLista().toString()); 
        assertThat("O departamento não foi criado e armazenado: ", dep, equalTo(Departamento.getDepartamentoLista().contains(dep)));
        assertNotNull(dep);
    }

    @Test
    public void teste_objeto_criado_por_metodo_com_parametros() {
        long id = 11;

        assertThat("O Obj esperado era: ", dep.registraDep(id, "Expedição", 189), equalTo(departamento.solicitaDep(id)));

        assertNotNull("O objeto não deveria estar nulo", departamento.solicitaDep(id));

    }

    @Test(expected = VerifyException.class)
    public void teste_objeto_criado_ja_existente_() {
        long id = 1;
        dep.registraDep(id, "Financeiro", 226);
    }

    @Test(expected = NullPointerException.class)
    public void teste_objeto_sendo_criado_nulo_() {
        dep = new Departamento(NULLONG, "Qualidade", 250);

    }

    /*
     * Está seção de testes tem o intuito de testar os métodos de remoção dos objetos da classe
     */

    @Test //TODO Testes de Remoção Precisão ser refatorados
    public void teste_remocao_objeto_existente() {
        long id = 250;
        assertThat("Os objetos deveriam ser iguais: ", new Departamento(id, "Rogerio", 145), equalTo(dep.removeDep(id)));

    }

    @Test(expected = VerifyException.class)
    public void teste_remocao_objeto_nao_existente() {
        long id = 179;
        dep.removeDep(id);
    }

    /*
     * Está seção de testes tem o intuito de testar métodos de busca de objetos da classe
     */

    @Test // Testando a busca por objetos num HashSet
    public void teste_busca_departamento_existente() {
        assertNotNull("Esperava receber um objeto", dep.solicitaDep(1));
        assertNotNull("Esperava receber um objeto", dep.solicitaDep(2));
        assertNotNull("Esperava receber um objeto", dep.solicitaDep(3));
    }

    @Test(expected = VerifyException.class)
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
    
    @Test(expected = IllegalArgumentException.class)
    public void teste_setNome_valor_nao_permitido() {
        dep.setNome("¥$Õ¨¬Q@#%");
    }

    @Test
    public void teste_setId_e_getId_correto() {
        long id = 25;
        dep.setIdDepartamento(id);
        assertThat("Os valores deveriam ser iguais: ", id, equalTo(dep.getIdDepartamento()));
    }

    @Test(expected = NullPointerException.class)
    public void teste_setId_valor_nulo() {
        dep.setIdDepartamento(NULLONG);
    }

    @Test(expected = IllegalArgumentException.class)
    public void teste_setId_valor_vazio() {
        dep.setIdDepartamento(EMPTYID);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void teste_setId_valor_nao_permitido() {
        dep.setIdDepartamento(-5);
    }

    @Test
    public void teste_setRamal_e_getRamal_correto() {
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
    
    @Test(expected = IllegalArgumentException.class)
    public void teste_setRamal_valor_nao_permitido() {
        dep.setRamal(9420);
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
    
//  @Test TESTES DESATIVADOS PARA REFORMULAÇÃO
//  public void teste_annotation_validator_correto() { // Código Retirado do Stack Overflow 
//      ValidatorFactory vf = Validation.buildDefaultValidatorFactory(); 
//      Validator validator = vf.getValidator();
//      dep = new Departamento();
//
//      Set<ConstraintViolation<Departamento>> constraintViolations = validator.validate(dep.registraDep(5, "Gilberto", 450));
//
//      for(ConstraintViolation<Departamento> cv : constraintViolations) {
//          System.out.println(String.format("Erro Encontrado! propriedade: [%s], value: [%s], message: [%s]", cv.getPropertyPath(), cv.getInvalidValue(), cv.getMessage()));
//      }
//  }

//@Test (expected = IllegalArgumentException.class)
//public void teste_notifica_annotation_validator_lista_incorreto() { 
//    //Cadastrando diversos departamentos com nomes que não devem ser registrados
//    dep.registraDep(80, "PRËMØNÏÇÃØ", 285);
//    dep.registraDep(81, "_¢8²¹²³²7!|øYžÕ#b", 405);
//    dep.registraDep(82, "Patrícia", 405);
//    dep.registraDep(83, "Bárbara", 405);
//    dep.registraDep(84, "[][][][]][", 405);
//    
//
//    ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
//    Validator validator = vf.getValidator();
//    Iterator<Departamento> iterator = Departamento.getDepartamentoLista().iterator();
//    Set<ConstraintViolation<Departamento>> constraintViolations;
//    Departamento obj = new Departamento();
//
//    //Melhorar está lógica abaixo
//    while (iterator.hasNext()) {
//        obj = iterator.next();
//        constraintViolations = validator.validate(obj);
//        for(ConstraintViolation<Departamento> cv : constraintViolations) {
//            System.out.println(String.format("Erro Encontrado! propriedade: [%s], value: [%s], message: [%s]", cv.getPropertyPath(), cv.getInvalidValue(), cv.getMessage()));
//        }
//    }
//    departamento.listarDepartamentos();
//}
}
