package SystemITR.JosueGuinea2A.Departamentos.Controller;

import SystemITR.JosueGuinea2A.Departamentos.DTO.DepartamentoDTO;
import SystemITR.JosueGuinea2A.Departamentos.Service.DepartamentosService;
import SystemITR.JosueGuinea2A.Response.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/departamentos")
@CrossOrigin // Para que la api pueda recibir peticiones de otros origenes / esta anotación solo va en el controller pero esta mal ya que no debe recibir peticiones de cualquier lado
public class DepartamentoController {

    /**
     * Aqui ya estamos inyectando la capa servce sobre el controller
     */

    private final DepartamentosService service;

    public DepartamentoController(DepartamentosService service) {
        this.service = service;
    }

    /**
     * Nuevo recursos : Ingresar información -> POST
     * Obtener recursos: GET
     * Actualizar recursos: PUT / PATCH
     * Eliminar recursos: DELETE
     */

    @PostMapping
    public ResponseEntity<ApiResponse<DepartamentoDTO>> nuevoDepartamento(@Valid @RequestBody DepartamentoDTO json){
        try{
            DepartamentoDTO dto = service.nuevoDepartamento(json);
            if (dto != null){
                log.info("Nuevo departamento registrado: " + dto);
                ApiResponse<DepartamentoDTO> respuesta = new ApiResponse<>(true, "Datos ingresados exitosamente", dto);// Aqui el json ya viene con el ID, a difenrecia de solo el json
                return ResponseEntity.ok(respuesta);
            }
            log.warn("Intento de inserción fallido: " + json);
            ApiResponse<DepartamentoDTO> respuestaFallida = new ApiResponse<>(false, "Intento fallido de inserción", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuestaFallida);
        }catch (Exception e){
            log.error("El proceso preseto fallas inesperadas. Consulte con el administrador");
            e.printStackTrace();
            ApiResponse<DepartamentoDTO> respuesta = new ApiResponse<>(false, "El proceso no se pudo completar", json); // Aqui no es dto, sino que se devuelven los datos que no se pudieron ingresar
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartamentoDTO>>> obtenerDatos(){ // Aqui se pone List porque son varios
        try{
            List<DepartamentoDTO> lista = service.obtenerTodo();
            if (lista != null){
                log.info("Datos de departamentos colsultados");
                ApiResponse<List<DepartamentoDTO>> respuestaExito = new ApiResponse<>(true, "Datos encontrados", lista);
                return ResponseEntity.ok(respuestaExito);
            }
            log.info("Datos no encontrados");
            ApiResponse<List<DepartamentoDTO>> respuestaNoEncontrada = new ApiResponse<>(true, "Datos encontrados");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuestaNoEncontrada);
        }
        catch (Exception e){
            log.error("El proceso presento fallas inesperadas. Consulte con el administrador");
            e.printStackTrace();
            ApiResponse<List<DepartamentoDTO>> respuesta = new ApiResponse<>(false, "El proceso no se pudo completar"); // Aqui no es dto, sino que se devuelven los datos que no se pudieron ingresar
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }

    @GetMapping("/{id}") // Aqui no se pone List porque solo trae una cosa por id no todos los registros por id
    public ResponseEntity<ApiResponse<DepartamentoDTO>> obtenerDatosPorId(@PathVariable Long id){
        // Path variable agarra el id y lo guarda en su variable para usarla
        try{
            DepartamentoDTO dto = service.buscarDepartamento(id);
            if (dto != null){
                log.info("Se obtuvieron los datos del departamento: " + dto);
                ApiResponse<DepartamentoDTO> respuesta = new ApiResponse<>(true, "Se obtuvieron los datos del departamento con id: " + id, dto);
                return ResponseEntity.ok(respuesta);
            }
            log.info("Datos no encontrados con ID: " + id);
            ApiResponse<DepartamentoDTO> respuesta = new ApiResponse<>(false, "Datos no encontrados con ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
        catch (Exception e){
            log.error("Error critico al obtener el departamento con ID: " + id);
            e.printStackTrace(); // Muestra el lugar exacto de donde ocurrio el error en la ejecución
            ApiResponse<DepartamentoDTO> respuesta = new ApiResponse<>(false, "Error al obtener el departamento con ID: " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((respuesta));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarDepartamento(@PathVariable Long id){
        try {
            boolean respuesta = service.eliminarData(id);
            if (respuesta){
                log.info("Departamento con ID: " + id + " eliminado");
                ApiResponse<Void> respuestaExitosa = new ApiResponse<>(true, "Departamento con ID: " + id + " eliminado");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body((respuestaExitosa));
            }
            log.info("Departamento con ID: " + id + " no fue encontrado");
            ApiResponse<Void> respuestaNoEncontrada = new ApiResponse<>(false, "Departamento con ID: " + id + " no fue encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuestaNoEncontrada);
        }
        catch (Exception e){
            log.error("Error critico al eliminar el departamento con ID: " + id);
            e.printStackTrace(); // Muestra el lugar exacto de donde ocurrio el error en la ejecución
            ApiResponse<Void> respuesta = new ApiResponse<>(false, "Error al eliminar el departamento con ID: " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((respuesta));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartamentoDTO>> actualizarData(@PathVariable Long id, @Valid @RequestBody DepartamentoDTO dto){ // Aqui el PathVariable recibe el id por URL, el RequestBody es porque va a recibir el json ya que ahi iran los nuevos valores y Valid para activar las anotaciones del DTO ya que si no se pone, las validaciones del DTO no se ejecutariam
        try{
            DepartamentoDTO data = service.actualizar(id, dto);
            if (data != null){
                log.info("Departamento con ID: " + id + " ha sido actualizado");
                ApiResponse<DepartamentoDTO> respuesta = new ApiResponse<>(true, "Departamento con ID: " + id + " ha sido actualizado", data);
                return ResponseEntity.ok(respuesta);
            }
            log.warn("No se pudo completar la actualización del departamento con ID: " + id );
            ApiResponse<DepartamentoDTO> respuesta = new ApiResponse<>(false, "No se pudo completar la actualización del departamento con ID: " + id );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        catch (Exception e){
            log.error("Error critico al actualizar el departamento con ID: " + id);
            e.printStackTrace(); // Muestra el lugar exacto de donde ocurrio el error en la ejecución
            ApiResponse<DepartamentoDTO> respuesta = new ApiResponse<>(false, "Error al actualizar el departamento con ID: " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((respuesta));
        }
    }

    @GetMapping("/abreviatura/{abreviatura}") // En este codigo, la mayor parte es reutilizado
    public ResponseEntity<ApiResponse<DepartamentoDTO>> buscarPorAbreviatura(@PathVariable String abreviatura){
        try{

            DepartamentoDTO data = service.buscarAbreviatura(abreviatura);
            if (abreviatura != null){
                log.info("Departamento encontrado con abreviatura: " + abreviatura);
                ApiResponse<DepartamentoDTO> respuesta = new ApiResponse<>(true, "Se obtuvieron los datos del departamento con id: " + abreviatura, data);
                return ResponseEntity.ok(respuesta);
            }
            log.info("Departamento no encontrado: " + abreviatura);
            ApiResponse<DepartamentoDTO> respuesta = new ApiResponse<>(false, "Departamento no encontrado: " + abreviatura);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
        catch (Exception e){
            log.error("Error critico al obtener el departamento con abreviatura: " + abreviatura);
            e.printStackTrace(); // Muestra el lugar exacto de donde ocurrio el error en la ejecución
            ApiResponse<DepartamentoDTO> respuesta = new ApiResponse<>(false, "Error al obtener el departamento con abreviatura: " + abreviatura);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((respuesta));
        }
    }
}
