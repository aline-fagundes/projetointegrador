package br.com.passaporteclio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.passaporteclio.adapter.DozerConverter;
import br.com.passaporteclio.domain.dto.CriaPresencaDto;
import br.com.passaporteclio.domain.dto.PresencaDto;
import br.com.passaporteclio.domain.entity.Presenca;
import br.com.passaporteclio.exception.ResourceNotFoundException;
import br.com.passaporteclio.repository.PresencaRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PresencaService {

	private PresencaRepository repository;

	public PresencaDto buscarPorId(Long id) {
		var presencaEntity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Presença não encontrada!"));
		return DozerConverter.parseObject(presencaEntity, PresencaDto.class);
	}

	public Page<PresencaDto> buscarTodas(Pageable pageable) {
		var page = repository.findAll(pageable);
		return page.map(presenca -> DozerConverter.parseObject(presenca, PresencaDto.class));
	}

	public Page<PresencaDto> buscarPorMuseu(Long museuId, Pageable paginacao) {
		var page = repository.findByMuseuId(museuId, paginacao);
		return page.map(presenca -> DozerConverter.parseObject(presenca, PresencaDto.class));
	}

	public CriaPresencaDto inserir(CriaPresencaDto criacaoPresencaDto) {
		var presencaEntity = DozerConverter.parseObject(criacaoPresencaDto, Presenca.class);
		var presencaGravada = DozerConverter.parseObject(repository.save(presencaEntity),
				CriaPresencaDto.class);

		return presencaGravada;
	}
}
