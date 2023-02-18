import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Summer {
    public static void main(String[] args) {
        File file = new File("in.txt");
        double sum = 0;
        int count = 0;

        try {
            Scanner sc = new Scanner(file);
            int next = Integer.parseInt(sc.nextLine());
            sum += next;
            count++;
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        file = new File("out.txt");
        try {
            FileWriter out = new FileWriter(file);
            out.write((sum / count) + "");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
