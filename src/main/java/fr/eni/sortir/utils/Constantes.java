package fr.eni.sortir.utils;

public final class Constantes {

    public static final String DATA_PATH = "D:\\data\\files\\";
    public static final String CSV_PATH = "D:\\data\\CSV\\";
    public static final String SALT = "saperlipopette";
    public static final String PU = "pu";    
    
    /*
     *  --- ALERT MESSAGES ---
     */
    public static final String MESSAGE = "message";
    public static final String TYPE_MESSAGE = "typeMessage";
    public static final String MSG_TYPE_SUCCESS = "succes";
    public static final String MSG_TYPE_WARNING= "warning";
    public static final String MSG_TYPE_DANGER= "danger";
    
    /*
     *  --- VALIDATION FORMULAIRE
     */
    public static final String MSG_DEUX_MDP_IDENTIQUES =  "Vos deux mot de passes ne sont pas identiques";
    public static final String MSG_MDP_INVALIDE =  "Votre mot de passe ne correspond pas au modèle requis";

    /*
     *  --- MESSAGES SESSION
     */
    public static final String MSG_SESSION_EXPIRE = "La durée de validité de votre session a expiré";
    public static final String MSG_SESSION_INVALIDE = "Votre ticket de session est invalide";
    public static final String MSG_SESSION_INEXISTANTE = "Votre ticket de session n'éxiste pas ou plus";
    
    /*
     *  --- MESSAGES AUTRES
     */
    public static final String MSG_MDP_REINITIALISE = "votre mot de passe a été modifié avec succès";
    public static final String MSG_MAIL_ENVOYE = "Un lien à été envoyé à votre adresse pour réinitialiser votre mot de passe";

    /*
     *  ---  AUTRES CONSTANTES
     */
    public static final long ONE_MINUTE_IN_MILLIS=60000;//millisecs
}
