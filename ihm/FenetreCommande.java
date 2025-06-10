package ihm;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;

import org.json.JSONException;

import modèle.*;

public class FenetreCommande extends JFrame {
    private static final long serialVersionUID = 1L;
    private Tomates baseTomates;
    private Fenetre fenetreCatalogue;
    private Panier panier;
    private JTable tablePanier;
    private JLabel labelSousTotal, labelExpedition, labelTotal;
    private DefaultTableModel tableModel;

    public FenetreCommande(Fenetre fenetreCatalogue, Tomates tomates, Panier panier) throws JSONException {
        this.fenetreCatalogue = fenetreCatalogue;
        this.panier = panier;
        this.baseTomates = tomates;
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
            private static final long serialVersionUID = 1L;

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
        colQuantite.setCellEditor(new QuantiteCellEditor(this, panier));

        JScrollPane scrollPane = new JScrollPane(tablePanier);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel panelBas = new JPanel(new BorderLayout());
        JPanel panelTotaux = new JPanel(new GridLayout(3, 2));

        labelSousTotal = createStyledLabel(30);
        labelExpedition = createStyledLabel(30);
        labelTotal = createStyledLabel(30, new Color(0, 128, 0), true);

        panelTotaux.add(new JLabel("Sous-Total :", JLabel.RIGHT));
        panelTotaux.add(labelSousTotal);
        panelTotaux.add(new JLabel("Expédition (forfait) :", JLabel.RIGHT));
        panelTotaux.add(labelExpedition);
        panelTotaux.add(new JLabel("TOTAL :", JLabel.RIGHT));
        panelTotaux.add(labelTotal);

        panelBas.add(panelTotaux, BorderLayout.CENTER);

        JPanel panelBoutons = new JPanel();
        JButton valider = new JButton("Valider le panier");
        JButton vider = new JButton("Vider le panier");
        JButton continuer = new JButton("Continuer les achats");

        valider.addActionListener(e -> {
            List<LignePanier> lignes = panier.getLignesCommande();
            new FicheClient(lignes, 5.50).setVisible(true);
            this.dispose();
        });

        vider.addActionListener(e -> {
            if (tablePanier.isEditing()) tablePanier.getCellEditor().stopCellEditing();
            panier.resetQuantites();
            panier.viderPanier();
            mettreAJourAffichage();
        });
        
        continuer.addActionListener(e -> {
            this.setVisible(false);      // masque la fenêtre panier
            fenetreCatalogue.mettreAJourPanier();
            fenetreCatalogue.setVisible(true); // réaffiche la fenêtre catalogue
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

        getContentPane().add(new JScrollPane(panelTomates), BorderLayout.WEST);
        mettreAJourAffichage();
    }

    private JLabel createStyledLabel(int size) {
        return createStyledLabel(size, Color.BLACK, false);
    }

    private JLabel createStyledLabel(int size, Color color, boolean bold) {
        JLabel label = new JLabel("0,00 €");
        label.setFont(new Font("Serif", bold ? Font.BOLD : Font.PLAIN, size));
        label.setForeground(color);
        return label;
    }

    public void mettreAJourAffichage() {
        tableModel.setRowCount(0);
        for (LignePanier ligne : panier.getLignesCommande()) {
            Tomate t = ligne.getTomate();
            int qte = ligne.getQuantité();
            float prix = t.getPrixTTC();
            float total = prix * qte;

            ImageIcon image = new ImageIcon("src/main/resources/images/Tomates40x40/" +
                    (t.getNomImage() != null ? t.getNomImage() : "default") + ".jpg");

            tableModel.addRow(new Object[]{
                new ImageIcon(image.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)),
                t.getDésignation(),
                String.format("%.2f €", prix),
                qte,
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
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final FenetreCommande fenetre;
        private final Panier panier;

        public QuantiteCellEditor(FenetreCommande fenetre, Panier panier) {
            this.fenetre = fenetre;
            this.panier = panier;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            String designation = table.getValueAt(row, 1).toString();

            LignePanier ligneTrouvée = panier.getLignesCommande().stream()
                .filter(p -> p.getTomate().getDésignation().equals(designation))
                .findFirst()
                .orElse(null);

            if (ligneTrouvée == null) {
                return new JLabel("Erreur");
            }

            Tomate tomate = ligneTrouvée.getTomate();
            int qteActuelle = ligneTrouvée.getQuantité();
            int stockDisponible = tomate.getStock() + qteActuelle;

            JComboBox<Integer> comboBox = new JComboBox<>();
            for (int i = 0; i <= stockDisponible; i++) {
                comboBox.addItem(i);
            }
            comboBox.setSelectedItem(qteActuelle);

            comboBox.addActionListener(e -> {
                int nouvelleQuantite = (int) comboBox.getSelectedItem();
                if(nouvelleQuantite == 0) {
                	panier.retirerTomate(tomate);
                } else {
                	panier.modifierQuantité(ligneTrouvée, nouvelleQuantite);
                }
                SwingUtilities.invokeLater(fenetre::mettreAJourAffichage);
                fireEditingStopped();
            });

            return comboBox;
        }

        @Override
        public Object getCellEditorValue() {
            return null; // handled via ActionListener
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new Fenetre().setVisible(true);
            } catch (JSONException e) {
                JOptionPane.showMessageDialog(null, "Erreur JSON : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
    }
}
