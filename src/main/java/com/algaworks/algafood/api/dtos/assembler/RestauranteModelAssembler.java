package com.algaworks.algafood.api.dtos.assembler;

import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

  @Autowired
  private ModelMapper modelMapper;

  public RestauranteModelAssembler() {
    super(RestauranteController.class, RestauranteModel.class);
  }


  @Override
  public RestauranteModel toModel(Restaurante restaurante) {

    var restauranteModel = createModelWithId(restaurante.getId(), restaurante);

    modelMapper.map(restaurante, restauranteModel);

    restauranteModel.add(linkTo(RestauranteController.class).withRel("restaurante"));

    return restauranteModel;
  }

  @Override
  public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
    return super.toCollectionModel(entities)
            .add(linkTo(RestauranteController.class).withSelfRel());
  }
}
