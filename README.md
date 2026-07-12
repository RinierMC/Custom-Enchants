# Custom Enchants

**Custom Enchants** adds 30 custom enchantments to your PowerNukkitX server. All enchantments work with commands, books, and inventory drag‑and‑drop. The plugin also provides an API for other developers to add their own enchantments.

---
## Features
- **30 unique enchantments** – universal, armor, weapon, and tool enchants.
- **Fully functional** – all enchants have active gameplay effects.
- **Book system** – store an enchantment in a book, then apply it by dragging onto an item.
- **Commands** – `/ce` to apply, remove, or list enchants.
- **API** – third‑party plugins can register their own custom enchantments.

---

## Installation

1. Download the latest
2. Place it in your server’s `plugins/` folder.
3. Restart the server (or use `/reload`).
4. All 30 enchantments are registered automatically.

---

## Commands

| Command | Description |
|---------|-------------|
| `/ce <enchant> <level>` | Apply the enchant directly (if holding a compatible item), or receive a book. |
| `/ce <enchant> remove` | Remove the enchantment from your held item. |
| `/ce list` | List all available enchantments with descriptions and categories. |
| `/ce help` | Show command usage. |

**Alias:** `/customenchant`

---

## How to Use Books

1. Get a book: `/ce <enchant> <level>` (hold nothing or an incompatible item).
2. Hold the book in your main hand.
3. Open your inventory and **drag the book onto** the item you want to enchant.
4. The enchantment is applied, and the book is consumed.

---

## Enchantment List (30)

| Enchantment | Category | Max Level | Description |
|-------------|----------|-----------|-------------|
| Overload | Armor (any) | 3 | +2 max health per level per piece. |
| Vitality | Armor (any) | 2 | Slowly regenerates health while worn. |
| Fortitude | Armor (any) | 3 | Reduces all incoming damage by 5% per level per piece. |
| Fire Resistance | Armor (any) | 1 | Grants Fire Resistance while worn. |
| Featherfall | Boots | 3 | Reduces fall damage by 40% per level. |
| Magnet | Boots | 3 | Pulls dropped items toward you (radius grows per level). |
| Reflect | Chestplate | 3 | Reflects melee damage back to the attacker. |
| Quickstep | Boots | 3 | Increases movement speed. |
| Leaper | Boots | 3 | Increases jump height. |
| Aquatic | Helmet | 1 | Grants Water Breathing. |
| Night Vision | Helmet | 1 | Grants Night Vision. |
| Glutton | Helmet | 2 | Increases saturation from food. |
| Haste | Universal | 2 | Gives Haste effect while held/worn. |
| Voidwalker | Universal | 3 | Chance to dodge incoming damage. |
| Soulbound | Universal | 1 | Item is not dropped on death. |
| Vampiric | Weapon (Sword/Axe) | 3 | Heals for a % of damage dealt. |
| Executioner | Weapon (Sword/Axe) | 3 | Extra damage to targets below 25% HP. |
| Berserker | Weapon (Sword/Axe) | 3 | Extra damage when below 50% HP. |
| Venom | Weapon (Sword/Axe) | 2 | Poisons the target on hit. |
| Withering | Weapon (Sword/Axe) | 2 | Withers the target on hit. |
| Cripple | Weapon (Sword/Axe) | 2 | Slows the target on hit. |
| Auto Smelt | Tool (all diggers) | 1 | Smelts ores and blocks automatically. |
| Explosive | Pickaxe | 2 | Chance to break 3×3 area. |
| Telepathy | Pickaxe | 1 | Mined items go directly to inventory. |
| Excavator | Shovel | 1 | Breaks 3×3 area of dirt/sand/gravel. |
| Lumberjack | Axe | 1 | Breaks entire tree. |
| Harvest | Hoe | 3 | Increases crop drops. |
| Scythe | Hoe | 1 | Breaks 3×3 area of crops/leaves. |
| Drilling | Pickaxe | 1 | Breaks vertical 3‑block line. |
| Tunnel | Pickaxe | 1 | Breaks 3×1 line in facing direction. |

---

## API for Developers

You can add your own enchantments from another plugin.

### Step 1: Add the dependency

Make sure `Custom_Enchants.jar` is in your classpath.

If you are using **Maven** in IntelliJ, you can install the plugin locally:

