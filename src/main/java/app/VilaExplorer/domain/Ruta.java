package app.VilaExplorer.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ruta")
public class Ruta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ruta")
    private Long idRuta;

    @Column(name = "nombre_ruta", nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_origen", nullable = false)
    private LugarInteres origen;

    @ManyToOne
    @JoinColumn(name = "id_destino", nullable = false)
    private LugarInteres destino;

    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false)
    private Usuario autor;
}