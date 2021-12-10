
import java.io.File;


/*
* @author Alejandro Santiago
*/

public class AppEAC6P2 {
    
    
    static final String MISSATGE_TRII_OPCIO = "Trii una opcó"; 
    static final String MISSATGE_TRII_OPCIO_MEDALLES = "Trii una opcio pel camp medalles de l'atleta"; 
    static final String MISSATGE_TRII_OPCIO_PAIS = "Trii una opcio pel camp pais de l'atleta"; 
    static final String MISSATGE_CODI_ATLETA ="Trii un codi d'atleta a donar d'alta";
    static final String MISSATGE_ERROR_LECTURA = "Error de lectura";
    static final String MISSATGE_ATLETA_NO_TROBAT = "No s'ha trobat l'atletaa la vila olimpica";
    static final String MISSATGE_TITOL_MENU = "GESTIO VILA OLIMPICA";
    static final String MISSATGE_PREGUNTA_MARXAATLETA = "Validar marxa d'atleta";
    static final String MISSATGE_TITOL_INGRES = "MENU INGRES ATLETA";
    static final String MISSATGE_TITOL_ALTA = "MENU MARXA ATLETA";
    static final String MISSATGE_TITOL_LLISTAT = "LLISTAT D'ATLETES";
    static final String MISSATGE_COMIAT = "Fins la propera";
    static final String MISSATGE_MITJANA = "La mitjana de'atletes ";
    static final String MISSATGE_PAIS_ATLETES = "El nombre d'atletes del pais ";
    static final String MISSATGE_LINIA_SEPARACIO = "-----------------------------------------------------------------------";
    static final String MISSATGE_INTRODUIR_OPCIO = "\nEsculliu una opció i premeu [ENTRAR]";
    static final String MISSATGE_LLISTAT_FITXERS = "--------------LLISTAT DE FITXERS----------";
    static final String MISSATGE_LLISTAT_DIRECTORIS = "--------------LLISTAT DE DIRECTORIS-------";
    static final String MISSATGE_LLISTAT_FITXERS_CAPSALERA = "          NOM                 MIDA (bytes)";
    static final String MISSATGE_LLISTAT_DIRECTORIS_CAPSALERA = "   OPCIO          DIRECTORI";
    static final String MISSATGE_BUSCA_ATLETA = "Introdueix el codi d'atleta a buscar: ";
    static final String MISSATGE_CONTINUAR = "\nPresiona [ENTRAR] per continuar";
    private FileUtils fileUtils;

    private DadesVila dadesVila;
    
    public static void main(String[] args) {
        AppEAC6P2 prg = new AppEAC6P2();
        prg.inici();
    }
    
    
    private void inici(){
    	fileUtils = new FileUtils();
        fileUtils.inicialitza();
        dadesVila = new DadesVila();
        dadesVila.inicialita();
        dadesVila = fileUtils.carregaOcupacio();
                  
        
        int opcio;
        
        String[] menuPrincipal = { 
                "1. Registrar atleta",
                "2. Marxa atleta",
                "3. Llistat d'atletes a la vila olimpica",
                "4. Buscar atleta a l'històric",
                "9. Gestió de dades\n",
                "0. Sortir\n\n"
            };

        do{
            mostrarMenu(menuPrincipal);
            opcio = UtilsES.demanarEnter(MISSATGE_TRII_OPCIO, MISSATGE_ERROR_LECTURA);
            switch (opcio){
                case 1:
                    opcioMenuRegistrar();
                    break;
                case 2:
                    opcioMenuMarxa();
                    break;
                case 3:
                    opcioMenuMostra();
                    break;
                case 4:
                    opcioBuscarAtletaHistoric(dadesVila);
                    break;
              
                case 9:
                    subMenuDades();
                    break;
                case 0:
                    System.out.println(MISSATGE_COMIAT);
                    fileUtils.guardaOcupacio(dadesVila);
            }
        }while(opcio != 0);
    }


    /**
     * 
     * @param menu
     */
    private void mostrarMenu(String[] menu){
        
        System.out.println();

        System.out.println(MISSATGE_LINIA_SEPARACIO);
        System.out.println(MISSATGE_TITOL_MENU);
        System.out.println(MISSATGE_LINIA_SEPARACIO);

        for(int i = 0; i < menu.length; ++i){
            System.out.println( menu[i] );
        }    
    }

