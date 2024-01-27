import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
public class ARN<E> extends AbstractCollection<E> {
    //les attributs
    private Noeud racine;
    private int taille;
    Noeud sentinelle;
    private Comparator<? super E> cmp;
    private class Noeud {
        //les attributs
        E cle;
        Noeud gauche;
        Noeud droit;
        Noeud pere;
        char couleur;
        // Constructeurs de Noeud
        /**
         * Crée un noeud Noir N avec une clé en paramètre. Le fils gauche,
         * le fils droit et le père pointent sur la sentinelle
         */
        Noeud(E cle) {
            this.cle = cle;
            this.gauche = sentinelle;
            this.droit = sentinelle;
            this.pere = sentinelle;
            this.couleur ='N';
        }
        /**
         * Crée un noeud  avec une clé et couleur en paramètre. Le fils gauche,
         * le fils droit et le père pointent sur la sentinelle
         */
        Noeud(E cle, char coleur) {

            this.cle = cle;
            this.gauche = sentinelle;
            this.droit = sentinelle;
            this.pere = sentinelle;
            this.couleur =couleur;

        }
//********************************************Les methodes de la classe Noeud ************
        /**
         * Renvoie le noeud contenant la clé minimale du sous-arbre enraciné
         * dans ce noeud
         *
         * @return le noeud contenant la clé minimale du sous-arbre enraciné
         * dans ce noeud
         */
        Noeud minimum() {
            Noeud courant = this;
            // Parcours à gauche
            while (courant.gauche != sentinelle) {
                courant = courant.gauche;
            }
            return courant;
        }
        /**
         * Renvoie le successeur de ce noeud
         *
         * @return le noeud contenant la clé qui suit la clé de ce noeud dans
         *   l'ordre des clés, null si c'es le noeud contenant la plus
         *  grande clé
         */
        Noeud suivant() {
            Noeud courant = this;
            // Cas où le Noeud possède un fils droit
            if (courant.droit != sentinelle) {
                // Si le fils droit existe, renvoyer le minimum du sous-arbre droit
                return courant.droit.minimum();
            }
            // Si le noeud n'a pas de fils droit, remonter dans l'arbre
            Noeud suivant = courant.pere;
            // On remonte tant que le noeud suivant existe et que le courant est le fils droit du suivant
            while (suivant != sentinelle && courant == suivant.droit) {
                courant = suivant;
                suivant = suivant.pere;
            }
            return suivant;
        }
    }

    //-----------Constructeurs de ARN-----------
    /**
     * Crée un arbre vide. Les éléments sont ordonnés selon l'ordre naturel
     */
    public ARN() {

        sentinelle=new Noeud(null,'N');
        racine = sentinelle;
        taille = 0;
        cmp = (a, b) -> ((Comparable<E>) a).compareTo(b);
    }
    /**
     * Crée un arbre vide. Les éléments sont comparés selon l'ordre imposé par
     * le comparateur
     *
     * @param comparateur
     *            le comparateur utilisé pour définir l'ordre des éléments
     */

    public ARN(Comparator<? super E> comparateur) {
        sentinelle=new Noeud(null,'N');
        racine = sentinelle;
        taille = 0;
        comparateur = (a, b) -> ((Comparable<E>) a).compareTo(b);

    }
    /**
     * Constructeur par recopie. Crée un arbre qui contient les mêmes éléments
     * que collection. L'ordre des éléments est l'ordre naturel.
     *
     * @param collection
     *            la collection à copier
     */
    public ARN(Collection<? extends E> collection) {

        this();
        addAll(collection);
    }
    // Méthodes
    /**
     * Crée un nouvel objet ABRIterator
     *
     * @return un nouvel objet ABRIterator
     */
    @Override
    public Iterator<E> iterator() {

        return new ARNIterator();
    }

    /**
     * Retourne la taille de l'arbre rouge et noir
     *
     * @return la taille de l'arbre rouge et noir
     */
    @Override
    public int size() {
        return taille;
    }

