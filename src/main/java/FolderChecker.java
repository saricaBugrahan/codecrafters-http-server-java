import javax.imageio.ImageIO;
import java.io.*;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FolderChecker {

    private static String folderPath;
    private static String folder;

    public static Set<String> listOfFilesInDir(){
        if(folderPath == null)
            return null;

        return Stream.of(new File(folderPath).listFiles())
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
        return data;
    }

    public static String getFolderPath() {
        return folderPath;
    }

    public static void setFolderPath(String folderPath) {
        FolderChecker.folderPath = folderPath;
    }

    public String getFolder() {
        return folder;
    }

    public static void setFolder(String folder) {
        FolderChecker.folder = folder;
    }




}
