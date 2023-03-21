package com.gardeners.app.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.UUID;

@Service
public class FileStorageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileStorageService.class);

    private final Path root = Paths.get("./uploads");

    public void init() {
        try {
            Files.createDirectories(root);
            LOGGER.debug("File root directory initialized");
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public String save(MultipartFile file, String username) {
        try {
            Files.createDirectories(this.root.resolve(username));
            LOGGER.debug("User file directory initialized");
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize user folder for upload!");
        }

        String originalFileExtension = file.getOriginalFilename().substring((file.getOriginalFilename().lastIndexOf('.') + 1));
        //when saving a file - overwrite its name (should be unique) to avoid duplicates (UUID + random int)
        Random random = new Random();
        String fileName = UUID.randomUUID() + "_" + random.nextInt(1000) + "." + originalFileExtension;
        LOGGER.debug("Saving a file: " + fileName);
        try {
            //TODO: use a compression to speed up loading times
            //save file in logged in user folder
            Files.copy(file.getInputStream(), this.root.resolve(username + "/" + fileName));
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }
            throw new RuntimeException(e.getMessage());
        }
        return fileName;
    }

    public Resource load(String filename, String username) {
        return loadFile(username + "/" + filename);
    }

    public Resource load(String filename) {
        return loadFile(filename);
    }

    private Resource loadFile(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                LOGGER.error("Could not read a file! Filename: " + file.getFileName().toString());
                //throw new RuntimeException("Could not read the file! Filename: " + file.getFileName().toString());
                return null;
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public boolean delete(String filename, String username) {
        try {
            Path file = root.resolve(filename.replaceAll("/images/", ""));
            boolean fileWasDeleted = Files.deleteIfExists(file);
            if(fileWasDeleted) LOGGER.debug("Old file was deleted. Filename: " + filename);
            else LOGGER.error("Old file not deleted!");
            return fileWasDeleted;
        } catch (IOException e) {
            LOGGER.error("file at " + filename + " does not exist");
            //throw new RuntimeException("Error: " + e.getMessage());
            return false;
        }
    }

/*    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }*/
}
