package com.arst.check.webApi.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arst.check.business.abstracts.TemplateService;
import com.arst.check.business.request.TemplateRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/templates")
@RequiredArgsConstructor
public class TemplateController {

	
    
    private final TemplateService templateService;

    @GetMapping("/getall")
    public List<String> getTemplateFiles() {
        return templateService.listTemplateFiles();  // Template dosyalarını döndüren servisi çağırıyoruz
    }
    
    
    // Dosya oluşturma
    @PostMapping("/create")
    public String createTemplate(@RequestBody TemplateRequest request) {
        try {
            // İstenilen dosyayı oluşturuyoruz
            templateService.createTemplateFile(request.fileName(), request.content());
            return "Dosya başarıyla oluşturuldu!";
        } catch (IOException e) {
            return "Hata: " + e.getMessage();
        }
    }
    
    
    // Mevcut dosyanın içeriğini güncelleme
    @PostMapping("/update")
    public String updateTemplate(@RequestBody TemplateRequest request) {
        try {
            // Dosya içeriğini güncelliyoruz
            templateService.updateTemplateFile(request.fileName(), request.content());
            return "Dosya başarıyla güncellendi!";
        } catch (IOException e) {
            return "Hata: " + e.getMessage();
        }
    }
	
}
