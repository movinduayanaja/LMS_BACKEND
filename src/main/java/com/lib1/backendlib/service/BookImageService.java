package com.lib1.backendlib.service;

import org.springframework.web.multipart.MultipartFile;

public interface BookImageService {
    String uploadCover(Integer bookId, MultipartFile image);
}