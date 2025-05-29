package com.devweb2025a.actividad3.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "computadores")
public class Computador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String marca;
    private String categoria;
    private String marcaCpu;
    private double velocidadCpU;
    private String tecnologiaRam;
    private int capacidadRam;
    private String tecnologiaDisco;
    private int capacidadDisco;
    private int numPuertosUSB;
    private int numPuertosHDMI;
    private String marcaMonitor;
    private double pulgadas;
    private double precio;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Computador() {
    }

    public Computador(int id, String marca, String categoria, String marcaCpu, double velocidadCpU, String tecnologiaRam, int capacidadRam, String tecnologiaDisco, int capacidadDisco, int numPuertosUSB, int numPuertosHDMI, String marcaMonitor, double pulgadas, double precio, Usuario usuario) {
        this.id = id;
        this.marca = marca;
        this.categoria = categoria;
        this.marcaCpu = marcaCpu;
        this.velocidadCpU = velocidadCpU;
        this.tecnologiaRam = tecnologiaRam;
        this.capacidadRam = capacidadRam;
        this.tecnologiaDisco = tecnologiaDisco;
        this.capacidadDisco = capacidadDisco;
        this.numPuertosUSB = numPuertosUSB;
        this.numPuertosHDMI = numPuertosHDMI;
        this.marcaMonitor = marcaMonitor;
        this.pulgadas = pulgadas;
        this.precio = precio;
        this.usuario = usuario;
    }

    // Getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMarcaCpu() {
        return marcaCpu;
    }

    public void setMarcaCpu(String marcaCpu) {
        this.marcaCpu = marcaCpu;
    }

    public double getVelocidadCpU() {
        return velocidadCpU;
    }

    public void setVelocidadCpU(double velocidadCpU) {
        this.velocidadCpU = velocidadCpU;
    }

    public String getTecnologiaRam() {
        return tecnologiaRam;
    }

    public void setTecnologiaRam(String tecnologiaRam) {
        this.tecnologiaRam = tecnologiaRam;
    }

    public int getCapacidadRam() {
        return capacidadRam;
    }

    public void setCapacidadRam(int capacidadRam) {
        this.capacidadRam = capacidadRam;
    }

    public String getTecnologiaDisco() {
        return tecnologiaDisco;
    }

    public void setTecnologiaDisco(String tecnologiaDisco) {
        this.tecnologiaDisco = tecnologiaDisco;
    }

    public int getCapacidadDisco() {
        return capacidadDisco;
    }

    public void setCapacidadDisco(int capacidadDisco) {
        this.capacidadDisco = capacidadDisco;
    }

    public int getNumPuertosUSB() {
        return numPuertosUSB;
    }

    public void setNumPuertosUSB(int numPuertosUSB) {
        this.numPuertosUSB = numPuertosUSB;
    }

    public int getNumPuertosHDMI() {
        return numPuertosHDMI;
    }

    public void setNumPuertosHDMI(int numPuertosHDMI) {
        this.numPuertosHDMI = numPuertosHDMI;
    }

    public String getMarcaMonitor() {
        return marcaMonitor;
    }

    public void setMarcaMonitor(String marcaMonitor) {
        this.marcaMonitor = marcaMonitor;
    }

    public double getPulgadas() {
        return pulgadas;
    }

    public void setPulgadas(double pulgadas) {
        this.pulgadas = pulgadas;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

