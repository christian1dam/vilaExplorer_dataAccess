package app.VilaExplorer.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "favoritos")
public class Favorito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_favorito;

    @Column(name = "id_usuario")
    private long id_usuario;
    @Column(name = "id_lugar")
    private long id_lugar;
    @Column(name = "puntuacion")
    private int puntuacion;

    //Todo

    public long getId_favorito() {
        return id_favorito;
    }

    public void setId_favorito(long id_favorito) {
        this.id_favorito = id_favorito;
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