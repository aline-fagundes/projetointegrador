package br.com.passaporteclio.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class AvaliacaoDto extends RepresentationModel<AvaliacaoDto> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@NotBlank
	private int nota;

	@NotBlank
	private String avaliacao;
	
	@NotBlank
	private MuseusDto museu;

}