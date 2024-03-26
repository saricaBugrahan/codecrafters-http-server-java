import java.io.*;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHandler {

    private static String folderPath = "./";
    private static String folder;

    public static Set<String> listOfFilesInDir(){
        if(folderPath == null)
            return null;

        return Stream.of(Objects.requireNonNull(new File(folderPath).listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());

    }
    public static byte[] search() throws IOException {
        Set<String> files = listOfFilesInDir();
        if(files == null)
            return null;

        if(files.contains(folder)){
            System.out.println(folder);
        }
        else{
            System.out.println("File not found");
            return null;
        }
        FileInputStream fileInputStream = new FileInputStream(new File(folderPath,folder));
        byte[] data = fileInputStream.readAllBytes();
        fileInputStream.close();
        return data;
    }

    public static void write(String data) throws IOException {
        FileWriter fileWriter = new FileWriter(folderPath+"/"+folder);
        fileWriter.write(data);
        fileWriter.close();
    }



    public static void setFolderPath(String folderPath) {
        FileHandler.folderPath = folderPath;
    }

    public static void setFolder(String folder) {
        FileHandler.folder = folder;
    }




}
