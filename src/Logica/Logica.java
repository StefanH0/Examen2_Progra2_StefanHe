/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import AccesoDatos.AccesoDatos;
import Entidades.Registro;
import Entidades.Vehiculo;
import java.time.Duration;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author laboratorio
 */
public class Logica {
 
  private List<Registro> activos;
    private List<Registro> historial;
    private AccesoDatos dao;

    private final double TARIFA = 500;

    public Logica() {
        activos = new ArrayList<>();
        dao = new AccesoDatos();
        historial = dao.leerRegistros(); // cargar historial desde archivo
    }

    // =========================
    // REGISTRAR ENTRADA
    // =========================
    public String registrarEntrada(String placa, String tipo, String horaEntradaStr) {

        if (placa == null || placa.isEmpty()) {
            throw new RuntimeException("Placa obligatoria");
        }

        if (tipo == null || tipo.isEmpty()) {
            throw new RuntimeException("Tipo obligatorio");
        }

        if (horaEntradaStr == null || horaEntradaStr.isEmpty()) {
            throw new RuntimeException("Hora de entrada obligatoria");
        }

        // Convertir String → LocalTime
        LocalTime horaEntrada = LocalTime.parse(horaEntradaStr);

        // Validar duplicado
        for (Registro r : activos) {
            if (r.getVehiculo().getPlaca().equalsIgnoreCase(placa)) {
                throw new RuntimeException("El vehículo ya está en el parqueo");
            }
        }

        Vehiculo v = new Vehiculo(placa, tipo);
        Registro registro = new Registro(v, horaEntrada);

        activos.add(registro);

        return "Ingreso registrado correctamente";
    }

    // =========================
    // REGISTRAR SALIDA
    // =========================
    public String registrarSalida(String placa, String horaSalidaStr) {

        if (horaSalidaStr == null || horaSalidaStr.isEmpty()) {
            throw new RuntimeException("Hora de salida obligatoria");
        }

        LocalTime horaSalida = LocalTime.parse(horaSalidaStr);

        Registro encontrado = null;

        for (Registro r : activos) {
            if (r.getVehiculo().getPlaca().equalsIgnoreCase(placa)) {
                encontrado = r;
                break;
            }
        }

        if (encontrado == null) {
            throw new RuntimeException("Vehículo no encontrado");
        }

        LocalTime horaEntrada = encontrado.getHoraEntrada();

        if (horaSalida.isBefore(horaEntrada)) {
            throw new RuntimeException("La salida no puede ser menor que la entrada");
        }

        // Calcular tiempo
        Duration duracion = Duration.between(horaEntrada, horaSalida);
        long minutos = duracion.toMinutes();

        long horas = minutos / 60;
        if (minutos % 60 != 0) {
            horas++;
        }

        if (horas == 0) {
            horas = 1;
        }

        double monto = horas * TARIFA;

        // Actualizar registro
        encontrado.setHoraSalida(horaSalida);
        encontrado.setMonto(monto);

        activos.remove(encontrado);
        historial.add(encontrado);

        // Guardar en archivo
        dao.guardarRegistro(encontrado);

        return "Salida registrada. Monto: ₡" + monto;
    }

    // =========================
    // GETTERS
    // =========================
    public List<Registro> getActivos() {
        return activos;
    }

    public List<Registro> getHistorial() {
        return historial;
    }

    // =========================
    // LIMPIAR HISTORIAL
    // =========================
    public void eliminarHistorial() {
        historial.clear();
    }
}
