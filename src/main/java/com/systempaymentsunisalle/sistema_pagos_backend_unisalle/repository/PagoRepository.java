package com.systempaymentsunisalle.sistema_pagos_backend_unisalle.repository;

import java.util.List;

import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.enums.StatusPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.entities.Pago;
import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.enums.PagoStatus;
import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.enums.TypePago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, String>{
    
    List<Pago> findByEstudianteCodigo(String codigo);
    List<Pago> findByStatus(StatusPago status);
    List<Pago> findByType(TypePago type);
}
