package br.com.contmatic.empresav2.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.google.common.base.VerifyException;

import br.com.contmatic.empresav2.model.Empresa;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmpresaTest {

    // Variáveis

    private static final String NULLSTR = null;
    private static final String EMPTYSTR = "";
    private static final Long NULLONG = (Long) null;
    private static final Long EMPTYID = (long) 0;
    private static Empresa EmpresaTest;
    private Empresa empresa; // criado para testar diferença de instâncias

    // Configuração do teste

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        EmpresaTest = new Empresa();

        EmpresaTest.registraEmpresa(1, "TestMatic", "57695925000111", "03575090", "1145649304", "05/04/1985");
        EmpresaTest.registraEmpresa(2, "Yahoo!123", "89138206000196", "72150704", "11941063792", "04/12/1986");
        EmpresaTest.registraEmpresa(3, "Cond-volt_Fios_e_Cabos", "60449385000109", "57071401", "1104028922", "22/01/1938");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        Empresa.getEmpresaLista().clear();
        EmpresaTest = null;
    }

    @Before
    public void setUp() throws Exception {
        this.empresa = new Empresa();
    }

    @After
    public void tearDown() throws Exception {
        this.empresa = null;
    }

    /*
     * Está seção de testes tem o intuito de testar os métodos principais da classe
     */

    @Test // Testa criando o obj pelo construtor
    public void teste_objeto_criado_por_construtor() {
        long id = 5;
        empresa = new Empresa(id, "HoHoHo", "89270828000173", "04789050", "junior@Junior.com", "16/07/1941");
        assertThat("O Obj esperado era: ", empresa, equalTo(empresa.solicitaEmpresa(id)));
        assertNotNull("O objeto não deveria estar nulo", empresa.solicitaEmpresa(id));
        System.out.println(empresa.listaEmpresas());
    }

    @Test
    public void teste_objeto_criado_por_metodo_com_parametros() {
        long id = 6;

        assertThat("O Obj esperado era:", empresa.registraEmpresa(id, "HoHoHo", "89270828000173", "04789050", "1125064896", "06/10/1972"), equalTo(empresa.solicitaEmpresa(id)));
        assertNotNull("O objeto não deveria estar nulo", empresa.solicitaEmpresa(id));
    }

    @Test(expected = VerifyException.class)
    public void teste_objeto_criado_ja_existente() {
        long id = 1;
        empresa = new Empresa(id, "HoHoHo", "89270828000173", "04789050", "1125064896", "12/08/1948");

    }

    @Test(expected = NullPointerException.class)
    public void teste_objeto_sendo_criado_nulo() {
        empresa = new Empresa(NULLONG, "HoHoHo", "89270828000173", "04789050", "1125064896", "01/08/1986");       
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void teste_objeto_sendo_criado_com_valores_de_contato_errado() {
        empresa = new Empresa(65, "KakaKu", "89270828000173", "04789050", "tatATaTA!", "02/02/1940");        
        System.out.println(empresa);
    }

    @Test
    public void teste_remocao_objeto_existente() {
        long id = 250;
        assertThat("O Obj esperado era:", new Empresa(id, "HoHoHo", "89270828000173", "04789050", "1125064896", "02/10/1959"), equalTo(empresa.removeEmpresa(id)));

    }

    @Test(expected = VerifyException.class)
    public void teste_remocao_objeto_nao_existente() {
        long id = 179;
        empresa.removeEmpresa(id);
    }

    @Test
    public void teste_busca_departamento_existente() {
        assertNotNull("Esperava receber um objeto", empresa.solicitaEmpresa(1));
        assertNotNull("Esperava receber um objeto", empresa.solicitaEmpresa(2));
        assertNotNull("Esperava receber um objeto", empresa.solicitaEmpresa(3));
    }

    @Test(expected = VerifyException.class)
    public void teste_busca_departamento_nao_existente() {
        long id = 899;
        empresa.solicitaEmpresa(id); // deve falhar
    }

    /*
     * Está seção de testes tem o intuito de testar os getters/setters da classe
     */

    @Test
    public void teste_setId_e_getId_correto() {
        long id = 25;
        empresa.setIdEmpresa(id);
        assertEquals("Os valores deveriam ser iguais", id, empresa.getIdEmpresa());
    }

    @Test(expected = NullPointerException.class)
    public void teste_setId_valor_nulo() {
        empresa.setIdEmpresa(NULLONG);
    }

    @Test(expected = IllegalArgumentException.class)
    public void teste_setId_valor_vazio() {
        empresa.setIdEmpresa(EMPTYID);
        System.out.println();
    }

    @Test
    public void teste_setNome_e_getNome_correto() {
        String nome = "Softmatic";
        empresa.setNome(nome);
        assertThat("Os valores deveriam ser iguais", nome, equalTo(empresa.getNome()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void teste_setNome_e_getNome_incorreto() {
        String nome = "チキンパステル";
        empresa.setNome(nome);
    }

    @Test(expected = NullPointerException.class)
    public void teste_setNome_valor_nulo() {
        empresa.setNome(NULLSTR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void teste_setNome_valor_vazio() {
        empresa.setNome(EMPTYSTR);
    }

    @Test
    public void teste_setCNPJ_e_getCNPJ_correto() {
        String cnpj = "15456145000169";
        empresa.setCnpj(cnpj);
        assertThat("Os valores deveriam ser iguais", cnpj, equalTo(empresa.getCnpj().replaceAll("\\D", "")));
    }

    @Test(expected = NullPointerException.class)
    public void teste_setCNPJ_valor_nulo() {
        empresa.setCnpj(NULLSTR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void teste_setCNPJ_valor_vazio() {
        empresa.setCnpj(EMPTYSTR);
    }
    
    @Test
    public void teste_setCep_e_getCep_correto() {
        String cep = "03575090";
        empresa.setCep(cep);
        assertThat("Os valores deveriam ser iguais", cep, equalTo(empresa.getCep().replaceAll("\\D", "")));
    }

    @Test(expected = NullPointerException.class)
    public void teste_setCep_valor_nulo() {
        empresa.setCep(NULLSTR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void teste_setCep_valor_vazio() {
        empresa.setCep(EMPTYSTR);
    }

    @Test
    public void teste_setTelefone_e_getTelefone_correto() {
        String telefone = "11998420563";
        empresa.setContato(telefone);
        assertThat("Os valores deveriam ser iguais", telefone, equalTo(empresa.getContato().replaceAll("\\D", "")));
    }

    @Test(expected = NullPointerException.class)
    public void teste_setTelefone_valor_nulo() {
        empresa.setContato(NULLSTR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void teste_setTelefone_valor_vazio() {
        empresa.setContato(EMPTYSTR);
    }

    // Teste para garantir que Telefones não sejam escritos com base em letras
    @Test(expected = IllegalArgumentException.class)
    public void teste_criando_telefone_com_letras() {
        empresa.setContato("DDoitodois");
    }
    
    @Test
    public void teste_setdtFundacao_e_getDtFundacao_correto() {
        String data = "16/02/1970";
        empresa.setDtFundacao(data);
        assertThat("Os valores deveriam ser iguais", data, equalTo(empresa.getDtFundacao()));
    }
    
    @Test
    public void teste_setdtFundacao_e_getDtFundacao_sem_barra() {
        String data = "16021970";
        empresa.setDtFundacao(data);
        assertThat("Os valores deveriam ser iguais", data, equalTo(empresa.getDtFundacao()));
    }

    @Test(expected = NullPointerException.class)
    public void teste_setDtFundacao_data_nula() {
        empresa.setDtFundacao(NULLSTR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void teste_setDtFundacao_data_vazia() {
        empresa.setDtFundacao(EMPTYSTR);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void teste_setDtFundacao_data_errada() {
        empresa.setDtFundacao("ab/de/efgh");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void teste_setDtFundacao_data_impossivel() {
        empresa.setDtFundacao("31/02/1900");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void teste_setDtFundacao_data_sem_formatacao() {
        empresa.setDtFundacao("05022011");
    }

    /*
     * Está seção de testes tem o intuito de testar os métodos de listagem
     */

    @Test
    public void teste_toString() {
        assertNotNull("Esperava receber uma lista", empresa.toString());
    }

    @Test
    public void teste_listarEmpresas() {
        assertNotNull("Esperava receber uma lista", empresa.listaEmpresas());
    }

}
