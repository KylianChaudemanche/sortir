package fr.eni.sortir.messages;

import java.util.ResourceBundle;

public class MsgReader {
    private static ResourceBundle rb;

    static {
	try {
	    rb = ResourceBundle.getBundle("fr.eni.sortir.messages.messages");
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private MsgReader() {

    }

    /**
     * @param code
     * @return
     */
    public static String getMessage(String key) {
	String message = "";
	try {
	    if (rb != null) {
		message = rb.getString(key);
	    } else {
		message = "Problème à la lecture du fichier contenant les messages";
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    message = "Une erreur inconnue est survenue";
	}
	return message;
    }
}
