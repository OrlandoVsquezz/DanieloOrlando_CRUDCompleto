package SystemITR.JosueGuinea2A.Departamentos.Repository;

import SystemITR.JosueGuinea2A.Departamentos.Entity.DepartamentosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartamentosRepository extends JpaRepository<DepartamentosEntity, Long> {
    //Método propios
}
