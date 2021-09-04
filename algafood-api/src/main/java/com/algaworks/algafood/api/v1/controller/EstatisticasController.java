package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.openapi.controller.EstatisticasControllerOpenApi;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.domain.service.VendaReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.algaworks.algafood.core.security.CheckSecurity.Estatisticas;

@RestController
@RequestMapping("/v1/estatisticas")
public class EstatisticasController implements EstatisticasControllerOpenApi {

  @Autowired
  private VendaQueryService vendaQueryService;

  @Autowired
  private VendaReportService vendaReportService;

  @Autowired
  private AlgaLinks algaLinks;

  public static class EstatisticasModel extends RepresentationModel<EstatisticasModel> {
  }

  @Override
  @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
  @Estatisticas.PodeConsultar
  public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro,
                                                  @RequestParam(required = false, defaultValue = "+00:00") String timeOffset){
    return vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
  }

  @Override
  @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
  @Estatisticas.PodeConsultar
  public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro,
                                                          @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) throws JRException {

    byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filtro, timeOffset);

    var headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");

    return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_PDF)
            .headers(headers)
            .body(bytesPdf);
  }

  @Override
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @Estatisticas.PodeConsultar
  public EstatisticasModel estatisticas() {
    var estatisticasModel = new EstatisticasModel();

    estatisticasModel.add(algaLinks.linkToEstatisticasVendasDiarias("vendas-diarias"));

    return estatisticasModel;
  }
}
