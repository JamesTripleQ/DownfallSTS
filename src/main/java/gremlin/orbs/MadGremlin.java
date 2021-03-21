package gremlin.orbs;

import com.megacrit.cardcrawl.orbs.AbstractOrb;
import gremlin.powers.GremlinPower;
import gremlin.powers.MadGremlinPower;

public class MadGremlin extends GremlinStandby{

    public MadGremlin(int hp) {
        super(hp,"Gremlin:MadGremlin", "angry", "idle", 25);
    }

    @Override
    public void updateDescription() {
        this.description = this.descriptions[0] + 2 + this.descriptions[1];
    }

    @Override
    public AbstractOrb makeCopy() {
        return new MadGremlin(hp);
    }

    @Override
    public void playChannelSFX() {

    }

    @Override
    public GremlinPower getPower() {
        return new MadGremlinPower(2);
    }
}
