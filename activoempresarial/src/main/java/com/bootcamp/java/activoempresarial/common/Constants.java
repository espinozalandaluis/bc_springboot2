package com.bootcamp.java.activoempresarial.common;

public class Constants {
    public class ClientType
    {
        public static final int Personal = 1;
        public static final int Empresarial = 2;
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
        public static final int Credito = 5;
    }
}
