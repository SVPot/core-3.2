import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        String path1 = "C:/Games/savegames/savegame1.dat";
        String path2 = "C:/Games/savegames/savegame2.dat";
        String path3 = "C:/Games/savegames/savegame3.dat";
        String zipFile = "C:/Games/savegames/savegames.zip";

        GameProgress gameProgress1 = new GameProgress(100, 50, 1, 1);
        GameProgress gameProgress2 = new GameProgress(90, 150, 2, 99);
        GameProgress gameProgress3 = new GameProgress(950, 250, 3, 168);

        saveGame(path1, gameProgress1);
        saveGame(path2, gameProgress2);
        saveGame(path3, gameProgress3);

        List<String> pathFiles = new ArrayList<>();
        pathFiles.add(path1);
        pathFiles.add(path2);
        pathFiles.add(path3);

        zipFiles(zipFile, pathFiles);

        deleteFiles(pathFiles);
    }

    public static boolean saveGame(String path, GameProgress gameProgress) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(path))) {
            objectOutputStream.writeObject(gameProgress);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean zipFiles(String zipFile, List<String> pathFiles) {
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            for (String pf : pathFiles) {
                FileInputStream fis = new FileInputStream(pf);
                ZipEntry zipEntry = new ZipEntry(pf);
                zos.putNextEntry(zipEntry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zos.write(buffer);
                zos.closeEntry();
                fis.close();
            }
            zos.close();
            fos.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean deleteFiles(List<String> pathFiles) {
        try {
            for (String pf : pathFiles) {
                if (Files.deleteIfExists(Paths.get(pf))) {
                    System.out.println("Файл " + pf + " удалён");
                }
            }
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
