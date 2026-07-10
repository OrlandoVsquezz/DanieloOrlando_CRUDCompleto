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
    public ResponseEntity<ApiResponse<List<DepartamentoDTO>>> obtenerDatos(){
        try{
            List<DepartamentoDTO> lista = service.obtenerTodo();
            if (lista != null){
                log.info("Datos de departamentos colsultados");
                ApiResponse<List<DepartamentoDTO>> respuestaExito = new ApiResponse<>(true, "Datos encontrados", lista);
                return ResponseEntity.ok(respuestaExito);
            }
            log.info("Datos no encontrados");
            ApiResponse<List<DepartamentoDTO>> respuestaNoEncontrada = new ApiResponse<>(true, "Datos encontrados", null);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuestaNoEncontrada);
        }
        catch (Exception e){
            log.error("El proceso preseto fallas inesperadas. Consulte con el administrador");
            e.printStackTrace();
            ApiResponse<List<DepartamentoDTO>> respuesta = new ApiResponse<>(false, "El proceso no se pudo completar", null); // Aqui no es dto, sino que se devuelven los datos que no se pudieron ingresar
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }

}
