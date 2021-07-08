package KonwersjaLiter;

import java.io.File;

public class DirConverter
{
    private final FileConverter fileConverter;

    public DirConverter(FileConverter fileConverter) {
        this.fileConverter = fileConverter;
    }

    public void convertFiles(String pathToDirectory, String extension) {
        File directory = new File(pathToDirectory);
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null && files.length != 0) {
                for (File file : files) {
                    if (file.getName().endsWith(extension)) {
                        fileConverter.convert(file.getAbsolutePath());
                    }
                }
            }
        }
    }
}
