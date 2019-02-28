package fr.eni.sortir.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TOKENS")
public class Token implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue
    @Column(name = "no_token")
    private Integer noToken;
    private String mail;
    private String token;
    @Column(name = "dateexpiration")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateExpiration;
    
    public Token() {
		super();
	}
	public Token(String mail, String token, Date dateExpiration) {
		super();
		this.mail = mail;
		this.token = token;
		this.dateExpiration = dateExpiration;
	}
	
	public Integer getNoToken() {
		return noToken;
	}
	public void setNoToken(Integer noToken) {
		this.noToken = noToken;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getDateExpiration() {
		return dateExpiration;
	}
	public void setDateExpiration(Date dateExpiration) {
		this.dateExpiration = dateExpiration;
	}
	@Override
	public String toString() {
		return "Token [noToken=" + noToken + ", mail=" + mail + ", token=" + token + ", dateExpiration="
				+ dateExpiration + "]";
	}
}