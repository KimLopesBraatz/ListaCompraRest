package br.com.listacompra.service.impl;

import br.com.listacompra.service.ListaService;
import br.com.listacompra.domain.Lista;
import br.com.listacompra.repository.ListaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Lista.
 */
@Service
@Transactional
public class ListaServiceImpl implements ListaService{

    private final Logger log = LoggerFactory.getLogger(ListaServiceImpl.class);
    
    @Inject
    private ListaRepository listaRepository;

    /**
     * Save a lista.
     *
     * @param lista the entity to save
     * @return the persisted entity
     */
    public Lista save(Lista lista) {
        log.debug("Request to save Lista : {}", lista);
        Lista result = listaRepository.save(lista);
        return result;
    }

    /**
     *  Get all the listas.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Lista> findAll(Pageable pageable) {
        log.debug("Request to get all Listas");
        Page<Lista> result = listaRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one lista by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Lista findOne(Long id) {
        log.debug("Request to get Lista : {}", id);
        Lista lista = listaRepository.findOne(id);
        return lista;
    }

    /**
     *  Delete the  lista by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Lista : {}", id);
        listaRepository.delete(id);
    }
}
