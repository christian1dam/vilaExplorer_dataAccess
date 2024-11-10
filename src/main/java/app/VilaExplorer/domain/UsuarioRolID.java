package app.VilaExplorer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioRolID implements Serializable {
    private Long usuario;
    private Long rol;
}
