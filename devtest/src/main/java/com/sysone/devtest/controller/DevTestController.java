package com.sysone.devtest.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sysone.devtest.model.Adicionales;
import com.sysone.devtest.model.Automovil;
import com.sysone.devtest.model.OpcionalesEnum;
import com.sysone.devtest.model.Variante;
import com.sysone.devtest.repository.DaoAdicionales;
import com.sysone.devtest.repository.DaoAutomovil;
import com.sysone.devtest.service.AdicionalesService;
import com.sysone.devtest.service.AutomovilService;

@Controller
public class DevTestController {

	@Autowired
	DaoAutomovil daoAutomovil;
	
	@Autowired
	DaoAdicionales daoAdicionales;
	
	/**
	 * @author AndresLizarraga
	 * GET API que retorna una lista con todos los automoviles creados en la base.
	 * @return Un objeto json con todos los automoviles creados en la base. **/
    @GetMapping({"/getAutomoviles"})
    public ResponseEntity<Object> getAutomoviles() {
    	try {
    	JsonArray json_array = new JsonArray();
    	List<Automovil> getAutomoviles = daoAutomovil.findAll();
		
    	if (getAutomoviles.size() > 0) {
    		
    		for (Automovil auto: getAutomoviles) {
    			JsonArray aux3 = new JsonArray();
    			for (Adicionales adic : auto.getAdicionales()) {
    				JsonObject aux2 = new JsonObject();
    				aux2.addProperty("opcional", adic.getOpcional().toString());
    				aux3.add(aux2);
    			}
    			JsonObject aux = new JsonObject();
    			
    			aux.addProperty("id", auto.getId());
    			aux.addProperty("modelo", auto.getModelo());
    			aux.addProperty("placa", auto.getPlaca());
    			aux.addProperty("variante", auto.getVariante().toString());
    			aux.addProperty("costo_fabricacion", auto.getCostoFabricacion());
    			aux.add("adicionales", aux3);
    			
    			json_array.add(aux);
    		}
    		JsonObject obj = new JsonObject();
			obj.addProperty("Error: ",0);
			obj.add("Results: ", json_array);
			return ResponseEntity.ok().body(obj);
    	}
    	else {
			JsonObject obj = new JsonObject();
			obj.addProperty("Error: ",1);
			obj.addProperty("Mensaje: ", "No se pudo encontrar ningún registro.");
			return ((BodyBuilder) ResponseEntity.notFound()).body(obj);
		} 
    	}
    	catch(Exception e) {
    		e.printStackTrace();
        	JsonObject obj = new JsonObject();
        	obj.addProperty("Error: ", 1);
        	obj.addProperty("Mensaje: ","Ocurrió un error al establecer la conexión con la base de datos. ");
        	return ResponseEntity.badRequest().body(obj); 
    	}
    }
    
    /**
	 * @author AndresLizarraga
	 * POST API que inserta un nuevo objeto 'Automovil' en la base de datos.
	 * @param automovil representa un objeto de tipo 'Automovil' en formato json pasado como parametro de Body en la solicitud.   
	 * @return el nuevo objeto 'Automovil' insertado en la base de datos en formato json. **/
    @PostMapping(path= {"/crearAutomovil"})
    public ResponseEntity<Object> crearAutomovil(@RequestBody Automovil automovil) {
    	try {
    	Automovil auto = new Automovil();
    	List<Adicionales> adicionales = new ArrayList<Adicionales>();
    	int automovilesCount = daoAutomovil.findAll().size();
    	auto.setModelo(automovil.getModelo());
    	auto.setPlaca(automovil.getPlaca());
    	auto.setVariante(automovil.getVariante());
    	daoAutomovil.save(auto);
      	if(automovil.getAdicionales()!= null) {
        	for (Adicionales adicional_ : automovil.getAdicionales()) {
        			Adicionales adicional = new Adicionales();
        			adicional.setAutomovil(auto);
        			adicional.setOpcional(adicional_.getOpcional());
        			adicional.calcularCosto();
        			daoAdicionales.save(adicional);
        			adicionales.add(adicional);
        	}
        	}
        	auto.setAdicionales(adicionales);
        	auto.calcularCostoFabricacion();
        	daoAutomovil.save(auto);
    	if (automovilesCount < daoAutomovil.findAll().size()) {
    	JsonObject obj = new JsonObject();
    	obj.addProperty("Status: ", 0);
    	obj.addProperty("Mensaje: ","Se agregó correctamente el automovil. ");
    	return ResponseEntity.ok().body(automovil); 
    	} else {
        	JsonObject obj = new JsonObject();
        	obj.addProperty("Error: ", 1);
        	obj.addProperty("Mensaje: ","Ocurrió un error al insertar el automovil. Por favor revisar el cuerpo de la solicitud.");
        	return ResponseEntity.badRequest().body(automovil); 
    	}
    	}
    	catch (ConstraintViolationException e) {
    		e.printStackTrace();
        	JsonObject obj = new JsonObject();
        	obj.addProperty("Error: ", 1);
        	obj.addProperty("Mensaje: ","Ocurrió un error al insertar el automovil. El campo variante no puede ser null.");
        	return ResponseEntity.badRequest().body(obj); 
    	}
    	catch (Exception e) {
    		e.printStackTrace();
        	JsonObject obj = new JsonObject();
        	obj.addProperty("Error: ", 1);
        	obj.addProperty("Mensaje: ","Ocurrió un error al insertar el automovil. Revisar conexión con la base de datos.");
        	return ResponseEntity.badRequest().body(obj); 
    	}
    }
    
