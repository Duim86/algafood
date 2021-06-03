package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

@RestController
@RequestMapping("/teste")
public class TesteController {

  @Autowired
  private CozinhaRepository cozinhaRepository;

  @Autowired
  private RestauranteRepository restauranteRepository;

  @GetMapping("/cozinhas/por-nome")
  public List<Cozinha> cozinhasPorNome(@RequestParam("nome") String nome) {
    return cozinhaRepository.findByNomeContaining(nome);
  }

  @GetMapping("/cozinhas/exists")
  public boolean cozinhaExists(String nome) {
    return cozinhaRepository.existsByNome(nome);
  }

//  @GetMapping("/restaurantes/por-taxa-frete")
//  public List<Restaurante> restaurantesPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal){
//    return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
//  }

  @GetMapping("/restaurantes/por-taxa-frete")
  public List<Restaurante> restaurantesPorTaxaFrete(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
    return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
  }

  @GetMapping("/restaurantes/por-nome")
  public List<Restaurante> restaurantesPorNome(String nome, Long cozinhaId){
//    return restauranteRepository.findByNomeContainingAndCozinhaId(nome, cozinhaId);
    return restauranteRepository.consultarPorNome(nome, cozinhaId);
  }

  @GetMapping("/restaurantes/primeiro-por-nome")
  public Optional<Restaurante> restaurantePrimeiroPorNome(String nome){
    return restauranteRepository.findFirstRestauranteByNomeContaining(nome);
  }

  @GetMapping("/restaurantes/top2-por-nome")
  public List<Restaurante> restaurantesTopDoisPorNome(String nome){
    return restauranteRepository.findTop2ByNomeContaining(nome);
  }

  @GetMapping("/restaurantes/count-por-cozinha")
  public int restaurantesCountPorCozinha(Long cozinhaId){
    return restauranteRepository.countByCozinhaId(cozinhaId);
  }

  @GetMapping("/restaurantes/com-frete-gratis")
  public List<Restaurante> restaurantesComFreteGratis(String nome){

    return restauranteRepository.findComFreteGratis(nome);
  }

  @GetMapping("/restaurantes/primeiro")
  public Optional<Restaurante> restaurantePrimeiro() {
    return restauranteRepository.buscarPrimeiro();
  }

  @GetMapping("/cozinhas/primeiro")
  public Optional<Cozinha> cozinhaPrimeiro() {
    return cozinhaRepository.buscarPrimeiro();
  }
}
