/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AccesoDatos;

import Entidades.Registro;
import Entidades.Vehiculo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.time.Duration;
/**
 *
 * @author laboratorio
 */
public class AccesoDatos {
    private String ruta = "data/registros.txt";

    // =========================
    // GUARDAR
    // =========================
    public void guardarRegistro(Registro r) {

        try (FileWriter fw = new FileWriter(ruta, true);
             PrintWriter pw = new PrintWriter(fw)) {

            String salida = (r.getHoraSalida() != null) 
                    ? r.getHoraSalida().toString() 
                    : "";

            pw.println(
                    r.getVehiculo().getPlaca() + ";" +
                    r.getVehiculo().getTipo() + ";" +
                    r.getHoraEntrada().toString() + ";" +
                    salida + ";" +
                    r.getMonto()
            );

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar archivo");
        }
    }

    // =========================
    // LEER
    // =========================
    public List<Registro> leerRegistros() {

        List<Registro> lista = new ArrayList<>();

        File archivo = new File(ruta);

        // Si no existe, retorna lista vacía
        if (!archivo.exists()) {
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                String[] partes = linea.split(";");

                String placa = partes[0];
                String tipo = partes[1];

                LocalTime entrada = LocalTime.parse(partes[2]);

                LocalTime salida = null;
                if (!partes[3].isEmpty()) {
                    salida = LocalTime.parse(partes[3]);
                }

                double monto = Double.parseDouble(partes[4]);

                Vehiculo v = new Vehiculo(placa, tipo);
                Registro r = new Registro(v, entrada, salida);
                r.setMonto(monto);

                lista.add(r);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al leer archivo");
        }

        return lista;
    }
}