    /**
   	 * @author AndresLizarraga
   	 * PUT API que modifica un objeto 'Automovil' en la base de datos a partir de su id.
   	 * @param id pasado como variable de ruta que representa el id del objeto 'Automovil' ya persistido en la base que se modificará.   
   	 * @param automovil representa un objeto de tipo 'Automovil' en formato json pasado como parametro de Body en la solicitud con las modificaciones solicitadas.
   	 * @return el objeto 'Automovil' modificado en la base de datos en formato json. **/
    @PutMapping (path= {"/modificarAutomovil/{id}"})
    public ResponseEntity<Object> modificarAutomovil(@PathVariable("id") Long id,
    		@RequestBody Automovil automovil) {
    	try {
    	Automovil automovil_ = daoAutomovil.getOne(id);
    	if (automovil_ != null) {
    		if(automovil.getAdicionales()!= null) {
    		
    			for (Adicionales ad : automovil_.getAdicionales()) {
        			daoAdicionales.delete(ad);
    			}
    			automovil_.removerAdicionales();
            	automovil_.setModelo(automovil.getModelo());
            	automovil_.setPlaca(automovil.getPlaca());
            	automovil_.setVariante(automovil.getVariante());
    			
    			for (Adicionales adicional_ : automovil.getAdicionales()) {
    				Adicionales adicional = new Adicionales();
    				adicional.setAutomovil(automovil_);
    				adicional.setOpcional(adicional_.getOpcional());
    				adicional.calcularCosto();
    				daoAdicionales.save(adicional);
    				automovil_.agregarAdicionales(adicional);
    			}
    		}    		
        	automovil_.calcularCostoFabricacion();
			daoAutomovil.save(automovil_);
 
            JsonObject obj = new JsonObject();
            obj.addProperty("Status: ", 0);
            obj.addProperty("Mensaje: ","Se modificó correctamente el automovil. ");
            return ResponseEntity.ok().body(automovil); 
    	}	else {
    		JsonObject obj = new JsonObject();
			obj.addProperty("Error: ",1);
			obj.addProperty("Mensaje: ", "No se pudo encontrar el registro a modificar.");
			return ((BodyBuilder) ResponseEntity.notFound()).body(obj);
    	}
    	}
    	catch (ConstraintViolationException e) {
    		e.printStackTrace();
        	JsonObject obj = new JsonObject();
        	obj.addProperty("Error: ", 1);
        	obj.addProperty("Mensaje: ","Ocurrió un error al modificar el automovil. El campo variante no puede ser null.");
        	return ResponseEntity.badRequest().body(obj); 
    	}
    	catch (EntityNotFoundException e) {
    		e.printStackTrace();
        	JsonObject obj = new JsonObject();
        	obj.addProperty("Error: ", 1);
        	obj.addProperty("Mensaje: ","Ocurrió un error al modificar el automovil. No se pudo encontrar la entidad buscada.");
        	return ResponseEntity.badRequest().body(obj); 
    	} catch (Exception e) {
    		e.printStackTrace();
    		JsonObject obj = new JsonObject();
        	obj.addProperty("Error: ", 1);
        	obj.addProperty("Mensaje: ","Ocurrió un error al insertar el automovil. Revisar cuerpo de la solicitud / conexión a la base de datos.");
        	return ResponseEntity.badRequest().body(obj);
    	}

    }
   
