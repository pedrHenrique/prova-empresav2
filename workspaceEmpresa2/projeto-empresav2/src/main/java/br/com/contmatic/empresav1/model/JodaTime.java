package br.com.contmatic.empresav1.model;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Months;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class JodaTime {
    
    private DateTime data;

    public JodaTime(DateTime data) {
        this.data = data;
    }
    
    public static void main(String [] args) {
        /*
         * Pega as Datas Mais Atuais
         */
//        DateTime dataFinal = new DateTime();
//        System.out.println("Data formatada Padrao: " + dataFinal.toString(dtfPadrao));
//        System.out.println("Data formatada Extenso: " + dataFinal.toString(dtfExtenso));
        dataTest();
        
//        DateTime dataInicio = new DateTime(2011, 9, 5, 0, 0);
//        
//        
//        
//         
//        DateTimeFormatter dtfPadrao = DateTimeFormat.forPattern("dd/MM/yyyy");
//        System.out.println("Data formatada Padrao: " + dataInicio.toString(dtfPadrao));
//        
//        DateTimeFormatter dtfExtenso = DateTimeFormat.forPattern("dd 'de' MMMM 'de' yyyy");
//        System.out.println("Data formatada Extenso: " + dataInicio.toString(dtfExtenso));
        
    }
    
    public static DateTime dataTest() {
        String[] aux = {};
        try(Scanner input = new Scanner(System.in)) {
            System.out.println("Digite a data seguindo a ordem de dd/mm/aaaa");
            aux = input.next().split("/");
        }
        for (String valor: aux) {
            System.out.println("Separação: " + valor);
        }
        int dia = Integer.parseInt(aux[0]);
        int mes = Integer.parseInt(aux[1].replaceFirst("0", ""));
        int ano = Integer.parseInt(aux[2]);
        
        DateTime dt = new DateTime(ano, mes, dia, 0, 0);
        DateTimeFormatter dtfPadrao = DateTimeFormat.forPattern("dd/MM/yyyy");
        System.out.println("Data formatada Padrao: " + dt.toString(dtfPadrao));
        return null;
    }
    

}
