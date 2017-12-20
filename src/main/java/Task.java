import java.io.File;
import java.io.IOException;
import java.nio.file.*;

class Task {
    /**
     * Написать функцию которая пройдет по всем каталогам внутри root и поднимет все файлы существующие в подкаталогах в root.
     * Все встречающиеся подкаталоги - удалить после того как все файлы из них будут перемещены в root
     * Написать Junit тест на работу этой функции
     * Можно создавать дополнительные функции в рамках данного класса
     * Использовать любые средства из JDK 8
     *
     * @param root - starting directory
     */
    static void filesUp(Path root) throws IOException {
        removeAllFilesInTreeToRoot(root);
        deleteAllDirectories(root);
    }

    private static void removeAllFilesInTreeToRoot(Path root) throws IOException {
        Files.walk(root)
                .filter(path -> Files.isRegularFile(path))
                .filter(file -> !file.getParent().equals(root))
                .forEach(file -> {
                    try {
                        Files.copy(file, Paths.get(root.toString() + File.separator + file.getFileName()),
                                StandardCopyOption.REPLACE_EXISTING);
                        Files.deleteIfExists(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    private static void deleteAllDirectories(Path root) throws IOException {
        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (!dir.equals(root)) {
                    Files.deleteIfExists(dir);
                }
                return FileVisitResult.CONTINUE;
            }
        });

    }
}