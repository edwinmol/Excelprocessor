package be.siteware.excelprocessor;

import java.util.List;

/**
 * Class that contains meta info about an imported bean
 */
public class MetaInfo {

    private Integer lineNumber;
    private List<ImportError> errors;

    public MetaInfo(Integer lineNumber, List<ImportError> errors) {
        this.lineNumber = lineNumber;
        this.errors = errors;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public List<ImportError> getErrors() {
        return errors;
    }
}
