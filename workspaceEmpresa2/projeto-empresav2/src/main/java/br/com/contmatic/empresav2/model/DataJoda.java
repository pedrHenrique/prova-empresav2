package br.com.contmatic.empresav2.model;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Months;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;


/*
 * DataJoda
 * Versão 1.0
 * 
 */

//TODO JAutoDoc Data
public class DataJoda {

    private static final DateTime DT = new DateTime();
    private DateTime data;

    public DataJoda(String data) {
        setData(cadastraData(data));
    }

    public DataJoda() {

    }

    // Método receberá a data e fará todo o processo de formata-lá para o padrão JodaTime
    // Data atualmente deverá ser passada como: dd/mm/aaaa
    public static DateTime cadastraData(String data) {
        String[] aux = {};
        aux = data.split("/");
        int dia = Integer.parseInt(aux[0]);
        int mes = Integer.parseInt(aux[1].replaceFirst("0", ""));
        int ano = Integer.parseInt(aux[2]);
        
        //TODO Formatar para que datas não possam ser inseridas no futuro.. Ver a utilização de Notification
        //no lugar de uma exception: https://martinfowler.com/articles/replaceThrowWithNotification.html
        return new DateTime(ano, mes, dia, DT.getHourOfDay(), DT.getMinuteOfHour()); // ano/mes/dia/horaDoDia/minutosDaHora
    }

    public DateTime getData() {
        return data;
    }

    public void setData(DateTime data) {
        this.data = data;
    }

}
