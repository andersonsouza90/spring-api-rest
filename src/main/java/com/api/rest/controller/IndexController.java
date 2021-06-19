package com.api.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.rest.model.Usuario;
import com.api.rest.repository.UsuarioRepository;

@CrossOrigin(origins = "*")
@RestController /*Arquitetura REST*/
@RequestMapping(value = "/usuario")
public class IndexController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping(value = "/{id}/relatoriopdf", produces = "application/json")
	public ResponseEntity relatorio(@PathVariable(value = "id") Long id) {
		
		Optional<Usuario> u = usuarioRepository.findById(id);
		
		return new ResponseEntity<Usuario>(u.get(), HttpStatus.OK);
	}
	
	//@PathVariable Recebe parametros na arquitetura REST
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity init(@PathVariable(value = "id") Long id) {
		
		Optional<Usuario> u = usuarioRepository.findById(id);
		
		return new ResponseEntity<Usuario>(u.get(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<Usuario>> usuario(){
		List<Usuario> listaUsuario = (List<Usuario>) usuarioRepository.findAll();
		
		return new ResponseEntity<List<Usuario>>(listaUsuario, HttpStatus.OK);
	}
	
	@PostMapping(value = "/", produces = "application/json")
	public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario u){
		
		//Não está sendo gravado o id usuario na tabela telefone
		//para contornar isso, varrer a lista de telefone e associar os ids aqui		
		for(int pos = 0; pos < u.getTelefones().size(); pos++) {
			u.getTelefones().get(pos).setUsuario(u);
		}
		
		Usuario usuarioSalvo = usuarioRepository.save(u);
		
		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
	}
	
	@PutMapping(value = "/", produces = "application/json")
	public ResponseEntity<Usuario> atualizar(@RequestBody Usuario u){
		
		//Não está sendo gravado o id usuario na tabela telefone
		//para contornar isso, varrer a lista de telefone e associar os ids aqui		
		for(int pos = 0; pos < u.getTelefones().size(); pos++) {
			u.getTelefones().get(pos).setUsuario(u);
		}
		
		Usuario usuarioSalvo = usuarioRepository.save(u);
		
		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
	}
	
	@PostMapping(value = "/{iduser}/idvenda/{idvenda}", produces = "application/json")
	public ResponseEntity cadastrarvenda(@PathVariable Long iduser,
												  @PathVariable Long idvenda){
		
		return new ResponseEntity("Id user: " + iduser + " idvenda: " + idvenda, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{iduser}", produces = "application/json")
	public ResponseEntity excluir(@PathVariable Long iduser) {
		usuarioRepository.deleteById(iduser);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	 //RequestParam Recebe parametros da url ?nome=teste
	/*@GetMapping(value = "/", produces = "application/json")	
	public ResponseEntity init(@RequestParam(value = "nome", required = true, defaultValue = "Nome não informado!") String nome,
							   @RequestParam(value = "salario", required = true, defaultValue = "0") Long salario
			) {
		return new ResponseEntity("Spring Boot REST API: " + nome + " Salário: " + salario, HttpStatus.OK);
	}*/
	
	//Retornando objetos
	/*@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity init() {
		
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		usuario.setNome("Dandy");
		usuario.setLogin("dandy@gmail.com.br");
		usuario.setSenha("123");
		
		Usuario usuario2 = new Usuario();
		usuario2.setId(2L);
		usuario2.setNome("Carina ");
		usuario2.setLogin("adm.carinasouza@gmail.com");
		usuario2.setSenha("123");
		
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios.add(usuario);
		usuarios.add(usuario2);
		
		return ResponseEntity.ok(usuarios);
	}*/
		
}
