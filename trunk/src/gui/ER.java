package gui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author sema
 */
public class ER {

    public static final void main(String [] args) {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader (isr);
        String er = null, cad = null;

        FileReader fr;
        try {
            System.out.print("Introduzca la ER: ");
            er = br.readLine();
            System.out.print("Introduzca la cadena a comparar: ");
            cad = br.readLine();
        } catch (IOException ex) {
            Logger.getLogger(ER.class.getName()).log(Level.SEVERE, null, ex);
        }

        Pattern p = Pattern.compile(er, Pattern.CASE_INSENSITIVE); // Insensible a mayúsculas
        Matcher m = p.matcher(cad);

        // ER: hol*, cadena: hola -> Coincide
        // ER: ^www, cadena: www.sema.es -> coincide
        // ER: ^www, cadena: sema.www.es -> No coincide
        // ER: ^www, cadena: awwwiiii -> No coincide
        // Se puede hacer para que sólo se permitan introducir ciertas ER, pq sino
        // con poner * saldrían toooodos los archivos existentes en la red.

        // Mínimo que contenga 3 caracteres

        if(m.find())
            System.out.println("¡Coincide!");
        else
            System.out.println("¡No coincide!");
    }

}
