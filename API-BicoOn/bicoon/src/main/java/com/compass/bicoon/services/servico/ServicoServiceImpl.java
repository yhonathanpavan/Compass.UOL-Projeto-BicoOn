package com.compass.bicoon.services.servico;

import com.compass.bicoon.dto.servico.ServicoDto;
import com.compass.bicoon.dto.servico.ServicoFormDto;
import com.compass.bicoon.entities.Categoria;
import com.compass.bicoon.entities.Servico;
import com.compass.bicoon.exceptions.objectNotFound.ObjectNotFoundException;
import com.compass.bicoon.repository.ServicoRepository;
import com.compass.bicoon.services.categoria.CategoriaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicoServiceImpl implements ServicoService{

    @Autowired
    ServicoRepository servicoRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    CategoriaService categoriaService;

    @Override
    public Page<ServicoDto> listarServicos(Pageable paginacao) {
        Page<Servico> servico = servicoRepository.findAll(paginacao);
        Page<ServicoDto> servicosDto = new PageImpl<>(servico.stream().map(e -> mapper.map(e, ServicoDto.class)).collect(Collectors.toList()));

        return servicosDto;
    }

    @Override
    public ServicoDto atualizarServico(Long id, ServicoFormDto servicoForm) {
        verificaExistenciaServico(id);
        Categoria categoria = categoriaService.verificaExistenciaCategoria(servicoForm.getCategoria());

            Servico servico = mapper.map(servicoForm, Servico.class);
            servico.setCategoria(categoria);
            servico.setId(id);
            servicoRepository.save(servico);

            return mapper.map(servico, ServicoDto.class);
    }

    @Override
    public void deletarServico(Long id) {
        verificaExistenciaServico(id);
        servicoRepository.deleteById(id);
    }

    @Override
    public Servico verificaExistenciaServico(Long id) {
        Optional<Servico> servicoOptional = servicoRepository.findById(id);
        if(servicoOptional.isPresent()){
            return servicoOptional.get();
        }
        throw new ObjectNotFoundException("Serviço não encontrado");
    }

}
