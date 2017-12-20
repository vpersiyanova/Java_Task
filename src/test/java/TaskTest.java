import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class TaskTest {
    private static final String ROOT_STR = System.getProperty("user.dir") + File.separator + "src" +
            File.separator + "test" + File.separator + "resources" + File.separator + "root";

    private static final Path ROOT_PATH = Paths.get(ROOT_STR);

    @Before
    public void setUp() throws IOException {
        // A
        final Path firstLevelDirA = Files.createTempDirectory(ROOT_PATH, "A");
        final Path secondLevelDirAA = Files.createTempDirectory(firstLevelDirA, "AA");
        final Path thirdLevelDirAAA = Files.createTempDirectory(secondLevelDirAA, "AAA");

        Files.createTempFile(firstLevelDirA, "A", "");
        Files.createTempFile(secondLevelDirAA, "AA", "");
        Files.createTempFile(thirdLevelDirAAA, "AAA", "");

        // B
        final Path firstLevelDirB = Files.createTempDirectory(ROOT_PATH, "B");
        final Path secondLevelDirBB = Files.createTempDirectory(firstLevelDirB, "BB");
        final Path thirdLevelDirBBB = Files.createTempDirectory(secondLevelDirBB, "BBB");

        Files.createTempFile(secondLevelDirBB, "B", "");
        Files.createTempFile(secondLevelDirBB, "BB", "");
        Files.createTempFile(thirdLevelDirBBB, "BBB", "");

        // C
        final Path firstLevelDirC = Files.createTempDirectory(ROOT_PATH, "C");

        Files.createTempFile(firstLevelDirC, "C", "");
        Files.createTempFile(firstLevelDirC, "CC", "");
        Files.createTempFile(firstLevelDirC, "CCC", "");
    }

    @Test
    public void testNumberOfFilesInRoot() throws IOException {
        Task.filesUp(ROOT_PATH);

        final long numberOfFiles = Files.walk(ROOT_PATH).filter(Files::isRegularFile).count();

        assertEquals(10, numberOfFiles);
    }

    @Test
    public void testNumberOfFoldersInRoot() throws IOException {
        Task.filesUp(ROOT_PATH);

        final long numberOfFolders = Files.walk(ROOT_PATH)
                .filter(path -> !path.equals(ROOT_PATH))
                .filter(Files::isDirectory)
                .count();

        assertEquals(0, numberOfFolders);
    }

    @Test
    public void testNumberOfFileStartWithA() throws IOException {
        Task.filesUp(ROOT_PATH);

        final long numberOfFilesStartWithA = Files.walk(ROOT_PATH)
                .filter(Files::isRegularFile)
                .filter(path -> path.getFileName().toString().startsWith("A"))
                .count();

        assertEquals(3, numberOfFilesStartWithA);
    }

    @After
    public void clear() throws IOException {
        Files.walk(ROOT_PATH)
                .filter(Files::isRegularFile)
                .filter(file -> !file.getFileName().toString().startsWith("rootFile"))
                .forEach(file -> {
                    try {
                        Files.delete(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

    }

}