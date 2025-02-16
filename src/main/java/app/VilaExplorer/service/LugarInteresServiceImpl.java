package app.VilaExplorer.service;

import app.VilaExplorer.domain.LugarInteres;
import app.VilaExplorer.enums.TipoEntidad;
import app.VilaExplorer.exception.CoordenadasNotFoundException;
import app.VilaExplorer.exception.LugarInteresNotActiveException;
import app.VilaExplorer.exception.LugarInteresNotFoundException;
import app.VilaExplorer.repository.LugarInteresRepository;
import app.VilaExplorer.repository.PuntuacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LugarInteresServiceImpl implements LugarInteresService {

    @Autowired
    private LugarInteresRepository lugarInteresRepository;

    @Autowired
    private PuntuacionRepository puntuacionRepository;

    @Override
    public LugarInteres updateLugarInteres(Long id, LugarInteres lugarInteresDetalle) throws LugarInteresNotFoundException {
        if (lugarInteresRepository.findById(id).isEmpty())
            throw new LugarInteresNotFoundException("Este ID " + id + " no se encuentra en la base de datos");

        LugarInteres lugarInteresFromDB = lugarInteresRepository.findById(id).get();

        // Actualizando los atributos de LugarInteres
        lugarInteresFromDB.setNombreLugar(lugarInteresDetalle.getNombreLugar());
        lugarInteresFromDB.setDescripcion(lugarInteresDetalle.getDescripcion());
        lugarInteresFromDB.setImagen(lugarInteresDetalle.getImagen());
        lugarInteresFromDB.setTipoLugar(lugarInteresDetalle.getTipoLugar());
        lugarInteresFromDB.setFechaAlta(lugarInteresDetalle.getFechaAlta());

        // Gestionar las coordenadas: eliminar las existentes y añadir las nuevas
        lugarInteresFromDB.getCoordenadas().clear();
        lugarInteresFromDB.getCoordenadas().addAll(lugarInteresDetalle.getCoordenadas());
        lugarInteresDetalle.getCoordenadas().forEach(coordenada -> coordenada.setLugarInteres(lugarInteresFromDB));

        return lugarInteresRepository.save(lugarInteresFromDB);
    }

    @Override
    public LugarInteres desactivarLugarInteres(Long id) throws LugarInteresNotFoundException {
        if (lugarInteresRepository.findById(id).isEmpty())
            throw new LugarInteresNotFoundException("Este ID " + id + " no se encuentra en la base de datos");
        LugarInteres lugarInteresFromDB = lugarInteresRepository.findById(id).get();

        // Cambiar el estado a inactivo (borrado lógico)
        lugarInteresFromDB.setActivo(false);

        return lugarInteresRepository.save(lugarInteresFromDB);
    }

    @Override
    public LugarInteres findById(Long id) throws LugarInteresNotFoundException {
        if (lugarInteresRepository.findById(id).isEmpty())
            throw new LugarInteresNotFoundException("Este ID " + id + " no se encuentra en la base de datos");
        return lugarInteresRepository.findById(id).get();
    }

    @Override
    public List<LugarInteres> findAll() throws LugarInteresNotFoundException {
        if (lugarInteresRepository.findAll().isEmpty())
            throw new LugarInteresNotFoundException("Actualmente la base de datos no cuenta con registros de LugarInteres");
        return lugarInteresRepository.findAll();
    }

    //metodo para obtener todos los lugares de interes activos
    @Override
    public List<LugarInteres> findAllActivos() throws LugarInteresNotFoundException {
        if (lugarInteresRepository.findAllByActivoTrue().isEmpty())
            throw new LugarInteresNotFoundException("Actualmente la base de datos no cuenta con registros activos de LugarInteres");
        return lugarInteresRepository.findAllByActivoTrue();
    }


    //metodo para guardar un lugar de interes
    @Override
    public LugarInteres save(LugarInteres lugarInteres) throws CoordenadasNotFoundException {
        // Validar que se hayan enviado coordenadas
        if (lugarInteres.getCoordenadas() == null || lugarInteres.getCoordenadas().isEmpty()) {
            throw new CoordenadasNotFoundException("La ubicación no contiene sus coordenadas para su ubicación"); // Es obligatorio tener al menos un par de coordenadas
        }
        // Asociar cada coordenada al lugar de interés
        lugarInteres.getCoordenadas().forEach(coordenada -> coordenada.setLugarInteres(lugarInteres));
        // Guardar el lugar de interés junto con las coordenadas asociadas
        return lugarInteresRepository.save(lugarInteres);
    }

    //metodo para desactivar un lugar de interes
    @Override
    public void deleteByIdLogico(Long id) throws LugarInteresNotFoundException {
        if (lugarInteresRepository.findById(id).isEmpty())
            throw new LugarInteresNotFoundException("El ID " + id + " no se encuentra en la base de datos");
        LugarInteres lugarInteresFromDB = lugarInteresRepository.findById(id).get();
        lugarInteresFromDB.setActivo(false);
        lugarInteresRepository.save(lugarInteresFromDB);
    }

    @Override
    public LugarInteres findLugarInteresActivoByID(Long id) throws LugarInteresNotFoundException, LugarInteresNotActiveException {
        if (lugarInteresRepository.findById(id).isEmpty())
            throw new LugarInteresNotFoundException("El ID " + id + " no se encuentra en la base de datos");
        LugarInteres lugarInteres = lugarInteresRepository.findById(id).get();
        if (!lugarInteres.getActivo())
            throw new LugarInteresNotActiveException("El LugarInteres al que quieres acceder no está activo");
        else return lugarInteres;
    }

    @Override
    public void actualizarPuntuacionMediaLugarInteres(Long idLugarInteres) {
        Double promedio = puntuacionRepository
                .findAveragePuntuacionByEntidad(idLugarInteres, TipoEntidad.LUGAR_INTERES)
                .orElse(0.0);
        LugarInteres lugarInteres = lugarInteresRepository.findById(idLugarInteres)
                .orElseThrow(() -> new RuntimeException("Lugar de interés no encontrado"));
        lugarInteres.setPuntuacionMediaLugar(promedio); // Actualiza la puntuación media
        lugarInteresRepository.save(lugarInteres); // Guarda el cambio
    }

    public LugarInteres activarLugarInteres(Long id) throws LugarInteresNotFoundException {
        Optional<LugarInteres> optionalLugarInteres = lugarInteresRepository.findById(id);
        if (optionalLugarInteres.isEmpty()) {
            throw new LugarInteresNotFoundException("El lugar de interés con ID " + id + " no se encuentra en la base de datos");
        }
        LugarInteres lugarInteres = optionalLugarInteres.get();
        lugarInteres.setActivo(true); // Cambiar el estado a activo
        return lugarInteresRepository.save(lugarInteres); // Guardar los cambios
    }

    @Override
    public List<LugarInteres> searchByKeyword(String keyword) {
        return lugarInteresRepository.findByNombreLugarContainingIgnoreCaseAndActivoTrue(keyword);
    }

}