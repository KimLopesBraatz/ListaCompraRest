package br.com.listacompra.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.listacompra.domain.Lista;
import br.com.listacompra.service.ListaService;
import br.com.listacompra.web.rest.util.HeaderUtil;
import br.com.listacompra.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Lista.
 */
@RestController
@RequestMapping("/api")
public class ListaResource {

    private final Logger log = LoggerFactory.getLogger(ListaResource.class);
        
    @Inject
    private ListaService listaService;

    /**
     * POST  /listas : Create a new lista.
     *
     * @param lista the lista to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lista, or with status 400 (Bad Request) if the lista has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/listas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Lista> createLista(@RequestBody Lista lista) throws URISyntaxException {
        log.debug("REST request to save Lista : {}", lista);
        if (lista.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lista", "idexists", "A new lista cannot already have an ID")).body(null);
        }
        Lista result = listaService.save(lista);
        return ResponseEntity.created(new URI("/api/listas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lista", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /listas : Updates an existing lista.
     *
     * @param lista the lista to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lista,
     * or with status 400 (Bad Request) if the lista is not valid,
     * or with status 500 (Internal Server Error) if the lista couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/listas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Lista> updateLista(@RequestBody Lista lista) throws URISyntaxException {
        log.debug("REST request to update Lista : {}", lista);
        if (lista.getId() == null) {
            return createLista(lista);
        }
        Lista result = listaService.save(lista);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lista", lista.getId().toString()))
            .body(result);
    }

    /**
     * GET  /listas : get all the listas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of listas in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/listas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Lista>> getAllListas(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Listas");
        Page<Lista> page = listaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/listas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /listas/:id : get the "id" lista.
     *
     * @param id the id of the lista to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lista, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/listas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Lista> getLista(@PathVariable Long id) {
        log.debug("REST request to get Lista : {}", id);
        Lista lista = listaService.findOne(id);
        return Optional.ofNullable(lista)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /listas/:id : delete the "id" lista.
     *
     * @param id the id of the lista to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/listas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLista(@PathVariable Long id) {
        log.debug("REST request to delete Lista : {}", id);
        listaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lista", id.toString())).build();
    }

}
