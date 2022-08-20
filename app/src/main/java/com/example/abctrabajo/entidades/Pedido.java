package com.example.abctrabajo.entidades;

public class Pedido extends Material {
    private String HoraPedido;
    private String HoraEntrega;

    public String getHoraPedido() {
        return HoraPedido;
    }

    public void setHoraPedido(String horaPedido) {
        HoraPedido = horaPedido;
    }

    public String getHoraEntrega() {
        return HoraEntrega;
    }

    public void setHoraEntrega(String horaEntrega) {
        HoraEntrega = horaEntrega;
    }



    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }


    private String Estado;

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    private int Cantidad;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    private int Id;


}
