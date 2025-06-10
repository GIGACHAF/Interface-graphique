package ihm;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import modèle.*;

import java.util.List;
import java.util.stream.Collectors;

public class Fenetre extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel panelTomates;
    private List<Tomate> toutesLesTomates;

    private JComboBox<String> comboCouleur;
    private JComboBox<String> comboType;
    
    private Panier panier = new Panier();
    private JButton labelPanier;
    private Tomates baseTomates;

    public Fenetre() {
        setTitle("🌿 Catalogue des Tomates");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);

        // Contenu principal avec BorderLayout
        contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        // Titre en haut
        JPanel panelHaut = new JPanel(new BorderLayout());
        
        // Panier
        JButton boutonPanier = new JButton("🛒 Panier : 0.00 €");
        this.labelPanier = boutonPanier; // pour pouvoir encore le modifier dans mettreAJourPanier()
        boutonPanier.setFont(new Font("SansSerif", Font.PLAIN, 16));
        boutonPanier.setHorizontalAlignment(SwingConstants.RIGHT);
        boutonPanier.addActionListener(e -> {
            FenetreCommande fPanier = new FenetreCommande(this, this.baseTomates, panier);
            fPanier.setVisible(true);
            this.setVisible(false); // masque la fenêtre catalogue
        });
        panelHaut.add(boutonPanier, BorderLayout.EAST);
        
        JLabel titre = new JLabel("Catalogue des Tomates 🍅", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 24));
        panelHaut.add(titre, BorderLayout.CENTER);

        contentPane.add(panelHaut, BorderLayout.NORTH);


        // Zone des tomates avec scroll
        panelTomates = new JPanel(new GridLayout(0, 3, 10, 10));
        JScrollPane scrollPane = new JScrollPane(panelTomates);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Bas de la fenêtre : filtres et boutons
        JPanel panelBas = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Combo Couleur
        comboCouleur = new JComboBox<>();
        comboCouleur.addItem("Toutes");
        panelBas.add(new JLabel("Couleur :"));
        panelBas.add(comboCouleur);

        // Combo Type
        comboType = new JComboBox<>();
        comboType.addItem("Toutes");
        panelBas.add(new JLabel("Type :"));
        panelBas.add(comboType);

        // Bouton filtrer
        JButton btnFiltrer = new JButton("Appliquer filtres");
        btnFiltrer.addActionListener(e -> appliquerFiltres());
        panelBas.add(btnFiltrer);

        // Bouton quitter
        JButton btnQuitter = new JButton("Quitter");
        btnQuitter.addActionListener(e -> System.exit(0));
        panelBas.add(btnQuitter);

        contentPane.add(panelBas, BorderLayout.SOUTH);

        // Charger les données
        chargerEtAfficherTomates();
    }

    private void chargerEtAfficherTomates() {
        String cheminFichier = "src/main/resources/data/tomates.json";
        this.baseTomates = OutilsBaseDonneesTomates.générationBaseDeTomates(cheminFichier);
        toutesLesTomates = baseTomates.getTomates();

        // Remplissage combo couleurs
        toutesLesTomates.stream()
            .map(t -> t.getCouleur().getDénomination())
            .distinct().sorted()
            .forEach(c -> comboCouleur.addItem(c));

        // Remplissage combo types
        toutesLesTomates.stream()
        	.map(tomate -> tomate.getType().toString())
        	.map(type -> type.replaceAll("\\s*\\(.*\\)", "").replace("_", " "))
        	.distinct().sorted()
        	.forEach(t -> comboType.addItem(t));

        afficherTomates(toutesLesTomates);
    }

    private void appliquerFiltres() {
        String couleurChoisie = (String) comboCouleur.getSelectedItem();
        String typeChoisi = (String) comboType.getSelectedItem();

        List<Tomate> filtrées = toutesLesTomates.stream()
        		.filter(t -> 
        	    (couleurChoisie.equals("Toutes") || t.getCouleur().getDénomination().equals(couleurChoisie)) &&
        	    (typeChoisi.equals("Toutes") || t.getType().toString().replaceAll("\\s*\\(.*\\)", "").replace("_", " ").equals(typeChoisi))
        	)
            .collect(Collectors.toList());

        afficherTomates(filtrées);
    }

    private void afficherTomates(List<Tomate> liste) {
        panelTomates.removeAll();
        for (Tomate t : liste) {
            panelTomates.add(creerPanelTomate(t));
        }
        panelTomates.revalidate();
        panelTomates.repaint();
    }

    private JPanel creerPanelTomate(Tomate tomate) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Image
        String cheminImage = "src/main/resources/images/Tomates200x200/" + tomate.getNomImage() + ".jpg";
        ImageIcon icon = new ImageIcon(cheminImage);
        Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(img));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(imageLabel, BorderLayout.CENTER);

        // Texte + bouton en bas
        JLabel titreLabel = new JLabel("<html><center>" + tomate.getDésignation() +
                "<br>(" + tomate.getCouleur().getDénomination() + ")</center></html>");
        titreLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton btnDetails = new JButton("Voir détails");
        btnDetails.addActionListener(e -> afficherDétailsTomate(tomate));

        JPanel basPanel = new JPanel(new BorderLayout());
        basPanel.add(titreLabel, BorderLayout.CENTER);
        basPanel.add(btnDetails, BorderLayout.SOUTH);

        panel.add(basPanel, BorderLayout.SOUTH);

        return panel;
    }
    
    private void afficherDétailsTomate(Tomate tomate) {
        JDialog dialog = new JDialog(this, tomate.getDésignation(), true);
        dialog.getContentPane().setLayout(new BorderLayout(10, 10));
        dialog.setSize(400, 500);
        dialog.setLocationRelativeTo(this);

        // Image
        String cheminImage = "src/main/resources/images/Tomates200x200/" + tomate.getNomImage() + ".jpg";
        ImageIcon icon = new ImageIcon(cheminImage);
        Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(img), JLabel.CENTER);

        // Description et info
        JTextArea description = new JTextArea(tomate.getDescription());
        description.setWrapStyleWord(true);
        description.setLineWrap(true);
        description.setEditable(false);
        description.setOpaque(false);

        JLabel prix = new JLabel("Prix : " + String.format("%.2f €", tomate.getPrixTTC()));
        JLabel stock = new JLabel("Stock disponible : " + tomate.getStock());

        SpinnerNumberModel modelQuantité = new SpinnerNumberModel(
            tomate.getStock() > 0 ? 1 : 0, 0, tomate.getStock(), 1
        );
        JSpinner spinnerQuantité = new JSpinner(modelQuantité);

        JButton ajouter = new JButton("Ajouter au panier");
        ajouter.setEnabled(tomate.getStock() > 0);

        ajouter.addActionListener(e -> {
            int q = (Integer) spinnerQuantité.getValue();
            if (q > 0 && q <= tomate.getStock()) {
                panier.ajouterTomate(tomate, q);
                mettreAJourPanier();
                dialog.dispose();
            }
        });
        
        JButton fermer = new JButton("Fermer");
        fermer.addActionListener(e -> dialog.dispose());

        JPanel panelCentre = new JPanel(new GridLayout(0, 1, 5, 5));
        panelCentre.add(prix);
        panelCentre.add(stock);
        panelCentre.add(new JLabel("Quantité :"));
        panelCentre.add(spinnerQuantité);
        panelCentre.add(ajouter);
        panelCentre.add(fermer);

        dialog.getContentPane().add(imageLabel, BorderLayout.NORTH);
        dialog.getContentPane().add(new JScrollPane(description), BorderLayout.CENTER);
        dialog.getContentPane().add(panelCentre, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    public void mettreAJourPanier() {
        labelPanier.setText("🛒 Panier : " + String.format("%.2f €", panier.getTotal()));
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Fenetre frame = new Fenetre();
            frame.setVisible(true);
        });
    }
}
