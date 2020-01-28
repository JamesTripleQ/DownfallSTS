package sneckomod.cards.unknowns;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.function.Predicate;

public class UnknownUncommonAttack extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownUncommonAttack");

    public UnknownUncommonAttack() {
        super(ID, CardType.ATTACK, CardRarity.UNCOMMON);
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.rarity == this.rarity && c.type == this.type;
    }
}