    /**
     * Recherche une clé. Cette méthode peut être utilisée par
     * {@link #contains(Object)} et {@link #remove(Object)}
     *
     * @param o
     *   /* clé à chercher
     *
     * @return le noeud qui contient la clé ou null si la clé n'est pas trouvée.
     *
     */
    private Noeud rechercher(Object o) {
        Noeud courant = racine;
        while (courant != sentinelle && cmp.compare((E) o, courant.cle) != 0) {
            courant = cmp.compare((E) o, courant.cle) < 0 ? courant.gauche : courant.droit;
        }
        return courant;
    }

    /**
     * Ajoute une collection dans un arbre rouge et noir
     *
     * @param c
     *      la collection à ajouter
     *
     * @return vrai si la collection a été ajouté
     */
    public boolean addAll(Collection<? extends E> c) {
        boolean modif = false;
        for (E e : c) {
            if (add(e)) {
                modif = true;
            }
        }
        return modif;
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
        {   ajouter(z);
            return true;}

        else return false;
    }
    /**
     * Ajoute un noeud. Cette méthode peut être utilisée par
     * {@link #add(E)}
     *
     * @param ajout
     *          le noeud à ajouter
     *
     */
    private void  ajouter(Noeud  ajout) {
        Noeud pere = sentinelle;
        Noeud courant = racine;
        while (courant != sentinelle) {
            pere = courant;
            courant = cmp.compare( ajout.cle, courant.cle) < 0 ? courant.gauche : courant.droit;
        }
        ajout.pere = pere;
        if (pere == sentinelle) { // arbre vide
            racine =  ajout;
        } else {
            if (cmp.compare( ajout.cle, pere.cle) < 0)
                pere.gauche =  ajout;
            else
                pere.droit =  ajout;
        }
        ajout.gauche =  ajout.droit = sentinelle;
        ajout.couleur = 'R';
        ajouterCorrection(ajout);
        taille ++;
    }



    // Méthode de rotation à gauche
    /**
     * Fait une rotation de l'arbre à gauche selon le noeud rotation
     * où a lieu la rotation
     *
     * @param rotation
     *            le noeud où a lieu la rotation
     */
    private void rotationGauche(Noeud rotation) {
        // Sauvegarde du sous-arbre droit du nœud de rotation
        Noeud tmp = rotation.droit;
        rotation.droit = tmp.gauche;

        if(tmp.gauche != sentinelle)
        {
            tmp.gauche.pere = rotation;
        }

        tmp.pere = rotation.pere;
        if(rotation.pere==sentinelle)
        {
            racine = tmp;
        }else
        {
            if(rotation.pere.gauche == rotation)
            {
                rotation.pere.gauche = tmp;
            }else
            {
                rotation.pere.droit = tmp;
            }
        }

        tmp.gauche = rotation;
        rotation.pere = tmp;
    }


    // Méthode de rotation droite
    /**
     * Fait une rotation de l'arbre à droite selon le noeud rotation
     * où a lieu la rotation
     *
     * @param rotation
     *            le noeud où a lieu la rotation
     */
    private Noeud rotationDroite(Noeud rotation) {
        // Assurez-vous que  rotation a un fils gauche non nul
        if (rotation == sentinelle || rotation.gauche == sentinelle) {
            return rotation; // Rien à faire, retourne le nœud d'origine
        }

        // Étape 1: Sauvegardez le fils gauche de  rotation
        Noeud x = rotation.gauche;

        // Étape 2: Mettez le fils droit de x comme fils gauche de  rotation
        rotation.gauche = x.droit;
        if (x.droit !=sentinelle) {
            x.droit.pere = rotation;
        }

        // Étape 3: Mettez  rotation comme le père de x
        x.pere = rotation.pere;

        // Étape 4: Mettez x comme le nouveau fils gauche du père de  rotation
        if (rotation.pere == sentinelle) {
            //  rotation était la racine, donc x devient la nouvelle racine
            racine = x;
        } else if (rotation == rotation.pere.gauche) {
            rotation.pere.gauche = x;
        } else {
            rotation.pere.droit = x;
        }

        // Étape 5: Mettez rotation comme le fils droit de x
        x.droit = rotation;

        // Étape 6: Mettez le père de rotation comme x
        rotation.pere = x;

        // Retourne éventuellement le nouveau nœud racine après la rotation
        return x;
    }

