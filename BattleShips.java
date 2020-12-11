import java.util.Scanner;

public class BattleShips {
    //Encapsulation baris 5 sampai baris 10
    public static int Sumx = 7;
    public static int Sumy = 7;
    public static int amerikaShips;
    public static int chinaShips;
    public static String[][] grid = new String[Sumx][Sumy];
    public static int[][] missedGuesses = new int[Sumx][Sumy];

    public static void main(String[] args){
        System.out.println("SELAMAT DATANG DI GAME BATTLESHIPS AMERIKA VS CHINA");
        System.out.println("ARENA TELAH DI BUAT\n");

        createOceanMap();

        deployAmerikaShips();

        deployChinaShips();

        do {
            Battle();
        }while(BattleShips.amerikaShips != 0 && BattleShips.chinaShips != 0);
    //Encapsulation
        gameOver();
    }

    public static void createOceanMap(){
        System.out.print("  ");
        for(int i = 0; i < Sumy; i++)
            System.out.print(i);
        System.out.println();

        for(int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = " ";
                if (j == 0)
                    System.out.print(i + "|" + grid[i][j]);
                else if (j == grid[i].length - 1)
                    System.out.print(grid[i][j] + "|" + i);
                else
                    System.out.print(grid[i][j]);
            }
            System.out.println();
        }

        System.out.print("  ");
        for(int i = 0; i < Sumy; i++)
            System.out.print(i);
        System.out.println();
    }

    public static void deployAmerikaShips(){
        Scanner input = new Scanner(System.in);

        System.out.println("\nAMERIKA SILAHKAN TURUNKAN KAPAL ANDA:");
        BattleShips.amerikaShips = 5;
        for (int i = 1; i <= BattleShips.amerikaShips; ) {
            System.out.print("MASUKKAN KOORDINAT X KAPAL " + i + ":");
            int x = input.nextInt();
            System.out.print("MASUKKAN KOORDINAT Y KAPAL " + i + ":");
            int y = input.nextInt();

            if((x >= 0 && x < Sumx) && (y >= 0 && y < Sumy) && (grid[x][y] == " "))
            {
                grid[x][y] =   "@";
                i++;
            }
            else if((x >= 0 && x < Sumx) && (y >= 0 && y < Sumy) && grid[x][y] == "@")
                System.out.println("ANDA TIDAK BISA MELETAKKAN KAPAL DI TEMPAT YANG SAMA");
            else if((x < 0 || x >= Sumx) || (y < 0 || y >= Sumy))
                System.out.println("KAMU TIDAK BISA MELETAKKAN KAPAL DI LUAR AREA 0-6 PADA SUMBU X DAN SUMBU Y" );
        }
        //Encapsulation
        printOceanMap();
    }

    public static void deployChinaShips(){
        System.out.println("\nKAPAL CHINA TELAH MEMASUKI AREA PERTEMPURAN");
        BattleShips.chinaShips = 5;
        for (int i = 1; i <= BattleShips.chinaShips; ) {
            int x = (int)(Math.random() * 10);
            int y = (int)(Math.random() * 10);

            if((x >= 0 && x < Sumx) && (y >= 0 && y < Sumy) && (grid[x][y] == " "))
            {
                grid[x][y] =   "x";
                System.out.println(i + ". KAPAL CHINA MASUK AREA TEMPUR");
                i++;
            }
        }
        //Encapsulation
        printOceanMap();
    }

    public static void Battle(){
        AmerikaTurn();
        ChinaTurn();

        printOceanMap();

        System.out.println();
        System.out.println("[ KAPAL AMERIKA: " + BattleShips.amerikaShips + " ][ KAPAL CHINA: " + BattleShips.chinaShips + "]");
        System.out.println();
    }

    public static void AmerikaTurn(){
        System.out.println("\nGILIRAN AMERIKA MENYERANG");
        int x = -1, y = -1;
        do {
            Scanner input = new Scanner(System.in);
            System.out.print("MASUKKAN KOORDINAT X : ");
            x = input.nextInt();
            System.out.print("MASUKKAN KOORDINAT Y : ");
            y = input.nextInt();

            if ((x >= 0 && x < Sumx) && (y >= 0 && y < Sumy))
            {
                if (grid[x][y] == "x")
                {
                    System.out.println("DUAR 1 KAPAL CHINA KENA !!");
                    grid[x][y] = "*";
                    --BattleShips.chinaShips;
                }
                else if (grid[x][y] == "@") {
                    System.out.println("AMERIKA MENGHANCURKAN KAPAL SENDIRI");
                    grid[x][y] = "!";
                    --BattleShips.amerikaShips;
                }
                else if (grid[x][y] == " ") {
                    System.out.println("AMERIKA TIDAK MENGENAI APAPUN");
                    grid[x][y] = "-";
                }
            }
            else if ((x < 0 || x >= Sumx) || (y < 0 || y >= Sumy))  //invalid guess
                System.out.println("KAMU TIDAK DAPAT MEMASUKKAN KAPAL DI LUAR AREA " + Sumx + " UNTUK " + Sumy + " AREA");
        }while((x < 0 || x >= Sumx) || (y < 0 || y >= Sumy));
    }

    public static void ChinaTurn(){
        System.out.println("\nGILIRAN CHINA");
        int x = -1, y = -1;
        do {
            x = (int)(Math.random() * 10);
            y = (int)(Math.random() * 10);

            if ((x >= 0 && x < Sumx) && (y >= 0 && y < Sumy)) //valid guess
            {
                if (grid[x][y] == "@")
                {
                    System.out.println("CHINA MENGHANCURKAN 1 KAPAL MU");
                    grid[x][y] = "*";
                    --BattleShips.amerikaShips;
                }
                else if (grid[x][y] == "!") {
                    System.out.println("CHINA MENEMBAK KAPALNYA SENDIRI");
                    grid[x][y] = "!";
                }
                else if (grid[x][y] == " ") {
                    System.out.println("CHINA TIDAK MENGENAI APAPUN");
                    if(missedGuesses[x][y] != 1)
                        missedGuesses[x][y] = 1;
                }
            }
        }while((x < 0 || x >= Sumx) || (y < 0 || y >= Sumy));
    }

    public static void gameOver(){
        System.out.println("[ KAPAL AMERIKA: " + BattleShips.amerikaShips + "] [KAPAL CHINA: " + BattleShips.chinaShips + "]");
        if(BattleShips.amerikaShips > 0 && BattleShips.chinaShips <= 0)
            System.out.println("AMERIKA MEMENANGKAN PERTEMPURAN !!!!!");
        else
            System.out.println("CHINA MEMENANGKAN PERTEMPURAN !!!!");
        System.out.println();
    }

    public static void printOceanMap(){
        System.out.println();
        System.out.print("  ");
        for(int i = 0; i < Sumy; i++)
            System.out.print(i);
        System.out.println();

        for(int x = 0; x < grid.length; x++) {
            System.out.print(x + "|");

            for (int y = 0; y < grid[x].length; y++){
                System.out.print(grid[x][y]);
            }

            System.out.println("|" + x);
        }

        System.out.print("  ");
        for(int i = 0; i < Sumy; i++)
            System.out.print(i);
        System.out.println();
    }
}
