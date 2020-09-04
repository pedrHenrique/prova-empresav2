package br.com.contmatic.empresav2.template;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.contmatic.empresav2.model.Departamento;
import br.com.contmatic.empresav2.model.Empresa;
import br.com.contmatic.empresav2.model.Endereco;
import br.com.contmatic.empresav2.model.Estado;
import br.com.contmatic.empresav2.model.Funcionario;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class FixtureTempleateLoader implements TemplateLoader {

    private static final String[] NOMES = {"Claudia Vasconcelos Pinto", "Eduardo Araujo Ribeiro", "Sofia Dantas", "Danton Camargo", "Bárbara Luz", "Jônatas, Oséias Amós Tomás"}; 
    private static final String[] CPF = {"78964260058", "74967040080", "79641635077", "39687447052", "13690061016", "47782562040"}; 
    private static final String[] CEP = {"76963-731", "63038-270", "57084-818", "96835-712", "65091-223", "64022-240", "68513-603", "77814-190"}; //ceps gerador a partir do 4devs
    private static final String[] NOMESDEP = {"Diretória", "Expedição", "Contábil", "Recepção","Segurança", "Qualidade", "Infraestrutura"}; //tem um nome aqui com erro
    private static final String[] NOMESEMP = {"Yahoo!", "AMD", "Clube do Hardware", "Dafiti", "Tifany&CO", "Mercedez-Benz", "Amazon.com, Itaú", "HLC"};
    private static final String[] CNPJS = {"71420773000175", "08887547000162", "10000944000112", "34237642000120", "14628526000125", "61589319000199"};
    private static final String[] DATAS = {"25/07/2029", "15/05/1971", "14/04/1959", "24/12/1968", "14/06/1999", "02/02/1990"};
    private static final String[] CONTATOS = {"28211740711", "10749174420", "2558693247", "4448956789", "testeMatic@cont.com", "PhoenixTest@contmatic.com"}; //Telefone/Celular/Email
    //private static final EnumSet<Estado> ESTADOS = EnumSet.allOf(Estado.class);
    
    @Override
    public void load() {
        Fixture.of(Departamento.class).addTemplate("valido", new Rule(){
            // Departamentos Válidos
            {
            add("idDepartamento", random(Long.class, range(10L, 299L))); //Os primeiros 10 serão exclusivos.
            add("nome", NOMESDEP[new Random().nextInt(NOMESDEP.length)]);
            add("ramal", random(Integer.class, range(1, 999)));
        }});
        
        Fixture.of(Departamento.class).addTemplate("invalido", new Rule(){ //para testes
            
            //Departamentos com valores inválidos
            {
            add("idDepartamento", random(Long.class, range(300L, 500L))); 
            add("nome", random("PRËMØNÏÇÃØ", "_¢8²¹²³²7!|øYžÕ#b", "[][][][]][", "チキンパステル", "             "));
            add("ramal", random(Integer.class, range(999L, 2000L)));
        }});
        
        Fixture.of(Empresa.class).addTemplate("valido", new Rule(){ //para testes
            //Exemplo empresa com valores válidos
            {
            add("idEmpresa", random(Long.class, range(10L, 499L))); 
            add("nome", NOMESEMP[new Random().nextInt(NOMESEMP.length)]);
            add("cnpj", CNPJS[new Random().nextInt(CNPJS.length)]);
            add("cep", CEP [new Random().nextInt(CEP.length)]);
            //add("dtFundacao", DATAS[new Random().nextInt(DATAS.length)]); //enquanto getDtFundacao tiver tipo de retorno DateTime, está forma do fixture não vai funcionar
            add("contato", CONTATOS[new Random().nextInt(CONTATOS.length)]);
        }});
        
        /* DESATIVADO ATÉ POSSÍVEL REFATORAÇÃO DE ESTRUTURA.*/
//        Fixture.of(Funcionario.class).addTemplate("valido", new Rule(){ //para testes
//            //Exemplo Funcionario Com valores válidos
//            {
//            add("idFuncionario", random(Long.class, range(10L, 3000L))); 
//            add("nome", NOMES[new Random().nextInt(NOMES.length)]);
//            add("cpf", uniqueRandom(CPF[new Random().nextInt(CPF.length)]));
//            //add("cep", CEP[new Random().nextInt(CEP.length)]);
//            add("contato", CONTATOS[new Random().nextInt(CONTATOS.length)]);
//            add("salario", random(Double.class, range(800.00, 10000.00)));
//            
//        }});
//   
//
//        Fixture.of(Endereco.class).addTemplate("valido", new Rule(){ //para testes
//            //Exemplo Endereco com valores válidos, todos gerador pelo 4devs
//            {
//            add("rua", random("Avenida Dedo de Deus", "Rua Primeiro de Maio", "Rua H12B", "Travessa Niterói", "Rua Projetada 899", "Rua Nove")); 
//            add("bairro", random("Vila União", "Paraíso", "Parque Jardim Santanense", "Maruípe", "Setor Habitacional Samambaia (Vicente Pires)", "Santo Antônio", "Bela Vista"));
//            add("num", random("4458", "205", "8", "702B", "4089C", "17", "35A", "35B", "701A", "6158", "54a", "54b", "27", "48B", "205", "777")); //O motivo de ter tantas numerações, é para evitar repetição de números.
//            add("cep", CEP[new Random().nextInt(CEP.length)]);
//            add("cidade", random("Teresina", "Lages", "Cariacica", "Parnamirim", "Florianópolis", "Nova Iguaçu", "Boa Vista", "Campo Grande"));            
//            //add("estado", has(2).of(Estado.class, "aleatorio"));
            
//        }});
    }
 

    //Método (provisório) criado para retorno literal da String. Ao Inves de um DateTime 
    public static String getData() {
        return DATAS[new Random().nextInt(DATAS.length)];
    }
    
//    private static List<String> ufStringTest() {
//        
//        List<String> enumNames = Stream.of(Estado.values())
//                .map(Enum::name)
//                .collect(Collectors.toList());
//        String[] enumStr = {};
//        System.out.println(enumNames.toString()); 
//        
//        return enumNames;
//        
//    }
    
//    public static void main (String[] args) {
//        ufStringTest();
//    }
    

}
