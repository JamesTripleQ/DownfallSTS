package champ.cards;

import champ.stances.BerserkerStance;
import champ.stances.DefensiveStance;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FancyFootwork extends AbstractChampCard {

    public final static String ID = makeID("FancyFootwork");

    // intellij stuff skill, self, uncommon

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public FancyFootwork() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player.stance.ID.equals(DefensiveStance.STANCE_ID) || AbstractDungeon.player.stance.ID.equals(BerserkerStance.STANCE_ID)) {
            return super.canUse(p, m);
        }
        cantUseMessage = EXTENDED_DESCRIPTION[0];
        return false;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player.stance.ID.equals(DefensiveStance.STANCE_ID)) {
            berserkOpen();
        } else if (AbstractDungeon.player.stance.ID.equals(BerserkerStance.STANCE_ID)) {
            defenseOpen();
        }
        atb(new DrawCardAction(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}