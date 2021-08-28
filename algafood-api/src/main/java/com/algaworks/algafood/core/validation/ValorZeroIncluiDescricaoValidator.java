package com.algaworks.algafood.core.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.math.BigDecimal;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {

  private String valorField;
  private String descricaoField;
  private String descricaoObrigatoria;

  @Override
  public void initialize(ValorZeroIncluiDescricao constraintAnnotation) {
    this.valorField = constraintAnnotation.valorField();
    this.descricaoField = constraintAnnotation.descricaoField();
    this.descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
  }

  @Override
  public boolean isValid(Object objetoValidacao, ConstraintValidatorContext context) {
    var valido = true;
    String descricao = null;
    BigDecimal valor = null;

    try {
      var valorProperty = BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), valorField);
      if(valorProperty != null) {
        valor = (BigDecimal) valorProperty.getReadMethod().invoke(objetoValidacao);
      }

      var descricaoProperty = BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), descricaoField);
      if(descricaoProperty != null) {
        descricao = (String) descricaoProperty.getReadMethod().invoke(objetoValidacao);
      }

      if(valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null ) {
        valido = descricao.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase());
      }
      return valido;
    } catch (Exception e) {
      throw new ValidationException(e);
    }
  }
}
