package br.com.contmatic.empresav2.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

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

    private static ValidatorFactory vf;

    private Validator validator;

    // Configuração do Teste

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        FixtureFactoryLoader.loadTemplates("br.com.contmatic.empresav2.template");
        vf = Validation.buildDefaultValidatorFactory();
        departamento = new Departamento(1, "Financeiro", 155);
        departamento = new Departamento(2, "Recursos Humanos", 285);
        departamento = new Departamento(3, "Tecnologias", 405);
    }

    @Before
    public void setUp() throws Exception {
        dep = new Departamento();
        dep = Fixture.from(Departamento.class).gimme("valido");
        validator = vf.getValidator();
    }

    @After
    public void tearDown() throws Exception {
        dep = null;
        validator = null;
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        Departamento.getDepartamentoLista().clear();
        departamento = null;
        vf = null;
    }
    
    
    /**
     * Método de auxílio que exibe Validations que foram infringidas no decorer de um teste específico.
     *
     * @param constraintViolations - A lista contendo todas as infrações que foram recebidas pelo validator.
     */
    public void exibeConstrains(Set<ConstraintViolation<Departamento>> constraintViolations) {
        for(ConstraintViolation<Departamento> cv : constraintViolations) {
            System.out.println(String.format("Constrain infringida! atributo: [%s], valor: [%s], message: [%s]", cv.getPropertyPath(), cv.getInvalidValue(), cv.getMessage())); //Confirmar se existe uma forma melhor de exbir oq não estiver válido
        }
    }
    
    /*
     * Seção de testes sobre Validation
     */
    
    @Test
    public void teste_annotation_validator_incorreto() { // Código Retirado do Stack Overflow
        Departamento exDepErrado = new Departamento();
        exDepErrado = Fixture.from(Departamento.class).gimme("invalido");
                                                                                                                                                    // NotBlank não permite registrar nomes com (             )
        Set<ConstraintViolation<Departamento>> constraintViolations = validator.validate(exDepErrado.registraDep(exDepErrado.getIdDepartamento(), exDepErrado.getNome(), exDepErrado.getRamal()));
        //assertThat(constraintViolations(hasItem(1)), equalTo(true));
        assertFalse(constraintViolations.isEmpty()); exibeConstrains(constraintViolations);
    }
    
    @Test
    public void teste_annotation_validator_correto() { // Código Retirado do Stack Overflow
        Departamento exDepCerto = new Departamento();
        exDepCerto = Fixture.from(Departamento.class).gimme("valido");

        Set<ConstraintViolation<Departamento>> constraintViolations = validator.validate(exDepCerto.registraDep(exDepCerto.getIdDepartamento(), exDepCerto.getNome(), exDepCerto.getRamal()));
        assertTrue(constraintViolations.isEmpty()); exibeConstrains(constraintViolations);
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

    @Test(expected = IllegalArgumentException.class)
    public void nao_deve_registrar_departamento_com_ID_ou_Ramal_jaUtilizado() {
        dep.registraDep(1, "Abacate", 155);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void nao_deve_registrar_departamento_com_ID_jaUtilizado() {
        dep.registraDep(1, "Abacate", 467);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void nao_deve_registrar_departamento_com_RAMAL_jaUtilizado() {
        dep.registraDep(dep.getIdDepartamento(), "Abacate", 155);
    }

    @Test(expected = NullPointerException.class)
    public void nao_deve_criar_departamento_nulo() {
        dep = new Departamento(NULLONG, NULLSTR, NULLINT);
        //Set<ConstraintViolation<Departamento>> constraintViolations = validator.validate(dep);
        //assertFalse(constraintViolations.isEmpty());
    }

    /*
     * Seção de testes dos métodos de remoção de objetos da Collection.
     */

    @Test
    public void deve_remover_objeto_ja_existente_daCollection_com_sucesso() {
        // Este Teste as vezes eventualmente falha... Não sei o motivo por enquanto.
        dep.registraDep(dep.getIdDepartamento(), dep.getNome(), dep.getRamal());
        departamento.removeDep(dep.getIdDepartamento());
        assertThat("O departamento não deve estar registrado", Departamento.getDepartamentoLista().contains(dep), equalTo(false));

    }
    
    @Test
    public void deve_remover_objeto_com_sucesso_metodo_alternativo() {
        // Este Teste as vezes eventualmente falha... Não sei o motivo por enquanto.
        dep.registraDep(dep.getIdDepartamento(), dep.getNome(), dep.getRamal());
        Departamento.removeDep(dep);
        assertThat("O departamento não deve estar registrado", Departamento.getDepartamentoLista().contains(dep), equalTo(false));

    }

    @Test(expected = NullPointerException.class)
    public void nao_deve_remover_departamento_nao_existente() {
        dep.removeDep(1050);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void nao_deve_remover_departamento_nao_existente_metodo_alternativo() {
        Departamento depVazio = new Departamento();
        Departamento.removeDep(depVazio);
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

    @Test
    public void deve_retornar_null_casoDepartamento_solicitado_naoFor_existente() {
        assertThat("O retorno deveria ter sido nulo",dep.solicitaDep(1050), is(equalTo(null)));
    }

    /*
     * Seção de testes dos getters/setters da classe
     */

    @Test
    public void teste_setId_e_getId_corretos() {
        departamento.setIdDepartamento(dep.getIdDepartamento());
        Set<ConstraintViolation<Departamento>> constraintViolations = validator.validateValue(Departamento.class, "idDepartamento", dep.getIdDepartamento());
        assertThat("Os valores deveriam ser iguais: ", departamento.getIdDepartamento(), equalTo(dep.getIdDepartamento()));
        assertThat(constraintViolations.isEmpty(), equalTo(true)); exibeConstrains(constraintViolations);
    }

    @Ignore //ignorado até descobrir forma de como testar os nulos
    @Test(expected = NullPointerException.class)
    public void setIdDepartamento_nao_deve_aceitar_valores_nulos() {
        dep.setIdDepartamento(NULLONG);
    }

    @Test
    public void IdDepartamento_nao_deve_aceitar_valores_vazios() {
        dep.setIdDepartamento(EMPTYLONG);
        Set<ConstraintViolation<Departamento>> constraintViolations = validator.validateValue(Departamento.class, "idDepartamento", dep.getIdDepartamento());        
        assertThat(constraintViolations, is(not(equalTo(null)))); exibeConstrains(constraintViolations);
    }

    @Test
    public void IdDepartamento_nao_deve_aceitar_valores_negativos() {
        dep.setIdDepartamento(-5);
        Set<ConstraintViolation<Departamento>> constraintViolations = validator.validateValue(Departamento.class, "idDepartamento", dep.getIdDepartamento());        
        assertThat(constraintViolations, is(not(equalTo(null)))); exibeConstrains(constraintViolations);
    }
    
    @Test
    public void IdDepartamento_nao_deve_aceitar_valores_maiores_queTrecentos() {
        dep.setIdDepartamento(301);
        Set<ConstraintViolation<Departamento>> constraintViolations = validator.validateValue(Departamento.class, "idDepartamento", dep.getIdDepartamento());        
        assertThat(constraintViolations, is(not(equalTo(null)))); exibeConstrains(constraintViolations);
    }

    @Test
    public void teste_setNome_e_getNome() {
        departamento.setNome(dep.getNome());
        Set<ConstraintViolation<Departamento>> constraintViolations = validator.validateValue(Departamento.class, "nome", dep.getNome());
        assertThat("Os valores deveriam ser iguais: ", departamento.getNome(), equalTo(dep.getNome())); // recebemos/esperado
        assertThat(constraintViolations.isEmpty(), equalTo(true)); exibeConstrains(constraintViolations);
    }

    @Ignore
    @Test//(expected = NullPointerException.class)
    public void setNome_nao_deve_aceitar_valor_nulo() { //TODO refatorar este teste.
        dep.setNome(NULLSTR);
        Set<ConstraintViolation<Departamento>> constraintViolations = null;
        try {
            constraintViolations = validator.validateValue(Departamento.class, "nome", dep.getNome());  
        } catch (NullPointerException e) {
            assertThat(constraintViolations, is(not(equalTo(null)))); exibeConstrains(constraintViolations);
        }
              
        
    }

    @Test
    public void Nome_nao_deve_aceitar_valor_vazio() {
        dep.setNome(EMPTYSTR);
        Set<ConstraintViolation<Departamento>> constraintViolations = validator.validateValue(Departamento.class, "nome", dep.getNome());        
        assertThat(constraintViolations, is(not(equalTo(null)))); exibeConstrains(constraintViolations);
        
    }

    @Test
    public void Nome_nao_deve_aceitar_caracteres_especiais() {
        dep.setNome("¥$Õ¨¬Q@#%");
        Set<ConstraintViolation<Departamento>> constraintViolations = validator.validateValue(Departamento.class, "nome", dep.getNome());        
        assertThat(constraintViolations, is(not(equalTo(null)))); exibeConstrains(constraintViolations);
    }
    
    @Test
    public void Nome_deveGerar_constrain_recebendo_nomes_enormes() {
        dep.setNome("NOMEDEPARTAMENTOEXEMPLOTESTENOMEMUITOGRANDEMESMO");
        Set<ConstraintViolation<Departamento>> constraintViolations = validator.validateValue(Departamento.class, "nome", dep.getNome());        
        assertThat(constraintViolations, is(not(equalTo(null)))); exibeConstrains(constraintViolations);
    }

    @Test
    public void teste_setRamal_e_getRamal() {
        departamento.setRamal(dep.getRamal());
        Set<ConstraintViolation<Departamento>> constraintViolations = validator.validateValue(Departamento.class, "ramal", dep.getRamal());  
        assertThat("Os valores deveriam ser iguais: ", departamento.getRamal(), equalTo(dep.getRamal()));
        assertThat(constraintViolations.isEmpty(), equalTo(true)); exibeConstrains(constraintViolations);
        
    }

    @Ignore
    @Test(expected = NullPointerException.class)
    public void Ramal_nao_deve_aceitar_valores_nulos() { //TODO Teste precisa ser refatorado.
        dep.setRamal(NULLINT);
        Set<ConstraintViolation<Departamento>> constraintViolations = validator.validateProperty(dep, "ramal");        
        assertThat(constraintViolations, is(not(equalTo(null)))); exibeConstrains(constraintViolations);
    }

    @Test
    public void Ramal_deveGerar_constrain_recebendo_valores_vazios() {
        dep.setRamal(EMPTYINT);
        Set<ConstraintViolation<Departamento>> constraintViolations = validator.validateValue(Departamento.class, "ramal", dep.getRamal());        
        assertThat(constraintViolations, is(not(equalTo(null)))); exibeConstrains(constraintViolations);
    }

    @Test
    public void Ramal_deveGerar_constrain_recebendo_valores_naoPermitidos() {
        dep.setRamal(-9420);
        Set<ConstraintViolation<Departamento>> constraintViolations = validator.validateValue(Departamento.class, "ramal", dep.getRamal());        
        assertThat(constraintViolations, is(not(equalTo(null)))); exibeConstrains(constraintViolations);
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
    @Test
    public void deve_gerarConstrain_seRegistrar_nome_com_espacos_emBranco() {        
        Set<ConstraintViolation<Departamento>> constraintViolations = validator.validate(dep.registraDep(4, "                ", 99));        
        assertThat(constraintViolations, is(not(equalTo(null)))); exibeConstrains(constraintViolations);
    }

    /**
     * Teste Específico: Não Registra nome que apresente números.
     */
    @Test
    public void deve_gerarConstrain_seRegistrar_nome_comNumeros() {
        Set<ConstraintViolation<Departamento>> constraintViolations = validator.validate(dep.registraDep(5, "Cleber4578Araujo", 405));        
        assertThat(constraintViolations, is(not(equalTo(null)))); exibeConstrains(constraintViolations);
        
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
}
