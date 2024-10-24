package app.VilaExplorer.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "puntuacion_lugar")
public class PuntuacionLugar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_puntuacion;

    @Column(name = "id_usuario", nullable = false)
    private long id_usuario;

    @Column(name = "id_lugar", nullable = false)
    private long id_lugar;

    @Column(name = "puntuacion", nullable = false)
    private int puntuacion;

    // Getters y setters

    public long getId_puntuacion() {
        return id_puntuacion;
    }

    public void setId_puntuacion(long id_puntuacion) {
        this.id_puntuacion = id_puntuacion;
    }

    public long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public long getId_lugar() {
        return id_lugar;
    }

    public void setId_lugar(long id_lugar) {
        this.id_lugar = id_lugar;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
}
