package alquileres.modelo;


/**
 * 
 * @author Christian
 * 
 * Representa a un vehÃ­culo en alquiler
 * De esta clase no se crearÃ¡n instancias
 * 
 * De un vehÃ­culo se conoce su matrÃ­cula, marca, modelo y el precio a pagar por
 * dÃ­a de alquiler
 * 
 * Para todo vehÃ­culo se puede calcular su coste de alquiler que depende del nÂº
 * de dÃ­as que se alquile (llamaremos a esta operaciÃ³n calcularPrecioAlquiler() )
 * 
 * Dos vehÃ­culos pueden compararse por su matrÃ­cula (es su orden natural)
 * 
 * Dos vehÃ­culos son iguales si ademÃ¡s de pertenecer a la misma clase tienen la
 * misma matrÃ­cula
 * 
 */
public abstract class Vehiculo implements Comparable<Vehiculo>{
	private String matricula;
	private String marca;
	private String modelo;
	private double precioDia;
	
	/**
	 * Constructor
	 */
	public Vehiculo(String matricula, String marca, String modelo,
	        double precioDia) {
		this.matricula = matricula.toUpperCase();
		this.marca = marca.toUpperCase();
		this.modelo = modelo.toUpperCase();
		this.precioDia = precioDia;
	}
	
	/**
	 * @return the matricula
	 */
	public String getMatricula() {
		return matricula;
	}

	/**
	 * @param matricula the matricula to set
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	/**
	 * @return the marca
	 */
	public String getMarca() {
		return marca;
	}

	/**
	 * @param marca the marca to set
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}

	/**
	 * @return the modelo
	 */
	public String getModelo() {
		return modelo;
	}

	/**
	 * @param modelo the modelo to set
	 */
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	/**
	 * @return the precioDia
	 */
	public double getPrecioDia() {
		return precioDia;
	}

	/**
	 * @param precioDia the precioDia to set
	 */
	public void setPrecioDia(double precioDia) {
		this.precioDia = precioDia;
	}
	/**
	 * Nos calcula el precio de alquiler en base a unos días y el precio por día del vehículo.
	 */
	public double calcularPrecioAlquiler(int dias) {
		return dias * this.getPrecioDia();
	}
	/**
	 * Redefinición de este método
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
		{
		return false;
		}
		if (obj == this)
		{
		return true;
		}
		if (this.getClass() != obj.getClass())
		{
		return false;
		}
		Vehiculo v = (Vehiculo) obj;
		return v.getMatricula().equals(this.getMatricula());
	}
	/**
	 * Redefinición del criterio natural de comparación
	 */
	@Override
	public int compareTo(Vehiculo o) {
		return this.getMatricula().compareTo(o.getMatricula());
	}
	/**
	 * Redefinición de hashCode()
	 * 
	 */
	@Override
	public int hashCode() {
		return matricula.hashCode() * 13;
	}
	/**
	 * Muestra la información del vehículo
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName().toUpperCase() +
				"\nMatrícula: " + this.getMatricula() + " | " +
				"Marca: " + this.getMarca() + " | " +
				"Modelo: " + this.getModelo() +
				"\nPrecio día alquiler: " + this.getPrecioDia() + "€ | ";
	}
}