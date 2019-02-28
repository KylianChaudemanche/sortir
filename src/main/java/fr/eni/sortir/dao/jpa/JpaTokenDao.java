package fr.eni.sortir.dao.jpa;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import fr.eni.sortir.dao.TokenDao;
import fr.eni.sortir.entities.Token;

public class JpaTokenDao extends JpaDao implements TokenDao {
	private static final Logger LOGGER = Logger.getLogger(JpaTokenDao.class.getName());
	private final String QUERY_ALL_TOKEN = "SELECT t FROM Token AS t";
	private final String QUERY_ALL_TOKEN_BY_MAIL_AND_TOKEN = "SELECT t FROM Token AS t WHERE mail = :mail AND token = :token";

	public JpaTokenDao(EntityManagerFactory emf) {
		super(emf);
	}

	@Override
	public Token addToken(Token token) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			em.persist(token);
			em.flush();
			transaction.commit();
		} catch (IllegalStateException | PersistenceException | IllegalArgumentException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			token = null;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			em.close();
		}
		return token;
	}

	@Override
	public Token findToken(Integer noTOken) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Token token = null;
		try {
			token = em.find(Token.class, noTOken);
		} catch (IllegalStateException | IllegalArgumentException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			em.close();
		}
		return token;
	}

	@Override
	public Token updateToken(Token token) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			em.merge(token);
			transaction.commit();
		} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			token = null;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			em.close();
		}
		return token;
	}

	@Override
	public Boolean removeToken(Integer noToken) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		Token token = null;
		try {
			token = em.find(Token.class, noToken);
			if (token != null) {
				transaction.begin();
				em.remove(token);
				transaction.commit();
			}
		} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			token = null;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			em.close();
		}
		return true;
	}

	@Override
	public Collection<Token> getAllToken() {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Collection<Token> listToken = null;
		try {
			listToken = em.createQuery(QUERY_ALL_TOKEN, Token.class).getResultList();
		} catch (IllegalStateException | PersistenceException | IllegalArgumentException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			em.close();
		}
		return listToken;
	}

	@Override
	public Token findTokenByMailAndToken(String mail, String strToken) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Token token = null;
		try {
			TypedQuery<Token> query = em.createQuery(QUERY_ALL_TOKEN_BY_MAIL_AND_TOKEN
					,Token.class)
					.setParameter("mail", mail)
					.setParameter("token", strToken);
			token = query.getSingleResult();
		} catch (IllegalStateException | IllegalArgumentException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		} catch(PersistenceException e){
			if(javax.persistence.NoResultException.class.equals(e.getClass())) {
				return null;
			}
		}finally {
			em.close();
		}
		return token;
	}
	
	

}
