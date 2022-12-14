package com.example.payroll.Services;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CSVFileStorageService {
    private static final Logger log = LoggerFactory.getLogger(CSVFileStorageService.class);
    private final Path root = Paths.get("uploads");

    public void init() throws RuntimeException {

        if( Files.isDirectory(root) )
            return;

        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public Path save(MultipartFile file,String append) throws RuntimeException {
        try {

            log.info("Saving.." + file.getOriginalFilename());
            Path path = this.root.resolve(file.getOriginalFilename()+append);
            Files.copy(file.getInputStream(), path);
            return path;

        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public Resource load(String filename) throws RuntimeException {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    public void delete(Path file) {

        try{
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Could not delete the file. Error: " + e.getMessage());
        }
    }

    public int isValidCSV(Path pathFile) throws RuntimeException {

        try {
            File file = pathFile.toFile();
            CSVReader reader = new CSVReader(new FileReader(file));

            reader.skip(1); // skip first line for titles
            String[] nextLine;
            boolean validCSV = true;
            int lines=0;

            while ((nextLine = reader.readNext()) != null) {

                // check number of columns and if name is empty
                if (nextLine.length != 2 || nextLine[0].trim().equals("")) {
                    validCSV = false;
                    break;
                }else{
                    // check if salary is a valid float
                    try {
                        Float.parseFloat(nextLine[1].trim());
                    } catch (NumberFormatException e) {
                        validCSV = false;
                        break;
                    }
                }
                lines++;
                log.info(nextLine[0] + "," + nextLine[1]);
            }

            reader.close();

            if(!validCSV) {
                file.delete();
                throw new CsvValidationException("Invalid values found in CSV.");
            }
            return lines;

        }catch (IOException e){
            throw new RuntimeException("Could not read the file. Error: " + e.getMessage());

        }catch(CsvValidationException e){
            throw new RuntimeException("Invalid CSV format. Error: " + e.getMessage());
        }
    }
}
