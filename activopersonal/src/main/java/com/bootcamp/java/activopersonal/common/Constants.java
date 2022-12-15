package com.bootcamp.java.activopersonal.common;

public class Constants {
    public class ClientType
    {
        public static final int PersonalRegular = 1;
        public static final int EmpresarialRegular = 2;
        public static final int PersonalVIP = 3;
        public static final int EmpresarialVIP = 4;
    };
    public class ClientDocumentType
    {
        public static final int DNI = 1;
        public static final int RUC = 2;
        public static final int CarnetExtranjeria = 3;
    };

    public class ProductType
    {
        public static final int Pasivos = 1;
        public static final int Activos = 2;
    }

    public class ProductSubType
    {
        public static final int Ahorro = 1;
        public static final int CuentaCorriente = 2;
        public static final int PlazoFijo = 3;
        public static final int CreditoPersonal = 4;
        public static final int CreditoEmpresarial = 5;
        public static final int TarjetaCredito = 6;
    }

    public class TransactionType
    {
        public static final int Deposito = 1;
        public static final int Retiro = 2;
        public static final int Pago = 3;
        public static final int Consumo = 4;
        public static final int TransferenciaSalida = 5;
        public static final int TransferenciaEntrada = 6;

    }

    public class  OwnAccountTransfer
    {
        public static final int Si = 1;
        public static final int No = 0;

    }

    public static final Double MaintenanceCost = 0.0;

    public static final String WebClientUriMSCliente = "http://localhost:8080/v1/client";

    public static final String WebClientUriMSProducto = "http://localhost:8080/v1/product";
    public static final long TimeOutWebClients = 10_000;
}
