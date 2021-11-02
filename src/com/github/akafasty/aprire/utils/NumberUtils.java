package com.github.akafasty.aprire.utils;

import java.text.*;
import java.util.Date;
import java.util.Locale;

public class NumberUtils {

    /**
     *
     * @param value value to be formatted
     * @return value formatted (2.000.000)
     */
    public static String numberFormat(double value) {

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.forLanguageTag("pt-BR"));

        numberFormat.setMaximumFractionDigits(1);


        return numberFormat.format(value);

    }


    /**
     *
     * @param valor value to be formatted
     * @return value formatted (2 Milhões)
     */
    public static String numberKFormat(double valor) {

        String[] simbols = { "", " Mil", " Milhões", " Bilhões", " Trilhões", " Quatrilhões" };
        int index;

        for (index = 0; valor / 999.9 >= 1.0; valor /= 999.9, ++index);

        DecimalFormat decimalFormat = new DecimalFormat("#.#", new DecimalFormatSymbols(Locale.forLanguageTag("pt-BR")));

        return decimalFormat.format(valor) + simbols[index];

    }


    /**
     *
     * @param time value to be formatted
     * @return value formatted (2 dias 1 hora e 53 minutos | Há 2 dias 1 hora e 53 minutos)
     */
    public static String numberTimeFormat(long time) {

        StringBuilder stringBuilder = new StringBuilder("há ");
        long value = (System.currentTimeMillis()-time);

        int seconds = (int) (value / 1000L);

        if (seconds >= 86400) {
            int days = seconds / 86400;
            seconds %= 86400;

            stringBuilder.append(days);

            if (days >= 1)
                return new SimpleDateFormat("[dd/MM/yyyy] ás HH:mm:ss").format(new Date(time));

            else stringBuilder.append(" dia ");

        }

        if (seconds >= 3600) {
            int hours = seconds / 3600;
            seconds %= 3600;

            stringBuilder.append(" e ");

            stringBuilder.append(hours);

            if (hours > 1)
                stringBuilder.append(" horas ");

            else stringBuilder.append(" hora ");

        }
        if (seconds >= 60) {
            int min = seconds / 60;
            seconds %= 60;

            stringBuilder.append(" e ");
            stringBuilder.append(min);

            if (min > 1)
                stringBuilder.append(" minutos ");

            else stringBuilder.append(" minuto ");
        }

        if (seconds >= 0) {
            
            stringBuilder.append(" e ");
            stringBuilder.append(seconds);

            if (seconds > 1)
                stringBuilder.append(" segundos");

            else stringBuilder.append(" segundo");
        }

        return stringBuilder.toString();

    }

    /**
     *
     * @param string string to be parsed
     * @return string parsed to integer
     */
    public static Integer integerParser(String string) {

        try { return Integer.parseInt(string); }

        catch (Exception exception) { return null; }

    }

    /**
     *
     * @param string string to be parsed
     * @return string parsed to double
     */
    public static Double doubleParser(String string) {

        try { return Double.parseDouble(string); }

        catch (Exception exception) { return null; }

    }

}
