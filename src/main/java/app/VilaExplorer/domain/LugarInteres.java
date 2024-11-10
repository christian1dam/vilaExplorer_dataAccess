package app.VilaExplorer.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


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

    @Column(name = "nombre_lugar", nullable = false)
    private String nombreLugar;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "fecha_alta", nullable = false)
    private LocalDate fechaAlta;

    @Column(name = "imagen", nullable = false)
    private String imagen;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @ManyToOne
    @JoinColumn(name = "id_tipo_lugar", nullable = false)
    private TipoLugarInteres tipoLugar;
}