    /**
     * Corrige les couleurs selon les propriétés d'un arbre
     * rouge et noir après une suppression
     *
     * @param correction
     *            le noeud à corriger
     */
    private void ajouterCorrection(Noeud correction) {
        while (correction.pere.couleur == 'R') {
            // (*) La seule propriété RN violée est (4) : correction et correction.pere sont rouges
            if (correction.pere == correction.pere.pere.gauche) {
                Noeud y = correction.pere.pere.droit; // l'oncle de correction
                if (y.couleur == 'R') {
                    // cas 1
                    correction.pere.couleur = 'N';
                    y.couleur = 'N';
                    correction.pere.pere.couleur = 'R';
                    correction = correction.pere.pere;
                } else {
                    if (correction == correction.pere.droit) {
                        // cas 2
                        correction = correction.pere;
                        rotationGauche(correction);
                    }
                    // cas 3
                    correction.pere.couleur = 'N';
                    correction.pere.pere.couleur = 'R';
                    rotationDroite(correction.pere.pere);
                }
            } else {
                Noeud y = correction.pere.pere.gauche; // l'oncle de correction
                if (y.couleur == 'R') {
                    // cas 1
                    correction.pere.couleur = 'N';
                    y.couleur = 'N';
                    correction.pere.pere.couleur = 'R';
                    correction = correction.pere.pere;
                } else {
                    if (correction == correction.pere.gauche) {
                        // cas 2
                        correction = correction.pere;
                        rotationDroite(correction);
                    }
                    // cas 3
                    correction.pere.couleur = 'N';
                    correction.pere.pere.couleur = 'R';
                    rotationGauche(correction.pere.pere);
                }
            }

        }

        racine.couleur = 'N';
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
        return rechercher(o) != sentinelle;
    }
    /**
     * Supprime le noeud suppression. Cette méthode peut être utilisée dans
     * {@link #remove(Object)} et {@link Iterator#remove()}
     *
     * @param suppression
     *            le noeud à supprimer
     *
     * @return le noeud contenant la clé qui suit celle de suppression dans l'ordre des
     *         clés. Cette valeur de retour peut être utile dans
     *         {@link Iterator#remove()}
     */
    private Noeud supprimer(Noeud suppression) {
        Noeud courant;
        Noeud pere;
        if (suppression.gauche == sentinelle|| suppression.droit == sentinelle)
            courant = suppression;
        else
            courant = suppression.suivant();
        // courant est le nœud à détacher
        if (courant.gauche != sentinelle)
            pere= courant.gauche;
        else
            pere = courant.droit;
        // xpere est le fils unique de courant ou la sentinelle si courant n'a pas de fils

        pere.pere = courant.pere; // inconditionnelle

        if (courant.pere == sentinelle) { // suppression de la racine
            racine = pere;
        } else {
            if (courant == courant.pere.gauche)
                courant.pere.gauche = pere;
            else
                courant.pere.droit = pere;
        }

        if (courant!= suppression)
            suppression.cle = courant.cle;
        if (courant.couleur == 'N') supprimerCorrection(pere);
       taille--;
        return courant;
    }

