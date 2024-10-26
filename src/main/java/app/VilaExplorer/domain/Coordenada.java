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
@Table(name = "coordenadas")
public class Coordenada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_coordenada;

    @Column(name = "latitud")
    private double latitud;
    @Column(name = "longitud")
    private double longitud;

    public long getId_coordenada() {
        return id_coordenada;
    }

    public void setId_coordenada(long id_coordenada) {
        this.id_coordenada = id_coordenada;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}