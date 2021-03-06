package pkg2ndsemesterexamproject.dal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadConfig {

    /**
     * denne metode læser en afsides fil, der tjekker filen for offsets på vores
     * departments, så vores departments kan se task før de må opstartes.
     *
     * @param name Navnet på Department der skal hentes Offset fra.
     * @return offset
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static int getOffsetFromDepartmentName(String name)
            throws FileNotFoundException, IOException {
        int offset = 0;
        File file = new File("data/config.offset");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = "";
        while ((line = br.readLine()) != null) {
            if (line.contains(name)) {
                int index = line.indexOf("=") + 1;
                offset = Integer.parseInt(line.substring(index));
            }
        }
        return offset;
    }
}
