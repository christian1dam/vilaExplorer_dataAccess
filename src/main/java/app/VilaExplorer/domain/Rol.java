package app.VilaExplorer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rol")
public class Rol {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_rol")
        private Long idRol;

        @Column (name = "nombre_rol", nullable = false)
        private String nombreRol;

        @OneToMany(mappedBy = "rol")
        @JsonIgnore
        private List<UsuarioRol> usuarios;
}