    private void opcioBuscarAtletaHistoric(DadesVila dVila) {
        String codiAtleta = UtilsES.demanarString(MISSATGE_BUSCA_ATLETA, MISSATGE_ERROR_LECTURA);

        String[] atleta = fileUtils.getHistoricAtleta(codiAtleta);

        if (atleta[DadesVila.ID_ATLETA] == null || atleta[DadesVila.ID_ATLETA].equals("")) {
            UtilsES.mostrarMissatge("DADES DE L'ALTA HOSPITALARIA", "No s'ha trobat el atleta");
        } else {
            // UtilsES.mostrarMissatge(titol, missatge);
            UtilsES.mostrarMissatge("DADES DE L'ATLETA",
                    "Codi atleta: " + atleta[DadesVila.ID_ATLETA] + System.getProperty("line.separator")
                    + "PAIS: " + atleta[DadesVila.ID_PAIS] + System.getProperty("line.separator")
                    + "MEDALLES: " + atleta[DadesVila.ID_MEDALLES] + System.getProperty("line.separator")
                    
            );
        }
    }
    
    
    private void opcioMostrarAtletesPais(){
        int posPais = UtilsES.demanarPais(
            dadesVila.valorsPaisos,
            MISSATGE_TRII_OPCIO_PAIS,
            MISSATGE_ERROR_LECTURA
            );

        int atletesCiutat = AtletaUtils.comptarAtletesPais(
            dadesVila.atletesVilaOlimpica,dadesVila.valorsPaisos[0][posPais]);

    	mostrarMissatge(MISSATGE_PAIS_ATLETES + 
            dadesVila.valorsPaisos[1][posPais]  + " es " + atletesCiutat); 
    }

    private void opcioMostrarMitAtletesPais(){
        int posPais = UtilsES.demanarPais(
            dadesVila.valorsPaisos,
            MISSATGE_TRII_OPCIO_PAIS,
            MISSATGE_ERROR_LECTURA
            );
            mostrarMitjanaAtlPais(posPais);
    }

    private void mostrarMitjanaAtlPais(int posPais){
        mostrarMissatge(
            MISSATGE_MITJANA + dadesVila.valorsPaisos[1][posPais] + " es " 
            + String.format("%.2f",
            AtletaUtils.calculaPercAtletaPais(
                dadesVila.atletesVilaOlimpica, dadesVila.valorsPaisos[0][posPais])));
    }

    private void opcioMostrarMitajnes(){
        for ( int i = 0; i < dadesVila.valorsPaisos[0].length; ++i){
            mostrarMitjanaAtlPais(i);
        }
    }

    private  static void mostrarMissatge(String missatge){
        System.out.println(missatge);
    }

    private void opcioMenuRegistrar(){
        System.out.println(System.getProperty("line.separator"));
        System.out.println(MISSATGE_LINIA_SEPARACIO);
        System.out.println(MISSATGE_TITOL_INGRES);        
        System.out.println(MISSATGE_LINIA_SEPARACIO);

        int codiPais = UtilsES.demanarPais(dadesVila.valorsPaisos, MISSATGE_TRII_OPCIO, MISSATGE_ERROR_LECTURA);
        int codiMedalles = UtilsES.demanarTipusMedalla(dadesVila.valorsMedalles, MISSATGE_TRII_OPCIO, MISSATGE_ERROR_LECTURA);
        String codiAtleta = generarCodiAtleta(dadesVila.codiUltimAtleta);
        registraAtleta( codiAtleta,codiPais,codiMedalles); ///~VALIDAR codiAtleta~///
    }

    private void opcioMenuMarxa(){
        System.out.println(System.getProperty("line.separator"));
        System.out.println(MISSATGE_LINIA_SEPARACIO);
        System.out.println(MISSATGE_TITOL_ALTA);        
        System.out.println(MISSATGE_LINIA_SEPARACIO);

        String codiAtleta = UtilsES.demanarString(MISSATGE_CODI_ATLETA, MISSATGE_ERROR_LECTURA);

        int pos = cercaAtleta(dadesVila.atletesVilaOlimpica, codiAtleta);
        
        if ( pos >= 0 ){                                                                        
            imprimeixMissatgeComiat(codiAtleta, pos);
            UtilsES.llegeixLinea();
            fileUtils.guardarAltaEnHistoric(dadesVila.atletesVilaOlimpica[pos]);
            dadesVila.numAtletesRegistrats = marxaAtleta(pos, dadesVila.numAtletesRegistrats);
        }else{
            System.out.println(MISSATGE_ATLETA_NO_TROBAT);
        }
    }

