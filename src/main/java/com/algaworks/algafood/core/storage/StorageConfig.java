package com.algaworks.algafood.core.storage;

import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.infrastructure.storage.CloudFotoStorageService;
import com.algaworks.algafood.infrastructure.storage.LocalFotoStorageService;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class StorageConfig {

  private final StorageProperties storageProperties;

  @Autowired
  public StorageConfig(StorageProperties storageProperties) {
    this.storageProperties = storageProperties;
  }

  @Bean
  public Cloudinary cloudinary() {
    Map<String, String> valuesMap = new HashMap<>();
    valuesMap.put("cloud_name", storageProperties.getCloud().getCloudName());
    valuesMap.put("api_key", storageProperties.getCloud().getApiKey());
    valuesMap.put("api_secret", storageProperties.getCloud().getApiSecret());
    return new Cloudinary(valuesMap);
  }

  @Bean
  public FotoStorageService fotoStorageService() {
    if(StorageProperties.TipoStorage.CLOUDINARY.equals(storageProperties.getTipo())){
      return new CloudFotoStorageService(cloudinary(), storageProperties);
    } else {
      return new LocalFotoStorageService(storageProperties);
    }
  }
}
