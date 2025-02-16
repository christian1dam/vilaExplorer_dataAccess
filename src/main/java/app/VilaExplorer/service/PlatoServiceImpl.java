package app.VilaExplorer.service;

import app.VilaExplorer.domain.Plato;
import app.VilaExplorer.domain.TipoPlato;
import app.VilaExplorer.domain.Usuario;
import app.VilaExplorer.exception.PlatoNotFoundException;
import app.VilaExplorer.exception.RolNotFoundException;
import app.VilaExplorer.exception.UsuarioNotFoundException;
import app.VilaExplorer.repository.TipoPlatoRepository;
import app.VilaExplorer.repository.UsuarioRepository;
import app.VilaExplorer.repository.PlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatoServiceImpl implements PlatoService {
    @Autowired
    private PlatoRepository platoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoPlatoRepository tipoPlatoRepository;

    @Override
    public Plato findById(Long id) throws PlatoNotFoundException {
        return platoRepository.findById(id)
                .orElseThrow(() -> new PlatoNotFoundException("Este ID " + id + " no se encuentra en la base de datos"));
    }

    @Override
    public List<Plato> findAll() {
        return platoRepository.findAll();
    }


    @Override
    public Plato save(Plato plato) {
        return platoRepository.save(plato);
    }

    @Override
    public void deleteById(Long id) throws PlatoNotFoundException {
        if (platoRepository.findById(id).isEmpty())
            throw new PlatoNotFoundException("Este ID " + id + " no se encuentra en la base de datos");
        platoRepository.deleteById(id);
    }

    // Implementación  para aprobar un plato
    @Override
    public Plato aprobarPlato(Long platoId, Long aprobadorId) throws PlatoNotFoundException, UsuarioNotFoundException, RolNotFoundException {
        if (platoRepository.findById(platoId).isEmpty())
            throw new PlatoNotFoundException("Este ID de PLATO " + platoId + " no se encuentra en la base de datos");

        if (usuarioRepository.findById(aprobadorId).isEmpty())
            throw new UsuarioNotFoundException("Este ID de USUARIO " + aprobadorId + " no se encuentra en la base de datos");

        Usuario usuarioFromDB = usuarioRepository.findById(aprobadorId).get();
        if (usuarioFromDB.getRolActual().getNombre().equalsIgnoreCase("cliente")) {
            throw new RolNotFoundException("El ID que has introducido no pertenece a ningún administrador o redactor que pueda aprobar la solicitud");
        }

        Plato platoFromDB = platoRepository.findById(platoId).get();
        platoFromDB.setAprobador(usuarioFromDB);
        platoFromDB.setEstado(true);

        return platoRepository.save(platoFromDB);
    }

    @Override
    public void createPlato(Plato plato) throws DataIntegrityViolationException {
        if (platoRepository.findByNombre(plato.getNombre()).isPresent()) {
            throw new DataIntegrityViolationException("Este plato ya existe en la base de datos.");
        }

        // Validar y asociar autor
        if (plato.getAutor() == null || plato.getAutor().getIdUsuario() == null) {
            throw new RuntimeException("El autor del plato no puede ser nulo.");
        }
        Usuario autor = usuarioRepository.findById(plato.getAutor().getIdUsuario())
                .orElseThrow(() -> new RuntimeException("El autor con ID " + plato.getAutor().getIdUsuario() + " no existe"));
        plato.setAutor(autor);

        // Validar y asociar tipoPlato
        if (plato.getTipoPlato() == null || plato.getTipoPlato().getIdTipoPlato() == null) {
            throw new RuntimeException("El tipo de plato no puede ser nulo.");
        }
        TipoPlato tipoPlato = tipoPlatoRepository.findById(plato.getTipoPlato().getIdTipoPlato())
                .orElseThrow(() -> new RuntimeException("El tipo de plato con ID " + plato.getTipoPlato().getIdTipoPlato() + " no existe"));
        plato.setTipoPlato(tipoPlato);

        // Guardar el plato
        platoRepository.save(plato);
    }


    @Override
    public Plato updatePlato(Long id, Plato platoDetalles) throws PlatoNotFoundException {
        if (platoRepository.findById(id).isEmpty())
            throw new PlatoNotFoundException("Este ID " + id + " no se encuentra en la base de datos");

        Plato platoFromDB = platoRepository.findById(id).get();

        // Si se quiere actualizar el plato sin modificar el tipo de la receta o el autor
        if (platoDetalles.getAutor() != null && platoDetalles.getTipoPlato() != null) {

            platoFromDB.setNombre(platoDetalles.getNombre());
            platoFromDB.setDescripcion(platoDetalles.getDescripcion());
            platoFromDB.setIngredientes(platoDetalles.getIngredientes());
            platoFromDB.setReceta(platoDetalles.getReceta());
            platoFromDB.setEstado(platoDetalles.isEstado());

            return platoRepository.save(platoFromDB);

        }

        platoFromDB.setNombre(platoDetalles.getNombre());
        platoFromDB.setDescripcion(platoDetalles.getDescripcion());
        platoFromDB.setIngredientes(platoDetalles.getIngredientes());
        platoFromDB.setReceta(platoDetalles.getReceta());
        platoFromDB.setEstado(platoDetalles.isEstado());
        platoFromDB.setTipoPlato(platoDetalles.getTipoPlato());
        platoFromDB.setAutor(platoDetalles.getAutor());

        return platoRepository.save(platoFromDB);
    }

    // Implementación para actualizar la puntuación media de un plato
    @Override
    public void actualizarPuntuacionMediaPlato(Long platoId) throws PlatoNotFoundException {
        // Verificar que el plato existe en la base de datos
        Plato plato = platoRepository.findById(platoId)
                .orElseThrow(() -> new PlatoNotFoundException("El plato con ID " + platoId + " no se encuentra en la base de datos"));

        // Calcular la puntuación media utilizando el metodo del repositorio
        Double puntuacionMedia = platoRepository.findAveragePuntuacionByPlatoId(platoId);

        // Actualizar la puntuación media en el objeto Plato y guardarlo en la base de datos
        if (puntuacionMedia != null) {
            plato.setPuntuacionMediaPlato(puntuacionMedia);
            platoRepository.save(plato);
        }
    }

    // Implementación para borrar lógicamente un plato
    @Override
    public void borrarLogico(Long platoId) throws PlatoNotFoundException {
        Plato plato = platoRepository.findById(platoId)
                .orElseThrow(() -> new PlatoNotFoundException("Plato con ID " + platoId + " no encontrado"));

        plato.setEliminado(true);
        platoRepository.save(plato);
    }

    // Implementación para encontrar platos aprobados y no eliminados
    @Override
    public List<Plato> findAprobadosNoEliminados() {
        return platoRepository.findByEstadoTrueAndEliminadoFalse();
    }

    // Implementación para encontrar platos no aprobados
    @Override
    public List<Plato> findNoAprobados() {
        return platoRepository.findByEstadoFalse();
    }

    // Implementación para encontrar platos eliminados
    @Override
    public List<Plato> findEliminados() {
        return platoRepository.findByEliminadoTrue();
    }


    // Implementación para encontrar platos no aprobados y no eliminados
    @Override
    public List<Plato> findNoAprobadosNoEliminados() {
        return platoRepository.findByEstadoFalseAndEliminadoFalse();
    }

    //Implmentacion para contar el total de platos
    @Override
    public long countAllPlatos() {
        return platoRepository.count();
    }


}