    private void imprimeixMissatgeComiat(String codiAtleta, int pos){
        String missatge =    
        MISSATGE_PREGUNTA_MARXAATLETA +": " +  codiAtleta + 
        " amb pais d'origen: " + dadesVila.atletesVilaOlimpica[pos][DadesVila.ID_PAIS] + 
        " havent guanyat: " +  dadesVila.atletesVilaOlimpica[pos][DadesVila.ID_MEDALLES];

        System.out.println(missatge);
    }
    private int marxaAtleta(int posicioPerEsborrar, int numAtletesRegistrats){
    	dadesVila.atletesVilaOlimpica[posicioPerEsborrar][DadesVila.ID_ATLETA] = "";
    	dadesVila.atletesVilaOlimpica[posicioPerEsborrar][DadesVila.ID_PAIS] = "";
    	dadesVila.atletesVilaOlimpica[posicioPerEsborrar][DadesVila.ID_MEDALLES] = "";

        return numAtletesRegistrats - 1;
    }

    private void opcioMenuMostra(){
        mostrarAtletesVilaOlimpica();
    }

    /**
     * Generació aleatoria del codi d'un pais dels disponibles a l'array paisos
     * @return
     */
    private int generaPais(){
    	return (int) (Math.random() * dadesVila.valorsPaisos[0].length);
    }

    private int generaMedalla(){
    	return (int) (Math.random() * dadesVila.valorsMedalles.length);
    }
    
    /**
     * Generació del proper codi d'atleta a assignar
     * @param codi atleta del darrer atleta assignat 
     * @return 
     */
    private String generarCodiAtleta(int codiUltatleta) {
    	return "AT"+(formataCodi(codiUltatleta+1));
    }
    
    /**
     * Formata un integer com a string amb 3 digits
     * @param codi d'atleta
     * @return 
     */
    private String formataCodi(int codi) {
    	return String.format("%03d", codi);
    }
    
    
    
    /**
     * 
     * @param codiAtleta
     * @param posPais
     * @param posMedalla
     */
    private void registraAtleta(String codiAtleta,int posPais, int posMedalla){        
        
        // Primer comprovem si tenim espai a la vila olimpica per el nou atleta
        int espaiDisponible;
        espaiDisponible=cercaHabitacioBuida(dadesVila.atletesVilaOlimpica);
        if ( espaiDisponible == -1){
            System.out.println("------------------------------------------");
            System.out.println("  Vila Olímpica plena, " + codiAtleta + " no pot ser enregistrat");
            System.out.println("------------------------------------------");
        }
        else{
            // Si tenim espais disponibles, mirem que el codi d'aquest atleta no estigui ja al sistema
            int pos = cercaAtleta(dadesVila.atletesVilaOlimpica,codiAtleta);
            if ( pos==-1 ){  // S'han recorregut tots els atletes i no s'ha trobat coincidencia
                dadesVila.atletesVilaOlimpica[espaiDisponible][DadesVila.ID_ATLETA] = codiAtleta;
                dadesVila.atletesVilaOlimpica[espaiDisponible][DadesVila.ID_PAIS] = 
                    trobaStringArray(posPais, dadesVila.valorsPaisos[0]);
                dadesVila.atletesVilaOlimpica[espaiDisponible][DadesVila.ID_MEDALLES] = 
                    trobaStringArray(posMedalla, dadesVila.valorsMedalles);
                dadesVila.numAtletesRegistrats++;
                dadesVila.codiUltimAtleta++;
                fileUtils.guardaOcupacio(dadesVila);
            }else{
                System.out.println("ERROR! l'ATLETA amb codi " + codiAtleta + " ja es troba a la vila olímpica");
            }
        }
    }
    
    /**
     * 
     * @param atlVilaOlimp
     * @param codiAtleta
     * @return
     */
    private int cercaAtleta(String[][] atlVilaOlimp, String codiAtleta){
        int posIndex = 0;
        boolean trobat = false;
        
        // Important: Mai fer servir un bucle tipus for, si hem de terminar la seva
        // execucio a mig cami (en aquest cas, el codi d'atleta a l'array)
        while( posIndex < atlVilaOlimp.length && !trobat){
            trobat = atlVilaOlimp[posIndex][DadesVila.ID_ATLETA] != null &&
                atlVilaOlimp[posIndex][DadesVila.ID_ATLETA].equalsIgnoreCase(codiAtleta);
            posIndex++;
        }

        if( !trobat ){
            return -1;
        }else{
            return (posIndex - 1);
        }
    }
    

