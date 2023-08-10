/*
 * * Objectif mouvements:
 * modifier cordonnées joueur et boite (x,y)
 * aller sur une case designé efficacement si possible
 * sinon rien (car boite sur le chemin ou bloqué)
 * methodes:
 *      +estlibre(p,d) pour savoir si le joueur p 
 *      peut se deplacer dans la direction d sanq bouger de caisse
 *      +suivant(p,d) modifie position de p si estlibre true
 */




 public class Joueur {
    private int longueur;
    private int largeur;
    private int x;
    private int y;

    public Joueur(int longueur, int largeur, int x, int y) {
        this.longueur = longueur;
        this.largeur = largeur;
        this.x = x;
        this.y = y;
    }

    public int getLongueur() {
        return longueur;
    }

    public void setLongueur(int longueur) {
        this.longueur = longueur;
    }

    public int getLargeur() {
        return largeur;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
 
