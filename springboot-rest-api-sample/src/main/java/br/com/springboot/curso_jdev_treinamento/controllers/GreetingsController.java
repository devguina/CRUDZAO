package br.com.springboot.curso_jdev_treinamento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_jdev_treinamento.model.Usuario;
import br.com.springboot.curso_jdev_treinamento.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {

	@Autowired // IC/CD ou CDI - injeção de dependencias
	private UsuarioRepository usuarioRepository;

	/**
	 *
	 * @param name the name to greet
	 * @return greeting text
	 */
	@RequestMapping(value = "olamundo/{name}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String greetingText(@PathVariable String name) {

		Usuario usuario = new Usuario();
		usuario.setNome(name);

		usuarioRepository.save(usuario); // grava no banco de dados

		return "Hello World " + name + "!";
	}

	/*
	 * @RequestMapping(value = "teste/{name}", method = RequestMethod.GET)
	 * 
	 * @ResponseStatus(HttpStatus.OK) public String testando(@PathVariable String
	 * name) {
	 * 
	 * 
	 * Usuario usuario = new Usuario(); usuario.setNome(name);
	 * 
	 * usuarioRepository.save(usuario);
	 * 
	 * return "Testando " + name + "!"; }
	 */

	@GetMapping(value = "listatodos") // primeiro metodo da API, LISTA TODOS RETORNANDO UM JSON
	@ResponseBody // retorna os dados para o corpo da resposta (RETORNA UM JSON)
	public ResponseEntity<List<Usuario>> listaUsuario() {

		List<Usuario> usuarios = usuarioRepository.findAll();// executa a consulta no banco de dados

		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); // retorna a lista em JSON
	}

	@PostMapping(value = "salvar") // mapeia a url
	@ResponseBody // vai fazer a descrição da resposta
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) { // Recebe os dados para salvar
		Usuario user = usuarioRepository.save(usuario);
		return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "delete") // mapeia a url
	@ResponseBody // vai fazer a descrição da resposta
	public ResponseEntity<String> delete(@RequestParam Long idUser) { // Recebe os dados para deletar
		usuarioRepository.deleteById(idUser);
		return new ResponseEntity<String>("User deletado com sucesso", HttpStatus.CREATED);
	}

	@GetMapping(value = "buscaruserid") // mapeia a url
	@ResponseBody // vai fazer a descrição da resposta
	public ResponseEntity<Usuario> buscaruserid(@RequestParam(name = "idUser") Long idUser) { // Recebe os dados para
																								// consultar
		Usuario usuario = usuarioRepository.findById(idUser).get(); // busca no banco de dados
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}

	@PutMapping(value = "atualizar") // mapeia a url
	@ResponseBody // vai fazer a descrição da resposta
	public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) { // Recebe os dados para salvar

		if (usuario.getId() == null) {
			return new ResponseEntity<String>("Id não foi informado para atualização", HttpStatus.OK);

		}
		Usuario user = usuarioRepository.saveAndFlush(usuario);
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}

	@GetMapping(value = "buscarpornome") // mapeia a url
	@ResponseBody // vai fazer a descrição da resposta
	public ResponseEntity<List<Usuario>> buscarpornome(@RequestBody @RequestParam(name = "name") String name) { // Recebe os dados

		List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());

		return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
	}
}
