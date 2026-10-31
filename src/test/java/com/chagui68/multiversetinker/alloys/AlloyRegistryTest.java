package com.chagui68.multiversetinker.alloys;

import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.tools.VanillaCatalyst;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.bukkit.Material;

import static org.junit.jupiter.api.Assertions.*;

class AlloyRegistryTest {

    private final AlloyRegistry registry = new AlloyRegistry();

    @Test
    @DisplayName("Must register all 16 predefined alloy recipes")
    void testPredefinedAlloysCount() {
        assertEquals(16, registry.getAllAlloys().size(), "There should be exactly 16 default alloys");
    }

    @Test
    @DisplayName("Recipe matching must be commutative (order independent)")
    void testAlloyMatchingCommutative() {
        TinkerAlloy bronze1 = registry.findAlloy("mvtink_copper", "mvtink_tin");
        TinkerAlloy bronze2 = registry.findAlloy("mvtink_tin", "mvtink_copper");

        assertNotNull(bronze1);
        assertNotNull(bronze2);
        assertEquals(bronze1.id(), bronze2.id());
        assertEquals("mvtink_bronze", bronze1.id());
        assertEquals("Bronze", bronze1.name());

        TinkerAlloy manyullyn = registry.findAlloy("mvtink_ardite", "mvtink_cobalt");
        assertNotNull(manyullyn);
        assertEquals("mvtink_manyullyn", manyullyn.id());
    }

    @Test
    @DisplayName("All alloys must have non-null stats and descriptions")
    void testAlloyProperties() {
        for (TinkerAlloy alloy : registry.getAllAlloys()) {
            assertTrue(alloy.id().startsWith("mvtink_"));
            assertNotNull(alloy.name());
            assertNotNull(alloy.colorHex());
            assertNotNull(alloy.traitName());
            assertNotNull(alloy.traitDescription());
            assertTrue(alloy.durabilityBonus() > 0);
            assertTrue(alloy.miningSpeed() > 0);
            assertTrue(alloy.attackDamageBonus() > 0);
        }
    }

    @Test
    @DisplayName("Non-stackable catalysts use a stackable ingot carrier")
    void catalystIngotCarriersSupportForgeBatches() {
        MaterialRegistry materials = new MaterialRegistry();
        registry.registerCatalystMaterials(materials);

        assertEquals(Material.GOLD_INGOT,
                materials.get(VanillaCatalyst.TOTEM_OF_UNDYING.getMaterialId()).getProcessedVanillaMaterial());
        assertEquals(Material.NETHER_STAR,
                materials.get(VanillaCatalyst.NETHER_STAR.getMaterialId()).getProcessedVanillaMaterial());
    }
}
