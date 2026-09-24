package sistema;

import bevande.AcquaCalda;
import bevande.Bevanda;
import bevande.Caffe;
import bevande.Camomilla;
import bevande.Cioccolata;
import bevande.Latte;
import bevande.Limone;
import bevande.The;
import bevande.Zucchero;

public class BevandaCreator {
	//metodi per creare una bevanda dal nome
	/*
	 * @param String
	 * @return Bevanda
	 */
	public static Bevanda fromString(String s) {
	    switch (s) {
	        case "Caffe'":
	            return new Caffe(0); // prezzo temporaneo (sovrascritto dal JSON)
	        case "Latte":
	            return new Latte(0);
	        case "The'":
	            return new The(0);
	        case "Camomilla":
	            return new Camomilla(0);
	        case "Cioccolata":
	            return new Cioccolata(0);
	        case "Acqua Calda":
	            return new AcquaCalda(0);
	        case "Zucchero":
	            return new Zucchero(0);
	        case "Limone":
	            return new Limone(0);
	        default:
	        	throw new IllegalArgumentException("Bevanda sconosciuta: " + s); 
	    }
	}
	/*
	 * @param String
	 * @param double
	 * @return Bevanda
	 */
	public static Bevanda fromString(String s,double prezzo)
    {
    	    switch (s) {
    	        case "Caffe'":
    	            return new Caffe(prezzo);
    	        case "Latte":
    	            return new Latte(prezzo);
    	        case "The'":
    	            return new The(prezzo);
    	        case "Camomilla":
    	            return new Camomilla(prezzo);
    	        case "Cioccolata":
    	            return new Cioccolata(prezzo);
    	        case "Acqua Calda":
    	            return new AcquaCalda(prezzo);
    	        case "Zucchero":
    	            return new Zucchero(prezzo);
    	        case "Limone":
    	        	return new Limone(prezzo);
    	        default:
    	            throw new IllegalArgumentException("Bevanda sconosciuta: " + s);
    	    }
    }

}