1. Download the source ZIP of `Custom_Enchants` and extract it.
2. Open a terminal in the extracted folder and run:
3. Restart IntelliJ to refresh the local repository.
4. Add the dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>org.powernukkitx</groupId>
    <artifactId>Custom_Enchants</artifactId>
    <version>1.1.0</version>
    <scope>provided</scope>
</dependency>
```

Alternatively, if you only have the JAR, add it manually via `File > Project Structure > Libraries > + > Java` and select the JAR.

### Step 2: Declare the dependency in your `plugin.yml`

```yaml
name: MyCustomEnchants
main: myplugin.Main
version: 1.0.0
depend: [Custom_Enchants]
```

### Step 3: Create your enchantment

Extend `BaseCustomEnchant` (included in the plugin):

```java
import org.powernukkitx.ce.enchantment.BaseCustomEnchant;

public class MyEnchant extends BaseCustomEnchant {
    public MyEnchant() {
        super(new Identifier("myplugin:myenchant"), "My Enchant", Rarity.RARE, EnchantmentType.ALL);
    }

    @Override
    public int getMaxLevel() { return 5; }

    @Override
    public int getMinEnchantAbility(int level) { return 10 * level; }

    @Override
    public int getMaxEnchantAbility(int level) { return 20 * level; }

    @Override
    public String getCategory() { return "Custom"; }

    @Override
    public String getDescription() { return "Does something awesome."; }
}
```

```markdown
## 🎯 Handling Custom Enchantment Effects

To make your custom enchantment actually **do something** (e.g., deal extra damage, heal the player, etc.), you need to listen to the appropriate PowerNukkitX events and check if the item has your enchantment.

The plugin provides utility methods in `EnchantUtil` for this purpose:

- `EnchantUtil.has(Item item, Enchantment enchant)` – checks if the item has the enchantment.
- `EnchantUtil.getLevel(Item item, Enchantment enchant)` – returns the level (0 if absent).

### Example: Lightning Strike on Hit

Create a listener that triggers when a player attacks an entity:

```java
import org.powernukkitx.Player;
import org.powernukkitx.entity.Entity;
import org.powernukkitx.event.EventHandler;
import org.powernukkitx.event.Listener;
import org.powernukkitx.event.entity.EntityDamageByEntityEvent;
import org.powernukkitx.item.Item;
import org.powernukkitx.ce.util.EnchantUtil;
import org.powernukkitx.ce.Main;
import myplugin.enchantments.MyLightningEnchant;

public class LightningListener implements Listener {
    private final MyLightningEnchant enchant;

    public LightningListener(MyLightningEnchant enchant) {
        this.enchant = enchant;
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;

        Item weapon = player.getInventory().getItemInMainHand();
        int level = EnchantUtil.getLevel(weapon, enchant);
        if (level <= 0) return;

        // Trigger lightning with a chance based on level
        if (Math.random() < 0.2 * level) {
            Entity target = event.getEntity();
            target.getLevel().strikeLightning(target.getPosition());
        }
    }
}
```

Then register this listener in your plugin:

```java
public void onEnable() {
    Main ce = (Main) getServer().getPluginManager().getPlugin("Custom_Enchants");
    if (ce != null) {
        MyLightningEnchant lightning = new MyLightningEnchant();
        ce.registerCustomEnchant(lightning, "lightning");

        // Register our custom listener
        getServer().getPluginManager().registerEvents(new LightningListener(lightning), this);
    }
}
```

Now your enchantment will strike lightning on hit!

---

### Performance Note

The listener is invoked **every time** the event fires (e.g., every attack). However, the first check inside the listener is:

```java
int level = EnchantUtil.getLevel(weapon, enchant);
if (level <= 0) return;
```

This check is **fast** (just reads NBT) and ensures that for items without your enchantment, the listener exits immediately with almost zero overhead. So while the listener is always called, it only executes heavy logic when the enchantment is actually present – making it efficient even on busy servers.
```

Now players can use `/ce myenchant 3` to get a book, and apply it exactly like the built‑in enchants.

---
## Notes

- **Permissions:** Only operators can use `/ce` by default.  
  To allow other players, give them the permission `customenchants.command.enchant`.
- **Compatibility:** Built for PowerNukkitX 1.19+.
- **No anvil support:** Books are applied via inventory drag‑and‑drop or commands – anvils are not supported because they only handle vanilla enchants.


---

## Support

For issues, suggestions, or API questions, open an issue on the plugin’s repository or contact me.
