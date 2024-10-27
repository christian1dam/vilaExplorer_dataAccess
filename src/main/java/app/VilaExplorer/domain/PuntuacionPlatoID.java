package app.VilaExplorer.domain;

import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PuntuacionPlatoID implements Serializable {
  private Long usuario;
    private Long plato;
}
