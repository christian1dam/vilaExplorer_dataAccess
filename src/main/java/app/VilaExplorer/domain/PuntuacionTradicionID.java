package app.VilaExplorer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PuntuacionTradicionID implements Serializable {
    private Long usuario;
    private Long fiesta;
}
