import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.net.URL;

public class FicheClient extends JFrame {
    // Champs de saisie (initialement vides)
    private JTextField nomField         = new JTextField(20);
    private JTextField prenomField      = new JTextField(20);
    private JTextField adresse1Field    = new JTextField(20);
    private JTextField adresse2Field    = new JTextField(20);
    private JTextField codePostalField  = new JTextField(20);
    private JTextField villeField       = new JTextField(20);
    private JTextField telephoneField   = new JTextField(20);
    private JTextField mailField        = new JTextField(20);

    // Boutons de paiement
    private JRadioButton cbRadio;
    private JRadioButton paypalRadio;
    private JRadioButton chequeRadio;

    // Boutons newsletter
    private JRadioButton ouiNewsRadio;
    private JRadioButton nonNewsRadio;

    // Boutons OK / Annuler
    private JButton okButton;
    private JButton cancelButton;

    public FicheClient() {
        initUI();
    }

    private void initUI() {
        setTitle("Ô'Tomates");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // 1) Charger l’icône et la redimensionner à 32×32
        URL iconUrl = getClass().getResource("/tomate.png");
        ImageIcon rawIcon = (iconUrl != null ? new ImageIcon(iconUrl) : null);
        ImageIcon icon32 = null;
        if (rawIcon != null) {
            Image img = rawIcon.getImage()
                               .getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            icon32 = new ImageIcon(img);
            setIconImage(img);
        }

        // 2) Construire le panneau principal
        JPanel content = new JPanel(new BorderLayout(10,10));
        content.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        setContentPane(content);

        // --- 3) En-tête ---
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        if (icon32 != null) {
            header.add(new JLabel(icon32));
        }
        JLabel titleLabel = new JLabel("Vos coordonnées");
        titleLabel.setFont(new Font("SansSerif", Font.ITALIC, 20));
        titleLabel.setForeground(new Color(0,128,0));
        header.add(titleLabel);
        content.add(header, BorderLayout.NORTH);

        // --- 4) Formulaire + sections ---
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4,4,4,4);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;

        int row = 0;
        addLabelAndField(form, c, row++, "Nom          :", nomField);
        addLabelAndField(form, c, row++, "Prénom       :", prenomField);
        addLabelAndField(form, c, row++, "Adresse 1    :", adresse1Field);
        addLabelAndField(form, c, row++, "Adresse 2    :", adresse2Field);
        addLabelAndField(form, c, row++, "Code postal  :", codePostalField);
        addLabelAndField(form, c, row++, "Ville        :", villeField);
        addLabelAndField(form, c, row++, "Téléphone    :", telephoneField);
        addLabelAndField(form, c, row++, "Mail         :", mailField);

        // Section Moyens de paiement
        JPanel paiementPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        paiementPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0,128,0), 2),
            "Moyen de paiement",
            TitledBorder.LEFT, TitledBorder.TOP,
            null, new Color(0,128,0)
        ));
        cbRadio     = new JRadioButton("Carte de crédit");
        paypalRadio = new JRadioButton("Paypal", true);
        chequeRadio = new JRadioButton("Chèque");
        ButtonGroup gpPay = new ButtonGroup();
        gpPay.add(cbRadio);
        gpPay.add(paypalRadio);
        gpPay.add(chequeRadio);
        paiementPanel.add(cbRadio);
        paiementPanel.add(paypalRadio);
        paiementPanel.add(chequeRadio);

        c.gridx = 0;
        c.gridy = row++;
        c.gridwidth = 2;
        form.add(paiementPanel, c);

        // Section Newsletter
        JPanel newsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        newsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0,128,0), 2),
            "Abonnement à notre Newsletter",
            TitledBorder.LEFT, TitledBorder.TOP,
            null, new Color(0,128,0)
        ));
        ouiNewsRadio  = new JRadioButton("Oui");
        nonNewsRadio = new JRadioButton("Non", true);
        ButtonGroup gpNews = new ButtonGroup();
        gpNews.add(ouiNewsRadio);
        gpNews.add(nonNewsRadio);
        newsPanel.add(ouiNewsRadio);
        newsPanel.add(nonNewsRadio);

        c.gridy = row++;
        form.add(newsPanel, c);

        content.add(form, BorderLayout.CENTER);

        // --- 5) Boutons OK / Annuler ---
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        okButton     = new JButton("OK");
        cancelButton = new JButton("Annuler");
        buttons.add(okButton);
        buttons.add(cancelButton);
        content.add(buttons, BorderLayout.SOUTH);

        // 6) Gestion des actions
        okButton.addActionListener(e -> onOk());
        cancelButton.addActionListener(e -> dispose());
        getRootPane().setDefaultButton(okButton);

        pack();
        setLocationRelativeTo(null);
    }

    private void onOk() {
        String nom    = nomField.getText().trim();
        String prenom = prenomField.getText().trim();
        String adresse= adresse1Field.getText().trim();
        String tel    = telephoneField.getText().trim();

        if (nom.isEmpty() || prenom.isEmpty() || adresse.isEmpty() || tel.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Veuillez remplir au minimum le Nom, le Prénom, l'Adresse 1 et le Téléphone.",
                "Champs manquants",
                JOptionPane.WARNING_MESSAGE);
        } else {
            dispose();
        }
    }

    private void addLabelAndField(JPanel panel, GridBagConstraints c,
                                  int row, String labelText, JTextField field) {
        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 1;
        panel.add(new JLabel(labelText), c);

        c.gridx = 1;
        panel.add(field, c);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FicheClient frame = new FicheClient();
            frame.setVisible(true);
        });
    }
}
