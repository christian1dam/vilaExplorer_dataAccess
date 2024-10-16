package app.VilaExplorer.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
    private long idLugarInteres;
    private LocalDate fechaAlta;
    private String nombreLugar;
    private String descripcion;
    private String imagen;
    private String categoria;



}
