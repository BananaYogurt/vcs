import java.io.File;

/**
 * Created by nice on 2017/8/24.
 */
public class MyFile {

    private File file;

    public MyFile(String file_name) {
        this.file = new File(file_name);
    }

    public synchronized File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