    /**
     * 
     * @param atlVilaOlimp
     * @return
     */
    private int cercaHabitacioBuida(String[][] atlVilaOlimp ) {
        int posIndex = 0;
        boolean habitacioTrobada = false;

        if ( dadesVila.numAtletesRegistrats == atlVilaOlimp.length ){
            return -1;
        }

        // Important: Mai fer servir un bucle tipus for, si hem de terminar la seva
        // execucio a mig cami (en aquest cas, quan trobem habitacio lliure)
        while(posIndex < atlVilaOlimp.length && !habitacioTrobada) {
            habitacioTrobada = atlVilaOlimp[posIndex][DadesVila.ID_ATLETA] == null
                    || atlVilaOlimp[posIndex][DadesVila.ID_ATLETA].equals("");
            posIndex++; 
        }

       if( !habitacioTrobada ){
            return -1;
        }else{
            return (posIndex - 1);
        }
     }
    
            
     /**
      * 
      */
     private void mostrarAtletesVilaOlimpica(){
        System.out.println(MISSATGE_LINIA_SEPARACIO);
        System.out.println(MISSATGE_TITOL_LLISTAT);
        System.out.println(MISSATGE_LINIA_SEPARACIO);

        System.out.println(MISSATGE_LINIA_SEPARACIO);
        System.out.println("NUM_HABITACIO" + "         ATLETA"+ "       PAIS D'ORIGEN"+ "             MEDALLES");
        System.out.println(MISSATGE_LINIA_SEPARACIO);

        for (int i = 0; i < dadesVila.atletesVilaOlimpica.length; i++){
            System.out.println ( formataDadesAtleta ( dadesVila.atletesVilaOlimpica[i], i ));
        }

        System.out.println("Nombre d'atletes a la vila olímpica:" + dadesVila.numAtletesRegistrats );
        System.out.println("Espai disponible a la vila olímpica:" + 
            ( dadesVila.atletesVilaOlimpica.length - dadesVila.numAtletesRegistrats)  );     
    }

    /**
     * 
     * @param dadaAtleta
     * @param posicio
     * @return
     */
    private String formataDadesAtleta( String dadaAtleta[], int posicio){

        String pais = dadaAtleta[DadesVila.ID_PAIS];
        String medalla = dadaAtleta[DadesVila.ID_MEDALLES];        
        String codiAtleta = dadaAtleta[DadesVila.ID_ATLETA];
        String codiHabitacio = "HAB" + formataCodi(posicio);
        
        String entradaAtleta = 
            codiHabitacio + "                " +
            codiAtleta + "                " +           
            pais+ "                " +        
            medalla;

        return entradaAtleta;
    }

    /**
     * Busca la posicio donada en un array de Strings i retorna el seu valor
     * @param posicio posicio a buscar en l'array
     * @param array 
     * @return el valor que hi ha a la posicio dins de l'array o un string buit si no es troba
     */
    private String trobaStringArray(int posicio, String array[]){
        // Fem comprovacions de valors no valids per la posicio del pais
        if ( posicio < 0 || posicio >= array.length){
            return "";
        }

        // si tot correcte retornem la el pais a l'array de paisos a la posicio donada
        return array [posicio];
    }
    
    /**
    *
    */
   private void subMenuDades() {
       int opcio;
       String[] subMenuDades = {"GESTIO ATLETES VILA OLÍMPICA - DADES",
           "\n1. Mostrar la carpeta de dades",
           "2. Canviar la carpeta de dades",
           "3. Llistar fitxers",
           "4. Eliminar fitxer\n",
           "0. Enrere\n\n"
       };

       do {
           mostrarMenu(subMenuDades);
           opcio = UtilsES.demanarEnter(MISSATGE_INTRODUIR_OPCIO, MISSATGE_ERROR_LECTURA);
           switch (opcio) {
               case 1:
                   mostraDirectoriDades();
                   break;
               case 2:
                   canviaDirectori();
                   break;
               case 3:
                   llistarFitxers();
                   break;
               case 4:
                   eliminarFitxers();
                   break;
           }
       } while (opcio != 0);
   }

   /**
    *
    */
   private void canviaDirectori() {

       String[] subMenuDirectoris = {
           "GESTIO ATLETES VILA OLÍMPICA - DADES - CANVI DIRECTORI",};
       String[] opcionsDefecte = {
           String.format("%1$" + 4 + "s", "1.") + String.format("%1$" + 25 + "s", ".."),
           String.format("%1$" + 4 + "s", "0.") + String.format("%1$" + 25 + "s", "Enrere"),};

       int opcio = 0;

       do {
           String[][] contingutDirectori = fileUtils.getLlistaContingut(fileUtils.getDirectoryDades());

           mostrarMenu(subMenuDirectoris);
           mostrarLlistatDirectoris(contingutDirectori, opcionsDefecte);

           opcio = UtilsES.demanarEnter(MISSATGE_INTRODUIR_OPCIO, MISSATGE_ERROR_LECTURA);

           if (opcio == 1) {
               fileUtils.canviarDirectori("..");
           } else if (opcio > 1 && (opcio - 2) < contingutDirectori.length) {
               fileUtils.canviarDirectori(contingutDirectori[opcio - 2][0]);
           }

       } while (opcio != 0);
   }

