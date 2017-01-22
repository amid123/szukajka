/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.tools;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author ringo99<amid123@gmail.com>
 */
@Service
@Scope("prototype")
public class AllegroLinkGenerator {

    public String generateLink(String title, String auctionId) {

        String titleBuffer =  title.replace("/", "-");       
              
        /**
         * Eliminujemy znaki specjalne zlinków i zastępujemy separatorem
         */
        titleBuffer = titleBuffer.replace(" ", "-");
        titleBuffer = titleBuffer.replace("=", "-");
        titleBuffer = titleBuffer.replace("+", "-");
        titleBuffer = titleBuffer.replace(".", "-");
        titleBuffer = titleBuffer.replace(",", "-");
        titleBuffer = titleBuffer.replace(":", "-");
        titleBuffer = titleBuffer.replace(";", "-");
        titleBuffer = titleBuffer.replace("*", "-");
        titleBuffer = titleBuffer.replace("$", "-");
        titleBuffer = titleBuffer.replace("#", "-");
        titleBuffer = titleBuffer.replace("^", "-");
        titleBuffer = titleBuffer.replace("(", "-");
        titleBuffer = titleBuffer.replace(")", "-");
        titleBuffer = titleBuffer.replace("{", "-");
        titleBuffer = titleBuffer.replace("}", "-");
        titleBuffer = titleBuffer.replace("@", "-");
        titleBuffer = titleBuffer.replace("!", "-");
        titleBuffer = titleBuffer.replace("&", "-");
        titleBuffer = titleBuffer.replace("_", "-");
        titleBuffer = titleBuffer.replace("\"", "-");
        titleBuffer = titleBuffer.replace("~", "-");
        titleBuffer = titleBuffer.replace("`", "-");
        titleBuffer = titleBuffer.replace("|", "-");
        titleBuffer = titleBuffer.replace("\\", "-");   // backslash ?
        titleBuffer = titleBuffer.replace("%", "-");
        
        /**
         * 
         * olskie znaki
         */
        titleBuffer = titleBuffer.replace("ą", "a");
        titleBuffer = titleBuffer.replace("Ą", "A");
        titleBuffer = titleBuffer.replace("ę", "e");
        titleBuffer = titleBuffer.replace("Ę", "E");
        
        titleBuffer = titleBuffer.replace("ł", "l");
        titleBuffer = titleBuffer.replace("Ł", "L");
        titleBuffer = titleBuffer.replace("ó", "o");
        titleBuffer = titleBuffer.replace("Ó", "O");
        titleBuffer = titleBuffer.replace("ź", "z");
        titleBuffer = titleBuffer.replace("Ź", "Z");
        titleBuffer = titleBuffer.replace("ż", "z");
        titleBuffer = titleBuffer.replace("Ż", "Z");
        titleBuffer = titleBuffer.replace("ś", "s");
        titleBuffer = titleBuffer.replace("Ś", "S");
        titleBuffer = titleBuffer.replace("ć", "c");
        titleBuffer = titleBuffer.replace("Ć", "C");
        titleBuffer = titleBuffer.replace("ń", "n");
        titleBuffer = titleBuffer.replace("Ń", "N");
        /**
         * Usuwamy nadmiar separatorów
         */
        
        titleBuffer = titleBuffer.replace("--", "-");
        titleBuffer = titleBuffer.replace("---", "-");
        titleBuffer = titleBuffer.replace("----", "-");
        titleBuffer = titleBuffer.replace("-----", "-");
        titleBuffer = titleBuffer.replace("------", "-");
        titleBuffer = titleBuffer.replace("-------", "-");
        titleBuffer = titleBuffer.replace("--------", "-");
        titleBuffer = titleBuffer.replace("---------", "-");
        
        
        String link = "http://allegro.pl/"+titleBuffer + "-i" + auctionId + ".html";

        return link;
    }
}
