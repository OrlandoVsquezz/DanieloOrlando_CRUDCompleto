package SystemITR.JosueGuinea2A.Departamentos.Entity;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "TBDEPARTAMENTOS")
public class DepartamentosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPARTAMENTO_ID")
    private Long id;
    @Column(name = "NOMBRE_DEPT")
    private String nombreDepto;
    @Column(name = "ABREVIATURA")
    private String abreviatura;
    @Column(name = "UBICACION")
    private String ubicacion;
}
