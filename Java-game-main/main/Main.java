import javax.swing.*;
import java.util.List;
import java.io.File;



public class Main {
    public static void main (String[] args) {
        KeyHandler keyH = new KeyHandler();
        int cpt=0;  
        String directoryPath = "niveaux/";
        // Create a File object for the directory
        File directory = new File(directoryPath);
        // Get all files in the directory
        File[] files = directory.listFiles();
        // Check if the directory exists
        String[] fileNames={};
        // Check if directory exists
        // Check if directory exists
        if (!directory.exists()) {
            System.out.println(directoryPath + " does not exist.");
            System.exit(1);
        }
                
        // Check if it's a directory
        if (!directory.isDirectory()) {
            System.out.println(directoryPath + " is not a directory.");
            System.exit(1);
        }

        // Check if any files exist in the directory
        if (null == files || files.length <= 0) {
            System.out.println("No files found in the directory.");
            System.exit(1);
        }

        // Print the filenames and store them in an array
        fileNames = new String[files.length];
        for (cpt = 0; cpt < files.length; cpt++) {
            File file = files[cpt];
            String fileName = file.getName();
            if (fileName.substring(fileName.lastIndexOf(".") + 1).equalsIgnoreCase("txt")) {
                fileNames[cpt] = fileName;
            }
        }
        cpt=0;

        Matrice matrice = new Matrice("niveaux/" + fileNames[cpt]);
        Joueur p=matrice.getJoueurs().get(0);
        matrice.getmatrice();
        Graphic game = new Graphic(matrice);
        game.setVisible(true);
        game.setResizable(false);
        game.pack();
        game.setLocationRelativeTo(null);
        game.startGameThread(); // starts the game!
        game.addKeyListener(keyH); // Add KeyHandler to the game
        game.setFocusable(true); 
        boolean gameWon = false;
        boolean gamereset = false;
        Direction s;
        
        for(;;){
            while (!gameWon && !gamereset) {
                
                s = keyH.getDirection();
                System.out.println(matrice.toString());
                suivant(p, s, matrice);
                gameWon =victory(matrice);
                gamereset = keyH.Pause();
                try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }

            }

            if (gameWon) {
                System.out.println("GG well played! You are a Top G");
                
                try {
                    Thread.sleep(200); // Adjust the delay as needed
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cpt++;
                if(cpt<11){
                    matrice = new Matrice("niveaux/" + fileNames[cpt]);
                    matrice.getmatrice();
                    game.updateMatrice(matrice);
                    p=matrice.getJoueurs().get(0);
                }
                else 
                    return;
                gameWon=false;
                
            }
            else if (gamereset) {
                System.out.println("HAHAHAAA, try again");
                try { Thread.sleep(16); } catch (InterruptedException e) { e.printStackTrace(); }
                matrice = new Matrice("niveaux/" + fileNames[cpt]);
                matrice.getmatrice();
                game.updateMatrice(matrice);
                p=matrice.getJoueurs().get(0);
                gamereset=false;
            } 
        }
    
    }
    
