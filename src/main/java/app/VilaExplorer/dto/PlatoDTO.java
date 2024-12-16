package app.VilaExplorer.dto;

/**
 * DTO para representar los datos de un Plato con información simplificada.
 */
public class PlatoDTO {
    private Long platoId;
    private String nombre;
    private String descripcion;
    private String ingredientes;
    private String receta;
    private boolean estado;
    private double puntuacionMediaPlato;
    private String imagen;
    private String tipoPlato;
    private String categoriaPlato;
    private String autor;
    private String aprobador;

    // Constructor vacío
    public PlatoDTO() {}

    // Constructor completo
    public PlatoDTO(Long platoId, String nombre, String descripcion, String ingredientes, String receta,
                    boolean estado, double puntuacionMediaPlato, String imagen, String tipoPlato,
                    String categoriaPlato, String autor, String aprobador) {
        this.platoId = platoId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ingredientes = ingredientes;
        this.receta = receta;
        this.estado = estado;
        this.puntuacionMediaPlato = puntuacionMediaPlato;
        this.imagen = imagen;
        this.tipoPlato = tipoPlato;
        this.categoriaPlato = categoriaPlato;
        this.autor = autor;
        this.aprobador = aprobador;
    }

    // Getters y Setters
    public Long getPlatoId() {
        return platoId;
    }

    public void setPlatoId(Long platoId) {
        this.platoId = platoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getReceta() {
        return receta;
    }

    public void setReceta(String receta) {
        this.receta = receta;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public double getPuntuacionMediaPlato() {
        return puntuacionMediaPlato;
    }

    public void setPuntuacionMediaPlato(double puntuacionMediaPlato) {
        this.puntuacionMediaPlato = puntuacionMediaPlato;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTipoPlato() {
        return tipoPlato;
    }

    public void setTipoPlato(String tipoPlato) {
        this.tipoPlato = tipoPlato;
    }

    public String getCategoriaPlato() {
        return categoriaPlato;
    }

    public void setCategoriaPlato(String categoriaPlato) {
        this.categoriaPlato = categoriaPlato;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getAprobador() {
        return aprobador;
    }

    public void setAprobador(String aprobador) {
        this.aprobador = aprobador;
    }
}
