package SystemITR.JosueGuinea2A.Departamentos.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
public class DepartamentoDTO {

    private Long id;
    @NotBlank @Size(max = 50, message = "Longitud invalida en el nombre del departamento [50 caracteres]")
    private String nombreDepto;
    @NotBlank @Size(max = 5, message = "La abreviación no puede exceder de cinco caracteres.")
    private String abreviatura;
    @NotBlank @Size(max = 100, message = "Longitud de la ubicación es invalida [100 caracteres]")
    private String ubicacion;
}
