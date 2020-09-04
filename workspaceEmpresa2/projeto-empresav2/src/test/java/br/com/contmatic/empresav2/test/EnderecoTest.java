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
        //endereco = new Endereco("Rua Francisco de Assis do Nascimento", "Doutor Juvêncio de Andrade", "A245", "62039-245", "Sobral", "CE");
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

    
    @Ignore
    @Test
    public void teste_registra_endereco_com_sucesso() throws ViaCEPException {
        end = Endereco.cadastraEnderecoViaCEP("60874-208", "456");
        assertThat(Endereco.getEnderecoLista().contains(end), equalTo(true));
    }

    @Ignore
    @Test //Lógica de Testes Está errada
    public void nao_deve_registra_endereco_ja_existente() throws ViaCEPException {
        end = Endereco.cadastraEndereco("Rua Francisco de Assis", "Doutor Juvêncio de Andrade", "A245", "62039-245", "Sobral", Estado.CE);        
        assertThat(Endereco.getEnderecoLista().contains(end), equalTo(false));        
    }
    
    @Ignore
    @Test 
    public void teste_remocao_deObjeto_deForma_diferenciada() {
        //O objeto removido será o do SetUpBeforeClass;
        Endereco.removeEndereco("62039-245", "A245");
        //Teste Improvisado
        assertThat(Endereco.getEnderecoLista().contains(null), equalTo(true));
    }

}
