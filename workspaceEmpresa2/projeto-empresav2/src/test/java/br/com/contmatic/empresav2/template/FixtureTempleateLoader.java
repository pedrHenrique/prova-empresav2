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

    // -> Descobri que as coisas não são da forma como eu imaginei que fossem...
    private static final String[] NOMES = {"Claudia Vasconcelos Pinto", "Eduardo Araujo Ribeiro", "Sofia Dantas", "Danton Camargo", "Bárbara Luz", "Jônatas Oséias Amós Tomás"}; 
    private static final String[] CEP = {"76963-731", "63038-270", "57084-818", "96835-712", "65091-223", "64022-240", "68513-603", "77814-190"}; //TODO Existe a possibilidade de um cep repetido ser utilizado, se atentar a isso num futuro próximo
    private static final String[] NOMESDEP = {"Diretória", "Expedição", "Contábil", "Recepção","Segurança", "Qualidade", "Infraestrutura"}; //tem um nome aqui com erro
    private static final String[] NOMESEMP = {"Yahoo!", "AMD", "Clube do Hardware", "Dafiti", "Tifany&CO", "Mercedez-Benz", "Amazon.com, Itaú", "HLC"};    
    private static final String[] DATAS = {"25/07/2029", "15/05/1971", "14/04/1959", "24/12/1968", "14/06/1999", "02/02/1990"};
    private static final String[] CONTATOS = {"28211740711", "10749174420", "2558693247", "4448956789", "testeMatic@cont.com", "PhoenixTest@contmatic.com"}; //Telefone/Celular/Email
    private static final String[] ESTADO = {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "PA", "PB", "PR", "PE", "PE", "PI", "RJ", "SP", "SE", "TO"};
    //private static final EnumSet<Estado> ESTADOS = EnumSet.allOf(Estado.class);
    
    @Override
    public void load() {
        Fixture.of(Departamento.class).addTemplate("valido", new Rule(){
            // Departamentos Válidos
            {
            add("idDepartamento", uniqueRandom(10L, 299L)); //Os primeiros 10 serão exclusivos.
            add("nome", random("Diretória", "Expedição", "Contábil", "Recepção","Segurança", "Qualidade", "Infraestrutura"));
            add("ramal", uniqueRandom(1, 999)); //TODO Ramal deveria ser uniqueRandom, porém o fixture apresenta erro quando iniciamos ele assim.
        }});
        
        Fixture.of(Departamento.class).addTemplate("invalido", new Rule(){ //para testes
            
            //Departamentos com valores inválidos
            {
            add("idDepartamento", uniqueRandom(300L, 500L)); 
            add("nome", random("PRËMØNÏÇÃØ", "_¢8²¹²³²7!|øYžÕ#b", "[][][][]][", "チキンパステル", "ロロノア・ゾロ ","             "));
            add("ramal", uniqueRandom(999, 2000));
        }});
        
        Fixture.of(Empresa.class).addTemplate("valido", new Rule(){ //para testes
            //Exemplo empresa com valores válidos
            {
            add("idEmpresa", random(Long.class, range(10L, 499L))); 
            add("nome", random("Yahoo!", "AMD", "Clube do Hardware", "Dafiti", "Tifany&CO", "Mercedez-Benz", "Amazon.com, Itaú", "HLC"));
            add("cnpj", cnpj());
            add("cep", CEP [new Random().nextInt(CEP.length)]);
            //add("dtFundacao", DATAS[new Random().nextInt(DATAS.length)]); //enquanto getDtFundacao tiver tipo de retorno DateTime, está forma do fixture não vai funcionar
            add("contato", random("28211740711", "10749174420", "2558693247", "4448956789", "testeMatic@cont.com", "PhoenixTest@contmatic.com"));
        }});
        
        
        Fixture.of(Funcionario.class).addTemplate("valido", new Rule(){ //para testes
            //Exemplo Funcionario Com valores válidos
            {
            add("idFuncionario", random(Long.class, range(20L, 3000L))); 
            add("nome", random("Claudia Vasconcelos Pinto", "Eduardo Araujo Ribeiro", "Sofia Dantas", "Danton Camargo", "Bárbara Luz", "Jônatas Oséias Amós Tomás")); //old NOMES[new Random().nextInt(NOMES.length)]
            add("cpf", regex("\\d{11}")); // -> Poderia ser dessa forma também \\d{3}.\\d{3}.\\d{3}-\\d{2}
            //add("cep", CEP[new Random().nextInt(CEP.length)]);
            add("contato", random("28211740711", "10749174420", "2558693247", "4448956789", "testeMatic@cont.com", "PhoenixTest@contmatic.com"));
            add("salario", uniqueRandom(800.00, 2500.00, 1500.00, 4500.00, 6000.00));
            
            
        }});
        /* DESATIVADO ATÉ POSSÍVEL REFATORAÇÃO DE ESTRUTURA.*/   

        Fixture.of(Endereco.class).addTemplate("valido", new Rule(){ //para testes
            //Exemplo Endereco com valores válidos, todos gerador pelo 4devs
            {
            add("rua", random("Avenida Dedo de Deus", "Rua Primeiro de Maio", "Rua H12B", "Travessa Niterói", "Rua Projetada 899", "Rua Nove")); 
            add("bairro", random("Vila União", "Paraíso", "Parque Jardim Santanense", "Maruípe", "Setor Habitacional Samambaia (Vicente Pires)", "Santo Antônio", "Bela Vista"));
            add("num", random("4458", "205", "8", "702B", "4089C", "17", "35A", "35B", "701A", "6158", "54a", "54b", "27", "48B", "205", "777")); //O motivo de ter tantas numerações, é para evitar repetição de números.
            add("cep", CEP[new Random().nextInt(CEP.length)]);
            add("cidade", random("Teresina", "Lages", "Cariacica", "Parnamirim", "Florianópolis", "Nova Iguaçu", "Boa Vista", "Campo Grande"));            
            //add("estado", ESTADO[new Random().nextInt(ESTADO.length)]);
            //TODO descobrir se o fixture consegue utilizar Enums
            
        }});
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
