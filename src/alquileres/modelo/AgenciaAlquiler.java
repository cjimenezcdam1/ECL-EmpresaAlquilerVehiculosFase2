package alquileres.modelo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * La clase guarda en una colección List (un ArrayList) la flota de vehículos
 * que una agencia de alquiler posee
 * 
 * Los vehículos se modelan como un interface List que se instanciará como una
 * colección concreta ArrayList
 * 
 * @author Christian
 * 
 */
public class AgenciaAlquiler {
	private String nombre; // el nombre de la agencia
	private List<Vehiculo> flota; // la lista de vehículos
	public static final File FICHERO_ENTRADA = new File("flota.csv");
	public static final File FICHERO_SALIDA = new File("marcasmodelos.txt");

	/**
	 * Constructor
	 * 
	 * @param nombre el nombre de la agencia
	 */
	public AgenciaAlquiler(String nombre) {
		this.nombre = nombre;
		this.flota = new ArrayList<>();
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * añade un nuevo vehículo solo si no existe
	 * 
	 */
	public void addVehiculo(Vehiculo v) {
		if(!flota.contains(v)) {
			flota.add(v);
		}
	}

	/**
	 * Extrae los datos de una línea, crea y devuelve el vehículo
	 * correspondiente
	 * 
	 * Formato de la línea:
	 * C,matricula,marca,modelo,precio,plazas para coches
	 * F,matricula,marca,modelo,precio,volumen para furgonetas
	 * 
	 * 
	 * Asumimos todos los datos correctos. Puede haber espacios antes y después
	 * de cada dato
	 */
	private Vehiculo obtenerVehiculo(String lineaInfo) throws NumberFormatException{
		String[] datos = lineaInfo.split(",");
		for(int i = 0; i < datos.length; i++) {
			datos[i] = datos[i].trim();
		}
		String matricula = datos[1].toUpperCase();
		String marca = datos[2].toUpperCase();
		String modelo = datos[3].toUpperCase();
		double precio = Double.parseDouble(datos[4]);
		if(datos[0].equalsIgnoreCase("F")) {
			double volumen = Double.parseDouble(datos[5]);
			Furgoneta furgoneta = new Furgoneta(matricula, marca, modelo, precio, volumen);
			return furgoneta;
		}else{
			int plazas = Integer.parseInt(datos[5]);
			Coche coche = new Coche(matricula, marca, modelo, precio, plazas);
			return coche;
		}
	}

	/**
	 * La clase Utilidades nos devuelve un array con las líneas de datos
	 * de la flota de vehículos  
	 */
	public int cargarFlota() {
		int lineasError = 0;
		BufferedReader entrada = null;
		try {
			entrada = new BufferedReader(
					new FileReader(FICHERO_ENTRADA));
			String linea = entrada.readLine();
			while (linea != null){
				this.obtenerVehiculo(linea);
				linea = entrada.readLine();
			}
			
		}
		catch (IOException e1) {
			System.out.println("Se ha producido un error al leer el fichero " + FICHERO_ENTRADA.toString());
		}
		catch (NumberFormatException e2) {
			lineasError += 1;
		}
		return lineasError;
	}

	/**
	 * Representación textual de la agencia
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Veh�culos en alquiler de la agencia " + this.getNombre()
					+ "\nTotal veh�culos: " + flota.size() + "\n");
		for(Vehiculo v: flota) {
			sb.append(v.toString() 
					+ "\n-----------------------------------------------------\n");
		}
		return sb.toString();
	}

	/**
	 * Busca todos los coches de la agencia
	 * Devuelve un String con esta información y lo que
	 * costaría alquilar cada coche el nº de días indicado * 
	 *  
	 */
	public String buscarCoches(int dias) {
		StringBuilder sb = new StringBuilder();
		sb.append("Coches de alquiler en la agencia\n");
		for(Vehiculo v: flota) {
			if(v instanceof Coche) {
				sb.append(v.toString()
						+ "\nCoste alquiler " + dias + " d�as: " + v.calcularPrecioAlquiler(dias)
						+ "\n-----------------------------------------------------\n");
			}
		}
		return sb.toString();
	}

	/**
	 * Obtiene y devuelve una lista de coches con más de 4 plazas ordenada por
	 * matrícula - Hay que usar un iterador
	 * 
	 */
	public List<Coche> cochesOrdenadosMatricula() {
		ArrayList<Coche> cochesCuatroPlazasOrdenados = new ArrayList<>();
		Iterator<Vehiculo> it = flota.iterator();
		while(it.hasNext()) {
			Vehiculo v = it.next();
			if(v instanceof Coche && ((Coche) v).getNumPlazas() > 4) {
				cochesCuatroPlazasOrdenados.add((Coche) v);
			}
		}
		cochesCuatroPlazasOrdenados.sort(Comparator.naturalOrder());
		return cochesCuatroPlazasOrdenados;
	}

	/**
	 * Devuelve la relación de todas las furgonetas ordenadas de
	 * mayor a menor volumen de carga
	 * 
	 */
	public List<Furgoneta> furgonetasOrdenadasPorVolumen() {
		ArrayList<Furgoneta> furgonetasPorVolumen = new ArrayList<>();
		Iterator<Vehiculo> it = flota.iterator();
		while(it.hasNext()) {
			Vehiculo v = it.next();
			if(v instanceof Furgoneta) {
				furgonetasPorVolumen.add((Furgoneta) v);
			}
		}
		furgonetasPorVolumen.sort(new Comparator<Furgoneta>()
		{
				public int compare(Furgoneta f1, Furgoneta f2) {
					return Double.compare(f2.getVolumenCarga(), f1.getVolumenCarga());
				}
				});
		return furgonetasPorVolumen;

	}

	/**
	 * Genera y devuelve un map con las marcas (importa el orden) de todos los
	 * vehículos que hay en la agencia como claves y un conjunto (importa el orden) 
	 * de los modelos en cada marca como valor asociado
	 */
	public Map<String, Set<String>> marcasConModelos() {
		TreeMap<String, Set<String>> marcasConModelos = new TreeMap<>();
		Iterator<Vehiculo> it = flota.iterator();
		while(it.hasNext()) {
			Vehiculo v = it.next();
			String marcaV = v.getMarca();
			String modeloV = v.getModelo();
			if(marcasConModelos.get(marcaV) == null) {
				TreeSet<String> nombres = new TreeSet<>();
				nombres.add(modeloV);
				marcasConModelos.put(marcaV, nombres);
			}else{
				marcasConModelos.get(marcaV).add(modeloV);
			}
		}
		return marcasConModelos;
	}

}
