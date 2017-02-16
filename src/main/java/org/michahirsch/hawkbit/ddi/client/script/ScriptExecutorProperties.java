package org.michahirsch.hawkbit.ddi.client.script;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("org.michahirsch.script")
public class ScriptExecutorProperties {

    private Map<String, ExecutorProperties> executors = new HashMap<>();
    
    public Map<String, ExecutorProperties> getExecutors() {
        return executors;
    }

    public void setExecutors(final Map<String, ExecutorProperties> executors) {
        this.executors = executors;
    }

    public static class ExecutorProperties {

        private List<String> fileextensions;
        private String execLine;

        public List<String> getFileextensions() {
            return fileextensions;
        }

        public void setFileextensions(final List<String> fileextensions) {
            this.fileextensions = fileextensions;
        }

        public String getExecLine() {
            return execLine;
        }

        public void setExecLine(final String execLine) {
            this.execLine = execLine;
        }
    }
}