   /**
    *
    */
   private void mostraDirectoriDades() {

       String[] subMenuMostraDirectori = {
           "GESTIO ATLETES VILA OLÍMPICA - DADES - MOSTRAR DIRECTORI DADES",};
       mostrarMenu(subMenuMostraDirectori);

       String dir = fileUtils.getDirectoryDades();
       System.out.println("\nEl directori de dades es: " + dir);
       UtilsES.demanaReturn(MISSATGE_CONTINUAR);
   }

   /**
    *
    */
   private void eliminarFitxers() {
       String[] subMenuEliminarFitxers = {
           "GESTIO ATLETES VILA OLÍMPICA - DADES - ELIMINAR FITXER",};

       mostrarMenu(subMenuEliminarFitxers);

       String nomFitxer = UtilsES.demanarString("\n\nIntrodueix el nom del fitxer a esborrar (del directori de dades)", MISSATGE_ERROR_LECTURA);
       boolean result = fileUtils.esborraFitxer(nomFitxer);

       if (result) {
           System.out.println("\nCORRECTE! Fitxer esborrat correctament");
       } else {
           System.out.println("\nERROR! no s'ha pogut esborrar el fitxer");
       }

       UtilsES.demanaReturn(MISSATGE_CONTINUAR);
   }

   /**
    *
    */
   private void llistarFitxers() {
       String[] subMenuLlistarFitxers = {
           "GESTIO ATLETES VILA OLÍMPICA - DADES - LLISTAR FITXERS",};
       mostrarMenu(subMenuLlistarFitxers);

       String[][] llistat = fileUtils.getLlistaContingut(fileUtils.getDirectoryDades());

       mostraLListatFitxers(llistat);
       UtilsES.demanaReturn(MISSATGE_CONTINUAR);
   }

   /**
    *
    * @param directoris
    * @param opcionsDefecte
    */
   public void mostrarLlistatDirectoris(String[][] contingutDirectori, String[] opcionsDefecte) {
       System.out.println("\n\n" + MISSATGE_LINIA_SEPARACIO);
       System.out.println(MISSATGE_LLISTAT_DIRECTORIS);
       System.out.println(MISSATGE_LINIA_SEPARACIO);
       System.out.println("\n\n" + MISSATGE_LLISTAT_DIRECTORIS_CAPSALERA + "\n");

       for (int i = 0; i < contingutDirectori.length; ++i) {
           if (contingutDirectori[i][FileUtils.FITXER_TIPUS_POS].equals("d")) {
               String opcio = Integer.toString(i + opcionsDefecte.length) + ".";
               String nomDirectori = contingutDirectori[i][FileUtils.FITXER_NOM_POS];

               opcio = String.format("%1$" + 4 + "s", opcio);
               nomDirectori = String.format("%1$" + 25 + "s", nomDirectori);

               System.out.print(opcio);
               System.out.println(nomDirectori);
           }
       }

       System.out.println("\n\n\n");
       for (String opcionStr : opcionsDefecte) {
           System.out.println(opcionStr);
       }
   }

   /**
    *
    * @param llistatFitxers
    */
   public void mostraLListatFitxers(String[][] llistatFitxers) {
       System.out.println("\n\n" + MISSATGE_LINIA_SEPARACIO);
       System.out.println(MISSATGE_LLISTAT_FITXERS);
       System.out.println(MISSATGE_LINIA_SEPARACIO);
       System.out.println("\n\n" + MISSATGE_LLISTAT_FITXERS_CAPSALERA + "\n");

       for (int i = 0; i < llistatFitxers.length; ++i) {
           if (llistatFitxers[i][FileUtils.FITXER_TIPUS_POS].equals("f")) {
               String nomFixter = llistatFitxers[i][FileUtils.FITXER_NOM_POS];
               String midaFixter = llistatFitxers[i][FileUtils.FITXER_MIDA_POS];

               nomFixter = String.format("%1$" + 20 + "s", nomFixter);
               midaFixter = String.format("%1$" + 20 + "s", midaFixter);

               System.out.print(nomFixter);
               System.out.println(midaFixter);
           }
       }
   }
}