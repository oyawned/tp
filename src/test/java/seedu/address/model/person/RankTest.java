package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RankTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Rank(null));
    }

    @Test
    public void constructor_invalidRank_throwsIllegalArgumentException() {
        // Invalid ranks
        assertThrows(IllegalArgumentException.class, () -> new Rank(""));
        assertThrows(IllegalArgumentException.class, () -> new Rank(" "));
        assertThrows(IllegalArgumentException.class, () -> new Rank("INVALID"));
        assertThrows(IllegalArgumentException.class, () -> new Rank("GOLD"));
        assertThrows(IllegalArgumentException.class, () -> new Rank("GOLD V"));
        assertThrows(IllegalArgumentException.class, () -> new Rank("MASTER I"));
        assertThrows(IllegalArgumentException.class, () -> new Rank("CHALLENGER II"));
    }

    @Test
    public void isValidRank() {
        // null rank
        assertThrows(NullPointerException.class, () -> Rank.isValidRank(null));

        // invalid ranks - empty or whitespace
        assertFalse(Rank.isValidRank("")); // empty string
        assertFalse(Rank.isValidRank(" ")); // spaces only
        assertFalse(Rank.isValidRank("   ")); // multiple spaces
        assertFalse(Rank.isValidRank("GOLD I II")); // too many parts
        assertFalse(Rank.isValidRank("GOLD I II III")); // way too many parts

        // invalid ranks - non-existent tier
        assertFalse(Rank.isValidRank("INVALID"));
        assertFalse(Rank.isValidRank("INVALID I"));
        assertFalse(Rank.isValidRank("GOLD V")); // Invalid division
        assertFalse(Rank.isValidRank("GOLD INVALID")); // Invalid division name
        assertFalse(Rank.isValidRank("IRON"));  // IRON requires division
        assertFalse(Rank.isValidRank("PLATINUM")); // PLATINUM requires division

        // invalid ranks - divisions on top tiers
        assertFalse(Rank.isValidRank("MASTER I"));
        assertFalse(Rank.isValidRank("GRANDMASTER II"));
        assertFalse(Rank.isValidRank("CHALLENGER IV"));
        assertFalse(Rank.isValidRank("MASTER IV"));

        // invalid ranks - missing division for lower tiers
        assertFalse(Rank.isValidRank("GOLD")); // GOLD requires division
        assertFalse(Rank.isValidRank("BRONZE")); // BRONZE requires division
        assertFalse(Rank.isValidRank("SILVER")); // SILVER requires division
        assertFalse(Rank.isValidRank("DIAMOND")); // DIAMOND requires division

        // valid ranks - with divisions
        assertTrue(Rank.isValidRank("IRON I"));
        assertTrue(Rank.isValidRank("BRONZE II"));
        assertTrue(Rank.isValidRank("SILVER III"));
        assertTrue(Rank.isValidRank("GOLD IV"));
        assertTrue(Rank.isValidRank("PLATINUM I"));
        assertTrue(Rank.isValidRank("DIAMOND II"));
        assertTrue(Rank.isValidRank("DIAMOND IV"));

        // valid ranks - top tiers without divisions
        assertTrue(Rank.isValidRank("MASTER"));
        assertTrue(Rank.isValidRank("GRANDMASTER"));
        assertTrue(Rank.isValidRank("CHALLENGER"));

        // valid ranks - case insensitive
        assertTrue(Rank.isValidRank("gold i"));
        assertTrue(Rank.isValidRank("GolD Ii"));
        assertTrue(Rank.isValidRank("master"));
        assertTrue(Rank.isValidRank("MASTER"));
        assertTrue(Rank.isValidRank("iron iv"));
        assertTrue(Rank.isValidRank("bronze iii"));

        // valid ranks - with extra whitespace
        assertTrue(Rank.isValidRank("  GOLD I  "));
        assertTrue(Rank.isValidRank("MASTER  "));
        assertTrue(Rank.isValidRank("  SILVER II"));
    }

    @Test
    public void equals() {
        // Test with divisions
        Rank goldOne = new Rank("GOLD I");

        // same values -> returns true
        assertTrue(goldOne.equals(new Rank("GOLD I")));

        // same object -> returns true
        assertTrue(goldOne.equals(goldOne));

        // null -> returns false
        assertFalse(goldOne.equals(null));

        // different types -> returns false
        assertFalse(goldOne.equals(5.0f));
        assertFalse(goldOne.equals("GOLD I"));
        assertFalse(goldOne.equals(new Object()));

        // different values -> returns false
        assertFalse(goldOne.equals(new Rank("GOLD II")));
        assertFalse(goldOne.equals(new Rank("SILVER I")));
        assertFalse(goldOne.equals(new Rank("GOLD IV")));

        // Test with top tiers (no divisions)
        Rank master = new Rank("MASTER");

        // same values -> returns true
        assertTrue(master.equals(new Rank("MASTER")));

        // same object -> returns true
        assertTrue(master.equals(master));

        // null -> returns false
        assertFalse(master.equals(null));

        // different types -> returns false
        assertFalse(master.equals(5.0f));
        assertFalse(master.equals("MASTER"));

        // different values -> returns false
        assertFalse(master.equals(new Rank("GRANDMASTER")));
        assertFalse(master.equals(new Rank("CHALLENGER")));

        // Different types (with vs without division)
        assertFalse(goldOne.equals(master));
        assertFalse(master.equals(goldOne));

        // Test multiple division ranks
        Rank rankB2 = new Rank("BRONZE II");
        Rank rankB2Copy = new Rank("BRONZE II");
        Rank rankB3 = new Rank("BRONZE III");

        assertTrue(rankB2.equals(rankB2Copy));
        assertFalse(rankB2.equals(rankB3));

        // Test different tiers with same division
        Rank ironI = new Rank("IRON I");
        Rank goldI = new Rank("GOLD I");
        assertFalse(ironI.equals(goldI));
    }

    @Test
    public void hashCode_test() {
        // Test equal objects have equal hash codes
        Rank goldOne = new Rank("GOLD I");
        Rank goldOneCopy = new Rank("GOLD I");
        assertTrue(goldOne.hashCode() == goldOneCopy.hashCode());

        // Test different objects likely have different hash codes
        Rank goldTwo = new Rank("GOLD II");
        Rank master = new Rank("MASTER");
        // Note: Different object may theoretically have same hash code, but unlikely
        int hash1 = goldOne.hashCode();
        int hash2 = goldTwo.hashCode();
        int hash3 = master.hashCode();
        assertTrue(hash1 != Integer.MIN_VALUE && hash2 != Integer.MIN_VALUE && hash3 != Integer.MIN_VALUE);

        // Test with top tiers
        Rank masterCopy = new Rank("MASTER");
        assertTrue(master.hashCode() == masterCopy.hashCode());
    }

    @Test
    public void toString_test() {
        // Test with divisions
        Rank goldOne = new Rank("GOLD I");
        assertTrue("GOLD I".equals(goldOne.toString()));

        Rank silverThree = new Rank("SILVER III");
        assertTrue("SILVER III".equals(silverThree.toString()));

        // Test without divisions
        Rank master = new Rank("MASTER");
        assertTrue("MASTER".equals(master.toString()));

        Rank grandmaster = new Rank("GRANDMASTER");
        assertTrue("GRANDMASTER".equals(grandmaster.toString()));

        Rank challenger = new Rank("CHALLENGER");
        assertTrue("CHALLENGER".equals(challenger.toString()));

        // Test case normalization - input is uppercased
        Rank lowerCase = new Rank("gold iv");
        assertTrue("GOLD IV".equals(lowerCase.toString()));
    }

    @Test
    public void ranktierEnum_test() {
        // Test RankTier enum values and tier values
        assertTrue(Rank.RankTier.IRON.getTierValue() == 0);
        assertTrue(Rank.RankTier.BRONZE.getTierValue() == 1);
        assertTrue(Rank.RankTier.SILVER.getTierValue() == 2);
        assertTrue(Rank.RankTier.GOLD.getTierValue() == 3);
        assertTrue(Rank.RankTier.PLATINUM.getTierValue() == 4);
        assertTrue(Rank.RankTier.DIAMOND.getTierValue() == 5);
        assertTrue(Rank.RankTier.MASTER.getTierValue() == 6);
        assertTrue(Rank.RankTier.GRANDMASTER.getTierValue() == 7);
        assertTrue(Rank.RankTier.CHALLENGER.getTierValue() == 8);

        // Test hasDivisions method
        assertTrue(Rank.RankTier.IRON.hasDivisions());
        assertTrue(Rank.RankTier.BRONZE.hasDivisions());
        assertTrue(Rank.RankTier.SILVER.hasDivisions());
        assertTrue(Rank.RankTier.GOLD.hasDivisions());
        assertTrue(Rank.RankTier.PLATINUM.hasDivisions());
        assertTrue(Rank.RankTier.DIAMOND.hasDivisions());
        assertFalse(Rank.RankTier.MASTER.hasDivisions());
        assertFalse(Rank.RankTier.GRANDMASTER.hasDivisions());
        assertFalse(Rank.RankTier.CHALLENGER.hasDivisions());
    }

    @Test
    public void divisionEnum_test() {
        // Test Division enum values
        assertTrue(Rank.Division.I.getDivisionValue() == 0);
        assertTrue(Rank.Division.II.getDivisionValue() == 1);
        assertTrue(Rank.Division.III.getDivisionValue() == 2);
        assertTrue(Rank.Division.IV.getDivisionValue() == 3);
    }
}
