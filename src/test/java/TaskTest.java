import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class TaskTest {
    private static final String TEST_ROOT = System.getProperty("user.dir") + File.separator + "src" +
            File.separator + "test" + File.separator + "resources" + File.separator + "root";

    @Test
    public void testFilesUp() throws IOException {
        Task.filesUp(Paths.get(TEST_ROOT));
    }

}