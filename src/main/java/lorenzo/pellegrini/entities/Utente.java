package lorenzo.pellegrini.entities;
import jakarta.persistence.*;

@Entity
@Table(name="utenti")
public class Utente {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

@Column(nullable = false)
    private String nome;

@Column(nullable = false)
    private String cognome;

@Column(unique = true, nullable = false)
    private String email;

    @OneToOne(mappedBy = "utente",cascade = CascadeType.ALL)
    private Tessera tessera;

    public Utente(){}

    public Utente(String nome, String cognome, String email){
        this.nome=nome;
        this.cognome=cognome;
        this.email=email;
    }
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Tessera getTessera() { return tessera; }
    public void setTessera(Tessera tessera) { this.tessera = tessera; }

}
