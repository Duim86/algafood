package com.algaworks.algafood.core.modelMapper;

import com.algaworks.algafood.api.RestauranteModel;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class modelMapperConfig {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

//  @Bean
//  public ModelMapper modelMapper() {
//    var modelMapper = new ModelMapper();
//    modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
//            .addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);
//
//    return modelMapper;
//  }
}
