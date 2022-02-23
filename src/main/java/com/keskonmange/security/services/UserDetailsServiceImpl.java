package com.keskonmange.security.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keskonmange.entities.Utilisateur;
import com.keskonmange.repository.JpaUtilisateur;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	JpaUtilisateur jpaUtilisateur;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Utilisateur user = jpaUtilisateur.findByEmail(username).get();
		if (user == null)
			throw new UsernameNotFoundException("Utilisateur introuvable!");
				
		return UserDetailsImpl.build(user);

	}

}
