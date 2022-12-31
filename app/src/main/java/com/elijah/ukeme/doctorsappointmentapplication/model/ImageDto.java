package com.elijah.ukeme.doctorsappointmentapplication.model;

public class ImageDto {

    private String fileName;
    private String downloadUrl;
    private String fileType;
    private long fileSize;

    public String getFileName() {
        return fileName;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getFileType() {
        return fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public ImageDto() {
    }
}
