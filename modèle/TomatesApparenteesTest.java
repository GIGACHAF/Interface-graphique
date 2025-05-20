package modèle;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class TomatesApparenteesTest {

    private static Tomates tomates;

    @BeforeClass
    public static void setUp() {
        tomates = OutilsBaseDonneesTomates.générationBaseDeTomates(
                "src/main/resources/data/tomates.json");
    }

    @Test
    public void testAjoutTomateDéjàExistante() {
        Tomate brandywine = tomates.getTomate("Tomate Brandywine");
        brandywine
                .addTomateApparentée(brandywine.getTomatesApparentées().get(0));
        assertEquals(4, brandywine.getTomatesApparentées().size());
    }

    @Test
    public void testAjoutTomateApparentéeAElleMême() {
        Tomate brandywine = tomates.getTomate("Tomate Brandywine");
        brandywine.addTomateApparentée(tomates.getTomate("Tomate Brandywine"));
        assertEquals(4, brandywine.getTomatesApparentées().size());
    }

    @Test
    public void testAjoutTomateNull() {
        Tomate brandywine = tomates.getTomate("Tomate Brandywine");
        brandywine.addTomateApparentée(null);
        assertEquals(4, brandywine.getTomatesApparentées().size());
    }

    @Test
    public void testAjoutTomateNonDeMêmeType() {
        Tomate brandywine = tomates.getTomate("Tomate Brandywine");
        brandywine.addTomateApparentée(
                tomates.getTomate("Tomate Orange Bourgois"));
        assertEquals(4, brandywine.getTomatesApparentées().size());
    }

}
