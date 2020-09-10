package br.com.contmatic.empresav2.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;

import com.google.common.base.VerifyException;
import com.sun.istack.internal.NotNull;

import br.com.parg.viacep.ViaCEP;
import br.com.parg.viacep.ViaCEPException;
import org.apache.commons.lang3.NotImplementedException;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Verify.verify;

// Classe Endereco Será futuramente trabalhada de melhor forma...
public class Endereco { // Não consegui fazer com que Endereco seja uma ENUM

    // Variáveis

    @NotBlank(message = "Sua rua não tem nome???")
    @Length(min = 1, max = 60, message = "Nome da rua não deve ser tão grande.")
    @Pattern(regexp = "[\\wªáéíóúâêîôûãõÁÉÍÓÚÂÊÎÔÛÃÕ- ]+", message = "Este tipo de nome de rua não pode ser aceito. Verifique se você não digitou nada de errado")
    private String rua; // VIACEP Logradouro
    
    @NotBlank
    @Length(min = 5, max = 40, message = "Nome da bairro não deve ser tão grande.")
    @Pattern(regexp = "[a-zA-ZáéíóúâêîôûãõÁÉÍÓÚÂÊÎÔÛÃÕ ]+", message = "Este tipo de nome de bairro não pode ser aceito. Verifique se você não digitou nada de errado")
    private String bairro;
    
    @NotBlank(message = "Numero de identificação não pode ficar vazio")
    @Max(value = 5, message = "O tamanho máximo para um número de identificação é de 5")
    @Pattern(regexp = "[\\dA-Z]+", message = "Numero só pode conter dígitos e uma a letra de identificação")
    private String num;
    
    @NotBlank(message = "CEP não pode ficar vazio")
    @Length(min = 9, message = "O tamanho mínimo do CEP deveria ser de 9 caracteres")
    @Pattern(regexp = "[\\D-]+", message = "CEP deve conter apenas números") //Teste promissor
    private String cep;
    
    @NotBlank(message = "Cidade não pode ficar vazia")
    @Length(min = 4, max = 30, message = "Nome da cidade não deve ser tão grande")
    @Pattern(regexp = "[a-zA-ZáéíóúâêîôûãõÁÉÍÓÚÂÊÎÔÛÃÕ ]+", message = "Este tipo de nome de cidade não pode ser aceito, verifique se você não digitou nada de errado") //Teste promissor
    private String cidade; // VIACEP Localidade
    
    @NotNull
    @Valid
    private Estado estado;
    
    private static Set<Endereco> enderecoLista = new HashSet<>();
    // Map Regras.
    // Chaves Não Podem ser repetidas.

    // Construtores

    public Endereco(String rua, String bairro, String num, String cep, String cidade, Estado estado) {
        this.rua = rua.replaceAll(("^[Rr][Uu][Aa] "), "");
        this.bairro = bairro;
        this.num = num;
        this.cep = Util.formataCEP(cep);
        this.cidade = cidade;
        this.estado = estado;
        registraEndereco(this);
    }

    public Endereco(String rua, String bairro, String num, String cep, String cidade, String estado) {
        this.rua = rua.replaceAll(("^[Rr][Uu][Aa] "), "");
        this.bairro = bairro;
        this.num = num;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = Estado.formata(estado);
        registraEndereco(this);
    }

    public Endereco() {

    }

    // Métodos

    private void registraEndereco(Endereco end) {
         try {
             
             verify(!(enderecoLista.contains(end))); // Verifique se o enderecoLista já não contem esse tipo de objeto
         } catch (VerifyException e) {
             
             System.out.println("Este endereço:\n" + end + "já está cadastrado. Insira um endereço alternativo, ou verifique se vc digitou a numeração correta.\n");
         return; //Return sai do método sem executar o restande dele.
         }
        enderecoLista.add(end);
        System.out.println(end.toString());

    }

    /**
     * Remove endereco com base em um CEP e a numeração da residência.
     *
     * @param cep, utilizado para descobrir a localização da residência
     * @param numero, numero utilizado para descobrir qual a residência daquele endereço deve ser removida
     */
    public static void removeEndereco(String cep, String numero) {
        // Iterator<Endereco> iterator = getEnderecoLista().iterator();
        Endereco obj = new Endereco();
        try {
            obj = Endereco.cadastraEnderecoViaCEP(cep, numero);
        } catch (ViaCEPException e) {
            e.printStackTrace();
            return;
        } catch (IllegalArgumentException ei) {
            ei.printStackTrace();// ("O CEP ou o número inserido não são válidos");
            return;
        } catch (VerifyException ie) {
            // Se Caímos aqui, é pq já existe um endereço já registrado.
            System.out.println("Antes tentar remover" + enderecoLista.toString());
            enderecoLista.remove(obj);
            System.out.println("Depois tentar remover" + enderecoLista.toString());
        }

    }

    public Endereco solicitaEndereco(String cep, String numero) {
        Iterator<Endereco> iterator = getEnderecoLista().iterator();
        Endereco obj = new Endereco();

         while (iterator.hasNext() && (obj.getCep().equals(cep) && obj.getNumero().equals(numero))) {
         obj = iterator.next();
         }
         //verify(obj.getIdDepartamento() == id, "O Departamento " + id + " não foi encontrado\n");
        return obj;
    }

