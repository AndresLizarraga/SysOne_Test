package com.sysone.devtest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sysone.devtest.model.Variante;
import com.sysone.devtest.service.AutomovilService;

@RunWith(SpringJUnit4ClassRunner.class)
class DevTestServicesTests {
	
	private final AutomovilService autoServ = new AutomovilService();
	
	private final Variante variante = Variante.FAMILIAR;
	
	@Test
	public void testAutomovilServiceContarVariantesisOk() {
		autoServ.contarVariantes(variante);
		assertEquals(1, autoServ.getContadorFamiliar());
	}
	
	@Test
	public void testAutomovilServiceGetByVariantesisOk() {
		autoServ.contarVariantes(variante);
		autoServ.getByVariante(variante);
		assertEquals(1, autoServ.getContadorFamiliar());
	}
}
