package br.com.contmatic.empresav2.model;

//TODO JAutoDoc TipoContato
public enum TipoContato {
    
    //Enums Tipo/Quantidade de Dígitos
    CELULAR("Celular", 11), 
    FIXO("Fixo", 10), 
    EMAIL("Email", 50);
    
    //Variaveis 
    private String descricao;
    private int tamanho;

    private TipoContato(String descricao, int tamanho) {
        this.descricao = descricao;
        this.tamanho = tamanho;
    }
    
    private TipoContato() {
        
    }

    
    //Metodos
    public String getDescricao() {
        return descricao;
    }

    
    public int get() {
        return tamanho;
    }
    
}
