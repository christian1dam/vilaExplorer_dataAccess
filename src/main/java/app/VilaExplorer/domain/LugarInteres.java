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

    //Todo Falta definir MtM

    public long getId_lugarinteres() {
        return id_lugarinteres;
    }

    public void setId_lugarinteres(long id_lugarinteres) {
        this.id_lugarinteres = id_lugarinteres;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getNombreLugar() {
        return nombreLugar;
    }

    public void setNombreLugar(String nombreLugar) {
        this.nombreLugar = nombreLugar;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
