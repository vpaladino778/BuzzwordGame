package components;

import data.Profile;

import java.io.IOException;
import java.nio.file.Path;

/**
 * This interface provides the structure for file components in
 * our applications. Note that by doing so we make it possible
 * for customly provided descendent classes to have their methods
 * called from this framework.
 *
 * @author Richard McKenna, Ritwik Banerjee
 */
public interface AppFileComponent {

    void saveData(AppDataComponent data, Path filePath) throws IOException;

    Profile loadData(Path filePath) throws IOException;

    void exportData(AppDataComponent data, Path filePath) throws IOException;
}
