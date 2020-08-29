package br.com.contmatic.empresav2.model;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.com.parg.viacep.ViaCEP;
import br.com.parg.viacep.ViaCEPEvents;
import br.com.parg.viacep.ViaCEPException;

public class Endereco implements ViaCEPEvents {

    /* Anotação para o Futuro
     * 
     * Pensar em uma lógica melhor...
     * Para Endereco Funcionar
     * Cada atributo do mesmo deve receber um parâmetro específico de VIACEP
     * E assim só depois de receber todo os parâmetros, deve salva-los no set... Se isso ainda for acontecer.
     * 
     */
    private String rua; //VIACEP Logradouro
    private String bairro;
    private String cidade; //VIACEP Localidade
    private String cep;
    private String viaEstado;
    private Estado estado;
    private static Set<Endereco> enderecoLista = new HashSet<>();
    // Map Regras.
    // Chaves Não Podem ser repetidas.


    public Endereco(String rua, String bairro, String cep, String cidade, Estado estado) {
        this.rua = rua;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
        registraEndereco(this);
    }
    
    public Endereco(String rua, String bairro, String cep, String cidade, String estado) {
        this.rua = rua;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = Estado.formata(estado);
        registraEndereco(this);
    }
    
    
    public Endereco() {
        
    }

    private void registraEndereco(Endereco end) {
        if (enderecoLista.contains(end)) {
            System.out.println("Endereço já cadastrado");
        } else {
            enderecoLista.add(end);
            System.out.println(end.toString());
        }
    }

    /**
     * Verifica e busca seu endereço se existente nos bancos do VIACEP.
     *<br> Está é a forma recomendada e mais simples de cadastrar seu endereco.
     *
     * @param Cep Ex.: 03575090
     * @throws ViaCEPException caso o CEP não seja encontrado
     */
    public Endereco cadastraEnderecoViaCEP(String CEP) throws ViaCEPException {
        //Talvez um try/catch no futuro seja necessário aqui.
        ViaCEP viaCEP = new ViaCEP(this);
        viaCEP.buscar(CEP.replaceAll("\\D", ""));
        
        
        return new Endereco(viaCEP.getLogradouro(), viaCEP.getBairro(), viaCEP.getCep(), viaCEP.getLocalidade(), viaCEP.getUf());
    }
    
    public Endereco alteraEndereco(String CEP) {
        return null;
    }

    public Endereco cadastraEndereco(String rua, String bairro, String cep, String cidade, Estado estado) {
        return new Endereco(rua, bairro, cep, cidade, estado);
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    // Para Testes
    public static void main(String[] args) throws ViaCEPException {
        Endereco end = new Endereco();
        end.cadastraEnderecoViaCEP("26385-270");
        
        // Endereco end = new Endereco().cadastraEndereco("Nespereira", "Eliane", "03575090", "São Paulo", Estado.SC);
        // System.out.println(end.estado.getDescricaoViaLista());
    }
    
    public static Set<Endereco> getEnderecoLista() {
        return enderecoLista;
    }

    @Override
    public void onCEPSuccess(ViaCEP cep) {

    }

    @Override
    public void onCEPError(String cep) {
        throw new IllegalArgumentException("O CEP inserido não é válido");

    }

    @Override
    public String toString() {
        return "Endereco [rua=" + rua + ", bairro=" + bairro + ", cidade=" + cidade + ", cep=" + cep + ", viaEstado=" + viaEstado + ", estado=" + estado + "]";
    }

}
