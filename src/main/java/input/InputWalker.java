package input;

import config.Props;
import exceptions.ConversionInterruptedException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;

public class InputWalker {

    private static Path currentPath;
    private static ArrayList<Path> paths = new ArrayList<>();

    public static void walk() {
        try (Stream<Path> paths = Files.walk(Paths.get(Props.getInputPath()))) {
            paths.filter(path -> path.toString().endsWith(".txt"))
                    .forEach(filePath -> InputWalker.paths.add(filePath));
        } catch (IOException e) {
            throw new ConversionInterruptedException("Ошибка при чтении директории " + Props.getInputPath());
        }
    }

    public static ArrayList<Path> getPaths() {
        return paths;
    }

    public static Path getNextPath() {
        Iterator<Path> iterator = paths.iterator();
        if (iterator.hasNext()) {
            currentPath = iterator.next();
            iterator.remove();
        } else {
            currentPath = null;
        }
        return currentPath;
    }

    public static Path getCurrentPath() {
        return currentPath;
    }

    public static String getCurrentFileName() {
        return currentPath.getFileName().toString().replace(".txt", "");
    }

    public static boolean hasNextPath() {
        return !paths.isEmpty();
    }
}
