/*
* @author Alejandro Santiago
*/

public class AtletaUtils {
    
    /**
     * Funcio per contar els atletes d'una nacionalitat concreta a la vila olimpica
     * en un moment donat
     * @param atletesVilaOlimpica
     * @param pais
     * @return number d'atletes del pais rebut com a parametre
     */
    public static int comptarAtletesPais(String[][] atletesVilaOlimpica, String pais) {
        int numAtletesPais = 0;
        for ( int i = 0; i < atletesVilaOlimpica.length; ++i){
            if ( atletesVilaOlimpica[i][DadesVila.ID_PAIS].equalsIgnoreCase(pais)){
                numAtletesPais++;
            }
        }

        return numAtletesPais;
    }	

   /**
    * Funcio per calcular el percentatge dels atleted d'un pais envers el total
    * @param atletesVilaOlimpica
    * @param pais
    * @return
    */
    public static float calculaPercAtletaPais(String[][] atletesVilaOlimpica, String pais) {
        float numAtletesTotal = 0.0f;
        float resultat = 0.0f;
        for ( int i = 0; i < atletesVilaOlimpica.length; ++i){
            if ( !atletesVilaOlimpica[i][DadesVila.ID_PAIS].isEmpty()){
                numAtletesTotal++;
            }
        }

        if ( numAtletesTotal > 0 ){
            float numAtletesPais = comptarAtletesPais(atletesVilaOlimpica, pais);
            resultat = numAtletesPais / numAtletesTotal;
        }

        return resultat;
    }
}