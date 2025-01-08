package com.arst.check.business.concretes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

import com.arst.check.business.abstracts.TemplateService;

@Service
public class TemplateManager implements TemplateService {

	@Override
    public List<String> listTemplateFiles() {
        List<String> files = new ArrayList<>();
        File folder = new File("src/main/resources/templates");
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().endsWith(".html")) {
                    files.add(file.getName().substring(0, file.getName().length() - 5));  // Sadece .html dosyalarını ekliyoruz
                }
            }
        }
        return files;
    }

	@Override
	public void createTemplateFile(String fileName, String content) throws IOException {
		
        // src/main/resources/templates dizininin yolu
        Path path = Paths.get("src/main/resources/templates/" + fileName+".html");

        // Eğer dosya zaten varsa, bir şey yapmadan geri dönebiliriz
        if (!Files.exists(path)) {
            // Dosyayı yazıyoruz
            Files.write(path, content.getBytes());
        } else {
            System.out.println("Dosya zaten var: " + fileName);
        }
		
	}

    // Mevcut dosyanın içeriğini güncelleme
    public void updateTemplateFile(String fileName, String newContent) throws IOException {
        // Dosya yolunu belirtiyoruz
        Path path = Paths.get("src/main/resources/templates/" + fileName+ ".html");

        // Dosyanın var olup olmadığını kontrol ediyoruz
        if (Files.exists(path)) {
            // Mevcut dosyanın içeriğini yeni içerik ile değiştiriyoruz
            Files.write(path, newContent.getBytes());
        } else {
            throw new IOException("Dosya bulunamadı: " + fileName);
        }

	
	

}
}
