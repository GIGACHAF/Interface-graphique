package ihm;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import java.text.DecimalFormat;

public class FactureFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private double fraisPort;

    public FactureFrame(String nom,
                        String prenom,
                        String adresse1,
                        String adresse2,
                        String codePostal,
                        String ville,
                        String telephone,
                        String mail,
                        DefaultTableModel tableModel,
                        double fraisPort) {
        super("Ô'Tomates - Votre facture");
        this.tableModel = tableModel;
        this.fraisPort   = fraisPort;
        initUI(nom, prenom, adresse1, adresse2, codePostal, ville, telephone, mail);
    }

    private void initUI(String nom,
                        String prenom,
                        String adresse1,
                        String adresse2,
                        String codePostal,
                        String ville,
                        String telephone,
                        String mail) {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        URL iconUrl = getClass().getResource("/tomate.png");
        if (iconUrl != null) {
            ImageIcon rawIcon = new ImageIcon(iconUrl);
            Image img32 = rawIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            setIconImage(img32);
        }

        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(content);

        // ────── NORTH ───────────────────────────────────────────────────────
        JPanel northWrapper = new JPanel();
        northWrapper.setLayout(new BoxLayout(northWrapper, BoxLayout.Y_AXIS));

        // 1) Logo + "Votre facture" + logo
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        if (iconUrl != null) {
            ImageIcon rawIcon = new ImageIcon(iconUrl);
            Image img32 = rawIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            header.add(new JLabel(new ImageIcon(img32)));
        }
        JLabel titleLabel = new JLabel("Votre facture");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 128, 0));
        header.add(titleLabel);

        if (iconUrl != null) {
            ImageIcon rawIcon = new ImageIcon(iconUrl);
            Image img32 = rawIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            header.add(Box.createHorizontalStrut(6));
            header.add(new JLabel(new ImageIcon(img32)));
        }
        northWrapper.add(header);

        // 2) Panneau des coordonnées client
        JPanel clientPanel = new JPanel();
        clientPanel.setLayout(new BoxLayout(clientPanel, BoxLayout.Y_AXIS));
        clientPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 128, 0), 1),
                "Coordonnées du client",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                new Font("SansSerif", Font.PLAIN, 12),
                new Color(0, 128, 0)
        ));
        clientPanel.setBackground(Color.WHITE);
        clientPanel.setOpaque(true);
        clientPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        clientPanel.add(createInfoLabel(nom + " " + prenom));
        clientPanel.add(createInfoLabel(adresse1));
        if (!adresse2.trim().isEmpty()) {
            clientPanel.add(createInfoLabel(adresse2));
        }
        clientPanel.add(createInfoLabel(codePostal + " " + ville));
        clientPanel.add(createInfoLabel("Tél : "   + telephone));
        clientPanel.add(createInfoLabel("Mail : "  + mail));

        clientPanel.setBorder(BorderFactory.createCompoundBorder(
                clientPanel.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        northWrapper.add(Box.createRigidArea(new Dimension(0, 8)));
        northWrapper.add(clientPanel);

        // 3) "Merci de votre visite !" encadré
        JLabel merciLabel = new JLabel("Merci de votre visite !");
        merciLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
        merciLabel.setForeground(new Color(0, 128, 0));
        merciLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 0)));
        merciLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        merciLabel.setOpaque(true);
        merciLabel.setBackground(Color.WHITE);
        merciLabel.setPreferredSize(new Dimension(200, 24));
        merciLabel.setMaximumSize(new Dimension(Short.MAX_VALUE, 24));
        merciLabel.setHorizontalAlignment(SwingConstants.CENTER);

        northWrapper.add(Box.createRigidArea(new Dimension(0, 8)));
        northWrapper.add(merciLabel);

        content.add(northWrapper, BorderLayout.NORTH);

        // ────── CENTER ──────────────────────────────────────────────────────
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setRowHeight(24);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        scroll.setPreferredSize(new Dimension(500, 200));
        content.add(scroll, BorderLayout.CENTER);

        // ────── SOUTH ───────────────────────────────────────────────────────
        JPanel southPanel = new JPanel(new BorderLayout());

        // Totaux (colonne gauche)
        JPanel totalsPanel = new JPanel();
        totalsPanel.setLayout(new BoxLayout(totalsPanel, BoxLayout.Y_AXIS));
        totalsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        double totalCommande = calculerTotalCommande();
        DecimalFormat df = new DecimalFormat("#0.00");

        JLabel totalCommandeLabel = new JLabel(
                "TOTAL TTC COMMANDE :  " + df.format(totalCommande) + " €"
        );
        totalCommandeLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        totalCommandeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel fraisPortLabel = new JLabel(
                "FORFAIT FRAIS DE PORT :  " + df.format(fraisPort) + " €"
        );
        fraisPortLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        fraisPortLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        totalsPanel.add(totalCommandeLabel);
        totalsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        totalsPanel.add(fraisPortLabel);

        southPanel.add(totalsPanel, BorderLayout.WEST);

        // Boutons (colonne droite)
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        JButton imprimerBtn = new JButton("Imprimer");
        JButton quitterBtn  = new JButton("Quitter");
        buttonsPanel.add(imprimerBtn);
        buttonsPanel.add(quitterBtn);
        southPanel.add(buttonsPanel, BorderLayout.EAST);

        // Actions des boutons
        imprimerBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                FactureFrame.this,
                "Fonction « Imprimer » non implémentée pour l’instant.",
                "À implémenter",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
        quitterBtn.addActionListener(e -> dispose());

        content.add(southPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private JLabel createInfoLabel(String texte) {
        JLabel lbl = new JLabel(texte);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private double calculerTotalCommande() {
        double somme = 0.0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Object valeur = tableModel.getValueAt(i, 3);
            if (valeur instanceof Number) {
                somme += ((Number) valeur).doubleValue();
            } else if (valeur instanceof String) {
                try {
                    somme += Double.parseDouble(((String) valeur).replace(",", "."));
                } catch (NumberFormatException ex) {
                    // On ignore les chaînes non convertibles
                }
            }
        }
        return somme;
    }
}
