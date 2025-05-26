package com.systempaymentsunisalle.sistema_pagos_backend_unisalle.dtos;

import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.enums.StatusPago;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStatusPagoDto {
    private StatusPago valor;
}
