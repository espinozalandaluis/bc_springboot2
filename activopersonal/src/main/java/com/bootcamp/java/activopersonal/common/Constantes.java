package com.bootcamp.java.activopersonal.common;

import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;

@Slf4j
public class Constantes {
    public static final Integer ClientTypePersonal = 1;
    public static final Integer ProductTypePasivo = 1;

    public static final Integer ProductTypeActivo = 2;
    public static final Integer ProductSubTypePasivo = 1;

    public static final Integer ProductSubTypeActivoCreditoPersonal = 4;

    public static final Integer TransactionTypeDeposito =    1;

    public static final Double MaintenanceCost = 0.0;

    public static final String WebClientUriMSCliente = "http://localhost:8080/v1/client";

    public static final String WebClientUriMSProducto = "http://localhost:8080/v1/product";

    public static final String WebClientUriMSPasivoCuentaCorriente = "http://localhost:8080/v1/pasivocuentacorriente";

    public static final String WebClientUriMSPasivoCuentaCorrienteTrx = "http://localhost:8080/v1/activopersonal/transaction";

    public static final Integer TransferenciasPropiaCuenta = 1;

    public static final Integer TipoTrxDeposito = 1;
    public static final Integer TipoTrxRetiro = 2;
    public static final Integer TipoTrxPago= 3;
    public static final Integer TipoTrxConsumo = 4;
    public static final Integer TipoTrxTransferenciaSalida = 5;
    public static final Integer TipoTrxTransferenciaEntrada = 6;

    public static final Integer Productoactivopersonals = 1;
    public static final Integer ProductoPasivoCuentaCorriente = 2;
    public static final Integer ProductoPasivoPlazoFijo = 3;

    public static final Integer ProductoActivoPersonal = 4;
    public static final Integer ProductoActivoEmpresarial = 5;
    public static final Integer ProductoActivoTarjetaCredito = 6;

    public static final long TimeOutWebClients = 10_000;
}
