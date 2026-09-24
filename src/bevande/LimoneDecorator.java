package bevande;

import sistema.Distributore;

/**
 * Decorator per aggiungere Limone a una {@link Bevanda}.
 * <p>
 * Questa classe estende {@link BevandaDecorator} e rappresenta l'aggiunta
 * del limone a una bevanda base. Il prezzo del limone viene recuperato
 * dinamicamente dal {@link sistema.Distributore}
 * </p>
 */
public class LimoneDecorator extends BevandaDecorator {
    private static final long serialVersionUID = -3202148330979134633L;

    /**
     * Costruisce un {@code LimoneDecorator} che avvolge una bevanda esistente,
     * aggiungendo l'ingrediente "Limone". Il prezzo moltiplicato per 2 visto che il suo 
     * prezzo vale solo come aggiunta
     *
     * @param bevanda la bevanda base a cui aggiungere il limone
     */
    public LimoneDecorator(Bevanda bevanda) {
        super(bevanda, new Limone(Distributore.getInstance().getPrezzo("Limone") * 2));
    }
}
