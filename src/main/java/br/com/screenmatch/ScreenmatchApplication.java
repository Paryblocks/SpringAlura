package br.com.screenmatch;

import br.com.screenmatch.principal.serie.Principal;
import br.com.screenmatch.principal.veiculo.PrincipalDesafio;
import br.com.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	@Autowired
	private SerieRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principalScreenMatch = new Principal(repository);
		PrincipalDesafio principalVeiculo = new PrincipalDesafio();
		principalScreenMatch.exibeMenu();
	}
}
