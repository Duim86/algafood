package com.algaworks.algafood.core.modelMapper;

import com.algaworks.algafood.api.model.EnderecoModel;
import com.algaworks.algafood.api.model.input.ItemPedidoInput;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;

@Configuration
public class modelMapperConfig {

  @Bean
  public ModelMapper modelMapper() {
    var modelMapper = new ModelMapper();

    modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
            .addMappings(mapper -> mapper.skip(ItemPedido::setId));

    var enderecoToEnderecoModelTypeMap =
            modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);

    enderecoToEnderecoModelTypeMap.<String>addMapping(
            src -> src.getCidade().getEstado().getNome(),
            (dest, value) -> dest.getCidade().setEstado(value));

    return modelMapper;
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
