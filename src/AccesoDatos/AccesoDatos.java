/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AccesoDatos;

import Entidades.Registro;
import Entidades.Vehiculo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author laboratorio
 */
public class AccesoDatos {

    private String ruta = "registros.txt";

    public void guardarRegistro(Registro r) {

        try (FileWriter fw = new FileWriter(ruta, true);
             PrintWriter pw = new PrintWriter(fw)) {

            pw.println(
                r.getVehiculo().getPlaca() + ";" +
                r.getVehiculo().getTipo() + ";" +
                r.getHoraEntrada() + ";" +
                r.getHoraSalida() + ";" +
                r.getMonto()
            );

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar archivo");
        }
    }
    
    public List<Registro> leerRegistros() {

        List<Registro> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                String[] partes = linea.split(";");

                String placa = partes[0];
                String tipo = partes[1];
                String entrada = partes[2];
                String salida = partes[3];
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
