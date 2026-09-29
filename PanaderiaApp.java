/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/*Actividad 02: Implementación de Tipo de Dato Abstracto (TDA) en Java
 *Proyecto: Sistema de Panadería (TDA, Herencia, Recursividad y GUI)
 * Nombre: Santos Martínez Violeta
 * Matrícula: 20251043
 * Grado: 4° Cuatrimestre 
 * Grupo: C
 * Parcial: 1
 * @author viole
*/

/**
 * 1. TDA BASE: ProductoPanaderia
 * Define el Tipo de Dato Abstracto con encapsulamiento.
 */
abstract class ProductoPanaderia {
    // Atributos encapsulados
    private String nombre;
    private double precio;

    // Constructor con validación pertinente
    public ProductoPanaderia(String nombre, double precio) {
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio del pan debe ser mayor a 0.");
        }
        this.nombre = nombre;
        this.precio = precio;
    }

    // Métodos de Acceso (Getters y Setters)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio > 0) {
            this.precio = precio;
        }
    }

    // Métodos de Lógica del Dominio 
    // Método lógico 1
    public String hornear() {
        return "El producto '" + nombre + "' está en el horno...";
    }

    // Método lógico 2 (Abstracto para aplicar polimorfismo)
    public abstract String obtenerDescripcion();
}

/**
 * 2. HERENCIA: PanDulce hereda de ProductoPanaderia
 */
class PanDulce extends ProductoPanaderia {
    private String tipoCobertura;

    public PanDulce(String nombre, double precio, String tipoCobertura) {
        super(nombre, precio); // Llama al constructor de la clase padre
        this.tipoCobertura = tipoCobertura;
    }

    public String getTipoCobertura() {
        return tipoCobertura;
    }

    public void setTipoCobertura(String tipoCobertura) {
        this.tipoCobertura = tipoCobertura;
    }

    // Método lógico 3: Sobrescritura de método
    @Override
    public String obtenerDescripcion() {
        return getNombre() + " con cobertura de " + tipoCobertura + " ($" + getPrecio() + ")";
    }

    // Método lógico 4: RECURSIVIDAD OBLIGATORIA
    /**
     * Calcula el costo total de un lote de panes mediante sumas sucesivas.
     * @param cantidad Número de panes a cobrar.
     * @return Costo total calculado de forma recursiva.
     */
    public double calcularCostoLoteRecursivo(int cantidad) {
        // Caso Base: Si la cantidad es 0, el costo a sumar es 0.
        if (cantidad <= 0) {
            return 0.0;
        }
        // Condición de Avance: Sumamos el precio de 1 pan y llamamos a la función con (cantidad - 1)
        return this.getPrecio() + calcularCostoLoteRecursivo(cantidad - 1);
    }
}

/**
 * 3. ENTORNO DE USUARIO
 */
public class PanaderiaApp extends JFrame {

    // Componentes de la interfaz
    private final JComboBox<String> comboPanes;
    private final JTextField txtCantidad;
    private final JTextArea txtResultados;
    private final JButton btnCalcular;

    public PanaderiaApp() {
        // Configuración básica de la ventana
        setTitle("Sistema de Panadería");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel Superior: Identificación del autor y título
        JPanel panelNorte = new JPanel(new GridLayout(2, 1));
        JLabel lblTitulo = new JLabel("La Panadería de Maria", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        
        // ¡REQUISITO DE ENTREGA! Autor visible en la interfaz
        JLabel lblAutor = new JLabel("Autor: Violeta Santos Martinez", SwingConstants.CENTER);
        lblAutor.setForeground(Color.LIGHT_GRAY);
        
        panelNorte.add(lblTitulo);
        panelNorte.add(lblAutor);
        add(panelNorte, BorderLayout.NORTH);

        // Panel Central: Formulario de entrada
        JPanel panelCentro = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelCentro.add(new JLabel("Seleccione el Pan:"));
        String[] opcionesPan = {"Concha (Vainilla) - $15.0", "Cuernito (Chocolate) - $18.0", "Dona (Azúcar) - $12.0"};
        comboPanes = new JComboBox<>(opcionesPan);
        panelCentro.add(comboPanes);

        panelCentro.add(new JLabel("Cantidad a comprar:"));
        txtCantidad = new JTextField();
        panelCentro.add(txtCantidad);

        btnCalcular = new JButton("Calcular y Procesar Pedido");
        panelCentro.add(new JLabel()); // Espacio vacío
        panelCentro.add(btnCalcular);

        add(panelCentro, BorderLayout.CENTER);

        // Panel Inferior: Resultados
        txtResultados = new JTextArea(8, 30);
        txtResultados.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtResultados);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Consola de Operaciones"));
        add(scrollPane, BorderLayout.SOUTH);

        // --- Lógica del Botón ---
        btnCalcular.addActionListener((ActionEvent e) -> {
            procesarPedido();
        });
    }

    /**
     * Método que integra el TDA, la GUI y la recursividad al presionar el botón.
     */
    private void procesarPedido() {
        try {
            // Validar entrada
            int cantidad = Integer.parseInt(txtCantidad.getText());
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "Por favor ingrese una cantidad mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Instanciar el objeto usando Herencia y Polimorfismo
            PanDulce panSeleccionado = null;
            int seleccion = comboPanes.getSelectedIndex();

            switch (seleccion) {
                case 0 -> panSeleccionado = new PanDulce("Concha", 15.0, "Vainilla");
                case 1 -> panSeleccionado = new PanDulce("Cuernito", 18.0, "Chocolate");
                case 2 -> panSeleccionado = new PanDulce("Dona", 12.0, "Azúcar");
            }

            if (panSeleccionado != null) {
                txtResultados.setText(""); // Limpiar consola
                
                // 1. Mostrar estado (Método lógico 1)
                txtResultados.append("horno" + panSeleccionado.hornear() + "\n");
                
                // 2. Mostrar descripción (Método lógico 2 y 3)
                txtResultados.append("Producto: " + panSeleccionado.obtenerDescripcion() + "\n");
                
                // 3. Aplicar Recursividad (Método lógico 4)
                txtResultados.append("Calculando costo de " + cantidad + " unidades recursivamente...\n");
                double total = panSeleccionado.calcularCostoLoteRecursivo(cantidad);
                
                txtResultados.append("TOTAL A PAGAR: $" + total + "\n");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido en la cantidad.", "Error de formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método principal para ejecutar la aplicación de escritorio
    public static void main(String[] args) {
        // Asegura que la GUI se ejecute en el hilo despachador de eventos (Buenas prácticas Swing)
        SwingUtilities.invokeLater(() -> {
            PanaderiaApp ventana = new PanaderiaApp();
            ventana.setVisible(true);
        });
    }
}
