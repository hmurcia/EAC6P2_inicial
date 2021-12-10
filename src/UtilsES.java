import java.util.Scanner;  

public class UtilsES {
	static final String MISSATGE_ERROR_LECTURA = "Error de lectura";
	static final String MISSATGE_LINIA_SEPARACIO = "-----------------------------------------------------------------------";
	static final String MISSATGE_LINIA_SEPARACIO_FINAL = "===================================================================";
	
    public static int demanarEnter(String missatge, String missatgeError){
        Scanner scanner = new Scanner(System.in);        
        int ret;
        boolean correcte;
        do{
            System.out.println(missatge);
            correcte=scanner.hasNextInt();
            if(!correcte){
                scanner.next();
                System.out.println(missatgeError);
            }
        }while(!correcte);
        ret = scanner.nextInt();  
        scanner.nextLine();        
        return ret;
    }

    public static String demanarString(String missatge, String missatgeError){
        Scanner scanner = new Scanner(System.in);
        System.out.println(missatge);
        String textIntroduit = scanner.nextLine();

        while (textIntroduit.isEmpty()){
            System.out.println(missatgeError);
            System.out.println(missatge);
            textIntroduit = scanner.nextLine();
        }
        return textIntroduit;
    }

    public static int demanarTipusMedalla(String [] valorsMedalles, String missatge, String missatgeError){
        boolean correcte = false;
        int opcio = 0;
                
        do{
        
            imprimirOpcionsArray(valorsMedalles);            
            opcio = demanarEnter(missatge, missatgeError);
        	
        	if (opcio >=0 && opcio< valorsMedalles.length){
                correcte=true;
            } 
        	
        }while(!correcte);

        return opcio;
    }

    public static int demanarPais(String [][] paisos, String missatge, String missatgeError){
        boolean correcte = false;
        int opcio = 0;
                
        do{
        
            imprimirOpcionsArray(paisos[1]);
            opcio = demanarEnter(missatge, missatgeError);
        	
        	if (opcio >=0 && opcio< paisos[0].length){
                correcte=true;
            } 
        	
        }while(!correcte);

        return opcio;
    }

    public static void llegeixLinea(){    	
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    private static void imprimirOpcionsArray(String arrayAImprimir[]){
        for (int i=0; i < arrayAImprimir.length; ++i){
            System.out.println(
                String.format("%1$"+ 3 + "s", i+".") + 
                arrayAImprimir[i]                
            );            
        }        
    }
    
  
    
    /**
    *
    * @param missatge
    */
   public static void demanaReturn(String missatge) {
       Scanner scanner = new Scanner(System.in);
       System.out.println(missatge);
       scanner.nextLine();
   }
   
   public static void mostrarMissatge(String titol, String missatge) {
       System.out.println();
       System.out.println();
       System.out.println(MISSATGE_LINIA_SEPARACIO);
       System.out.println(titol);
       System.out.println(MISSATGE_LINIA_SEPARACIO);
       System.out.println(missatge);
       System.out.println(MISSATGE_LINIA_SEPARACIO_FINAL);
   }
   
   
}