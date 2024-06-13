package org.elsquatrecaps.utilities.console;

import static org.elsquatrecaps.utilities.console.Print.ToPrintTypes.MESSAGE;
import static org.elsquatrecaps.utilities.console.Print.ToPrintTypes.NUMBER;
import static org.elsquatrecaps.utilities.console.Print.ToPrintTypes.PERCENT_BAR;

/**
 *
 * @author josep
 */
public class Print {    
    public static void printPercentage(String message, int made, int all){
        printPercentage(message, made, all, '*', NUMBER, PERCENT_BAR);
    }
    
    public static void printPercentage(int made, int all){
        printPercentage(made, all, '*', NUMBER, PERCENT_BAR);
    }
    
    public static void printPercentage(String message, int made, int all, ToPrintTypes... toPrint){
        printPercentage(message, made, all, '*', toPrint);
    }
    
    public static void printPercentage(int made, int all, ToPrintTypes... toPrint){
        printPercentage(made, all, '*', toPrint);
    }
    
    /**
     *
     * @param made
     * @param all
     * @param barChar
     * @param toPrint
     */
    public static void printPercentage(int made, int all, char barChar, ToPrintTypes... toPrint){
        printPercentage("", made, all, barChar, toPrint);
    }
    
    public static void printPercentage(String message, int made, int all, char barChar, ToPrintTypes... toPrint){
        float perc;
        int aToPrint=0;
        for(ToPrintTypes pt: toPrint){
            aToPrint = aToPrint | pt.id();
        }
        if(!message.isEmpty()){
            aToPrint = aToPrint | MESSAGE.id;
        }
        //System.out.println("\033[H\033[2J");  
        System.out.print("\r");
        System.out.flush();
        perc = ((float)made)/all*100;
        if((aToPrint & PERCENT_BAR.id()) !=0){
            for(int i=0; i<perc; i++){
                System.out.print("*");
            }
        }
        if((aToPrint & NUMBER.id()) !=0){
            System.out.print(String.format( " (%f%%)", perc));            
        }
        if((aToPrint & MESSAGE.id()) !=0){
            System.out.print(String.format( " - %s", message));            
        }
    }
    
    public enum ToPrintTypes{
        NUMBER(1),
        PERCENT_BAR(2),
        MESSAGE(4);
        
        private int id;        
        private ToPrintTypes(int id) {        
            this.id = id;
        }
        public int id(){
            return this.id;
        }
    }
    
}
