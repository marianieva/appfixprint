package adv.service;

import java.util.List;

import adv.model.entities.Impresora;

public interface ImpresoraService {
	
	/**
	 * Obtiene una lista de todas las impresoras almacenadas en la base de datos.
	 * 
	 * @return Lista de impresoras.
	 */
	List<Impresora> listaImpresoras();
	
	/**
     * Obtiene una lista de impresoras asociadas a un cliente específico.
     * 
     * @param idCliente ID del cliente.
     * @return Lista de impresoras asociadas al cliente.
     */
	List<Impresora> listaImpresorasPorCliente(int idCliente);
	
	/**
     * Encuentra una impresora por su número de serie.
     * 
     * @param serialNumber Número de serie de la impresora a buscar.
     * @return La impresora encontrada, o null si no se encuentra ninguna.
     */
	Impresora findOne(int idImpresora);
	
	/**
     * Actualiza la información de una impresora en la base de datos.
     * 
     * @param impresora La impresora con la información actualizada.
     * @return La impresora actualizada, o null si ocurre un error.
     */
	Impresora updateOne(Impresora impresora);
	
	/**
     * Da de alta una nueva impresora en la base de datos.
     * 
     * @param impresora La impresora a dar de alta.
     * @return La impresora dada de alta.
     */
	Impresora altaImpresora(Impresora impresora);

}
