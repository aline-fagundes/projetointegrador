package br.com.passaporteclio.service;

import br.com.passaporteclio.domain.dto.EnderecoDto;
import br.com.passaporteclio.domain.dto.MuseusDto;
import br.com.passaporteclio.domain.dto.NotaMediaMuseuDto;
import br.com.passaporteclio.domain.entity.Endereco;
import br.com.passaporteclio.domain.entity.Museus;
import br.com.passaporteclio.exception.ResourceNotFoundException;
import br.com.passaporteclio.repository.MuseusRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class MuseusServiceTest {
	
	@InjectMocks
	private MuseusService museuService;
	
	@Mock
	private MuseusRepository museuRepository;
	
	@Before
	public void setUp() { MockitoAnnotations.openMocks(this); }
	
	public MuseusDto mockMuseusDtoWithoutId() {
		return MuseusDto.builder()
				.descricaoMuseu("Museu Teste")
				.funcionamentoMuseu("funcionamento")
				.nome("Museu Teste")
				.urlFoto("urlFoto")
				.urlInstagram("url insta")
				.urlSite("url Site")
				.endereco(EnderecoDto.builder()
						.bairro("bairro 1")
						.cep("14807406")
						.cidade("Araraquara")
						.estado("SP")
						.numero(10)
						.pais("Brasil")
						.build())
				.build();
	}
	
	public Museus mockMuseusEntity() {
		return Museus.builder()
				.id(1L)
				.descricaoMuseu("Museu Teste")
				.funcionamentoMuseu("funcionamento")
				.nome("Museu Teste")
				.urlFoto("urlFoto")
				.urlInstagram("url insta")
				.urlSite("url Site")
				.endereco(Endereco.builder()
						.id(1L)
						.bairro("bairro 1")
						.cep("14807406")
						.cidade("Araraquara")
						.estado("SP")
						.numero(10)
						.pais("Brasil")
						.build())
				.build();
	}

	public Page<Museus> mockPageMuseus(){
		return new PageImpl<>(Collections.singletonList(mockMuseusEntity()));
	}

	@Test
	public void testInserir() {
		MuseusDto museuToInsert = mockMuseusDtoWithoutId();
		
		when(museuRepository.save(any())).thenReturn(mockMuseusEntity());
		
		MuseusDto museuReturned = museuService.inserir(museuToInsert);

		Assert.assertEquals(1L, (long) museuReturned.getId());
	}

	@Test
	public void testBuscarPorIdComSucesso(){
		when(museuRepository.findById(any())).thenReturn(Optional.of(mockMuseusEntity()));

		MuseusDto museuReturned = museuService.buscarPorId(1L);

		Assert.assertNotNull(museuReturned);

	}
	@Test(expected = ResourceNotFoundException.class)
	public void testBuscarPorIdSemSucesso(){
		when(museuRepository.findById(any())).thenReturn(Optional.empty());

		museuService.buscarPorId(1L);
	}
	@Test
	public void testBuscarTodos(){
		Pageable pageable = PageRequest.of(0, 8);

		when(museuRepository.findAll(pageable)).thenReturn(mockPageMuseus());

		Page<MuseusDto> museusDtoPage = museuService.buscarTodos(pageable);

		Assert.assertEquals(1L, museusDtoPage.getTotalElements());
	}

	@Test
	public void testBuscarPorNome(){
		Pageable pageable = PageRequest.of(0, 8);

		when(museuRepository.findByNome("Teste",pageable)).thenReturn(mockPageMuseus());

		Page<MuseusDto> museusDtoPage = museuService.buscarPorNome("Teste",pageable);

		Assert.assertEquals(1L, museusDtoPage.getTotalElements());
	}

	@Test
	public void testDeletarComSucesso(){
		when(museuRepository.findById(any())).thenReturn(Optional.of(mockMuseusEntity()));
		doNothing().when(museuRepository).delete(any());

		museuService.deletar(1L);

		verify(museuRepository).delete(any());
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testDeletarSemSucesso(){
		when(museuRepository.findById(any())).thenReturn(Optional.empty());
		doNothing().when(museuRepository).delete(any());

		museuService.deletar(1L);

	}

	@Test
	public void testAtualizarComSucesso(){
		when(museuRepository.findById(1L)).thenReturn(Optional.of(mockMuseusEntity()));
		when(museuRepository.save(any())).thenReturn(mockMuseusEntity());


		MuseusDto museuReturned = museuService.atualizar(1L,mockMuseusDtoWithoutId());

		Assert.assertEquals(1L, (long) museuReturned.getId());
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testAtualizarSemSucesso(){
		when(museuRepository.findById(any())).thenReturn(Optional.empty());

		MuseusDto museuReturned = museuService.atualizar(1L,mockMuseusDtoWithoutId());

		Assert.assertEquals(1L, (long) museuReturned.getId());
	}

	@Test
	public void testNotaMediaComSucesso(){
		when(museuRepository.findById(1L)).thenReturn(Optional.of(mockMuseusEntity()));
		when(museuRepository.getNotaMedia(1L)).thenReturn(10.0);

		NotaMediaMuseuDto mediaReturned = museuService.calculaMedia(1L);

		Assert.assertEquals(10.0, mediaReturned.getNotaMedia(), 0.0);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testNotaMediaSemSucesso(){
		when(museuRepository.findById(any())).thenReturn(Optional.empty());
		when(museuRepository.getNotaMedia(1L)).thenReturn(10.0);

		NotaMediaMuseuDto mediaReturned = museuService.calculaMedia(1L);

		Assert.assertEquals(10.0, mediaReturned.getNotaMedia(), 0.0);
	}
}
