package com.example.crud;

import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class AzureBlobService {

  private final BlobContainerClient containerClient;

  public AzureBlobService(@Value("${azure.storage.connection-string}") String connectionString,
      @Value("${azure.storage.container-name}") String containerName) {
    BlobServiceClient serviceClient = new BlobServiceClientBuilder()
        .connectionString(connectionString)
        .buildClient();

    this.containerClient = serviceClient.getBlobContainerClient(containerName);
    if (!containerClient.exists()) {
      containerClient.create();
    }
  }

  public String uploadFile(MultipartFile file) throws IOException {
    String blobName = UUID.randomUUID() + "-" + file.getOriginalFilename();
    BlobClient blobClient = containerClient.getBlobClient(blobName);

    blobClient.upload(file.getInputStream(), file.getSize(), true);

    // Optional: set content type
    blobClient.setHttpHeaders(new BlobHttpHeaders()
        .setContentType(file.getContentType()));

    return blobClient.getBlobUrl(); // return public URL if the blob is accessible
  }
}
