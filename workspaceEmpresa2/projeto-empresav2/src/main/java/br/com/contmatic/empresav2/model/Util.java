package br.com.contmatic.empresav2.model;

import static com.google.common.base.Preconditions.checkArgument;

//TODO JAutoDoc TipoContato
public enum Util {

                  // Enums Tipo/Quantidade de Dígitos
                  CELULAR("Celular", 11),
                  FIXO("Fixo", 10),
                  EMAIL("Email", 50);

    // Variaveis
    private String descricao;
    private int tamanho;

    private Util(String descricao, int tamanho) {
        this.descricao = descricao;
        this.tamanho = tamanho;
    }

    private Util() {

    }

    // Metodos
    public String getDescricao() {
        return descricao;
    }

    public int get() {
        return tamanho;
    }

    public static String formataCPF(String cpf) {
        cpf = (cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11));
        return cpf;
    }
    
    public static String formataCEP(String cep) {
        cep = cep.replace("\\D", "");
        cep = cep.substring(0, 5) + "-" + cep.substring(6, 9);
        return cep;
    }
    
    /**
     * <h1> DESATIVADO </h1>
     * 
     * Formata nome rua removendo qualquer caractere "rua" no nome.
     * <br> Método criado com a intenção de uso apenas pelas funções internas 
     *
     * @param rua O nome da sua rua
     * @return O nome da rua sem qualquer caractere "rua" nele
     */
    public static String formataNomeRua(String rua) {
        rua = rua.replaceAll("[ ]+", " "); // remove possíveis múltiplos espaços em brancos
        rua = rua.replaceAll(("^[Rr][Uu][Aa]"), "");
        return rua;
    }

    public static Util tipoContato(String contato) {
        switch (contato.length()) {
            case 10:
                return Util.FIXO;
            case 11:
                return Util.CELULAR;
            default:
                return Util.EMAIL;
        }
    }

    public static String formataContato(String contato) {
        int aux = (contato.replaceAll("\\D", "").length());
        String contatoFormato;

        switch (aux) {
            case 10: // Enum Poderia conter a formatação que acontece dentro desse bloco de código
                contatoFormato = "(" + contato.substring(0, 2) + ")" + contato.substring(2, 6) + "-" + contato.substring(6);
                break;
            case 11:
                contatoFormato = "(" + contato.substring(0, 2) + ")" + contato.substring(2, 7) + "-" + contato.substring(7);
                break;

            default:
                checkArgument(contato.contains("@") && contato.contains(".com") && !(contato.length() < 7 || contato.length() > 50),
                    "O contato inserido não corresponde a nenhum email ou telefone/celular." + "\n Digite apenas os números do telefone. ");
                contatoFormato = contato;
        }
        return contatoFormato;
    }
}

/*
 * Remover o Formater das Clases que utilizam contato e inserir ele aqui Link para remoção de dúvidas: http://blog.triadworks.com.br/enums-sao-mais-que-constantes
 * 
 */
