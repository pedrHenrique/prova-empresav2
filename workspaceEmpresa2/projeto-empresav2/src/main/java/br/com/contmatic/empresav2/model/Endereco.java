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
    
    private String cep;
    private Estado estado;
    private static Set<Endereco> enderecoLista = new HashSet<>();
    // Map Regras.
    // Chaves Não Podem ser repetidas.


    public Endereco(String rua, String bairro, String cep, String cidade, Estado estado) {
        this.cep = cep;
        this.estado = estado;
        registraEndereco(this);
    }

    public Endereco(String cep) {
        this.cep = cep;
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
     * Verifica seu CEP e busca seu endereco completo.
     *
     * @param Cep Ex.: 03575090
     * @throws ViaCEPException caso o CEP não seja encontrado
     */
    public String buscaEndereco(String CEP) throws ViaCEPException {
        this.cep = CEP;
        ViaCEP viaCEP = new ViaCEP(this);
        viaCEP.buscar(cep.replaceAll("\\D", ""));
        new Endereco(viaCEP.getCep());
        System.out.println(cep);
        return cep;
    }

    public Endereco cadastraEndereco(String rua, String bairro, String cep, String cidade, Estado estado) {
        return new Endereco(rua, bairro, cep, cidade, estado);
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(cep);
    }

    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    // Para Testes
    public static void main(String[] args) throws ViaCEPException {
        Endereco end = new Endereco();
        end.buscaEndereco("03575090");
        System.out.println(getEnderecoLista());   
        
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

}
