package com.sysone.devtest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sysone.devtest.controller.DevTestController;
import com.sysone.devtest.model.Adicionales;
import com.sysone.devtest.model.Automovil;
import com.sysone.devtest.model.OpcionalesEnum;
import com.sysone.devtest.model.Variante;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = DevTestController.class)
public class DevTestControllerTest {
	  
		  @MockBean
	      DevTestController devTestController;
	  
		  @Autowired
		  private MockMvc mockMvc;
		  
		  @Test
		  void whenValidGetRequest_thenReturns200() throws Exception {
			    mockMvc.perform(MockMvcRequestBuilders
			    	      .get("/getAutomoviles"))
			     	  .andExpect(status().isOk());
		  }
		  
		  @Test
		  void whenValidGetStatisticsRequest_thenReturns200() throws Exception {
			    mockMvc.perform(MockMvcRequestBuilders
			    	      .get("/stats"))
			     	  .andExpect(status().isOk());
		  }
		  
		   @Test
		   void whenValidInputOnPost_thenReturns200() throws Exception {
		    Automovil auto = new Automovil();
		    Adicionales adic = new Adicionales();
		    adic.setOpcional(OpcionalesEnum.AA);
		    adic.setAutomovil(auto);
		    List<Adicionales> adicionales = new ArrayList<Adicionales>();
		    auto.setModelo("Optra");
		    auto.setPlaca("RTX-280");
		    auto.setVariante(Variante.FAMILIAR);
		    adicionales.add(adic);
		    auto.setAdicionales(adicionales);
		    JsonArray json = new JsonArray();
		    JsonObject aux = new JsonObject();
		    aux.addProperty("modelo", auto.getModelo());
		    aux.addProperty("placa", auto.getPlaca());
		    aux.addProperty("variante", auto.getVariante().toString());
		    JsonObject aux2 = new JsonObject();
		    aux2.addProperty("opcional", adic.getOpcional().toString());
		    json.add(aux2);
		    
		    aux.add("adicionales", json);
		    
		    mockMvc.perform(MockMvcRequestBuilders
		    	      .post("/crearAutomovil")
		          .contentType("application/json")
		          .content(aux.toString()))
		     	  .andExpect(status().isOk());
		  }
		   
		   @Test
		   void whenValidInputOnPut_thenReturns200() throws Exception {
		    Automovil auto = new Automovil();
		    Adicionales adic = new Adicionales();
		    adic.setOpcional(OpcionalesEnum.AA);
		    adic.setAutomovil(auto);
		    List<Adicionales> adicionales = new ArrayList<Adicionales>();
		    auto.setModelo("Optra");
		    auto.setPlaca("RTX-280");
		    auto.setVariante(Variante.FAMILIAR);
		    adicionales.add(adic);
		    auto.setAdicionales(adicionales);
		    JsonArray json = new JsonArray();
		    JsonObject aux = new JsonObject();
		    aux.addProperty("modelo", auto.getModelo());
		    aux.addProperty("placa", auto.getPlaca());
		    aux.addProperty("variante", auto.getVariante().toString());
		    JsonObject aux2 = new JsonObject();
		    aux2.addProperty("opcional", adic.getOpcional().toString());
		    json.add(aux2);
		    
		    aux.add("adicionales", json);
		    
		    mockMvc.perform(MockMvcRequestBuilders
		    	      .put("/modificarAutomovil/1")
		          .contentType("application/json")
		          .content(aux.toString()))
		     	  .andExpect(status().isOk());
		  }
		   
		   @Test
		   void whenValidDeleteRequest_thenReturns200() throws Exception {
				    mockMvc.perform(MockMvcRequestBuilders
				    	      .delete("/eliminarAutomovil/1/"))
				     	  .andExpect(status().isOk());
			}
}
