
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

/**
 * <p>
 * Implantation de l'interface Collection basée sur les arbres binaires de
 * recherche. Les éléments sont ordonnés soit en utilisant l'ordre naturel (cf
 * Comparable) soit avec un Comparator fourni à la création.
 * </p>
 *
 * <p>
 * Certaines méthodes de AbstractCollection doivent être surchargées pour plus
 * d'efficacité.
 * </p>
 *
 * @param <E>
 *            le type des clés stockées dans l'arbre
 */
public class ABR<E> extends AbstractCollection<E> {
    private Noeud racine;
    private int taille;
    private Comparator<? super E> cmp;

    private class Noeud {
        E cle;
        Noeud gauche;
        Noeud droit;
        Noeud pere;

        Noeud(E cle) {
            this.cle = cle;
            this.gauche = null;
            this.droit = null;
            this.pere = null;
        }

        /**
         * Renvoie le noeud contenant la clé minimale du sous-arbre enraciné
         * dans ce noeud
         *
         * @return le noeud contenant la clé minimale du sous-arbre enraciné
         *         dans ce noeud
         */
        Noeud minimum() {
            // TODO
            Noeud x = this;
            while (x.gauche != null) {
                x = x.gauche;
            }
            return x;
        }

        /**
         * Renvoie le successeur de ce noeud
         *
         * @return le noeud contenant la clé qui suit la clé de ce noeud dans
         *         l'ordre des clés, null si c'es le noeud contenant la plus
         *         grande clé
         */
        Noeud suivant() {
            // TODO
            if (droit != null) {
                return droit.minimum();
            }
            Noeud y = pere;
            Noeud x = this;
            while (y != null && x == y.droit) {
                x = y;
                y = y.pere;
            }
            return y;
        }
    }

    // Consructeurs

    /**
     * Crée un arbre vide. Les éléments sont ordonnés selon l'ordre naturel
     */
    public ABR() {
        // TODO
        racine = null;
        taille = 0;
        cmp = (a, b) -> ((Comparable<E>) a).compareTo(b);
    }

    /**
     * Crée un arbre vide. Les éléments sont comparés selon l'ordre imposé par
     * le comparateur
     *
     * @param cmp
     *            le comparateur utilisé pour définir l'ordre des éléments
     */
    public ABR(Comparator<? super E> cmp) {
        // TODO
        racine = null;
        taille = 0;
        cmp = (a, b) -> ((Comparable<E>) a).compareTo(b);

    }

    /**
     * Constructeur par recopie. Crée un arbre qui contient les mêmes éléments
     * que c. L'ordre des éléments est l'ordre naturel.
     *
     * @param c la collection à copier
     */
    public ABR(Collection<? extends E> c) {
        this();
        addAll(c);
    }


    /**
     * Ajoute l'élément dans l'arbre rouge et noir
     *
     * @param element
     *            l'élément à ajouter
     *
     * @return vrai si l'élément a été ajouté
     */
    @Override
    public boolean add(E element) {
        Noeud z = new Noeud(element);
        if(!this.contains(z.cle))
        {inserer(z);
            return true;}
        else return false;
    }
    /**
     * inserer un noeud. Cette méthode peut être utilisée par
     * {@link #add(E)}
     *
     * @param z
     *  le noeud à ajouter
     *
     */

    private void inserer(Noeud z) {
        Noeud y = null;
        Noeud x = racine;
        while (x != null) {
            y = x;
            x = cmp.compare(z.cle, x.cle) < 0 ? x.gauche : x.droit;
        }
        z.pere = y;
        if (y == null) {
            racine = z;
        } else if (cmp.compare(z.cle, y.cle) < 0) {
            y.gauche = z;
        } else if (cmp.compare(z.cle, y.cle) > 0) {
            y.droit = z;
        }
        taille++;
    }
    /**
     * Ajoute une collection dans un arbre rouge et noir
     *
     * @param c
     *      la collection à ajouter
     *
     * @return vrai si la collection a été ajouté
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modif = false;
        for (E e : c) {
            if (add(e)) {
                modif = true;
            }
        }
        return modif;
    }

    @Override
    public Iterator<E> iterator() {
        return new ABRIterator();
    }

    @Override
    public int size() {
        return taille;
    }

    // Quelques méthodes utiles

    /**
     * Recherche une clé. Cette méthode peut être utilisée par
     * {@link #contains(Object)} et {@link #remove(Object)}
     *
     * @param o la clé à chercher
     * @return le noeud qui contient la clé ou null si la clé n'est pas trouvée.
     */
    private Noeud rechercher(Object o) {
        // TODO
        Noeud x = racine;
        while (x != null && cmp.compare((E) o, x.cle) != 0) {
            x = cmp.compare((E) o, x.cle) < 0 ? x.gauche : x.droit;
        }
        return x;
    }
    /**
     * Recherche si l'objet est dans l'arbre rouge et noir
     *
     * @param o
     *            l'objet à rechercher
     *
     * @return vrai si un noeud contient l'objet
     */
    @Override
    public boolean contains(Object o) {
        return rechercher(o) != null;
    }
    @Override
    public boolean containsAll(Collection<?> c) {
        boolean retour=true;
        for (Object e : c) {
            if (!this.contains(e)) {
                retour=false;
            }
        }
        return retour;
    }


