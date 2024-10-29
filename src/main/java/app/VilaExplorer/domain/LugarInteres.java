package app.VilaExplorer.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lugar_interes")
public class LugarInteres {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lugar_interes")
    private Long idLugarInteres;

    @Column(name = "fecha_alta", nullable = false)
    private LocalDate fechaAlta;

    @Column(name = "nombre_lugar", nullable = false)
    private String nombreLugar;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String imagen;

    @ManyToOne
    @JoinColumn(name = "id_tipo_lugar", nullable = false)
    private TipoLugarInteres tipoLugar;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_coordenada", referencedColumnName = "id_coordenada", nullable = false)
    private Coordenada coordenada;
}