    /**
     * Corrige les couleurs selon les propriétés d'un arbre
     * rouge et noir après une suppression
     *
     * @param x
     *            le noeud à corriger
     */
    private void supprimerCorrection(Noeud x) {
        while (x != racine && x.couleur == 'N') {
            // (*) est vérifié ici
            if (x == x.pere.gauche) {
                Noeud w = x.pere.droit; // le frère de x
                if (w.couleur == 'R') {
                    // cas 1
                    w.couleur = 'N';
                    x.pere.couleur = 'R';
                    rotationGauche(x.pere);
                    w = x.pere.droit;
                }
                if (w.gauche.couleur == 'N' && w.droit.couleur == 'N') {
                    // cas 2
                    w.couleur = 'R';
                    x = x.pere;
                } else {
                    if (w.droit.couleur == 'N') {
                        // cas 3
                        w.gauche.couleur = 'N';
                        w.couleur = 'R';
                        rotationDroite(w);
                        w = x.pere.droit;
                    }
                    // cas 4
                    w.couleur = x.pere.couleur;
                    x.pere.couleur = 'N';
                    w.droit.couleur = 'N';
                    rotationGauche(x.pere);
                    x = racine;
                }
            } else {
                // Cas miroir, gauche <-> droite
                Noeud w = x.pere.gauche; // le frère de x (miroir)

                if (w != sentinelle && w.couleur == 'R') {
                    // Cas 1'
                    w.couleur = 'N';
                    x.pere.couleur = 'R';
                    rotationDroite(x.pere);
                    w = x.pere.gauche;
                }

                if ((w.droit == sentinelle  || w.droit.couleur == 'N') &&
                        (w.gauche == sentinelle || w.gauche.couleur == 'N')) {
                    // Cas 2'
                    w.couleur = 'R';
                    x = x.pere;
                } else {
                    if (w.gauche == sentinelle || w.gauche.couleur == 'N') {
                        // Cas 3'
                        if (w.droit != sentinelle ) {
                            w.droit.couleur = 'N';
                        }
                        w.couleur = 'R';
                        rotationGauche(w);
                        w = x.pere.gauche;
                    }
                    // Cas 4'
                    if (w != sentinelle ) {
                        w.couleur = x.pere.couleur;
                    }
                    if (x.pere != sentinelle ) {
                        x.pere.couleur = 'N';
                    }
                    if (w != sentinelle  && w.gauche != sentinelle ) {
                        w.gauche.couleur = 'N';
                    }
                    if (x.pere != sentinelle ) {
                        rotationDroite(x.pere);
                    }
                    x = racine;
                }
            }
        }
        // (**) est vérifié ici
        if (x !=sentinelle ) {

            x.couleur = 'N';
        }
    }
    /**
     * Les itérateurs doivent parcourir les éléments dans l'ordre ! Ceci peut se
     * faire facilement en utilisant {@link Noeud#minimum()} et
     * {@link Noeud#suivant()}
     */
    private class ARNIterator implements Iterator<E> {
        private Noeud precedent;
        private Noeud suivant;
        /**
         * Crée un ABRIterator avec courant étant le minimum de l'arbre
         */
        public ARNIterator() {
            precedent = sentinelle;
            suivant = racine.minimum();
        }
////////// Methode
        /**
         * Retourne si le noeud courant possède un suivant
         * selon l'ordre des clés
         *
         * @return vrai si le noeud suivant n'est pas null
         */
        public boolean hasNext() {
            return suivant != sentinelle;
        }
        /**
         * Place courant sur le noeud suivant selon l'ordre des clés
         *
         * @return la clé du noeud
         */
        // Méthode pour obtenir l'élément suivant dans le parcours
        public E next() throws IllegalStateException {
            // S'il n'y a pas d'élément suivant, lance une exception
            if (!hasNext()) {
                throw new IllegalStateException();
            }
            precedent = suivant;
            suivant = suivant.suivant();
            return precedent.cle;

        }
        /**
         * Permet de supprimer le noeud précédent
         */
        public void remove() throws IllegalStateException {
            // Vérifie si la méthode remove() est appelée sans appel préalable de next()
            if (precedent == sentinelle) {
                throw new IllegalStateException();
            }
            supprimer(precedent);
            // Réinitialise le nœud précédent à la sentinelle après la suppression
            precedent = sentinelle;

        }

    }
    public String toString() {
        StringBuffer buf = new StringBuffer();
        toString(racine, buf, "", maxStrLen(racine));
        return buf.toString();
    }

    private void toString(Noeud x, StringBuffer buf, String path, int len) {
        if (x == sentinelle)
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
        buf.append("-- " + x.cle.toString()+"-"+x.couleur);
        if (x.gauche != sentinelle || x.droit != sentinelle) {
            buf.append(" --");
            for (int j = x.cle.toString().length(); j < len; j++)
                buf.append('-');
            buf.append('|');
        }
        buf.append("\n");
        toString(x.gauche, buf, path + "G", len);
    }

    private int maxStrLen(Noeud x) {
        return x == sentinelle ? 0 : Math.max(x.cle.toString().length(),
                Math.max(maxStrLen(x.gauche), maxStrLen(x.droit)));
    }
    /**
     * Supprimer l'objet de l'arbre rouge et noir
     *
     * @param objet
     *            l'objet à supprimer
     *
     * @return vrai si l'objet existe et a été supprimé
     */
    @Override
    public boolean remove(Object objet) {
        Noeud noeud = rechercher(objet);

        if (noeud != sentinelle) {
            supprimer(noeud);
            return true;
        }
        return false;
    }

}