    /**
     * Supprime le noeud z. Cette méthode peut être utilisée dans
     * {@link #remove(Object)} et {@link Iterator#remove()}
     *
     * @param z
     *            le noeud à supprimer
     * @return le noeud contenant la clé qui suit celle de z dans l'ordre des
     *         clés. Cette valeur de retour peut être utile dans
     *         {@link Iterator#remove()}
     */
    private Noeud supprimer(Noeud z) {
        Noeud y;
        Noeud x;
        if (z.gauche == null || z.droit == null) {
            y = z;
        } else {
            y = z.suivant();
        }
        if (y.gauche != null) {
            x = y.gauche;
        } else {
            x = y.droit;
        }
        if (x != null) {
            x.pere = y.pere;
        }
        if (y.pere == null) {
            racine = x;
        } else if (y == y.pere.gauche) {
            y.pere.gauche = x;
        } else {
            y.pere.droit = x;
        }
        if (y != z) {
            z.cle = y.cle;
        }
        taille--;
        return y;
    }
    /**
     *
     * Supprime tous les éléments de cette arbre
     * L'arbre sera vide après le retour de cette méthode.
     *
     * */
    public void clear() {
        for (E e : this) {
            this.remove(e);
        }
    }
    /**
     *cette méthode supprime tous les élement commun entre l'arbre et la collection passé en paramétre
     *
     */

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modif = false;
        for (Object e : c) {
            if (remove(e)) {
                modif = true;
            }
        }
        return modif;
    }


    /**
     * Les itérateurs doivent parcourir les éléments dans l'ordre ! Ceci peut se
     * faire facilement en utilisant {@link Noeud#minimum()} et
     * {@link Noeud#suivant()}
     */
    private class ABRIterator implements Iterator<E> {
        private Noeud precedent;
        private Noeud suivant;

        public ABRIterator() {
            precedent = null;
            suivant = racine.minimum();
        }


        public boolean hasNext() {
            return suivant != null;
        }

        public E next() throws IllegalStateException {
            if (!hasNext()) {
                throw new IllegalStateException();
            }
            precedent = suivant;
            suivant = suivant.suivant();
            return precedent.cle;

        }

        public void remove() throws IllegalStateException {
            if (precedent == null) {
                throw new IllegalStateException();
            }
            supprimer(precedent);
            precedent = null;

        }
    }

    // Pour un "joli" affichage

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        toString(racine, buf, "", maxStrLen(racine));
        return buf.toString();
    }

    private void toString(Noeud x, StringBuffer buf, String path, int len) {
        if (x == null)
            return;
        toString(x.droit, buf, path + "D", len);
        for (int i = 0; i < path.length(); i++) {
            for (int j = 0; j < len + 6; j++)
                buf.append(' ');
            char c = ' ';
            if (i == path.length() - 1)
                c = '+';
            else if (path.charAt(i) != path.charAt(i + 1))
                c = '|';
            buf.append(c);
        }
        buf.append("-- " + x.cle.toString());
        if (x.gauche != null || x.droit != null) {
            buf.append(" --");
            for (int j = x.cle.toString().length(); j < len; j++)
                buf.append('-');
            buf.append('|');
        }
        buf.append("\n");
        toString(x.gauche, buf, path + "G", len);
    }

    private int maxStrLen(Noeud x) {
        return x == null ? 0 : Math.max(x.cle.toString().length(),
                Math.max(maxStrLen(x.gauche), maxStrLen(x.droit)));
    }

    // TODO : voir quelles autres méthodes il faut surcharger

    // addAll(),add(),containsAll(),contains(),clear(),removeAll()
}