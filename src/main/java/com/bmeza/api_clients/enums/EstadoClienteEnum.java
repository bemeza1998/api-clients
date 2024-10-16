package com.bmeza.api_clients.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum EstadoClienteEnum {

    ACTIVO("ACT", "Activo"),
    BLOQUEADO("BLQ", "Bloqueado");
  
    private final String valor;
    private final String texto;
    
}
