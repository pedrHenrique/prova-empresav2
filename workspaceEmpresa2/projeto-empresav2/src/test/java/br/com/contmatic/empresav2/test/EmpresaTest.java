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

import br.com.contmatic.empresav2.model.DataJoda;
import br.com.contmatic.empresav2.model.Empresa;
import br.com.contmatic.empresav2.template.FixtureTempleateLoader;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmpresaTest {

    // Variáveis

    private static final String NULLSTR = null;
    private static final String EMPTYSTR = "";
    private static final Long NULLONG = null;
    private static final Long EMPTYLONG = (long) 0;
    private static Empresa empresa;
    private Empresa emp; // criado para testar diferença de instâncias
    private static String fixtureData;

    // Configuração do teste

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        FixtureFactoryLoader.loadTemplates("br.com.contmatic.empresav2.template");
        fixtureData = FixtureTempleateLoader.getData();
        empresa = new Empresa(1, "TestMatic", "57695925000111", "03575090", "1145649304", "05/04/1985");
        empresa = new Empresa(2, "TiãoIndustries", "89138206000196", "72150704", "11941063792", "04/12/1986");
        empresa = new Empresa(3, "Cond-volt_Fios_e_Cabos", "60449385000109", "57071401", "Sony@Sony.com", "22/01/1938");
    }

    @Before
    public void setUp() throws Exception {
        emp = new Empresa();
        emp = Fixture.from(Empresa.class).gimme("valido");
    }

    @After
    public void tearDown() throws Exception {
        emp = null;
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        Empresa.getEmpresaLista().clear();
        empresa = null;
    }

    /*
     * Seção de testes dos métodos de criação dos objetos da classe
     */

    @Test // Testa criando o obj pelo construtor
    public void deve_criar_empresa_valida_atraves_doConstrutor_utilizando_Fixture() {
        emp = new Empresa(emp.getIdEmpresa(), emp.getNome(), emp.getCnpj(), emp.getCep(), emp.getContato(), fixtureData);
        assertThat("A empresa devia ter sido criada e armazenada: ", Empresa.getEmpresaLista().contains(emp), equalTo(true));
        assertNotNull("O objeto não deveria estar nulo", emp);
        System.out.println(emp.listaEmpresas());
    }

    @Test
    public void deve_criar_empresa_valida_atraves_deMetodo_utilizando_Fixture() {
        emp.registraEmpresa(emp.getIdEmpresa(), emp.getNome(), emp.getCnpj(), emp.getCep(), emp.getContato(), fixtureData);
        assertThat("A empresa devia ter sido criada e armazenada: ", Empresa.getEmpresaLista().contains(emp), equalTo(true));
        assertNotNull("O objeto não deveria estar nulo", emp);
        System.out.println(emp.toString());
    }

    @Test(expected = VerifyException.class)
    public void nao_deve_registrar_empresa_com_ID_ja_utilizado() {
        emp = new Empresa(1, "DevoFalhar", "89270828000173", "04789050", "1125064896", "12/08/1948");

    }

    @Test(expected = NullPointerException.class)
    public void nao_deve_criar_empresa_nula() {
        emp = new Empresa(NULLONG, NULLSTR, NULLSTR, NULLSTR, NULLSTR, NULLSTR);
    }

    
    /*
     * Seção de testes dos métodos de remoção de objetos da Collection.
     */
    
    @Test
    public void deve_remover_objeto_ja_existente_daCollection_com_sucesso() {
        //Registra para depois remover
        emp.registraEmpresa(emp.getIdEmpresa(), emp.getNome(), emp.getCnpj(), emp.getCep(), emp.getContato(), fixtureData);
        empresa.removeEmpresa(emp.getIdEmpresa());
        assertThat("A empresa não devia estar registrada", Empresa.getEmpresaLista().contains(emp), equalTo(false));

    }

    @Test(expected = VerifyException.class)
    public void nao_deve_remover_empresa_nao_existente() {
        emp.removeEmpresa(1035);
    }
    
    /*
     * Seção de testes dos métodos de busca de objetos da Collection
     */

    @Test
    public void deve_retornar_empresa_ja_cadastrada() {
        assertNotNull("Esperava receber um objeto", emp.solicitaEmpresa(1));
        assertNotNull("Esperava receber um objeto", emp.solicitaEmpresa(2));
        assertNotNull("Esperava receber um objeto", emp.solicitaEmpresa(3));
    }

    @Test(expected = VerifyException.class)
    public void nao_deve_retornar_empresa_nao_existente() {
        emp.solicitaEmpresa(1035); // deve falhar
    }

    /*
     * Seção de testes dos getters/setters da classe
     */

    @Test
    public void teste_setId_e_getId_correto() {
        empresa.setIdEmpresa(emp.getIdEmpresa());
        assertThat("Os valores deveriam ser iguais: ", empresa.getIdEmpresa(), equalTo(emp.getIdEmpresa()));
    }

    @Test(expected = NullPointerException.class)
    public void setId_nao_deve_aceitar_nulos() {
        emp.setIdEmpresa(NULLONG);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setId_nao_deve_aceitar_vazios() {
        emp.setIdEmpresa(EMPTYLONG);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void setId_nao_deve_aceitar_valores_incorretos() {
        emp.setIdEmpresa(-6);
    }

    @Test
    public void teste_setNome_e_getNome_correto() {
        empresa.setNome(emp.getNome());
        assertThat("Os valores deveriam ser iguais", empresa.getNome(), equalTo(emp.getNome()));
    }

    @Test(expected = NullPointerException.class)
    public void setNome_nao_deve_aceitar_valor_nulo() {
        emp.setNome(NULLSTR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNome_nao_deve_aceitar_valor_vazio() {
        emp.setNome(EMPTYSTR);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void setNome_nao_deve_aceitar_caracteres_naoValidos() {
        String nome = "チキンパステル";
        emp.setNome(nome);
    }

    @Test
    public void teste_setCNPJ_e_getCNPJ_correto() {
        empresa.setCnpj(emp.getCnpj()); //Set adiciona uma formatação a variável. Replace remove essa formatação para a real comparação
        assertThat("Os valores deveriam ser iguais", empresa.getCnpj().replaceAll("\\D", ""), equalTo(emp.getCnpj()));
    }

    @Test(expected = NullPointerException.class)
    public void setCnpj_nao_deve_aceitar_valor_nulo() {
        emp.setCnpj(NULLSTR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setCnpj_nao_deve_aceitar_valor_vazio() {
        emp.setCnpj(EMPTYSTR);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void setCnpj_nao_deve_aceitar_valor_naoValido() {
        String CNPJ = "ABCDEFGHIJOLMN";
        emp.setCnpj(CNPJ);
    }

//    @Test
//    public void teste_setCep_e_getCep_correto() {
//        empresa.setCep(emp.getCep()); //Set adiciona uma formatação a variável. Replace remove essa formatação para a real comparação
//        assertThat("Os valores deveriam ser iguais", empresa.getCep().replaceAll("\\D", ""), equalTo(emp.getCep()));
//    }
//
//    @Test(expected = NullPointerException.class)
//    public void setCep_nao_deve_aceitar_valor_nulo() {
//        emp.setCep(NULLSTR);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void setCep_nao_deve_aceitar_valor_vazio() {
//        emp.setCep(EMPTYSTR);
//    }
    
//  @Test
//  public void setCep_nao_deve_aceitar_valor_naoValido() {
//      empresa.setCep(emp.getCep()); //Set adiciona uma formatação a variável. Replace remove essa formatação para a real comparação
//      assertThat("Os valores deveriam ser iguais", empresa.getCep(), equalTo(emp.getCep().replaceAll("\\D", "")));
//  }

    @Test
    public void teste_setTelefone_e_getTelefone_correto() {
        empresa.setContato(emp.getContato()); //Set adiciona uma formatação a variável. Replace remove essa formatação para a real comparação
        assertThat("Os valores deveriam ser iguais", empresa.getContato().replaceAll("\\D", ""), equalTo(emp.getContato()));
    }

    @Test(expected = NullPointerException.class)
    public void setTelefone_nao_deve_aceitar_valores_nulos() {
        emp.setContato(NULLSTR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setTelefone_nao_deve_aceitar_valores_vazios() {
        emp.setContato(EMPTYSTR);
    }

    // Teste para garantir que Telefones não sejam escritos com base em letras
    @Test(expected = IllegalArgumentException.class)
    public void setTelefone_nao_deve_aceitar_valores_nao_permitidos() {
        emp.setContato("DDoitodois");
    }

    @Test
    public void teste_setdtFundacao_e_getDtFundacao_correto() { //refatorar posivelmente no futuro
        String data = fixtureData;
        emp.setDtFundacao(data);
        assertThat("Os valores deveriam ser iguais", DataJoda.cadastraData(data), equalTo(emp.getDtFundacao()));
    }

    @Test(expected = NullPointerException.class)
    public void setDtFundacao_nao_deve_aceitar_valores_nulos() {
        emp.setDtFundacao(NULLSTR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setDtFundacao_nao_deve_aceitar_valores_vazios() {
        emp.setDtFundacao(EMPTYSTR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setDtFundacao_nao_deve_aceitar_valores_incorretos() {
        emp.setDtFundacao("ab/de/efgh");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setDtFundacao_nao_deve_aceitar_datas_impossiveis() {
        emp.setDtFundacao("31/02/1900");
    }

    /*
     * Está seção de testes tem o intuito de testar os métodos de listagem
     */

    @Test
    public void teste_toString() {
        assertNotNull("Esperava receber uma lista", emp.toString());
    }

    @Test
    public void teste_listarEmpresas() {
        assertNotNull("Esperava receber uma lista", emp.listaEmpresas());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void empresa_nao_deve_ser_criada_com_telefone_incorreto() {
        emp = new Empresa(65, "KakaKu", "89270828000173", "04789050", "tatATaTA!", "02/02/1940");
    }
    
    @Test(expected = IllegalArgumentException.class) //Futuramente irá funcionar
    public void teste_setdtFundacao_e_getDtFundacao_sem_formatacao() {
        String data = "16021970";
        emp.setDtFundacao(data);
        assertThat("Os valores deveriam ser iguais", data, equalTo(emp.getDtFundacao()));
    }

}
