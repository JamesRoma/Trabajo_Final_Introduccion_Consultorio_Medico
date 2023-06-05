package Consultorio;

public class Paciente extends Persona{
	
	private String enfermedad;
	
	
	public Paciente(String nombre, int edad, String enfermedad) {
        super(nombre, edad);
        this.enfermedad = enfermedad;
    }
	
	public String getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }

}
