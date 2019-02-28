package fr.eni.sortir.dao;

import java.util.Collection;

import fr.eni.sortir.entities.Token;

public interface TokenDao {
    Token addToken(Token token);

    Token findToken(Integer noTOken);

    Token updateToken(Token token);

    Boolean removeToken(Integer noToken);

    Collection<Token> getAllToken();

    Token findTokenByMailAndToken(String mail, String strToken);
}
