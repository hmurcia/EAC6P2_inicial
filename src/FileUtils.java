import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileUtils {

    public static final int FITXER_NOM_POS = 0;
    public static final int FITXER_MIDA_POS = 1;
    public static final int FITXER_TIPUS_POS = 2;

    private String directoriDades;
    
    private static final String LOG_FILE = "atletes_log.bin";
    private static final String DATA_FILE = "vilatletes.txt";

    public void inicialitza() {
        // Per defecte es el directori de treball + /dades 
        directoriDades = System.getProperty("user.dir") + File.separator + "dades";
        File dadesDir = new File(directoriDades);
        if (!dadesDir.exists()) {
            dadesDir.mkdir();
        }
        mostraDirectoryDades();
    }

    /**
     * Funció per canviar de directori al director indicat com a paràmetre. Si
     * rep ".." pujarà 1 nivell.
     *
     * @param directori nom del directori a realitzar el canvi
     * @return true si ha pogut completar l'operació, false en la resta de casos
     */
    public Boolean canviarDirectori(String directori) {

        String newDirectoriDades = "";

        // Si l'usuari vol pujar un nivell en els directoris
        if (directori.equals("..")) {
            File novaLocalitzacio = new File(directoriDades);
            if (novaLocalitzacio.exists() && novaLocalitzacio.getParentFile() != null) {
                newDirectoriDades = novaLocalitzacio.getParentFile().getAbsolutePath();
            }
        } else { // O si vol canviar a un nou directori
            newDirectoriDades = directoriDades + File.separator + directori;
        }

        // comprovació que tot ha anat bé
        File testNewDir = new File(newDirectoriDades);

        if (testNewDir.exists()) {
            directoriDades = newDirectoriDades;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Funció per obtenir el llistat del contingut d'una ruta donada com a
     * paràmetre
     *
     * @param path ruta on volem llistar el contingut
     * @return Un array de Strings amb els llistat de fitxers i directoris a la
     * ruta indicada, la mida i si és fitxer o directori. Un array buit si
     * alguna cosa no ha anat be
     */
    public String[][] getLlistaContingut(String path) {

        File file = new File(path);
        File[] llistatDirectori = file.listFiles();

        String[][] contingut = new String[llistatDirectori.length][3];

        // Loop a tot el contingut del directori
        for (int i = 0; i < llistatDirectori.length; i++) {
            long mida = llistatDirectori[i].length();

            contingut[i][FITXER_NOM_POS] = llistatDirectori[i].getName();
            contingut[i][FITXER_MIDA_POS] = Long.toString(mida);

            // Si es fitxer, afegim f si no d
            if (llistatDirectori[i].isDirectory()) {
                contingut[i][FITXER_TIPUS_POS] = "d";
            } else {
                contingut[i][FITXER_TIPUS_POS] = "f";
            }
        }
        return contingut;
    }

    /**
     * Funcion Get de la variable directoriDades
     *
     * @return el valor de la variable directoriDades
     */
    public String getDirectoryDades() {
        return directoriDades;
    }

    /**
     * Funció per esborrar el fitxer amb el nom que es rep com a parèmetre. La
     * localització del fitxer ha de ser a la carpeta de dades
     *
     * @param nomFitxer nom del fitxer a esborrar
     * @return true si s'ha pogut esborrar, false en la resta de casos
     */
    public boolean esborraFitxer(String nomFitxer) {
        // Sanity check
        if (nomFitxer.isEmpty()) {
            return false;
        }

        File file = new File(directoriDades + File.separator + nomFitxer);
        return file.delete();
    }

    /**
     * Funció interna per mostrar el directori de dades (test)
     */
    private void mostraDirectoryDades() {
        System.out.println(directoriDades);
    }
    
    
    
    /**
     * Funció per carregar l'ocupació de les habitacions de la vila
     *
     * @return una variable del tipus DadesVila amb les dades carregades
     */
    public DadesVila carregaOcupacio() {

        String nomFitxer = directoriDades + File.separator + DATA_FILE;

        DadesVila dVila = new DadesVila();
        
        String file = "my-file.txt";
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error.");
        }

        return dVila;
    }
    
    /**
     * Funció per guardar l'ocupació de la vila olímpica en un monent donat
     *
     * @param dVila variable amb les dades de tots els atletes registrats
     */
    public void guardaOcupacio(DadesVila dVila) {

        try {
            String nomFitxer = directoriDades + File.separator + DATA_FILE;
            File dadesFile = new File(nomFitxer);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(dadesFile);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            
            for (int i = 0; i < dVila.numAtletesRegistrats; i++) {
                try {
                    bw.write(dVila.atletesVilaOlimpica[i][0] + ", " +
                            dVila.atletesVilaOlimpica[i][1] + ", " +
                            dVila.atletesVilaOlimpica[i][2] + ",");
                    bw.newLine();
                } catch (IOException ex) {
                    Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public void guardarAltaEnHistoric(String dadesAtleta[]) {

        String nomFitxer = directoriDades + File.separator + LOG_FILE;

        
    }

    /**
     * Funció per buscar les dades d'un atleta en l'historic de atletes
     *
     * @param idatleta id de l'atleta a busca en l'historic
     * @return Array amb les dades de l'atleta
     */
    public String[] getHistoricAtleta(String idAtleta) {

        String nomFitxer = directoriDades + File.separator + LOG_FILE;
        String[] atleta = new String[5];

       
        return atleta;
    }   
}
