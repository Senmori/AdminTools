package net.senmori.admintools.asset.assets;

import net.senmori.admintools.Project;
import net.senmori.admintools.util.Directory;
import java.io.File;
import java.nio.file.Path;

/**
 * An asset that is on the local system.
 * This asset is <i>NOT</i> in the supplied executable.
 */
public class LocalFileAsset extends FileAsset {

    public static LocalFileAsset of(Project project, String child) {
        Directory workingDir = project.getWorkingDirectory();
        return new LocalFileAsset(new File(workingDir.getFile(), child));
    }

    public static LocalFileAsset of(Directory parent, File child) {
        return new LocalFileAsset(new File(parent.getFile(), child.getPath()));
    }

    /**
     * Create a new {@link LocalFileAsset}.
     *
     * @param file the file
     * @return the new {@link LocalFileAsset}
     */
    public static LocalFileAsset of(File file) {
        return new LocalFileAsset(file);
    }

    /**
     * Create a new {@link LocalFileAsset}.
     *
     * @param path the path to the asset
     * @return the new {@link LocalFileAsset}
     */
    public static LocalFileAsset of(String path) {
        return new LocalFileAsset(new File(path));
    }

    /**
     * Create a new {@link LocalFileAsset}.
     *
     * @param path the {@link Path} of the asset
     * @return the new {@link LocalFileAsset}
     */
    public static LocalFileAsset of(Path path) {
        return new LocalFileAsset(path.toFile());
    }

    private LocalFileAsset(File file) {
        super(file);
    }
}
