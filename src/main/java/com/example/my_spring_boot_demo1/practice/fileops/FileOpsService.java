package com.example.my_spring_boot_demo1.practice.fileops;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class FileOpsService {

    @Value("${file.path}")
    private String basePath;

    @Value("${file.folder.source}")
    private String sourcePath;

    @Value("${file.folder.target}")
    private String targetPath;

    private static final Pattern SOURCE_FILE_NAME_REGEX =
            Pattern.compile("^DATA_(\\d{6})\\d{2}_\\d{2}\\.txt$"); //DATA_yyyyMMdd_num.txt

    public Path locateSourceDirectory() {
        return Path.of(basePath)
                .resolve(sourcePath);
    }

    public Path locateTargetDirectory(String targetYearMonth) {
        return Path.of(basePath)
                .resolve(targetPath)
                .resolve(targetYearMonth);
    }

    public void copyFileFromSourceToTarget() {
        Path sourceDirectory = locateSourceDirectory();
        log.info("[copyFileFromSourceToTarget] source directory: {}", sourceDirectory.toString());

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceDirectory)) {
            for (Path file : stream) {
                String fileName = file.getFileName().toString();
                Matcher matcher = SOURCE_FILE_NAME_REGEX.matcher(fileName);
                if (matcher.matches()) {
                    log.info("[copyFileFromSourceToTarget] file: {} start copy.", fileName);

                    String targetYearMonth = matcher.group(1);
                    Path targetDirectory = locateTargetDirectory(targetYearMonth);
                    log.info("[copyFileFromSourceToTarget] target directory: {}", targetDirectory.toString());
                    Files.createDirectories(targetDirectory);

                    Path targetFile = targetDirectory.resolve(fileName);
                    Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
                    log.info("[copyFileFromSourceToTarget] file copy done, copy to: {}", targetFile);
                }
            }
        } catch (IOException e) {
            log.error("[copyFileFromSourceToTarget] error message: ", e);
        }
    }
}
