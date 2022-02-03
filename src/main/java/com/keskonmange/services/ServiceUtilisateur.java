package com.keskonmange.services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.keskonmange.entities.Utilisateur;
import com.keskonmange.repository.JpaUtilisateur;

@Controller
public class ServiceUtilisateur {

	@Autowired
	JpaUtilisateur ju;



	public Optional<Utilisateur> findById(Integer pid){
		return ju.findById(pid);
	}

	public Iterable<Utilisateur> findAll(){
		return ju.findAll();
	}

	public Utilisateur save(Utilisateur utilisateur){

		cryptageMotdePasse(utilisateur);


		return ju.save(utilisateur);
	}

	private void cryptageMotdePasse(Utilisateur utilisateur)  {
		try {
			String str = "azerty";
			MessageDigest msg;

			msg = MessageDigest.getInstance("SHA-256");

			byte[] hash = msg.digest(str.getBytes(StandardCharsets.UTF_8));

			StringBuilder s = new StringBuilder();
			for (byte b : hash) {
				s.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
			}

			utilisateur.setPwd(s.toString());

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteById(Integer pid){
		ju.deleteById(pid);
	}


}
