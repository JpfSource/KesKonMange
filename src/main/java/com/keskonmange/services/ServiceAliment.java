package com.keskonmange.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keskonmange.entities.Aliment;
import com.keskonmange.repository.JpaAliment;

@Service
public class ServiceAliment {

	@Autowired
	JpaAliment ja;
	
	public Optional<Aliment> findById(Integer pid){
		return ja.findById(pid);
	}

	public Iterable<Aliment> findAll(){
		return ja.findAll();
	}
	
	public Aliment save(Aliment aliment){
		return ja.save(aliment);
	}

	public void deleteById(Integer pid){
		ja.deleteById(pid);
	}

	public void getPlats(Integer pid){
		ja.getPlats(pid);
	}
}
