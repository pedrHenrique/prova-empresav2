package br.com.contmatic.empresav2.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.contmatic.empresav2.model.Endereco;
import br.com.contmatic.empresav2.model.Estado;
import br.com.parg.viacep.ViaCEPException;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import jdk.nashorn.internal.ir.annotations.Ignore;

public class EnderecoTest {

    private static Endereco endereco; // Testar diferenças entre 2 instâncias
    private Endereco end;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        FixtureFactoryLoader.loadTemplates("br.com.contmatic.empresav2.template");
        endereco = new Endereco("Rua Francisco de Assis do Nascimento", "Doutor Juvêncio de Andrade", "A245", "62039-245", "Sobral", "CE");
    }

    @Before
    public void setUp() throws Exception {
        end = new Endereco();
        end = Fixture.from(Endereco.class).gimme("valido");
    }

    @After
    public void tearDown() throws Exception {
        end = null;
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        Endereco.getEnderecoLista().clear();
        endereco = null;
    }

    /*
     * Seção de testes dos métodos de criação dos objetos da classe
     */
    
    @Test
    public void teste_deveRegistrar_endereco_comSucesso() {
        end = Endereco.cadastraEnderecoViaCEP("60874-208", "456");
        assertThat(Endereco.getEnderecoLista().contains(end), equalTo(true));
    }
    
    @Test
    public void teste_deveRegistrar_endereco_comSucesso_metodo_alternativo() {
        end = Endereco.cadastraEndereco("Rua São Tomaz", "Angelim", "801", "64040-113", "Teresina", Estado.PI);
        assertThat(Endereco.getEnderecoLista().contains(end), equalTo(true));
    }
    
    @Test
    @org.junit.Ignore //Ignorado até que o validation factory seja compreendido e implementado nesta classe.
    public void nao_deve_registrar_endereco_naoExistente_peloVIACEP() {
        end = Endereco.cadastraEnderecoViaCEP("47890-078", "1");
    }
    
    /*
     * Seção de testes dos métodos de remoção de objetos da Collection.
     */
    
    @Test 
    public void teste_deveRemover_objeto_passando_CEP_NUM_doObjeto() {
        end.removeEndereco("62039245", "A245");
        assertThat(Endereco.getEnderecoLista().contains(Endereco.solicitaEndereco("62039245", "A245")), equalTo(false));
        assertNull(Endereco.solicitaEndereco("62039245", "A245"));
    }
    
    @Test 
    public void teste_deveRemover_objeto_passando_oProprio_objeto() {
        end = Endereco.cadastraEnderecoViaCEP("72444-134", "420J");
        Endereco.removeEndereco(end);
        assertThat(Endereco.getEnderecoLista().contains(end), equalTo(false));
        assertNull(Endereco.solicitaEndereco("72444-134", "420J"));
    }
    
    /*
     * Seção de testes dos métodos de busca de objetos da Collection
     */
    
    @Test 
    public void deve_buscar_endereco_cadastrado_eRetornar_ele() {        
        end = Endereco.cadastraEndereco("Cleber", "Jardim Juvêncio", "207", "37701-047", "Sobral", Estado.AP);
        //assertNotNull(endereco.solicitaEndereco("37701-047", "207")); Ficou mais simples, porém não há feedback do poq o teste falhou.
        assertThat("O Endereço retornado não deveria ter sido nulo", Endereco.solicitaEndereco("37701-047", "207"), is(not(equalTo(null)))); //teste assim ficou mais robusto, porém com menor
        assertThat("Os Endereços comparados deveriam ser iguais", Endereco.solicitaEndereco("37701047", "207").equals(end), equalTo(true)); // legibilidade
    }
    
    /*
     * Seção de testes dos getters/setters da classe
     */
    
    @Test
    public void teste_setRua_e_getRua_nome_correto() {        
        endereco.setRua(end.getRua());
        assertThat(endereco.getRua(), equalTo(end.getRua()));
    }

}
