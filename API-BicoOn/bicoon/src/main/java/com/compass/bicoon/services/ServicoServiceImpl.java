package com.compass.bicoon.services;

import com.compass.bicoon.dto.ServicoDto;
import com.compass.bicoon.dto.ServicoFormDto;
import com.compass.bicoon.entities.Categoria;
import com.compass.bicoon.entities.Servico;
import com.compass.bicoon.repository.ServicoRepository;
import com.compass.bicoon.util.ValidaConsulta;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ServicoServiceImpl implements ServicoService{

    @Autowired
    ServicoRepository servicoRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    ValidaConsulta validaConsulta;

    @Override
    public Page<ServicoDto> listarServicos(Pageable paginacao) {
        Page<Servico> categoria = servicoRepository.findAll(paginacao);
        Page<ServicoDto> servicosDto = new PageImpl<>(categoria.stream().map(e -> mapper.map(e, ServicoDto.class)).collect(Collectors.toList()));

        return servicosDto;
    }

    @Override
    public ServicoDto atualizarServico(Long id, ServicoFormDto servicoForm) {
        Servico servico = validaConsulta.verificaExistenciaServico(id);
        Categoria categoria = validaConsulta.verificaExistenciaCategoria(servicoForm.getCategoria());

            servico = mapper.map(servicoForm, Servico.class);
            servico.setCategoria(categoria);
            servico.setId(id);
            servicoRepository.save(servico);

            return mapper.map(servico, ServicoDto.class);

    }

    @Override
    public String deletarServico(Long id) {
        Servico servico = validaConsulta.verificaExistenciaServico(id);

        servicoRepository.deleteById(id);

        return "Serviço excluído com sucesso!";
    }

}
