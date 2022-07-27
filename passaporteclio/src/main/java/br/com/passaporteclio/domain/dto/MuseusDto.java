package br.com.passaporteclio.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = false)
@Data
public class MuseusDto extends RepresentationModel<MuseusDto> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@NotBlank
	@Size(max=100)
	private String nome;

	@NotBlank
	@Size(max=1000)
	private String descricaoMuseu;

	@NotBlank
	@Size(max=500)
	private String funcionamentoMuseu;

	@NotBlank
	private String urlFoto;

	@NotBlank
	private String urlSite;

	@NotBlank
	private String urlInstagram;

	@Valid
	private EnderecoDto endereco;
}
