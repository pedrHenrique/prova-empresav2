package br.com.contmatic.empresav2.model;

import br.com.parg.viacep.ViaCEP;
import br.com.parg.viacep.ViaCEPEvents;
import br.com.parg.viacep.ViaCEPException;

public class TestCep implements ViaCEPEvents {

    private String cep;
    private ViaCEP viaCEP;

    public static void main(String[] args) throws ViaCEPException {
        new TestCep().run();

    }

    public void run() {
        setCep("03575090");
        viaCEP = new ViaCEP(this); // this deve ser passado caso queremos utilizar o implements

        System.out.println("Iniciando o Processo");
        try {
            viaCEP.buscar(getCep());
        } catch (ViaCEPException ex) {
            System.err.println("Ocorreu um erro na classe " + ex.getClasse() + ": " + ex.getMessage());
        }
        
        System.out.println(viaCEP.getCep());
        System.out.println(viaCEP.getBairro());
        System.out.println(viaCEP.getComplemento());
        System.out.println(viaCEP.getUf());
    }
    
    @Override
    public void onCEPSuccess(ViaCEP cep) {
        System.out.println();
        System.out.println("CEP " + cep.getCep() + " encontrado!");
        System.out.println("Logradouro: " + cep.getLogradouro());
        System.out.println("Complemento: " + cep.getComplemento());
        System.out.println("Bairro: " + cep.getBairro());
        System.out.println("Localidade: " + cep.getLocalidade());
        System.out.println("UF: " + cep.getUf());
        System.out.println("Gia: " + cep.getGia());
        System.out.println("Ibge: " + cep.getIbge());
        System.out.println();
    }

    @Override
    public void onCEPError(String cep) {
        System.out.println();
        System.out.println("Não foi possível encontrar o CEP " + cep + "!");
        System.out.println();
    }


    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

}
