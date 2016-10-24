package br.com.listacompra.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Lista.
 */
@Entity
@Table(name = "lista")
public class Lista implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "total")
    private Float total;

    @OneToMany(mappedBy = "lista")
    @JsonIgnore
    private Set<Item> items = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Lista descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Float getTotal() {
        return total;
    }

    public Lista total(Float total) {
        this.total = total;
        return this;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Set<Item> getItems() {
        return items;
    }

    public Lista items(Set<Item> items) {
        this.items = items;
        return this;
    }

    public Lista addItem(Item item) {
        items.add(item);
        item.setLista(this);
        return this;
    }

    public Lista removeItem(Item item) {
        items.remove(item);
        item.setLista(null);
        return this;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lista lista = (Lista) o;
        if(lista.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lista.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Lista{" +
            "id=" + id +
            ", descricao='" + descricao + "'" +
            ", total='" + total + "'" +
            '}';
    }
}
