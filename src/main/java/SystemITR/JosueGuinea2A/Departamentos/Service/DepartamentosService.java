package SystemITR.JosueGuinea2A.Departamentos.Service;

import SystemITR.JosueGuinea2A.Departamentos.DTO.DepartamentoDTO;
import SystemITR.JosueGuinea2A.Departamentos.Entity.DepartamentosEntity;
import SystemITR.JosueGuinea2A.Departamentos.Repository.DepartamentosRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DepartamentosService {

    // Inyección de dependencias y la mas recomendada
    private final DepartamentosRepository repo;
    public DepartamentosService(DepartamentosRepository repo){
        this.repo = repo;
    }

    public DepartamentoDTO nuevoDepartamento(@Valid DepartamentoDTO dto){
        try{
            //1. Convertir a Entity
            DepartamentosEntity entity = convertirAEntity(dto);
            //2. Guardar en la base de datos
            DepartamentosEntity entitySave = repo.save(entity);
            //3. Devolver una respuesta
            return convertirADTO(entitySave);
        }catch (Exception e){
            log.error("Error al ingresar la información del departamento" + e.getMessage());
            return null;
        }
    }



    private DepartamentosEntity convertirAEntity(@Valid DepartamentoDTO dto) {
        DepartamentosEntity objEntity = new DepartamentosEntity();
        objEntity.setNombreDepto(dto.getNombreDepto());
        objEntity.setAbreviatura(dto.getAbreviatura());
        objEntity.setUbicacion(dto.getUbicacion());
        return objEntity;
    }

    private DepartamentoDTO convertirADTO(@Valid DepartamentosEntity entity){
        DepartamentoDTO objDTO = new DepartamentoDTO();
        objDTO.setNombreDepto(entity.getNombreDepto());
        objDTO.setAbreviatura(entity.getAbreviatura());
        objDTO.setUbicacion(entity.getUbicacion());
        return objDTO;
    }

    public List<DepartamentoDTO> obtenerTodo() {
        List<DepartamentosEntity> data = repo.findAll();
        return data.stream().map(this::convertirADTO).collect(Collectors.toList()); // El map le da los valores del entity al dto
    }
}
