package modèle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class TomatesTest {

    private static Tomates tomates;

    @BeforeClass
    public static void setUp() throws Exception {
        tomates = OutilsBaseDonneesTomates.générationBaseDeTomates(
                "src/main/resources/data/tomates.json");
    }

    @Test
    public void testRecuperationTomates() {
        assertEquals(63, tomates.getTomates().size());
    }

    @Test
    public void testRecuperationTomatesPremiereEtDerniere() {
        assertEquals("Tomate Russian Persimmon",
                tomates.getTomates().get(0).getDésignation());
        assertEquals(15, tomates.getTomates().get(0).getNbGrainesParSachet());
        assertEquals(4.95F, tomates.getTomates().get(0).getPrixTTC(), 0F);
        assertEquals(10, tomates.getTomates().get(0).getStock());
        assertEquals("Tomate Grosse Hâtive d'Orléans",
                tomates.getTomates().get(62).getDésignation());
        assertEquals(Couleur.ROUGE, tomates.getTomates().get(62).getCouleur());
        assertEquals("Variété Locale de la Région Centre-Val de Loire",
                tomates.getTomates().get(62).getSousTitre());
    }

    @Test
    public void testGetterTomateParDésignation() {
        assertNotNull(tomates.getTomate("Tomate Brandywine"));
        assertNull(tomates.getTomate("Tomate Brandywine goes to Hollywood"));
        assertEquals("Tomate Brandywine",
                tomates.getTomate("Tomate Brandywine").getDésignation());
    }

    @Test
    public void testNbTomatesApparentées() {
        for (Tomate tomate : tomates.getTomates()) {
            assertEquals(4, tomate.getTomatesApparentées().size());
        }
    }

    @Test
    public void testTomatesApparentéesDeMêmeType() {
        for (Tomate tomate : tomates.getTomates()) {
            for (Tomate apparentée : tomate.getTomatesApparentées()) {
                assertEquals(tomate.getType(), apparentée.getType());
            }
        }
    }

    @Test
    public void testFiltreTomatesBasiques() {
        assertEquals(47, tomates.tomatesDeType(TypeTomate.TOMATES).size());
    }

    @Test
    public void testFiltreTomatesCerises() {
        assertEquals(16,
                tomates.tomatesDeType(TypeTomate.TOMATES_CERISES).size());
    }

    @Test
    public void testFiltreParCouleur() {
        for (int i = 0; i < Couleur.values().length; i++) {
            List<Tomate> tomatesCouleur = tomates
                    .tomatesDeCouleur(Couleur.values()[i]);
            assertTrue(tomatesCouleur.size() > 0);
            for (Tomate tomate : tomatesCouleur) {
                assertEquals(Couleur.values()[i], tomate.getCouleur());
            }
        }
    }

    @Test
    public void testFiltresTomatesBasiquesParCouleur() {
        for (int i = 0; i < Couleur.values().length; i++) {
            List<Tomate> tomatesCouleur = tomates.tomatesDetypeDeCouleur(
                    TypeTomate.TOMATES, Couleur.values()[i]);
            assertTrue(tomatesCouleur.size() > 0);
            for (Tomate tomate : tomatesCouleur) {
                assertEquals(TypeTomate.TOMATES, tomate.getType());
                assertEquals(Couleur.values()[i], tomate.getCouleur());
            }
        }
    }

    @Test
    public void testFiltresTomatesCerisesParCouleur() {
        for (int i = 0; i < Couleur.values().length; i++) {
            List<Tomate> tomatesCerisesCouleur = tomates.tomatesDetypeDeCouleur(
                    TypeTomate.TOMATES_CERISES, Couleur.values()[i]);
            if (!(Couleur.values()[i] == Couleur.MULTICOLORE)
                    && !(Couleur.values()[i] == Couleur.BLEU)) {
                assertTrue(tomatesCerisesCouleur.size() > 0);
                for (Tomate tomate : tomatesCerisesCouleur) {
                    assertEquals(TypeTomate.TOMATES_CERISES, tomate.getType());
                    assertEquals(Couleur.values()[i], tomate.getCouleur());
                }
            }
        }
    }
}
