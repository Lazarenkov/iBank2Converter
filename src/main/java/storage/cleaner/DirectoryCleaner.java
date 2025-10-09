package storage.cleaner;

import config.Props;
import storage.walker.InputWalker;

import java.io.File;
import java.nio.file.Path;

public class DirectoryCleaner {

    public static void deleteInputFiles() {
        if (Props.isClearInput()) {
            for (Path path : InputWalker.getPaths()) {
                File file = new File(path.toUri());
                file.delete();
            }
        }
    }

    public static void clearOutputDirectories() {
        if (Props.isClearOutput()) {
            Path[] dirs = new Path[]{Path.of(Props.getOutputPath()), Path.of(Props.getDCTOutputPath())};
            for (Path dir : dirs) {
                File directory = new File(dir.toUri());
                File[] files = directory.listFiles();
                for (File file : files) {
                    file.delete();
                }
            }
        }
    }
}