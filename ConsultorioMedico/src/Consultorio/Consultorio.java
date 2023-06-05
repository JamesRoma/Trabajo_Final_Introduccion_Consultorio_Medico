package Consultorio;

import java.util.ArrayList;
import java.util.List;

public class Consultorio {
	
	private List<Paciente> pacientes;
    private List<Medico> medicos;
    
    public Consultorio() {
        pacientes = new ArrayList<>();
        medicos = new ArrayList<>();
    }
    
    public void agregarPaciente(Paciente paciente) {
        pacientes.add(paciente);
    }

    public void agregarMedico(Medico medico) {
        medicos.add(medico);
    }

    public void asignarMedico(Paciente paciente, Medico medico) {
        // código para asignar un médico a un paciente
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

}