    /**
   	 * @author AndresLizarraga
   	 * DELETE API que elimina un objeto 'Automovil' de la base de datos a partir de su id.
   	 * @param id pasado como variable de ruta que representa el id del objeto 'Automovil' ya persistido en la base que se eliminará.   
   	 * @return un mensaje en formato json notificando el resultado de la solicitud. **/
	@DeleteMapping (path= {"/eliminarAutomovil/{id}"})
	public ResponseEntity<Object> eliminarAutomovil(@PathVariable("id") Long id) {
		try {
		Automovil auto = new Automovil(); 
		auto = daoAutomovil.getOne(id);
		
		if (auto.getModelo() != null) {
		daoAutomovil.delete(auto);
		
		JsonObject obj = new JsonObject();
		obj.addProperty("Error: ",0);
		obj.addProperty("Mensaje: ", "El Automovil con id " + id + " " + "ha sido eliminado correctamente.");
		return ResponseEntity.ok().body(obj);
		}
		else {
			JsonObject obj = new JsonObject();
			obj.addProperty("Error: ",1);
			obj.addProperty("Mensaje: ", "No se pudo encontrar el automovil con id:  " + id);
			return ((BodyBuilder) ResponseEntity.notFound()).body(obj);
		}
		}
		catch (EntityNotFoundException e) {
    		e.printStackTrace();
        	JsonObject obj = new JsonObject();
        	obj.addProperty("Error: ", 1);
        	obj.addProperty("Mensaje: ","Ocurrió un error al eliminar el automovil. No se pudo encontrar la entidad buscada.");
        	return ResponseEntity.badRequest().body(obj); 
		}
		catch (Exception e) {
			e.printStackTrace();
			JsonObject obj = new JsonObject();
			obj.addProperty("Error: ",1);
			obj.addProperty("Mensaje: ", "Ocurrio un problema al eliminar el automovil: " + id);
			return ((BodyBuilder) ResponseEntity.badRequest()).body(obj);
		}
		}
	
    /**
   	 * @author AndresLizarraga
   	 * GET API que retorna un objeto json con las estadísticas de todos los objetos 'Automovil' persistidos con sus diferentes características.   
   	 * @return un objeto json con todas estadísticas de los objetos de tipo 'Automovil' persistidos en la base con sus diferentes características. **/
	@GetMapping({"/stats"})
	public ResponseEntity<Object> getstats() {		
		 try {
		 JsonArray cars = new JsonArray();
		 JsonArray optionals = new JsonArray();
		 AutomovilService autoServ = new AutomovilService();
		 AdicionalesService adicServ= new AdicionalesService();
		 List<Automovil> getAutomoviles = daoAutomovil.findAll();
		 int cantidadAutomoviles = daoAutomovil.findAll().size();
		 if (getAutomoviles != null) {
			
			 for (Automovil auto : getAutomoviles) {
				 autoServ.contarVariantes(auto.getVariante());
				 for (Adicionales adic : auto.getAdicionales()) {
					 adicServ.contarAdicionales(adic.getOpcional());
				 }
			 }
			 
			 for (Variante var: Variante.values()) {
				 JsonObject aux2 = new JsonObject();
				 aux2.addProperty("model", var.getNombre());
				 aux2.addProperty("count", autoServ.getByVariante(var) != 0 ? autoServ.getByVariante(var) : 0);
				 aux2.addProperty("percent", autoServ.getByVariante(var) != 0 ? (autoServ.getByVariante(var)*100/cantidadAutomoviles) : 0);
				 cars.add(aux2);
			 }
			 
			 for (OpcionalesEnum op : OpcionalesEnum.values()) {
				JsonObject aux3 = new JsonObject();
				aux3.addProperty("optional", op.getNombre().toString());
				aux3.addProperty("count", adicServ.getByAdicional(op) != 0 ? adicServ.getByAdicional(op) : 0);
				aux3.addProperty("percent", adicServ.getByAdicional(op) != 0 ? adicServ.getByAdicional(op)*100/cantidadAutomoviles : 0);
				optionals.add(aux3);
			 }
			 
			 JsonObject aux = new JsonObject();
			 aux.addProperty("count_car", cantidadAutomoviles);
			 aux.add("cars", cars);
			 aux.add("optionals", optionals);
				return ResponseEntity.ok().body(aux);
			 
		 } else {
				JsonObject obj = new JsonObject();
				obj.addProperty("Error: ",1);
				obj.addProperty("Mensaje: ", "No encontraron automoviles para listar");
				return ((BodyBuilder) ResponseEntity.notFound()).body(obj);
		 }
		 } catch (Exception e) {
			 e.printStackTrace();
	        	JsonObject obj = new JsonObject();
	        	obj.addProperty("Error: ", 1);
	        	obj.addProperty("Mensaje: ","Ocurrió un error al consultar el servicio. Revisar conexión con la base de datos.");
	        	return ResponseEntity.badRequest().body(obj); 
	    	
		 }
	 } 
    
}
