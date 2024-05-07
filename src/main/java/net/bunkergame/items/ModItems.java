package net.bunkergame.items;

import net.bunkergame.BunkerGame;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ModItems {

    private static Item registerItem(String id, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(BunkerGame.MOD_ID, id), item);
    }

    private static Item registerItem(String id, Item item, ItemGroup itemGroup) {
        Item returnItem = Registry.register(Registries.ITEM, new Identifier(BunkerGame.MOD_ID, id), item);
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries -> entries.add(returnItem));
        return returnItem;
    }


    public static void register() {
        BunkerGame.LOGGER.debug("Registering Mod Items");
    }

    public static class InfoCard extends Item {
        public String stat;

        public InfoCard(Settings settings) {
            super(settings);
        }

        @Override
        public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
            if (!world.isClient) {
                if (hand == Hand.MAIN_HAND) {
                    player.sendMessage(Text.literal(stat), false);
                }
            }
            return TypedActionResult.success(player.getStackInHand(hand));
        }
    }


    public static final InfoCard CHARACTER_CARD = (InfoCard) registerItem("character_card", new InfoCard(new Item.Settings()),
            ItemGroups.INGREDIENTS);
    public static final InfoCard AGE_BIO_CARD = (InfoCard) registerItem("age_bio_card", new InfoCard(new Item.Settings()),
            ItemGroups.INGREDIENTS);
    //    public static final InfoCard BIO_CARD = (InfoCard) registerItem("bio_card", new InfoCard(new Item.Settings()),
//            ItemGroups.INGREDIENTS);
//    public static final InfoCard HEIGHT_CARD = (InfoCard) registerItem("height_card", new InfoCard(new Item.Settings()),
//            ItemGroups.INGREDIENTS);
    public static final InfoCard BODY_TYPE_CARD = (InfoCard) registerItem("body_type_card", new InfoCard(new Item.Settings()),
            ItemGroups.INGREDIENTS);
    public static final InfoCard PROFESSION_CARD = (InfoCard) registerItem("profession_card", new InfoCard(new Item.Settings()),
            ItemGroups.INGREDIENTS);
    //    public static final InfoCard EXPERIENCE_CARD = (InfoCard) registerItem("experience_card", new InfoCard(new Item.Settings()),
//            ItemGroups.INGREDIENTS);
    public static final InfoCard HEALTH_CARD = (InfoCard) registerItem("health_card", new InfoCard(new Item.Settings()),
            ItemGroups.INGREDIENTS);
    public static final InfoCard PHOBIA_CARD = (InfoCard) registerItem("phobia_card", new InfoCard(new Item.Settings()),
            ItemGroups.INGREDIENTS);
    public static final InfoCard TRAIT_CARD = (InfoCard) registerItem("trait_card", new InfoCard(new Item.Settings()),
            ItemGroups.INGREDIENTS);
    public static final InfoCard HOBBY_CARD = (InfoCard) registerItem("hobby_card", new InfoCard(new Item.Settings()),
            ItemGroups.INGREDIENTS);
    public static final InfoCard INVENTORY_CARD = (InfoCard) registerItem("inventory_card", new InfoCard(new Item.Settings()),
            ItemGroups.INGREDIENTS);
    public static final InfoCard ADDITIONAL_INFORMATION_CARD = (InfoCard) registerItem("additional_information_card", new InfoCard(new Item.Settings()),
            ItemGroups.INGREDIENTS);
    public static final InfoCard SPECIAL_CONDITIONS_CARD_1 = (InfoCard) registerItem("special_conditions_card_1", new InfoCard(new Item.Settings()),
            ItemGroups.INGREDIENTS);
    public static final InfoCard SPECIAL_CONDITIONS_CARD_2 = (InfoCard) registerItem("special_conditions_card_2", new InfoCard(new Item.Settings()),
            ItemGroups.INGREDIENTS);

}
