package com.sysone.devtest;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sysone.devtest.model.Adicionales;
import com.sysone.devtest.model.Automovil;
import com.sysone.devtest.model.OpcionalesEnum;
import com.sysone.devtest.model.Variante;
import com.sysone.devtest.repository.DaoAdicionales;
import com.sysone.devtest.repository.DaoAutomovil;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class DevTestDatabaseTest {
	
	   @Autowired
	   private TestEntityManager entityManager;

	   @Autowired
	   private DaoAutomovil daoAuto;
	   
	   @Autowired
	   private DaoAdicionales daoAdic;
	   
	   private final OpcionalesEnum opcional = OpcionalesEnum.AA;

	   @Test
	   public void whenFindAllAutomoviles() {
		   Automovil auto = new Automovil();
		   auto.setModelo("Optra");
		   auto.setVariante(Variante.FAMILIAR);
		   entityManager.persist(auto);
		   entityManager.flush();
		   Automovil auto2 = new Automovil();
		   auto2.setModelo("Corsa");
		   auto2.setVariante(Variante.SEDAN);
		   entityManager.persist(auto2);
		   entityManager.flush();
		   List <Automovil> autos = daoAuto.findAll();
		   assertTrue(autos.size()>0);
	   }
	
	   @Test
	   public void whenFindAllAdicionales() {
		   Automovil auto = new Automovil();
		   auto.setModelo("Optra");
		   auto.setVariante(Variante.FAMILIAR);
		   Adicionales adic = new Adicionales();
		   adic.setAutomovil(auto);
		   adic.setOpcional(opcional);
		   entityManager.persist(auto);
		   entityManager.persist(adic);
		   entityManager.flush();
		   
		   Automovil auto2 = new Automovil();
		   auto2.setModelo("Corsa");
		   auto2.setVariante(Variante.SEDAN);
		   Adicionales adic2 = new Adicionales();
		   adic.setAutomovil(auto2);
		   adic.setOpcional(opcional);
		   entityManager.persist(auto2);
		   entityManager.persist(adic2);
		   entityManager.flush();
		   
		   List <Adicionales> adicionales = daoAdic.findAll();
		   
		   assertTrue(adicionales.size()>0);
	   }
	   
	   @Test
	   public void whenFindByIdAutomovil() {
		   Automovil auto = new Automovil();
		   auto.setModelo("Optra");
		   auto.setVariante(Variante.COUPE);
		   Adicionales adic = new Adicionales();
		   adic.setAutomovil(auto);
		   entityManager.persist(auto);
		   entityManager.flush();
		   Automovil auto2 = daoAuto.getOne(auto.getId());
		   assertTrue(auto2!=null);
	   }
	   
}
