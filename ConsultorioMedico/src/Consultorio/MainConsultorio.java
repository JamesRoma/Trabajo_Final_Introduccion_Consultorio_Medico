package Consultorio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class MainConsultorio {

	public static void main(String[] args) {
		
		Consultorio consultorio = new Consultorio();
		
		String usuarioCorrecto = "admin";
        String contraseñaCorrecta = "1234";
        int intentos = 0;

        while (intentos < 3) {
            String usuario = JOptionPane.showInputDialog("Usuario:");

            JPasswordField passwordField = new JPasswordField();
            int respuestaContraseña = JOptionPane.showConfirmDialog(null,
                    passwordField,
                    "Contraseña: ",
                    JOptionPane.OK_CANCEL_OPTION);

            if (respuestaContraseña != JOptionPane.OK_OPTION) {
                return;
            }

            String contraseña = new String(passwordField.getPassword());

            if (usuarioCorrecto.equalsIgnoreCase(usuario) && contraseñaCorrecta.equalsIgnoreCase(contraseña)) {
                break;
            }

            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
            intentos++;
        }

        if (intentos == 3) {
            JOptionPane.showMessageDialog(null,
                    "Usuario bloqueado. Por favor, contáctese con un administrador.");
            return;
        }
        
        String fileName = "Consultorio.txt";

        int opcion = 0;
        while (opcion != 8) {
            opcion = Integer.parseInt(JOptionPane.showInputDialog("Ingrese una opción:\n1. Agregar paciente\n2. Agregar médico\n3. Asignar médico a paciente\n4. Mostrar lista de pacientes\n5. Mostrar lista de Médicos\n6. Buscar Médico por Nombre\n7. Buscar Paciente por Nombre\n8. Guardar Cita Médica"));
            switch (opcion) {
                case 1:
                	boolean valido0 = false;
                	String nombre = " ";
                	while (!valido0) {
                	    try {
                	        nombre = JOptionPane.showInputDialog("Ingrese el nombre del paciente:");
                	        Integer.parseInt(nombre);
                	        valido0 = false;
                	    } catch (NumberFormatException e) {
                	        if (nombre.matches("[a-zA-Z]+")) {
                	            valido0 = true;
                	        } else {
                	            JOptionPane.showMessageDialog(null, "Solo debe de contener Letras.");
                	        }
                	    }
                	}
            		
                    int edad = 0;
                    boolean valido = false;
                    while (!valido) {
                        try {
                            edad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la edad del paciente:"));
                            valido = true;
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "La edad debe ser un número entero válido.");
                        }
                    }
                    String enfermedad = JOptionPane.showInputDialog("Ingrese la enfermedad del paciente:");

                    Paciente paciente = new Paciente(nombre, edad, enfermedad);
                    consultorio.agregarPaciente(paciente);

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                        writer.write("Paciente: " + nombre + " - " + edad + " años - " + enfermedad + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                	boolean valido1 = false;
                	String nombreM = " ";
                	while (!valido1) {
                	    nombreM = JOptionPane.showInputDialog("Ingrese el nombre del médico:");
                	    if (nombreM.matches("[a-zA-Z]+")) {
                	        valido1 = true;
                	    } else {
                	        JOptionPane.showMessageDialog(null, "Solo debe de contener Letras.");
                	    }
                	}
                    //nombre = JOptionPane.showInputDialog("Ingrese el nombre del médico:");
                    edad = 0;
                    valido = false;
                    while (!valido) {
                        try {
                            edad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la edad del médico:"));
                            valido = true;
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "La edad debe ser un número entero válido.");
                        }
                    }
                    String especialidad = JOptionPane.showInputDialog("Ingrese la especialidad del médico:");

                    Medico medico = new Medico(nombreM, edad, especialidad);
                    consultorio.agregarMedico(medico);

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                        writer.write("Médico: " + nombreM + " - " + edad + " años - " + especialidad + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    // código para asignar un médico a un paciente
                	boolean valido2 = false;
                	String nombrePaciente = " ";
                	while (!valido2) {
                	    try {
                	    	nombrePaciente = JOptionPane.showInputDialog("Ingrese el nombre del paciente:");
                	        Integer.parseInt(nombrePaciente);
                	        valido2 = false;
                	    } catch (NumberFormatException e) {
                	        if (nombrePaciente.matches("[a-zA-Z]*$")) {
                	            valido2 = true;
                	        } else {
                	            JOptionPane.showMessageDialog(null, "Solo debe de contener Letras.");
                	        }
                	    }
                	}
                    //String nombrePaciente = JOptionPane.showInputDialog("Ingrese el nombre del paciente:");
                    Paciente pacienteSeleccionado = null;
                    for (Paciente p : consultorio.getPacientes()) {
                        if (p.getNombre().equals(nombrePaciente)) {
                            pacienteSeleccionado = p;
                            break;
                        }
                    }
                    if (pacienteSeleccionado == null) {
                        JOptionPane.showMessageDialog(null, "No se encontró al paciente con el nombre ingresado.");
                        break;
                    }

                    String nombreMedico = JOptionPane.showInputDialog("Ingrese el nombre del médico:");
                    Medico medicoSeleccionado = null;
                    for (Medico m : consultorio.getMedicos()) {
                        if (m.getNombre().equals(nombreMedico)) {
                            medicoSeleccionado = m;
                            break;
                        }
                    }
                    if (medicoSeleccionado == null) {
                        JOptionPane.showMessageDialog(null, "No se encontró al médico con el nombre ingresado.");
                        break;
                    }

                     consultorio.asignarMedico(pacienteSeleccionado, medicoSeleccionado);
                     
                     try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                         writer.write("Asignación: Médico " + medicoSeleccionado.getNombre() + " asignado al paciente " + pacienteSeleccionado.getNombre() + "\n");
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                     
                     JOptionPane.showMessageDialog(null, "Se ha asignado el médico " + medicoSeleccionado.getNombre() + " al paciente " + pacienteSeleccionado.getNombre());
                     break; 
                case 4:
                     StringBuilder sb1 = new StringBuilder();
                     sb1.append("Lista de pacientes:\n");
                     for (Paciente p : consultorio.getPacientes()) {
                         sb1.append(p.getNombre() + " - " + p.getEdad() + " años - " + p.getEnfermedad() + "\n");
                     }
                     JOptionPane.showMessageDialog(null, sb1.toString());
                     break; 
                case 5:
                     StringBuilder sb2 = new StringBuilder();
                     sb2.append("Lista de médicos:\n");
                     for (Medico m : consultorio.getMedicos()) {
                         sb2.append(m.getNombre() + " - " + m.getEdad() + " años - " + m.getEspecialidad() + "\n");
                     }
                     JOptionPane.showMessageDialog(null, sb2.toString());
                     break; 
                case 6:
                     String nombreMedico1 = JOptionPane.showInputDialog("Ingrese el nombre del médico a buscar:");
                     Medico medicoEncontrado1 = null;
                     for (Medico m : consultorio.getMedicos()) {
                         if (m.getNombre().equals(nombreMedico1)) {
                             medicoEncontrado1 = m;
                             break;
                         }
                     }
                     if (medicoEncontrado1 == null) {
                         JOptionPane.showMessageDialog(null, "No se encontró al médico con el nombre ingresado.");
                     } else {
                         JOptionPane.showMessageDialog(null, "Médico encontrado: " + medicoEncontrado1.getNombre() + " - " + medicoEncontrado1.getEdad() + " años - " + medicoEncontrado1.getEspecialidad());
                     }
                     break; 
                case 7:
                      String nombrePaciente1=JOptionPane.showInputDialog("Ingrese el nombre del paciente a buscar:");
                      Paciente pacienteEncontrado=null; 
                      for(Paciente p:consultorio.getPacientes()){
                          if(p.getNombre().equals(nombrePaciente1)){
                              pacienteEncontrado=p; 
                              break; 
                          }
                      }
                      if(pacienteEncontrado==null){
                          JOptionPane.showMessageDialog(null,"No se encontró al paciente con el nombre ingresado."); 
                      }else{
                          JOptionPane.showMessageDialog(null,"Paciente encontrado: "+pacienteEncontrado.getNombre()+" - "+pacienteEncontrado.getEdad()+" años - "+pacienteEncontrado.getEnfermedad()); 
                      }
                      break; 
            }
        }

        StringBuilder sb3 = new StringBuilder();
        sb3.append("Información del consultorio:\n");
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            for (String line : lines) {
                sb3.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dateTime = null;
        do {
            String fecha = JOptionPane.showInputDialog("Ingrese la fecha y hora (formato: dd/MM/yyyy HH:mm):");
            try {
                dateTime = LocalDateTime.parse(fecha, formatter);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(null, "La fecha y hora ingresada no es válida. Por favor ingrese la fecha y hora en el formato correcto (dd/MM/yyyy HH:mm).");
            }
        } while (dateTime == null);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,true))) { //adjuntar al archivo
            writer.write("+---------------------+\n");
            writer.write("| Fecha y Hora        |\n");
            writer.write("+---------------------+\n");
            writer.write(String.format("| %-19s |\n", dateTime.format(formatter)));
            writer.write("+---------------------+\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        sb3.append("\nFecha y hora:\n");
        sb3.append(String.format("%-19s\n", dateTime.format(formatter)));

        JOptionPane.showMessageDialog(null, sb3.toString());
    }

}
