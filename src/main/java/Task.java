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
        removeAllFilesToRoot(root);
        deleteAllChildrenDirectories(root);
    }

    private static void removeAllFilesToRoot(Path root) throws IOException {
            Files.walk(root)
                    .filter(path -> Files.isRegularFile(path))
                    .forEach(file -> {
                        try {
                            Files.copy(file, Paths.get(root.toString() + File.separator + file.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                            Files.delete(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
    }

    private static void deleteAllChildrenDirectories(Path root) throws IOException {
        Files.walk(root)
                .filter(path -> Files.isDirectory(path))
                .forEach(directory -> {
                    try {
                        Files.delete(directory);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }
}