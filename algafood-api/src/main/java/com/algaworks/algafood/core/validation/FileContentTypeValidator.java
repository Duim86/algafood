package com.algaworks.algafood.core.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

  private List<String> fileType;

  @Override
  public void initialize(FileContentType constraintAnnotation) {
    this.fileType = Arrays.asList(constraintAnnotation.allowed());
  }

  @Override
  public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
    return value == null
            || this.fileType.contains(value.getContentType());
  }
}
