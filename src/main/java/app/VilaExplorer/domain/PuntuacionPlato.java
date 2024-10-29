package app.VilaExplorer.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "puntuacion_plato")
public class PuntuacionPlato {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_puntuacion_plato")
  private Long idPuntuacionPlato;

  @ManyToOne
  @JoinColumn(name = "id_usuario", nullable = false, foreignKey = @ForeignKey(name = "FK_puntuacion_plato_usuario"))
  private Usuario usuario;

  @ManyToOne
  @JoinColumn(name = "id_plato", nullable = false, foreignKey = @ForeignKey(name = "FK_puntuacion_plato_plato"))
  private Plato plato;

  @Column(nullable = false)
  private Integer puntuacion;
}
