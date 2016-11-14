/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SistemaControlRentaIVAFx.BusinessProcess;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 *
 * @author freddy ayala
 */
public class Calculadora {
    
    //Metodos Presupuesto
    public Double calcularVentaPorCuota(Double credito, Double prima, Integer cuotas){
        Double venta;
        venta = credito*cuotas - prima;
        return venta;  
    }
    
    public Double calcularCompraTotal(Double a,Double b,Double c,Double d,Double e){
        Double total = null;
        total = a + b + c + d + e;
        return total;  
    }
    
    public Double calcularIvaVenta(Double venta){
        Double ivaVenta;
        ivaVenta = venta - venta/1.13;
        return ivaVenta;  
    }
    
     public Double calcularIvaCompra(Double compraTotal){
        Double ivaCompra;
        ivaCompra = compraTotal - compraTotal/1.13;
        BigDecimal big = new BigDecimal(ivaCompra);
        big = big.setScale(2, RoundingMode.HALF_UP);
        return ivaCompra;  
    }
     
    public Double calcularPagoIVA ( Double ivaVenta, Double ivaCompra){
        Double pagoIva;
        pagoIva = ivaVenta - ivaCompra;
        return pagoIva;
        
    }
    
     public BigDecimal calcularUtilidadNeta (Double venta, Double compraTotal, Double pagoIva){
        Double utilidad;
        utilidad = venta - compraTotal + pagoIva;
        BigDecimal big = new BigDecimal(utilidad);
        big = big.setScale(2, RoundingMode.HALF_UP);
        return big;
        
    }
   public Double calcularPagoCuenta (Double utilidadNeta){
        Double pagoCuenta;
        pagoCuenta = utilidadNeta*0.175;
        return pagoCuenta;
        
    }
   
    public Double calcularPagoRenta (Double pagoCuenta, Double utilidadNeta){
        Double pagoRenta;
        pagoRenta = pagoCuenta - utilidadNeta;
        return pagoRenta;
        
    }
    
    public Double calcularGananciaNeta(Double utilidadNeta, Double pagoRenta){
        Double gananciaNeta;
        gananciaNeta = utilidadNeta - pagoRenta;
        return gananciaNeta;
        
    }
   //Metodos Costos
    
    public Double calcularPagoIvaCosto(Double valor){
       Double pagoIva;
       pagoIva = valor*0.13;
       return pagoIva;
        
    }
    public Double calcularAbonoPagoCuenta(Double valor){
       Double pagoCuenta;
       pagoCuenta = valor*0.02;
       return pagoCuenta;
    }
    
    
}