    /**
     * Busca e verifica se seu endereço existe nos registros do VIACEP. <br>
     * Está é a forma recomendada e mais prática de cadastrar seu endereco.
     *
     * @param cep Ex.: 03575090
     * @param numero - O Número da sua residência Ex.: 406, 2091, 107A, 1017B.
     * @throws ViaCEPException caso o CEP não seja encontrado
     */
    public static Endereco cadastraEnderecoViaCEP(String cep, String numero) throws ViaCEPException {
        ViaCEP viaCEP = new ViaCEP();

        try {
            viaCEP.buscar(cep.replaceAll("\\D", ""));
        } catch (Exception viaCEPException) {
            throw new IllegalArgumentException("O CEP inserido não é válido");
        }

        return new Endereco(viaCEP.getLogradouro(), viaCEP.getBairro(), numero, viaCEP.getCep(), viaCEP.getLocalidade(), viaCEP.getUf());
    }

    public Endereco alteraEndereco(String cep) {
        throw new NotImplementedException("Método ainda não implementado");
    }

    /**
     * Cadastra o endereco inserido. <br>
     * Método alternativo caso o VIACEP pare de funcionar eventualmente.
     *
     * @param rua - Ex.: Alameda dos Cisnes
     * @param bairro - Ex.: Vila Jardim Vitória
     * @param numero - O Número da sua residência
     * @param cep - Ex.: 74865355 (<b> Sem o traço </b>)
     * @param cidade - Ex.: Goiânia
     * @param estado - Ex.: GO
     * @return retorna o objeto Endereco.
     */
    public static Endereco cadastraEndereco(String rua, String bairro, String numero, String cep, String cidade, Estado estado) {
        return new Endereco(rua, bairro, numero, cep, cidade, estado);
    }

    // Getters and Setters

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
//        rua = rua.replaceAll("[ ]+", " "); // remove os espaços em brancos
//        rua = checkNotNull(rua.replaceAll(("^[Rr][Uu][Aa]"), ""), "Rua não deve estar vazia");
//        checkArgument(rua.matches("[\\wªáéíóúâêîôûãõÁÉÍÓÚÂÊÎÔÛÃÕ ]+") && (rua.length() >= 2 && rua.length() <= 40),
//            "Este tipo de nome de rua não pode ser aceito, verifique se você não digitou nada de errado");
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
//        bairro = checkNotNull(bairro.replaceAll(("[ ]+"), " "), "Bairro não pode ficar vazio");
//        checkArgument(bairro.matches("[a-zA-ZáéíóúâêîôûãõÁÉÍÓÚÂÊÎÔÛÃÕ() ]+") && (bairro.length() >= 5 && bairro.length() <= 40),
//            "Este tipo de nome de bairro não pode ser aceito, verifique se você não digitou nada de errado");
        this.bairro = bairro;
    }

    public String getNumero() {
        return num;
    }

    public void setNumero(String numero) {
//        checkArgument(numero.matches("[\\dA-Z]+") && (numero.length() <= 5),
//            numero + " não pode ser registrado como um número de casa. Numero pode conter 5 dígitos contando com a letra de identificação");
//        this.num = checkNotNull(numero, "O numero da residência precisa ser inserido");
        this.num = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
//        cidade = checkNotNull(cidade.replaceAll(("[ ]+"), " "), "Cidade não pode ficar em branco");
//        checkArgument(cidade.matches("[a-zA-ZáéíóúâêîôûãõÁÉÍÓÚÂÊÎÔÛÃÕ ]+") && (cidade.length() >= 5 && cidade.length() <= 60),
//            "Este tipo de nome de cidade não pode ser aceito, verifique se você não digitou nada de errado");
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
//        cep = checkNotNull(cep.replaceAll("\\D", ""), "CEP não pode ficar em branco"); // Cep deve sair no formato: 03575-090
//        checkArgument(cep.length() == 8, "Digite apenas os números do CEP");
//        this.cep = cep.substring(0, 5) + "-" + cep.substring(5, 8);
        this.cep = cep;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
//        this.estado = checkNotNull(estado, "O estado " + estado + " não pode ter sido encontrado. Tente novamente");
        this.estado = estado;
    }

    @Override
    public int hashCode() { // Não entendi pq quando dando new, podemos utilizar o append e o hashcode...
        return new HashCodeBuilder().append(this.cep).append(this.num).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Endereco fn = (Endereco) obj;
        return new EqualsBuilder().append(this.cep, fn.cep).append(this.num, fn.num).isEquals();
    }

    // Para Testes
     public static void main(String[] args) throws ViaCEPException {
     Endereco end = new Endereco();
     end.cadastraEnderecoViaCEP("58052232", "105");
     Endereco end2 = new Endereco();
     end2.cadastraEnderecoViaCEP("58020673", "2098");
     Endereco end3 = new Endereco();
     end3.cadastraEnderecoViaCEP("41502-280", "21A");
     end.cadastraEnderecoViaCEP("41502-280", "304C");
     end.cadastraEnderecoViaCEP("41502-280", "304C");
    
     new Endereco().cadastraEndereco("rua São José", "Eliane", "83", "03575-090", "Santa Catarina", Estado.SC);
     new Endereco().cadastraEndereco("RUA Vila Viana", "Boa Vista", "205", "60860-660", "Ceara", Estado.CE);
     new Endereco().cadastraEndereco("", "Boa Vista", "205", "60860-660", "Ceara", Estado.CE); // deve falhar
     }

    public static Set<Endereco> getEnderecoLista() {
        return enderecoLista;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE).concat("\n");
    }

}
