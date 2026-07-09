package SystemITR.JosueGuinea2A.Departamentos.Controller;

import SystemITR.JosueGuinea2A.Departamentos.DTO.DepartamentoDTO;
import SystemITR.JosueGuinea2A.Departamentos.Service.DepartamentosService;
import SystemITR.JosueGuinea2A.Response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            ApiResponse<DepartamentoDTO> respuesta = new ApiResponse<>(true, "Datos ingresados exitosamente", dto);// Aqui el json ya viene con el ID, a difenrecia de solo el json
            return ResponseEntity.ok(respuesta);
        }catch (Exception e){
            e.printStackTrace();
            ApiResponse<DepartamentoDTO> respuesta = new ApiResponse<>(false, "El proceso no se pudo completar", json); // Aqui no es dto, sino que se devuelven los datos que no se pudieron ingresar
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }
}
