import java.util.Random;
public class Main {
    public static void main(String[] args) {
        // Création d'un arbre rouge-noir
        ARN<Integer> arbreRN = new ARN<>();
        ABR<Integer> arbreBR = new ABR<>();
        Random rand =new Random();
        int TAILLE =100000;// pour eviter de faire les tests manuels on declare Taille pour qu'on fait les test pour des taille different jusqu'a taille
        int plus = 5000;// represente les taille difent car chaque fois la boucle ajoute plus jusqu'a taille
        System.out.printf("%12s%12s%12s%n", "Taille", "ARN (en ms)", "ABR(en ms)");
        for (int ind = 0; ind < TAILLE; ind+=plus) {
            long debut = System.currentTimeMillis();
            for (int i=0; i< ind; i++){
                arbreRN.add(i);
            }
            long fin = System.currentTimeMillis() -debut;

            long debut2 = System.currentTimeMillis();
            for (int i=0; i< ind; i++){
                arbreBR.add(i);
            }
            long fin2 = System.currentTimeMillis() -debut2;
            System.out.printf("%12s%12s%12s%n", ind, fin, fin2);

        }

       System.out.println("------------- cas moyen ----------");
        ARN<Integer> arbreRN1 = new ARN<>();
        ABR<Integer> arbreBR1= new ABR<>();
        //cas moyen d'insertion
        System.out.printf("%12s%12s%12s%n", "Taille", "ARN (en ms)", "ABR(en ms)");
        for (int ind = 0; ind < TAILLE; ind+=plus) {

         int []tab=new int[TAILLE];
            for (int j = 0; j < ind; j+=1) {
                tab[j]=  rand.nextInt(ind);
            }
            long start =System.currentTimeMillis();// pour calculer le temps d'execution
        for (int j = 0; j < ind; j+=1) {
            arbreRN1.add(tab[j]);
        }
            long stop = System.currentTimeMillis()-start;

            long start1 =System.currentTimeMillis();// pour calculer le temps d'execution
            for (int z = 0; z < ind; z+=1) {
                arbreBR1.add(tab[z]);
            }
            long stop1 = System.currentTimeMillis()-start1;
            System.out.printf("%12s%12s%12s%n", ind, stop , stop1);
        }


       /////////////////////////////////rechercher des element dans la table
     System.out.println("-----------------chercher dans un arbre --------");
        ARN<Integer> arbreRN2 = new ARN<>();
        ABR<Integer> arbreBR2 = new ABR<>();

        System.out.printf("%12s%12s%12s%n", "Taille", "ARN (en s)", "ABR(en s)");
        for (int i = plus; i < TAILLE; i+=plus) {
            for (int j = 0; j < i; j += 1) {
                arbreRN2.add(j);
                arbreBR2.add(j);
            }

            long start = System.currentTimeMillis(); // pour calculer le temps d'execution
            for (int j = 0; j < 2*i; j += 1) {
                arbreRN2.contains(j);
            }
            long stop = System.currentTimeMillis()-start;
            long start1 = System.currentTimeMillis(); // pour calculer le temps d'execution
            for (int z = 0; z < 2*i; z+=1) {
                arbreBR2.contains(z);
            }
            long stop1 = System.currentTimeMillis()-start1;

            System.out.printf("%12s%12s%12s%n", 2*i, stop , stop1);
        }

        }
    }