    public static boolean suivant(Joueur p, Direction s, Matrice matrice) {
        if (estlibre(p, s, matrice)){
            try { Thread.sleep(20); } catch (InterruptedException e) { e.printStackTrace(); }
            switch (s) {
                case EST:
                    if (matrice.getElement(p.getX(), p.getY() + 1) == '#') return false;

                    if (matrice.getElement(p.getX(), p.getY() + 1) == '@')
                        matrice.setElement(p.getX(), p.getY() + 1, 'a');
                    else
                        matrice.setElement(p.getX(), p.getY() + 1, 'A');

                    if (matrice.getElement(p.getX(), p.getY()) == 'a')
                        matrice.setElement(p.getX(), p.getY(), '@');
                    else
                        matrice.setElement(p.getX(), p.getY(), ' ');
                    p.setY(p.getY() + 1);
                    break;

                case OUEST:
                    if (matrice.getElement(p.getX(), p.getY() - 1) == '#') return false;

                    if (matrice.getElement(p.getX(), p.getY() - 1) == '@')
                        matrice.setElement(p.getX(), p.getY() - 1, 'a');
                    else
                        matrice.setElement(p.getX(), p.getY() - 1, 'A');

                    if (matrice.getElement(p.getX(), p.getY()) == 'a')
                        matrice.setElement(p.getX(), p.getY(), '@');
                    else
                        matrice.setElement(p.getX(), p.getY(), ' ');
                    p.setY(p.getY() - 1);
                    break;

                case SUD:
                    if (matrice.getElement(p.getX() + 1, p.getY()) == '#') return false;

                    if (matrice.getElement(p.getX() + 1, p.getY()) == '@')
                        matrice.setElement(p.getX() + 1, p.getY(), 'a');
                    else
                        matrice.setElement(p.getX() + 1, p.getY(), 'A');

                    if (matrice.getElement(p.getX(), p.getY()) == 'a')
                        matrice.setElement(p.getX(), p.getY(), '@');
                    else
                        matrice.setElement(p.getX(), p.getY(), ' ');
                    p.setX(p.getX() + 1);
                    break;

                case NORD:
                    if (matrice.getElement(p.getX() - 1, p.getY()) == '#') return false;

                    if (matrice.getElement(p.getX() - 1, p.getY())=='@')
                        matrice.setElement(p.getX()-1,p.getY(),'a');
                    else
                        matrice.setElement(p.getX()-1,p.getY(),'A');

                    if(matrice.getElement(p.getX(),p.getY())=='a')
                        matrice.setElement(p.getX(),p.getY(),'@');
                    else
                        matrice.setElement(p.getX(),p.getY(),' ');
                    p.setX(p.getX() - 1);
                    break;
            }
            return true;
        }
        else{
            int n=1,i=2,j=0;
            outerLoop:for(;;){
                switch(s) {
                case EST: 
                    if(!Character.isUpperCase(matrice.getElement(p.getX(),p.getY()+n)) && matrice.getElement(p.getX(), p.getY() + n) != 'b')
                    break outerLoop;
                    break;
                case OUEST:
                    if(!Character.isUpperCase(matrice.getElement(p.getX(),p.getY()-n)) && matrice.getElement(p.getX(), p.getY() - n) != 'b')
                    break outerLoop;
                    break;
                case NORD:
                    if(!Character.isUpperCase(matrice.getElement(p.getX()-n,p.getY())) && matrice.getElement(p.getX()-n, p.getY() ) != 'b')
                    break outerLoop;
                    break;
                case SUD:
                    if(!Character.isUpperCase(matrice.getElement(p.getX()+n,p.getY())) && matrice.getElement(p.getX()+n, p.getY() ) != 'b')
                    break outerLoop;
                    break;
                case NONE: 
                    return false;
                }
                n++;
            }
            switch(s) {
                case EST: 
                    if (matrice.getElement(p.getX(), p.getY() + n) == '#') return false;
                    if (matrice.getElement(p.getX(), p.getY()) == 'a')
                    matrice.setElement(p.getX(), p.getY(), '@');
                else
                    matrice.setElement(p.getX(), p.getY(), ' ');
                
                for(i=1;i<=n;i++){
                    if(i==1){
                        if (matrice.getElement(p.getX(), p.getY()+i) == 'b')
                            matrice.setElement(p.getX(), p.getY()+i, 'a');
                        else{
                            matrice.setElement(p.getX(), p.getY()+i, 'A');
                        }
                    }
                    else{
                      for(j=0;i+j<=n;j++){
                            if(matrice.getElement(p.getX(), p.getY()+(i+j))=='b')
                                matrice.setElement(p.getX(), p.getY()+(i+j), 'b');
                            else if(matrice.getElement(p.getX(), p.getY()+(i+j))=='@')
                                matrice.setElement(p.getX(), p.getY()+(i+j), 'b');
                            else if(matrice.getElement(p.getX(), p.getY()+(i+j))=='#')
                                continue;
                            else 
                                matrice.setElement(p.getX(), p.getY()+(i+j), 'B');
                        }
                    
                    
                    }
                    
                }
                    p.setY(p.getY() + 1);
                    break;


                case OUEST:
                    if (matrice.getElement(p.getX(), p.getY() - n) == '#') return false;
                    
                    if (matrice.getElement(p.getX(), p.getY()) == 'a')
                        matrice.setElement(p.getX(), p.getY(), '@');
                    else
                        matrice.setElement(p.getX(), p.getY(), ' ');
                    
                    for(i=1;i<=n;i++){
                        if(i==1){
                            if (matrice.getElement(p.getX(), p.getY()-i) == 'b')
                                matrice.setElement(p.getX(), p.getY()-i, 'a');
                            else{
                                matrice.setElement(p.getX(), p.getY()-i, 'A');
                            }
                        }
                        else{
                          for(j=0;i+j<=n;j++){
                                if(matrice.getElement(p.getX(), p.getY()-(i+j))=='b')
                                    matrice.setElement(p.getX(), p.getY()-(i+j), 'b');
                                else if(matrice.getElement(p.getX(), p.getY()-(i+j))=='@')
                                    matrice.setElement(p.getX(), p.getY()-(i+j), 'b');
                                else if(matrice.getElement(p.getX(), p.getY()-(i+j))=='#')
                                    continue;
                                else 
                                    matrice.setElement(p.getX(), p.getY()-(i+j), 'B');
                            }
                        
                        
                        }
                        
                    }

                    p.setY(p.getY() - 1);
                    break;


                case NORD:
                    if (matrice.getElement(p.getX()- n, p.getY() ) == '#') return false;
                    if (matrice.getElement(p.getX(), p.getY()) == 'a')
                    matrice.setElement(p.getX(), p.getY(), '@');
                else
                    matrice.setElement(p.getX(), p.getY(), ' ');
                
                for(i=1;i<=n;i++){
                    if(i==1){
                        if (matrice.getElement(p.getX()-i, p.getY()) == 'b')
                            matrice.setElement(p.getX()-i, p.getY(), 'a');
                        else{
                            matrice.setElement(p.getX()-i, p.getY(), 'A');
                        }
                    }
                    else{
                      for(j=0;i+j<=n;j++){
                            if(matrice.getElement(p.getX()-(i+j), p.getY())=='b')
                                matrice.setElement(p.getX()-(i+j), p.getY(), 'b');
                            else if(matrice.getElement(p.getX()-(i+j), p.getY())=='@')
                                matrice.setElement(p.getX()-(i+j), p.getY(), 'b');
                            else if(matrice.getElement(p.getX()-(i+j), p.getY())=='#')
                                continue;
                            else 
                                matrice.setElement(p.getX()-(i+j), p.getY(), 'B');
                        }
                    
                    
                    }
                    
                }
                    p.setX(p.getX() - 1);
                    break;


                case SUD:
                    if (matrice.getElement(p.getX()+ n, p.getY() ) == '#') return false;
                    if (matrice.getElement(p.getX(), p.getY()) == 'a')
                    matrice.setElement(p.getX(), p.getY(), '@');
                    else
                        matrice.setElement(p.getX(), p.getY(), ' ');
                    
                    for(i=1;i<=n;i++){
                        if(i==1){
                            if (matrice.getElement(p.getX()+i, p.getY()) == 'b')
                                matrice.setElement(p.getX()+i, p.getY(), 'a');
                            else{
                                matrice.setElement(p.getX()+i, p.getY(), 'A');
                            }
                        }
                        else{
                        for(j=0;i+j<=n;j++){
                            if(matrice.getElement(p.getX()+(i+j), p.getY())=='b')
                                matrice.setElement(p.getX()+(i+j), p.getY(), 'b');
                            else if(matrice.getElement(p.getX()+(i+j), p.getY())=='@')
                                matrice.setElement(p.getX()+(i+j), p.getY(), 'b');
                            else if(matrice.getElement(p.getX()+(i+j), p.getY())=='#')
                                continue;
                            else 
                                matrice.setElement(p.getX()+(i+j), p.getY(), 'B');
                            }
                        
                        
                        }
                        
                    }
                    p.setX(p.getX() + 1);
                    break;
                case NONE: 
                    return false;
                }

        }
        return true;
    }
    
    public static boolean estlibre(Joueur p, Direction s, Matrice matrice) {
        switch(s) {
            case EST: 
                if(Character.isUpperCase(matrice.getElement(p.getX(),p.getY()+1)) || matrice.getElement(p.getX(), p.getY()+1) == 'b') return false;
                break;
            case OUEST:
                if(Character.isUpperCase(matrice.getElement(p.getX(),p.getY()-1)) || matrice.getElement(p.getX(), p.getY()-1) == 'b') return false;
                break;
            case NORD:
                if(Character.isUpperCase(matrice.getElement(p.getX()-1,p.getY())) || matrice.getElement(p.getX()-1, p.getY()) == 'b') return false;
                break;
            case SUD:
                if(Character.isUpperCase(matrice.getElement(p.getX()+1,p.getY())) || matrice.getElement(p.getX()+1, p.getY()) == 'b') return false;
                break;
            case NONE: 
                return false;
        }
        return true;
    }

    

    public static boolean victory(Matrice matrice){
        for(int i=0;i<matrice.getRows();i++){
            for(int j=0;j<matrice.getCols();j++){
            if(matrice.getElement(i, j)=='@') return false;
            }
        }
        return true;
    }
  



    
    
}