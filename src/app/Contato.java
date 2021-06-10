package app;

public class Contato {

	private String nome;
	private String telefone;
	
	public Contato(String pNome, String pTelefone) {
		nome = pNome;
		telefone = pTelefone;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
}