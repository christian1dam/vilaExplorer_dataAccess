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
    private long id_lugarinteres;

    @Column(name = "fechaAlta")
    private LocalDate fechaAlta;
    @Column(name = "nombreLugar")
    private String nombreLugar;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "imagen")
    private String imagen;
    @Column(name = "categoria")
    private String categoria;



}
