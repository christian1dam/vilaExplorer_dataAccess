package app.VilaExplorer.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ruta_lugar")
public class RutaLugar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_ruta_lugar;

    @Column(name = "id_ruta")
    private int id_ruta;
    @Column(name = "id_lugar")
    private long id_lugar;

    //Todo

    public long getId_ruta_lugar() {
        return id_ruta_lugar;
    }

    public void setId_ruta_lugar(long id_ruta_lugar) {
        this.id_ruta_lugar = id_ruta_lugar;
    }

    public int getId_ruta() {
        return id_ruta;
    }

    public void setId_ruta(int id_ruta) {
        this.id_ruta = id_ruta;
    }

    public long getId_lugar() {
        return id_lugar;
    }

    public void setId_lugar(long id_lugar) {
        this.id_lugar = id_lugar;
    }
}
