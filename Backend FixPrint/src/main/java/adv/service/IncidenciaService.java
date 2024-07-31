package adv.service;

import java.util.List;

import adv.model.dto.IncidenciaCompletaDto;
import adv.model.dto.IncidenciaDto;
import adv.model.entities.Incidencia;
import adv.model.entities.Producto;

public interface IncidenciaService {
	
	/**
	 * Método para buscar todas las incidencias.
	 *
	 * @return Una lista de todas las incidencias encontradas.
	 */
	List<Incidencia> listaIncidencia();
	
	List<IncidenciaDto> listaIncidenciaDto();
	
	IncidenciaCompletaDto findOneDto(int idIncidencia);
	
	/**
	 * Método para buscar una incidencia por su identificador.
	 *
	 * @param idIncidencia El identificador de la incidencia a buscar.
	 * @return La incidencia encontrada o null si no se encuentra.
	 */
	Incidencia findOne(int idIncidencia );
	
	/**
	 * Método para actualizar una incidencia.
	 *
	 * @param incidencia La incidencia a actualizar.
	 * @return La incidencia actualizada o null si ocurre un error.
	 */
	Incidencia updateOne(Incidencia incidencia);
	
	/**
	 * Método para dar de alta una nueva incidencia.
	 *
	 * @param incidencia La incidencia a dar de alta.
	 * @return La incidencia dada de alta.
	 */
	Incidencia altaIncidencia(Incidencia incidencia);
	
	/**
	 * Método para cerrar una incidencia.
	 *
	 * @param incidencia La incidencia a cerrar.
	 * @return true si la incidencia se cierra correctamente, false de lo contrario.
	 */
	boolean cerrarIncidencia(IncidenciaCompletaDto incidencia);
	
	/**
	 * Método para cancelar una incidencia.
	 *
	 * @param idIncidencia El identificador de la incidencia a cancelar.
	 * @return true si la incidencia se cancela correctamente, false de lo contrario.
	 */
	boolean cancelarIncidencia(int idIncidencia);
	
	/**
	 * Método para iniciar una incidencia.
	 *
	 * @param idIncidencia El identificador de la incidencia a iniciar.
	 * @return true si la incidencia se inicia correctamente, false de lo contrario.
	 */
	boolean iniciarIncidencia(int idIncidencia);
	
	/**
	 * Método para obtener todas las incidencias asociadas a un usuario.
	 *
	 * @param idUsuario El identificador del usuario.
	 * @return Una lista de incidencias asociadas al usuario.
	 */
	List<IncidenciaCompletaDto> listaIncidenciaPorUsuario(int idUsuario);
	
	/**
	 * Método para obtener todas las incidencias asociadas a una impresora.
	 *
	 * @param serialNumber El número de serie de la impresora.
	 * @return Una lista de incidencias asociadas a la impresora.
	 */
	List<Incidencia> listaIncidenciaPorImpresora(int serialNumber);
	
	/**
	 * Método para obtener todas las incidencias finalizadas.
	 *
	 * @return Una lista de incidencias finalizadas.
	 */
	List<IncidenciaCompletaDto> listaIncidenciasFinalizadas();
	
	/**
	 * Método para obtener todas las incidencias pendientes.
	 *
	 * @return Una lista de incidencias pendientes.
	 */
	List<IncidenciaCompletaDto> listaIncidenciasPendientes();
	
	void asignarProductoAIncidencia(int idIncidencia, int idProducto, int cantidad);
	
	List<IncidenciaCompletaDto> listaIncidenciaPorTecnico(int idUsuario);
	
	List<IncidenciaCompletaDto> listaIncidenciasEnCurso();

}
