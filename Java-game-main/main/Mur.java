public class Mur {
    private int longueur;
    private int largeur;
    private int x;
    private int y;

    public Mur(int longueur, int largeur, int x, int y) {
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
