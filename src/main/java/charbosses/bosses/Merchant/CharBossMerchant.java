package charbosses.bosses.Merchant;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Ironclad.ArchetypeAct1PerfectedStrike;
import charbosses.bosses.Ironclad.ArchetypeAct2Strength;
import charbosses.bosses.Ironclad.ArchetypeAct3Block;
import charbosses.core.EnemyEnergyManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbRed;
import com.megacrit.cardcrawl.vfx.BobEffect;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import evilWithin.EvilWithinMod;
import evilWithin.monsters.FleeingMerchant;
import evilWithin.vfx.NeowBossRezEffect;

public class CharBossMerchant extends AbstractCharBoss {

    private BobEffect bob = new BobEffect();
    private Color tempColor = new Color();
    private Color glowColor = new Color(2F,2F,2F, 2F);

    private TextureAtlas atlasGlow;
    private Skeleton skeletonGlow;
    private AnimationState stateGlow;
    private AnimationStateData stateDataGlow;

    private float curveAlpha = 1F;
    private boolean curveUp = false;
    private float curveDuration = 1F;

    private NeowBossRezEffect rezVFX;
    private float rezTimer;

    public CharBossMerchant() {
        super("Merchant", "EvilWithin:Merchant", 500, -4.0f, -16.0f, 220.0f, 290.0f, null, 0.0f, -20.0f, PlayerClass.IRONCLAD);

        if (EvilWithinMod.tempAscensionHack){
            EvilWithinMod.tempAscensionHack = false;
            AbstractDungeon.ascensionLevel = EvilWithinMod.tempAscensionOriginalValue;
            EvilWithinMod.tempAscensionOriginalValue = 0;
        }

        this.energyOrb = new EnergyOrbRed();
        this.energy = new EnemyEnergyManager(3);

        drawX = 1260.0F * Settings.scale;
        drawY = 460.0F * Settings.scale;

        loadAnimation(EvilWithinMod.assetPath("images/monsters/merchant/noShadow/skeleton.atlas"), EvilWithinMod.assetPath("images/monsters/merchant/noShadow/skeleton.json"), 1.0F);
        AnimationState.TrackEntry e = state.setAnimation(0, "idle", true);

        float time = e.getEndTime() * MathUtils.random();
        e.setTime(time);
        e.setTimeScale(1.0F);

        this.energyString = "[R]";

        initGlowMesh(time);


        this.tint.color = new Color(.5F, .5F, 1F, 0F);
        this.tint.changeColor(Color.WHITE.cpy(), 2F);
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.CYAN, true));
        AbstractDungeon.effectsQueue.add(new IntenseZoomEffect(this.hb.cX, this.hb.cY, false));
        rezVFX = new NeowBossRezEffect(this.hb.cX, this.hb.cY);
        this.rezTimer = 2F;
    }

    public void initGlowMesh(float time) {
        float glowscale = .98F;
        this.atlasGlow = new TextureAtlas(Gdx.files.internal(EvilWithinMod.assetPath("images/monsters/merchant/noShadow/skeletonGlow.atlas")));
        SkeletonJson json = new SkeletonJson(this.atlasGlow);
        if (CardCrawlGame.dungeon != null && AbstractDungeon.player != null) {
            if (AbstractDungeon.player.hasRelic("PreservedInsect") && !this.isPlayer && AbstractDungeon.getCurrRoom().eliteTrigger) {
                glowscale += 0.3F;
            }

            if (ModHelper.isModEnabled("MonsterHunter") && !this.isPlayer) {
                glowscale -= 0.3F;
            }
        }

        json.setScale(Settings.scale / glowscale);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(EvilWithinMod.assetPath("images/monsters/merchant/noShadow/skeleton.json")));
        this.skeletonGlow = new Skeleton(skeletonData);
        this.skeletonGlow.setColor(glowColor);
        this.stateDataGlow = new AnimationStateData(skeletonData);
        this.stateGlow = new AnimationState(this.stateDataGlow);

        AnimationState.TrackEntry e = stateGlow.setAnimation(0, "idle", true);
        e.setTime(time);
        e.setTimeScale(1.0F);
    }

    @Override
    public void generateDeck() {
        //ArrayList<AbstractBossDeckArchetype> archetypes = new ArrayList<AbstractBossDeckArchetype>();
        AbstractBossDeckArchetype archetype = new ArchetypeAct3MerchantBoss();

        archetype.initialize();

    }

    public void renderGlow(SpriteBatch sb){

        if (!this.curveUp){
            this.curveDuration -= Gdx.graphics.getDeltaTime();
            if (this.curveDuration < 0.0F) {
                this.curveUp = true;
            }
        } else {
            this.curveDuration += Gdx.graphics.getDeltaTime();
            if (this.curveDuration > 1.0F) {
                this.curveUp = false;
            }
        }


        this.curveAlpha = Interpolation.pow2.apply(0.5F,1F, this.curveDuration / 1F);

        this.skeletonGlow.setColor(new Color(this.glowColor.r * this.curveAlpha, this.glowColor.g * this.curveAlpha, this.glowColor.b * this.curveAlpha, this.glowColor.a * this.curveAlpha));
        //this.skeletonGlow.setColor(glowColor);

        this.stateGlow.update(Gdx.graphics.getDeltaTime());
        this.stateGlow.apply(this.skeletonGlow);
        this.skeletonGlow.updateWorldTransform();
        this.skeletonGlow.setPosition(this.drawX + this.animX, this.drawY + this.animY);
        this.skeletonGlow.setFlip(this.flipHorizontal, this.flipVertical);
        sb.end();
        CardCrawlGame.psb.begin();
        sr.draw(CardCrawlGame.psb, this.skeletonGlow);
        CardCrawlGame.psb.end();
        sb.begin();
        sb.setBlendFunction(770, 771);
    }

    @Override
    public void render(SpriteBatch sb) {
        this.animY = this.bob.y;
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.MERCHANT_RUG_IMG, FleeingMerchant.DRAW_X, FleeingMerchant.DRAW_Y, 512 * Settings.scale, 512 * Settings.scale);
        //sb.draw(ImageMaster.loadImage(EvilWithinMod.assetPath("images/monsters/merchant/onlyShadow/skeleton.png")), FleeingMerchant.DRAW_X, FleeingMerchant.DRAW_Y, 201F * Settings.scale, 51F * Settings.scale);
        renderGlow(sb);
        super.render(sb);
    }

    @Override
    public void update() {
        this.bob.update();
        super.update();
        if (this.rezTimer <= 0F){
            this.rezVFX.end();
        } else {
            this.rezTimer -= Gdx.graphics.getDeltaTime();
        }
    }
}

