package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Table(name = "libros")
@Setter
@NamedQueries({
        @NamedQuery(name = "libros",
                query = "select l from Libro l"
        )
})
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="titulo", length=150, nullable=false)
    private String titulo;

    private int anoPub;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="autor_id")
    private Autor autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="categoria_id")
    private Categoria categoria;
}
