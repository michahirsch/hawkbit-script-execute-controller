package org.michahirsch.hawkbit.ddi.client.script;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.michahirsch.hawkbit.ddi.client.ScriptExecutionException;
import org.michahirsch.hawkbit.ddi.client.script.ScriptExecutorProperties.ExecutorProperties;

import com.google.common.io.Files;

public class ConfigurableScriptExecutorService implements ScriptExecutorService {

    private static final String EXEC_LINE_PLACEHOLDER = "{scriptfile}";

    private final ScriptExecutorProperties scriptExecutorProperties;

    public ConfigurableScriptExecutorService(final ScriptExecutorProperties scriptExecutorProperties) {
        this.scriptExecutorProperties = scriptExecutorProperties;
    }

    @Override
    public void execute(final File file) {
        final String fileExtension = Files.getFileExtension(file.getName());

        final Optional<ExecutorProperties> executorPropertiesForExtension = findExecutorPropertiesForExtension(
                fileExtension);
        executorPropertiesForExtension.orElseThrow(() -> new ScriptExecutionException(-1,
                "No script execution properties found for extension " + fileExtension));

        final String execLine = replacePlaceholderInExecLine(executorPropertiesForExtension.get().getExecLine(), file);
        
        try {
            final Process p = Runtime.getRuntime().exec(execLine);
            p.waitFor();
        } catch (final IOException | InterruptedException e) {
            throw new ScriptExecutionException(-1, e.getMessage());
        }
    }

    private String replacePlaceholderInExecLine(final String execLine, final File scriptFile) {
        return execLine.replace(EXEC_LINE_PLACEHOLDER, scriptFile.getAbsolutePath());
    }

    private Optional<ExecutorProperties> findExecutorPropertiesForExtension(final String extension) {
        return scriptExecutorProperties.getExecutors().values().stream()
                .filter(executor -> executor.getFileextensions().contains(extension)).findFirst();
    }
}
