package net.senmori.admintools.config.spec;

import com.electronwill.nightconfig.core.ConfigSpec;
import net.senmori.admintools.Project;

public class ProjectConfigSpec extends ConfigSpec {

    private ProjectConfigSpec(Project project) {
        //isCorrect( project.getConfig() );
    }
}
