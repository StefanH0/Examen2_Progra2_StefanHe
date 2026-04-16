/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.time.LocalTime;

/**
 *
 * @author laboratorio
 */
public class Registro {
    private Vehiculo vehiculo;
    private LocalTime horaEntrada;
    private LocalTime horaSalida;
    private double monto;

    // Constructor vacío
    public Registro() {
    }

    // Constructor solo con entrada
    public Registro(Vehiculo vehiculo, LocalTime horaEntrada) {
        this.vehiculo = vehiculo;
        this.horaEntrada = horaEntrada;
        this.horaSalida = null;
        this.monto = 0;
    }

    // Constructor completo
    public Registro(Vehiculo vehiculo, LocalTime horaEntrada, LocalTime horaSalida) {
        this.vehiculo = vehiculo;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.monto = 0;
    }

    // Getters y Setters
    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}