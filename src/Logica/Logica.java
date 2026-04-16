/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import Entidades.Registro;
import Entidades.Vehiculo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author laboratorio
 */
public class Logica {
 
    private List<Registro> activos;
    private List<Registro> historial;
    private final double TARIFA = 500;

    public Logica() {
        activos = new ArrayList<>();
        historial = new ArrayList<>();
    }

    // =========================
    // REGISTRAR ENTRADA
    // =========================
    public String registrarEntrada(String placa, String tipo, String horaEntrada) {

        if (placa == null || placa.isEmpty()) {
            throw new RuntimeException("Placa obligatoria");
        }

        if (tipo == null || tipo.isEmpty()) {
            throw new RuntimeException("Tipo obligatorio");
        }

        if (horaEntrada == null || horaEntrada.isEmpty()) {
            throw new RuntimeException("Hora de entrada obligatoria");
        }

        validarHora(horaEntrada);

        // Verificar duplicado
        for (Registro r : activos) {
            if (r.getVehiculo().getPlaca().equalsIgnoreCase(placa)) {
                throw new RuntimeException("El vehículo ya está dentro del parqueo");
            }
        }

        Vehiculo vehiculo = new Vehiculo(placa, tipo);
        Registro registro = new Registro(vehiculo, horaEntrada);

        activos.add(registro);

        return "Ingreso registrado correctamente";
    }

    // =========================
    // REGISTRAR SALIDA
    // =========================
    public String registrarSalida(String placa, String horaSalida) {

        if (horaSalida == null || horaSalida.isEmpty()) {
            throw new RuntimeException("Hora de salida obligatoria");
        }

        validarHora(horaSalida);

        Registro encontrado = null;

        for (Registro r : activos) {
            if (r.getVehiculo().getPlaca().equalsIgnoreCase(placa)) {
                encontrado = r;
                break;
            }
        }

        if (encontrado == null) {
            throw new RuntimeException("Vehículo no encontrado en parqueo");
        }

        int entradaMin = convertirAMinutos(encontrado.getHoraEntrada());
        int salidaMin = convertirAMinutos(horaSalida);

        if (salidaMin < entradaMin) {
            throw new RuntimeException("La hora de salida no puede ser menor que la entrada");
        }

        int diferencia = salidaMin - entradaMin;

        // Calcular horas con redondeo
        int horas = diferencia / 60;
        if (diferencia % 60 != 0) {
            horas++;
        }

        if (horas == 0) {
            horas = 1;
        }

        double monto = horas * TARIFA;

        encontrado.setHoraSalida(horaSalida);
        encontrado.setMonto(monto);

        activos.remove(encontrado);
        historial.add(encontrado);

        return "Salida registrada. Monto: ₡" + monto;
    }

    // =========================
    // VALIDAR FORMATO HH:mm
    // =========================
    private void validarHora(String hora) {

        if (!hora.contains(":")) {
            throw new RuntimeException("Formato inválido. Use HH:mm");
        }

        String[] partes = hora.split(":");

        if (partes.length != 2) {
            throw new RuntimeException("Formato inválido. Use HH:mm");
        }

        try {
            int h = Integer.parseInt(partes[0]);
            int m = Integer.parseInt(partes[1]);

            if (h < 0 || h > 23 || m < 0 || m > 59) {
                throw new RuntimeException("Hora fuera de rango");
            }

        } catch (NumberFormatException e) {
            throw new RuntimeException("Formato inválido. Use números");
        }
    }

    // =========================
    // CONVERTIR HH:mm → minutos
    // =========================
    private int convertirAMinutos(String hora) {
        String[] partes = hora.split(":");
        int h = Integer.parseInt(partes[0]);
        int m = Integer.parseInt(partes[1]);
        return h * 60 + m;
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

    public void eliminarHistorial() {
        historial.clear();
    }
}

