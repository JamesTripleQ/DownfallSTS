package automaton.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FormatEncoded extends AbstractBronzeCard {

    public final static String ID = makeID("FormatEncoded");

    //stupid intellij stuff attack, all, rare

    private static final int DAMAGE = 6;
    private static final int BLOCK = 6;

    public FormatEncoded() {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CardColor.COLORLESS);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        thisEncodes();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
    }

    public void upp() {
        upgradeDamage(2);
        upgradeBlock(2);
    }
}