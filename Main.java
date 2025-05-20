import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
public class Main extends JFrame {

    private JPanel panel1;
    private JList list1;

    public Main() {
        setTitle("Ô'Tomates");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300,200);
        setLocationRelativeTo(null);
        setVisible(true);
        chargerDonneesJSON();
    }
    private void chargerDonneesJSON() {
        try {
            String contenu = new String(Files.readAllBytes(Paths.get("C:\\Users\\killi\\IdeaProjects\\SAE JAVA\\src\\tomates.json")));
            JSONArray tomates = new JSONArray(contenu); // ton JSON est un tableau

            DefaultListModel<String> model = new DefaultListModel<>();
            for (int i = 0; i < tomates.length(); i++) {
                JSONObject tomate = tomates.getJSONObject(i);
                String designation = tomate.getString("désignation");
                model.addElement(designation); // ajoute le nom à la JList
            }

            list1.setModel(model);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new Main();
    }
}
