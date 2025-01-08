package com.arst.check.business.abstracts;

import java.io.IOException;
import java.util.List;

public interface TemplateService {

    public List<String> listTemplateFiles();
    public void createTemplateFile(String fileName, String content) throws IOException;
    public void updateTemplateFile(String fileName, String newContent) throws IOException;

}
