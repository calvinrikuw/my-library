package com.example.library.service.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.UUID;

@Component
public class LocalFileStorage {

    private final Path root;
    private final String publicPrefix;

    public LocalFileStorage(@Value("${app.upload.root:uploads}") String rootDir,
                            @Value("${app.upload.public-prefix:/static}") String publicPrefix) {
        this.root = Path.of(rootDir);
        this.publicPrefix = publicPrefix;
    }

    public String saveCover(CoverData cover) throws Exception {
        if (cover == null || cover.getBytes().length == 0) {
            return null;
        }

        // 按 yyyyMM 格式定义目录
        LocalDate now = LocalDate.now();
        String dir = String.format("%d%02d", now.getYear(), now.getMonthValue());

        Path folder = root.resolve(dir);
        Files.createDirectories(folder);

        // 获取文件扩展名
        String ext = getExtension(cover.getOriginalFilename());
        String name = UUID.randomUUID() + (ext == null ? "" : ext.toLowerCase());
        Path target = folder.resolve(name);
        Files.write(target, cover.getBytes());

        // 返回文件的对外访问路径
        String prefix = publicPrefix.endsWith("/")
                ? publicPrefix.substring(0, publicPrefix.length() - 1)
                : publicPrefix;

        return prefix + "/" + dir + "/" + name;
    }

    private static String getExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int i = filename.lastIndexOf('.');
        return (i >= 0) ? filename.substring(i) : null;
    }
}
