package br.com.contmatic.empresav2.test;

import static org.hamcrest.CoreMatchers.equalTo;
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
     * Seção de testes dos getters/setters da classe
     */

    
    @Test
    public void teste_setRua_e_getRua_nome_correto() {        
        endereco.setRua(end.getRua());
        assertThat(endereco.getRua(), equalTo(end.getRua()));
    }

    
    @Test
    public void teste_registra_endereco_com_sucesso() {
        end = Endereco.cadastraEnderecoViaCEP("60874-208", "456");
        assertThat(Endereco.getEnderecoLista().contains(end), equalTo(true));
    }
    
    @Test 
    public void teste_remocao_deObjeto_deForma_diferenciada() {
        Endereco.removeEndereco(end.solicitaEndereco("62039-245", "A245"));
        //assertThat(Endereco.getEnderecoLista().contains(end.solicitaEndereco("62039-245", "A245")), equalTo(false));
        assertNull(end.solicitaEndereco("62039-245", "A245"));
    }
    
    @Test 
    public void teste_remocao_deObjeto_dois() {
        end = Endereco.cadastraEnderecoViaCEP("72444-134", "A420");
        Endereco.removeEndereco("72444-134", "A420");
        //assertThat(Endereco.getEnderecoLista().contains(end.solicitaEndereco("62039-245", "A245")), equalTo(false));
        assertNull(end.solicitaEndereco("72444-134", "A420"));
    }
    
    @Test 
    public void deve_buscar_Endereco_eRetornar_ele() {
        end = Endereco.cadastraEnderecoViaCEP("28245-970", "558");
        assertNotNull(endereco.solicitaEndereco("28245-970", "558"));
    }

}
