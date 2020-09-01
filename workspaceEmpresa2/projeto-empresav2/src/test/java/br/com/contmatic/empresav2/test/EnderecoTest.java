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
import jdk.nashorn.internal.ir.annotations.Ignore;

public class EnderecoTest {

    private static Endereco endereco; // Testar diferenças entre 2 instâncias
    private Endereco end;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        endereco = new Endereco("Rua Francisco de Assis do Nascimento", "Doutor Juvêncio de Andrade", "A245", "62039-245", "Sobral", "CE");
    }

    @Before
    public void setUp() throws Exception {
        end = new Endereco();
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

    @Ignore
    @Test
    public void teste_setRua_e_getRua_nome_correto() {
        fail("Not yet implemented");
    }

    @Test
    public void teste_registra_endereco_com_sucesso() throws ViaCEPException {
        end = Endereco.cadastraEnderecoViaCEP("60874-208", "456");
        assertThat(Endereco.getEnderecoLista().contains(end), equalTo(true));
    }

    @Test //Lógica de Testes Está errada
    public void nao_deve_registra_endereco_ja_existente() throws ViaCEPException {
        end = Endereco.cadastraEndereco("Rua Francisco de Assis", "Doutor Juvêncio de Andrade", "A245", "62039-245", "Sobral", Estado.CE);        
        assertThat(Endereco.getEnderecoLista().contains(end), equalTo(false));        
    }
    
    @Test 
    public void teste_remocao_deObjeto_deForma_diferenciada() {
        //O objeto removido será o do SetUpBeforeClass;
        Endereco.removeEndereco("62039-245", "A245");
        //Teste Improvisado
        assertThat(Endereco.getEnderecoLista().contains(null), equalTo(true));
    }

}
