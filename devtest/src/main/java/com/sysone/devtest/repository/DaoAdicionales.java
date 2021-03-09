package com.sysone.devtest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sysone.devtest.model.Adicionales;
import com.sysone.devtest.model.Automovil;

public interface DaoAdicionales extends JpaRepository<Adicionales, Long> {

	@Modifying
	@Query("delete from Adicionales a where a.automovil=null")
	void deleteAdicional();
	
	List<Adicionales> findByAutomovil(Automovil automovil);
}
