// FenetreCommande.java
package ihm;

import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import org.json.JSONException;

import modèle.OutilsBaseDonneesTomates;
import ihm.Panier;
import modèle.Tomate;
import modèle.Tomates;

public class FenetreCommande extends JFrame {
    private Tomates baseTomates;
    private Panier panier;
    private JTable tablePanier;
    private JLabel labelSousTotal, labelExpedition, labelTotal;
    private DefaultTableModel tableModel;
    private java.util.List<Tomate> tomatesAffichées = new java.util.ArrayList<>();

    public FenetreCommande() throws JSONException {
        baseTomates = OutilsBaseDonneesTomates.générationBaseDeTomates("src/main/resources/data/tomates.json");
        panier = new Panier();
        initUI();
    }

    private void initUI() {
        setTitle("Ô'Tomates");
        setSize(700, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        JLabel titre = new JLabel("Votre panier", JLabel.CENTER);
        titre.setFont(new Font("Serif", Font.BOLD, 50));
        titre.setForeground(new Color(0, 128, 0));
        getContentPane().add(titre, BorderLayout.NORTH);

        String[] columnNames = {"", "Produit", "Prix", "Quantité", "Total"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tablePanier = new JTable(tableModel) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 0 ? ImageIcon.class : Object.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };
        tablePanier.setRowHeight(80);

        TableColumn colQuantite = tablePanier.getColumnModel().getColumn(3);
        colQuantite.setCellEditor(new QuantiteCellEditor(this, panier, tomatesAffichées));

        JScrollPane scrollPane = new JScrollPane(tablePanier);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel panelBas = new JPanel(new BorderLayout());
        JPanel panelTotaux = new JPanel(new GridLayout(3, 2));

        labelSousTotal = new JLabel("0,00 €");
        labelSousTotal.setFont(new Font("Serif", Font.PLAIN, 30));
        labelExpedition = new JLabel("5,50 €");
        labelExpedition.setFont(new Font("Serif", Font.PLAIN, 30));
        labelTotal = new JLabel("0,00 €");
        labelTotal.setFont(new Font("Serif", Font.BOLD, 30));
        labelTotal.setForeground(new Color(0, 128, 0));

        panelTotaux.add(new JLabel("Sous-Total :", JLabel.RIGHT));
        panelTotaux.add(labelSousTotal);
        panelTotaux.add(new JLabel("Expédition (forfait) :", JLabel.RIGHT));
        panelTotaux.add(labelExpedition);
        panelTotaux.add(new JLabel("TOTAL :", JLabel.RIGHT));
        panelTotaux.add(labelTotal);

        panelBas.add(panelTotaux, BorderLayout.CENTER);

        JPanel panelBoutons = new JPanel();
        JButton valider   = new JButton("Valider le panier");
        JButton vider     = new JButton("Vider le panier");
        JButton continuer  = new JButton("Continuer les achats");

        // Listener pour Valider : ouvre FicheClient en lui passant le contenu du panier
        valider.addActionListener(e -> {
            Map<Tomate,Integer> lignes = panier.getLignesCommande();
            double fraisPort = 5.50;

            FicheClient fiche = new FicheClient(lignes, fraisPort);
            fiche.setVisible(true);
            this.dispose();
        });

        vider.addActionListener(e -> {
            if (tablePanier.isEditing()) {
                tablePanier.getCellEditor().stopCellEditing();
            }
            panier.resetQuantites();
            panier.viderPanier();
            mettreAJourAffichage();
        });

        panelBoutons.add(valider);
        panelBoutons.add(vider);
        panelBoutons.add(continuer);
        panelBas.add(panelBoutons, BorderLayout.SOUTH);

        getContentPane().add(panelBas, BorderLayout.SOUTH);

        JPanel panelTomates = new JPanel(new GridLayout(0, 1));
        for (Tomate tomate : baseTomates.getTomates()) {
            JButton btn = new JButton(tomate.getDésignation());
            btn.addActionListener(e -> {
                if (tomate.getStock() > 0) {
                    panier.ajouterTomate(tomate, 1);
                    mettreAJourAffichage();
                } else {
                    JOptionPane.showMessageDialog(this, "Stock épuisé !");
                }
            });
            panelTomates.add(btn);
        }

        JScrollPane scrollTomates = new JScrollPane(panelTomates);
        getContentPane().add(scrollTomates, BorderLayout.WEST);
    }

    private void mettreAJourAffichage() {
        tableModel.setRowCount(0);
        tomatesAffichées.clear();

        for (Map.Entry<Tomate, Integer> entry : panier.getLignesCommande().entrySet()) {
            Tomate t       = entry.getKey();
            int quantité   = entry.getValue();
            float prix     = t.getPrixTTC();
            float total    = prix * quantité;

            ImageIcon image;
            if (t.getNomImage() != null) {
                image = new ImageIcon("src/main/resources/images/Tomates40x40/" + t.getNomImage() + ".jpg");
            } else {
                image = new ImageIcon("src/main/resources/images/Tomates40x40/default.jpg");
            }

            tomatesAffichées.add(t);

            tableModel.addRow(new Object[] {
                new ImageIcon(image.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)),
                t.getDésignation(),
                String.format("%.2f €", prix),
                quantité,
                String.format("%.2f €", total)
            });
        }

        float sousTotal = panier.getTotal();
        float expedition = 5.50f;
        float total = sousTotal + expedition;

        labelSousTotal.setText(String.format("%.2f €", sousTotal));
        labelExpedition.setText(String.format("%.2f €", expedition));
        labelTotal.setText(String.format("%.2f €", total));
    }

    private static class QuantiteCellEditor extends AbstractCellEditor implements TableCellEditor {
        private JComboBox<Integer> comboBox;
        private FenetreCommande fenetre;
        private Panier panier;
        private java.util.List<Tomate> tomatesAffichées;

        public QuantiteCellEditor(FenetreCommande fenetre, Panier panier, java.util.List<Tomate> tomatesAffichées) {
            this.fenetre          = fenetre;
            this.panier           = panier;
            this.tomatesAffichées = tomatesAffichées;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            String designation = table.getValueAt(row, 1).toString();

            Tomate tomateTrouvée = null;
            for (Tomate t : panier.getLignesCommande().keySet()) {
                if (t.getDésignation().equals(designation)) {
                    tomateTrouvée = t;
                    break;
                }
            }

            if (tomateTrouvée == null) {
                return new JLabel("Erreur");
            }

            final Tomate tomateFinale  = tomateTrouvée;
            int qteDansPanier          = panier.getLignesCommande().getOrDefault(tomateFinale, 0);
            int max                    = tomateFinale.getStock() + qteDansPanier;

            comboBox = new JComboBox<>();
            for (int i = 1; i <= max; i++) {
                comboBox.addItem(i);
            }

            comboBox.setSelectedItem(qteDansPanier);

            comboBox.addActionListener(e -> {
                int nouvelleQuantite = (int) comboBox.getSelectedItem();
                panier.retirerTomate(tomateFinale);
                panier.ajouterTomate(tomateFinale, nouvelleQuantite);
                SwingUtilities.invokeLater(() -> fenetre.mettreAJourAffichage());
            });

            return comboBox;
        }

        @Override
        public Object getCellEditorValue() {
            return comboBox.getSelectedItem();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new FenetreCommande().setVisible(true);
            } catch (JSONException e) {
                JOptionPane.showMessageDialog(null, "Erreur JSON : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
    }
}
