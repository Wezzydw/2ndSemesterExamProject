package pkg2ndsemesterexamproject.dal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScanFolder {

    private List<String> allFiles;
    private NewFilesDataDump dataDump;

    public ScanFolder() throws IOException {
        allFiles = new ArrayList();
        dataDump = new NewFilesDataDump();
    }

    /**
     * Denne metode kigger mappen "data" igennem, og tjekker om der er tilføjet
     * nye csv- eller txt-filer. Er der tilføjet nye filer, kaldes
     * writeSavedFile metoden.
     *
     * @throws IOException
     * @throws SQLException
     */
    public void updateFiles() throws IOException, SQLException {
        allFiles = loadSavedFile();
        File files = new File("data/");
        File[] listOfFiles = files.listFiles();
        List<String> filePaths = new ArrayList<>();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                if ((file.getName().endsWith(".csv")
                        || file.getName().endsWith(".txt"))
                        && !hasFileBeenSaved(file.getName())) {
                    filePaths.add(file.getName());
                    dataDump.WriteDataFromNewFilesToDb(file);
                    writeSavedFile(file.getName());
                    allFiles.add(file.getName());
                }
            }
        }
    }

    /**
     * Denne metode sørger for at læse filen "readfiles" for navne på filer som
     * er skrevet ned i filen.
     *
     * @return En liste af Strings, med navne på filer som allerede er blevet
     * læst.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<String> loadSavedFile() throws FileNotFoundException, IOException {
        FileReader fileReader = new FileReader(new File("data/readfiles.txt"));
        BufferedReader br = new BufferedReader(fileReader);
        List<String> readFile = new ArrayList();
        String line;

        while ((line = br.readLine()) != null) {
            readFile.add(line);
        }
        br.close();
        return readFile;
    }

    /**
     * Denne metode skriver filnavne ned i "readfiles.txt" filen.
     *
     * @param linesToWrite filnavne der skal skrives ned i "readfiles.txt".
     * @throws IOException
     */
    public void writeSavedFile(String linesToWrite) throws IOException {
        FileWriter writer = new FileWriter(new File("data/readfiles.txt"), true);
        BufferedWriter bw = new BufferedWriter(writer);
        bw.append(linesToWrite);
        bw.newLine();
        bw.close();
    }

    /**
     * Denne metode kigger i "readfiles.txt" og tjekker om filnavnene er skrevet
     * i "readfiles.txt" filen.
     *
     * @param filepaths Filnavn der tjekkes for.
     * @return boolean: True hvis et filnavn allerede står i "readfiles.txt".
     * False hvis et filnavn ikke allerede står i "readfiles.txt".
     * @throws IOException
     */
    public boolean hasFileBeenSaved(String filepaths) throws IOException {
        List<String> filesToRead = allFiles;
        if (filepaths.equals("readfiles.txt")) {
            return true;
        }
        for (String string : filesToRead) {
            if (string.equals(filepaths)) {
                return true;
            }
        }
        return false;
    }
}
