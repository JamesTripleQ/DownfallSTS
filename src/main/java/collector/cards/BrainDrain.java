package collector.cards;

import collector.CollectorCollection;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightBulbEffect;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.makeInHand;

public class BrainDrain extends AbstractCollectorCard {
    public final static String ID = makeID(BrainDrain.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , , 

    public BrainDrain() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(new LightBulbEffect(m.hb)));
        } else {
            this.addToBot(new VFXAction(new LightBulbEffect(m.hb), 0.2F));
        }
        AbstractCard q = CollectorCollection.getCollectedCard(m);
        if (upgraded)
            q.upgrade();
        makeInHand(q);
    }

    public void upp() {
        uDesc();
    }
}