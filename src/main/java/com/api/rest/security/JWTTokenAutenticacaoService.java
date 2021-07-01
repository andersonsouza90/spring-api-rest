package com.api.rest.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.api.rest.ApplicationContextLoad;
import com.api.rest.model.Usuario;
import com.api.rest.repository.UsuarioRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Component
public class JWTTokenAutenticacaoService {
	
	//@Autowired
	//private UsuarioRepository usuarioRep; //Vou usar o contexto da aplicação para instanciar o repotório
	
	/*Tem de validade do Token 2 dias*/
	private static final long EXPIRATION_TIME = 172800000;
	
	/*Uma senha unica para compor a autenticacao e ajudar na segurança*/
	private static final String SECRET = "SenhaExtremamenteSecreta";
	
	/*Prefixo padrão de Token*/
	private static final String TOKEN_PREFIX = "Bearer";
	
	private static final String HEADER_STRING = "Authorization";
	
	//gerando token de autenticação e adicionando o cabeçalho e resposta http
	/*Gerando token de autenticado e adiconando ao cabeçalho e resposta Http*/
	public void addAuthentication(HttpServletResponse response , String username) throws IOException {
		
		/*Montagem do Token*/
		String JWT = Jwts.builder() /*Chama o gerador de Token*/
				        .setSubject(username) /*Adicona o usuario*/
				        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) /*Tempo de expiração*/
				        .signWith(SignatureAlgorithm.HS512, SECRET).compact(); /*Compactação e algoritmos de geração de senha*/
		
		/*Junta token com o prefixo*/
		String token = TOKEN_PREFIX + " " + JWT; /*Bearer 87878we8we787w8e78w78e78w7e87w*/
		
		/*Adiciona no cabeçalho http*/
		response.addHeader(HEADER_STRING, token); /*Authorization: Bearer 87878we8we787w8e78w78e78w7e87w*/
		
		/*Escreve token como responsta no corpo http*/
		response.getWriter().write("{\"Authorization\": \""+token+"\"}");
		
	}
	
	//Retonar o usuário validado com token ou retorna null
	public Authentication getAuthentication(HttpServletRequest request) {
		//Pega o token enviado no cabeçalho http
				String token = request.getHeader(HEADER_STRING);
				
				if(token != null) {
					
					//faz validação do token do usuário na requisição
					String user = Jwts.parser().setSigningKey(SECRET)
									  .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
								      .getBody().getSubject();
					
					if(user != null) {
						
						Usuario u = ApplicationContextLoad.getApplicationContext()
									.getBean(UsuarioRepository.class).findUserByLogin(user);
						
						if(u != null) {
							return new UsernamePasswordAuthenticationToken(
																		u.getLogin(), 
																		u.getSenha(), 
																		u.getAuthorities());
						}				
					}			
				}
				
				return null; //não autorizado
	}
	
}
