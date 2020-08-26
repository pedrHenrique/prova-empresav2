package br.com.contmatic.empresav2.template;

import java.util.Random;

import br.com.contmatic.empresav2.model.Departamento;
import br.com.contmatic.empresav2.model.Empresa;
import br.com.contmatic.empresav2.model.Funcionario;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class FixtureTempleateLoader implements TemplateLoader {

    private static final String[] NOMESDEP = {"Diretória", "Expedição", "Contábil", "Recepção","Segurança", "Qualidade", "Infraestrutura"}; //tem um nome aqui com erro
    private static final String[] NOMESEMP = {"Yahoo!", "AMD", "Clube do Hardware", "Dafiti", "Tifany&CO", "Mercedez-Benz", "Amazon.com, Itaú", "HLC"};
    private static final String[] CNPJS = {"71420773000175", "08887547000162", "10000944000112", "34237642000120", "14628526000125", "61589319000199"};
    private static final String[] DATAS = {"25/07/2029", "15/05/1971", "14/04/1959", "24/12/1968", "14/06/1999", "02/02/1990"};
    private static final String[] CONTATOS = {"28211740711", "10749174420", "2558693247", "4448956789", "testeMatic@cont.com", "PhoenixTest@contmatic.com"}; //Telefone/Celular/Email
    
    @Override
    public void load() {
        Fixture.of(Departamento.class).addTemplate("valido", new Rule(){
            // Departamentos Válidos
            {
            add("idDepartamento", random(Long.class, range(10L, 299L))); //Os primeiros 10 serão exclusivos.
            add("nome", NOMESDEP[new Random().nextInt(NOMESDEP.length)]);//new Random().nextInt(NOMES.length) Não Funcionou
            add("ramal", random(Integer.class, range(1, 999)));
        }});
        
        Fixture.of(Departamento.class).addTemplate("invalido", new Rule(){ //para testes
            
            //Departamentos Com valores inválidos
            {
            add("idDepartamento", random(Long.class, range(170L, 200L))); 
            add("nome", random("PRËMØNÏÇÃØ", "_¢8²¹²³²7!|øYžÕ#b", "[][][][]][", "チキンパステル"));
            add("ramal", random(Integer.class, range(1, 999)));
        }});
        
        Fixture.of(Empresa.class).addTemplate("valido", new Rule(){ //para testes
            //Exemplo empresa Com valores válidos
            {
            add("idEmpresa", random(Long.class, range(10L, 499L))); 
            add("nome", NOMESEMP[new Random().nextInt(NOMESEMP.length)]);
            add("cnpj", CNPJS[new Random().nextInt(CNPJS.length)]);
            //add("cep", random("77021688", "79043063", "32145340", "32145340", "68557816", "91250640", "49085030")); Primeiro Transformar Cep em ENUM, depois adicionar o fixture
            //add("dtFundacao", DATAS[new Random().nextInt(DATAS.length)]); //enquanto getDtFundacao tiver tipo de retorno DateTime, está forma do fixture não vai funcionar
            add("contato", CONTATOS[new Random().nextInt(CONTATOS.length)]);
        }});
        
        Fixture.of(Funcionario.class).addTemplate("valido", new Rule(){ //para testes
            //Exemplo Funcionario Com valores válidos
            {
            add("idFuncionario", random(Long.class, range(10L, 3000L))); 
            add("nome", random("Claudia Vasconcelos Pinto", "Eduardo Araujo Ribeiro", "Sofia Dantas", "Danton Camargo", "Bárbara Luz", "Jônatas, Oséias Amós Tomás"));
            add("cpf", random("78964260058", "74967040080", "79641635077", "39687447052", "13690061016", "47782562040"));
            //add("cep", random("06853040", "73813762", "76807700", "66840370", "49009079", "60331295", "78058673")); Primeiro Transformar Cep em ENUM, depois adicionar o fixture
            add("contato", random("28211740711", "10749174420", "2558693247", "4448956789", "junior@Junior.com", "ridouane8626@uorak.com"));
            add("salario", random(Double.class, range(800.00, 10000.00)));
            
        }});
    }

    //Método (provisório) criado para retorno literal da String. Ao Inves de um DateTime 
    public static String getData() {
        return DATAS[new Random().nextInt(DATAS.length)];
    }
    

}
